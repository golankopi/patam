package managment;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

import exceptions.MatrixException;
import logic.BFS;
import logic.DFS;
import logic.Matrix;
import logic.PipeSearchable;
import logic.PipeSolution;
import logic.Problem;
import logic.Solution;
import logic.State;
import logic.PipeState;

public class PGS implements Solver{

	
	@Override
	public Solution solve(Problem problem) {
		State s = new PipeState((Matrix) problem);
		
		s.print();
		System.out.println("**************Starting PGS*****************");
		System.out.println("**************Starting PGS BFS*****************");

		ArrayList<State> list = new BFS().search(new PipeSearchable(s));
		System.out.println("**************Starting PGS DFS*****************");

		ArrayList<State> list2 = new DFS().search(new PipeSearchable(s));

		System.out.println("**************PGS BFS*****************");
		printList(list);
		System.out.println("**************PGS DFS*****************");
		printList(list2);
		
		Solution ps = new PipeSolution(list,s);
		return ps;
	}

	@Override
	public Problem createProblem(Problem problem) {
		// TODO Auto-generated method stub
		return null;
	}

	public void printList(ArrayList<State> list)
	{
		if(list !=null){
			for(State s1: list)
			{
				s1.print();
				try{
					System.out.print("p:");
					s1.getCameFrom().getLocation().print();
				}catch(Exception e){}
			}
		}
	}
 	
  	public static void main(String[]args)
	{
		File file = new File("C:\\test\\level3.txt");
		File file2 = new File("C:\\test\\level2.txt");

	    try {
			Matrix mat = new Matrix(file);
			//mat.print();
			Matrix mat2 = new Matrix(file2);
			PipeState s = new PipeState(mat2);
			PGS pgs = new PGS();
			pgs.solve(mat2);
			
		} catch (MatrixException e) {
			e.printStackTrace();
		}
	}
}
