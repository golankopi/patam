package managment;

import logic.Problem;

public interface Solver {
	public Solution solve(Problem problem);
	public Problem createProblem(Problem problem);
	
}
