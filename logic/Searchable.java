package logic;

import java.util.ArrayList;
import java.util.Comparator;

public interface Searchable {
	State getInitialState();
	State getGoalState();
	ArrayList<State> getAllPossibleStates(State s);
	boolean isGoal(State n);
	ArrayList<State> backTrack(State n);
	Comparator<? super State> getComp();
}
