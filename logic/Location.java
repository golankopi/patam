package logic;

public class Location {
	private int i;
	private int j;
	
	public Location(int i,int j)
	{
		this.i = i;
		this.j = j;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}
	
	public void print()
	{
		System.out.println("["+getI()+","+getJ()+"]");
	}
}
