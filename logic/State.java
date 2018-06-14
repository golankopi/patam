package logic;

import java.util.ArrayList;
import java.util.HashSet;

import exceptions.MatrixException;
import util.Dictionary;

public class State {
	private Matrix state;
	private double cost = 0;
	private State cameFrom;	
	Location location;
	private boolean visited = false;
	private HashSet<String> fathers ; // a set of states already evaluated

	public State(Matrix state){ // CTOR for first state
		try {
			this.state = new Matrix(state);
		} catch (MatrixException e) {e.printStackTrace();}
		this.location = new Location(state.getStart().getI(),state.getStart().getJ(),state.getStart().getValue())  ;
		this.cameFrom = null;
		fathers = new HashSet<String>();
		
	}

	public State(State s,Location newlocation){ // CTOR for first state
		try {
			this.state = new Matrix(s.state);
		} catch (MatrixException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.location = new Location(newlocation.getI(),newlocation.getJ(),newlocation.getValue());
		state.setLocation(newlocation);
		fathers = new HashSet<String>(s.fathers);
		//this.setCameFrom(s);
	}
	
	public boolean equals(State s){ // it’s easier to simply overload
		return state.equals(s.state); // if matrix not equals
	}

	public ArrayList<State> getNeighbors() {
		ArrayList<State> list = new ArrayList<State>();
		ArrayList<State> up = new ArrayList<State>();
		ArrayList<State> down = new ArrayList<State>();
		ArrayList<State> left = new ArrayList<State>();
		ArrayList<State> right = new ArrayList<State>();
		Character myParent = getParentDiraction();
		System.out.println("my p:" + myParent);
		if(!(myParent.equals('u')))up = getPosibleUp();
		if(!(myParent.equals('d')))down = getPosibleDown();
		if(!(myParent.equals('l')))left = getPosibleLeft();
		if(!(myParent.equals('r')))right = getPosibleRight();
		System.out.println(fathers.toString());
		for(State s : up)
		{
			if(!fathers.contains(s.toString()))
				list.add(s);
		}
		for(State s : down)
		{
			if(!fathers.contains(s.toString()))
				list.add(s);
		}
		for(State s : left)
		{
			if(!fathers.contains(s.toString()))
				list.add(s);
		}
		for(State s : right)
		{
			if(!fathers.contains(s.toString()))
				list.add(s);
		}
		return list;
	} 
	
	public char getParentDiraction()
	{
		try{
			if(cameFrom.getLocation() !=null)
			{
			Location loc = cameFrom.location;
			if(location.getI() < loc.getI())
				return 'd';
			if(location.getI() > loc.getI())
				return 'u';
			if(location.getJ() < loc.getJ())
				return 'r';
			if(location.getJ() > loc.getJ())
				return 'l';
		
			}
		}catch(Exception e){}
		return 'q';
	}
	
	private ArrayList<State> getPosibleUp()
	{
		//System.out.println("Checking neighbors UP");
		ArrayList<State> list = new ArrayList<State>();
		Dictionary dic = new Dictionary();
		char current = location.getValue();
		if(location.getI() > 0)
		{
			Location newLocation = new Location(location.getI()-1,location.getJ(),getState().getValue(location.getI()-1,location.getJ()));
			State up = new State(this,newLocation);
			char newVal = newLocation.getValue();
			if(newVal == 'F' || newVal == '7' ||newVal == 'J' || newVal == 'L'  )
			{
				Location l = new Location(newLocation.getI(),newLocation.getJ(),'F');
				if(dic.validate(current,'F','u'))
					list.add(new State(up,l));
				Location l2 = new Location(newLocation.getI(),newLocation.getJ(),'7');
				if(dic.validate(current,'7','u'))
					list.add(new State(up,l2));
			}
			else if(newVal == '|' || newVal == '-')
			{
				Location l = new Location(newLocation.getI(),newLocation.getJ(),'|');
				if(dic.validate(current,'|','u'))
					list.add(new State(up,l));
			}
			else if(newVal == 'g'){
				Location l = new Location(newLocation.getI(),newLocation.getJ(),'g');
				if(dic.validate(current,'g','u'))
					list.add(new State(up,l));
			}
		}
		//printList(list);
		return list;
	}
	
	
	private ArrayList<State> getPosibleDown()
	{
		Dictionary dic = new Dictionary();
		char current = location.getValue();
		//System.out.println("Checking neighbors Down");
		ArrayList<State> list = new ArrayList<State>();
		//location.print();
		if(location.getI() < state.getRows()-1)
		{
			Location newLocation = new Location(location.getI()+1,location.getJ(),getState().getValue(location.getI()+1,location.getJ()));
			//newLocation.print();
			State down = new State(this,newLocation);
			char newVal = newLocation.getValue();
			//System.out.println("in down new val = "  + newVal);
			if(newVal == 'F' || newVal == '7' ||newVal == 'J' || newVal == 'L'  )
			{
				Location l2 = new Location(newLocation.getI(),newLocation.getJ(),'L');
				if(dic.validate(current,'L','d'))
					list.add(new State(down,l2));
				Location l = new Location(newLocation.getI(),newLocation.getJ(),'J');
				if(dic.validate(current,'J','d'))
					list.add(new State(down,l));
				
			}
			else if(newVal == '|' || newVal == '-')
			{
				Location l = new Location(newLocation.getI(),newLocation.getJ(),'|');
				if(dic.validate(current,'|','d'))
					list.add(new State(down,l));
			}
			else if(newVal == 'g'){
				Location l = new Location(newLocation.getI(),newLocation.getJ(),'g');
				if(dic.validate(current,'g','d'))
					list.add(new State(down,l));
			}
		}
		//printList(list);
		return list;
	}
	
	private ArrayList<State> getPosibleRight()
	{
		Dictionary dic = new Dictionary();
		char current = location.getValue();

		//System.out.println("Checking neighbors Right");
		ArrayList<State> list = new ArrayList<State>();
		if(location.getJ() < state.getCols()-1)
		{
			Location newLocation = new Location(location.getI(),location.getJ()+1,getState().getValue(location.getI(),location.getJ()+1));
			//System.out.println("in right mylocation: "  );
			//location.print();
			//System.out.println("in right newlocation: "  );
			//newLocation.print();
			State right = new State(this,newLocation);
			char newVal = newLocation.getValue();
			if(newVal == 'F' || newVal == '7' ||newVal == 'J' || newVal == 'L'  )
			{
				Location l = new Location(newLocation.getI(),newLocation.getJ(),'J');
				if(dic.validate(current,'J','r'))
					list.add(new State(right,l));
				Location l2 = new Location(newLocation.getI(),newLocation.getJ(),'7');
				if(dic.validate(current,'7','r'))
					list.add(new State(right,l2));
			}
			else if(newVal == '|' || newVal == '-')
			{
				Location l = new Location(newLocation.getI(),newLocation.getJ(),'-');
				if(dic.validate(current,'-','r'))
					list.add(new State(right,l));
			}
			else if(newVal == 'g'){
				Location l = new Location(newLocation.getI(),newLocation.getJ(),'g');
				if(dic.validate(current,'g','r'))
					list.add(new State(right,l));
			}
		}
		//printList(list);
		return list;
	}
	
	private ArrayList<State> getPosibleLeft()
	{
		Dictionary dic = new Dictionary();
		char current = location.getValue();
		
		//System.out.println("Checking neighbors Left");
		ArrayList<State> list = new ArrayList<State>();
		if(location.getJ() > 0)
		{
			Location newLocation = new Location(location.getI(),location.getJ()-1,getState().getValue(location.getI(),location.getJ()-1));
			State left = new State(this,newLocation);
			char newVal = newLocation.getValue();
			if(newVal == 'F' || newVal == '7' ||newVal == 'J' || newVal == 'L'  )
			{
				Location l = new Location(newLocation.getI(),newLocation.getJ(),'F');
				if(dic.validate(current,'F','l'))
					list.add(new State(left,l));
				Location l2 = new Location(newLocation.getI(),newLocation.getJ(),'L');
				if(dic.validate(current,'L','l'))
					list.add(new State(left,l2));
			}
			else if(newVal == '|' || newVal == '-')
			{
				Location l = new Location(newLocation.getI(),newLocation.getJ(),'-');
				if(dic.validate(current,'-','l'))
					list.add(new State(left,l));
			}
			else if(newVal == 'g'){
				Location l = new Location(newLocation.getI(),newLocation.getJ(),'g');
				if(dic.validate(current,'g','l'))
					list.add(new State(left,l));
			}
		}
		//printList(list);
		return list;
	}
	
	@Override
	public String toString()
	{
		//String s = "[" + this.location.getI() + this.location.getJ() + "]" + this.location.getValue();
		String s = "[" + this.location.getI() + this.location.getJ() + "]";

		return s;
	}

	private void printList(ArrayList<State> list)
	{
		for(State s1: list)
		{
			s1.print();
			//System.out.println("\n");
		}
	}
	
	public Matrix getState() {
		return state;
	}

	public void setState(Matrix state) {
		this.state = state;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public State getCameFrom() {
		return cameFrom;
	}

	public void setCameFrom(State cameFrom) {
		this.cameFrom = cameFrom;
		this.fathers.add(cameFrom.toString());
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	public void print()
	{
		state.print();
		location.print();
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
}
