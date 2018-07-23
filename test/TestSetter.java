package test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import exceptions.MatrixException;
import logic.GeneralState;
import logic.Location;
import logic.Matrix;
import logic.Searchable;
import logic.Searcher;
import logic.State;
import logic.Searchable.StateComperator;
import logic.algo.BFS;
import logic.algo.DFS;
import logic.algo.HC_DFS;
import logic.algo.HC_Heap;
import managment.CacheManager;
import managment.FileCacheManager;
import managment.Solver;
import pipeGame.PGS;
import pipeGame.PipeSearchable;
import pipeGame.PipeSimpleClientHandler;
import pipeGame.PipeSimpleServer;
import pipeGame.PipeState;
import server.ClientHandler;
import server.Server;

public class TestSetter {
	
	public static void setClasses(DesignTest dt){
		
		// set the server's Interface, e.g., "Server.class"
		// don't forget to import the correct package e.g., "import server.Server"
		dt.setServerInteface(Server.class);
		// now fill in the other types according to their names
		// server's implementation
		dt.setServerClass(PipeSimpleServer.class);
		// client handler interface
		dt.setClientHandlerInterface(ClientHandler.class);
		// client handler class
		dt.setClientHandlerClass(PipeSimpleClientHandler.class);
		// cache manager interface
		dt.setCacheManagerInterface(CacheManager.class);
		// cache manager class
		dt.setCacheManagerClass(FileCacheManager.class);
		// solver interface
		dt.setSolverInterface(Solver.class);
		// solver class
		dt.setSolverClass(PGS.class);
		// searchable interface
		dt.setSearchableInterface(Searchable.class);
		// searcher interface
		dt.setSearcherInterface(Searcher.class);
		// your searchable pipe game class
		dt.setPipeGameClass(PipeSearchable.class);
		// your Best First Search implementation
		dt.setBestFSClass(BFS.class);
	}
	
	// run your server here
	static Server s;
	public static void runServer(int port){
		s=new PipeSimpleServer(port);
		s.start(new PipeSimpleClientHandler());
	}
	// stop your server here
	public static void stopServer(){
		s.stop();
	}
	
	/* ------------- Best First Search Test --------------
	 * You are given a Maze
	 * Create a new Searchable from the Maze
	 * Solve the Maze
	 * and return a list of moves from {UP,DOWN,RIGHT,LEFT}
	 *  
	 */
	public static List<String> solveMaze(Maze m){
		Grid end = m.getExit();
		System.out.println("This is the end: " + end);
		class GenericMazeState extends GeneralState
		{
			Grid g;
			
			public GenericMazeState(GenericMazeState s,Grid g){ // CTOR for first state
				this.g = new Grid(g.row, g.row);
				this.location = new Location(s.getGrid().row,s.getGrid().col);
				fathers = new HashSet<String>(s.fathers);
				setCost(genCost());
				//this.setCameFrom(s);
			}
			
			public GenericMazeState(Grid g)
			{
				this.g = g;
				this.location = new Location(g.row,g.col);
				this.cost = genCost();
				fathers = new HashSet<String>();

			}
			
			public Grid getGrid()
			{
				return this.g;
			}
			
			@Override
			public ArrayList<State> getNeighbors() {
				List<Grid> list = m.getPossibleMoves(g);
				ArrayList<State> result = new ArrayList<State>();
				for(Grid g : list)
				{
					//System.out.println(g.toString());
					Location loc = new Location(g.row,g.col);
					//if(!fathers.contains(g.toString()))
					if(!fathers.contains(loc.toString()))
						result.add(new GenericMazeState(g));
					GenericMazeState near = endIsNear();
					if(near != null)
						result.add(near);
					//System.out.println(!fathers.contains(g.toString()));
				}
				return result;
			}
			
			public GenericMazeState endIsNear()
			{
				int ei,ej,gi,gj;
				ei = end.row;
				ej = end.col;
				gi = this.g.row;
				gj = this.g.col;
				if(((gi == ei-1) && (gj == ej))||((gi == ei-1) && (gj == ej+1))||((gi == ei-1) && (gj == ej-1))||((gi == ei) && (gj == ej+1))||((gi == ei) && (gj == ej-1))||((gi == ei+1) && (gj == ej-1))||((gi == ei+1) && (gj == ej))||((gi == ei+1) && (gj == ej+1)))
					return new GenericMazeState(end);
				
				return null;
			}
			
			@Override
			public int genCost() {
				return Math.abs(end.col-g.col + end.row-g.row);
			}
			
			@Override
			public String toString() {
				return location.toString();
			}
		}
		
		class GenericMazeSearchable implements Searchable {
			private State firstState;
			private State goalState;

			protected StateComperator comp;
			
			public GenericMazeSearchable() 
			{
				this.firstState = this.getInitialState();
				this.goalState = this.getGoalState();
				comp = new StateComperator();
			}
			
			@Override
			public State getInitialState() {
				return new GenericMazeState(m.getEntrance());
			}

			@Override
			public State getGoalState() {
				return new GenericMazeState(m.getExit());
			}

			@Override
			public ArrayList<State> getAllPossibleStates(State s) {
				return s.getNeighbors();
			}

			@Override
			public boolean isGoal(State v) {
				//System.out.println("----------------isGoal---------------------");
				//System.out.println(this.getGoalState().toString());
				//System.out.println(v.toString());
				//System.out.println("----------------isGoal---------------------");

				if(this.goalState.toString().equals(v.toString()))
						return true;
				return false;
			}

			@Override
			public ArrayList<State> backTrack(State n) {
				System.out.println("Start Trace ----------------------------------------------------");
				ArrayList<State> list = new ArrayList<State>();
				State papa = n.getCameFrom();
				while(papa != null)
				{
					n = papa;
					papa = n.getCameFrom();
					System.out.println(n.toString());
					
				}
				return list;
			}

			public StateComperator getComp() {
				return comp;
			}


			public void setComp(StateComperator comp) {
				this.comp = comp;
			}
			
			class StateComperator implements Comparator<State>
			{

				@Override
				public int compare(State o1, State o2) {
					if(o1.getCost() >= o2.getCost())
						return 1;
					else 
						return -1;
				}
			}
		}
		
		
		//System.out.println("This is the maze obj that we get");
		
		ArrayList<State> list2 = new BFS().search(new GenericMazeSearchable());
		list2 = new DFS().search(new GenericMazeSearchable());
		list2 = new HC_Heap().search(new GenericMazeSearchable());
		
		
		return null;
	}

}
