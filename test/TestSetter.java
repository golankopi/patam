package test;

import java.util.List;

import logic.Searchable;
import logic.Searcher;
import logic.algo.BFS;
import managment.CacheManager;
import managment.FileCacheManager;
import managment.Solver;
import pipeGame.PGS;
import pipeGame.PipeSearchable;
import pipeGame.PipeSimpleClientHandler;
import pipeGame.PipeSimpleServer;
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
		System.out.println("This is the maze obj that we get");
		
		System.out.println(m.getPossibleMoves(m.getEntrance()));
		return null;
	}

}
