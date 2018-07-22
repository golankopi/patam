package mazeGame;

import java.util.ArrayList;

import logic.GeneralState;
import logic.State;

public class GenericMazeState<T> extends GeneralState {

	private T t;
	
	
	
	@Override
	public ArrayList<State> getNeighbors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int genCost() {
		// TODO Auto-generated method stub
		return 0;
	}

}
