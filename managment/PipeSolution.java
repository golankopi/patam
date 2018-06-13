package managment;

import java.util.ArrayList;

import logic.State;
import util.Dictionary;

public class PipeSolution implements Solution {

	ArrayList<String> innerList = new ArrayList<>();
	Dictionary dic = new Dictionary();
	
	public PipeSolution(ArrayList<State> list, State s) {
		for(State st: list)
		{
			char wanted = st.getLocation().getValue();
			char current = s.getState().getValue(st.getLocation().getI(),st.getLocation().getJ());
			//System.out.println("old val:" + current);
			//System.out.println("new val:" + wanted);
			int rightRotation = dic.getRotationRight(current,wanted);
			String temp = "["+st.getLocation().getI()+","+st.getLocation().getJ()+"]," + rightRotation;
			System.out.println(temp+","+wanted);
			innerList.add(temp);
		}
	}

}
