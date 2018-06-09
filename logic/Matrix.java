package logic;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import exceptions.MatrixException;
import util.FileHandler;

public class Matrix implements Problem {
	private int rows;
	private int cols;
	private Location start;
	private Location end;
	private char[][]matrix = null;
	
	public Matrix(String level) throws MatrixException {
		String[] spllitedLevel = level.split(System.lineSeparator());
		rows = spllitedLevel.length;
		cols = spllitedLevel[0].length();
		if(rows <= 0 || cols <= 0)
			throw new MatrixException("Problem initiate matrix becuse of file missmatch rows:"+rows + " cols:"+ cols );
		
		char[][] matrix = new char[rows][cols];
		for(int i = 0; i < rows; i++)
		{
			String line = spllitedLevel[i];
			if(line == null)
				throw new MatrixException("Problem initiate matrix (line is null Problem) line:" + i); 
			for(int j = 0; j < cols; j++)
			{
				matrix[i][j] = line.charAt(j);
			}
		}
		this.matrix = matrix;
		start = findStartLocation(this.matrix, rows, cols);
		end = findEndLocation(this.matrix, rows, cols);
		//start.print();
		//end.print();
	}
	
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public void printMatrix()
	{
		for(int i =0 ; i < this.rows; i++)
		{
			for(int j =0 ; j < this.cols; j++)
			{
				System.out.print(this.matrix[i][j]);
			}
			System.out.println("");
		}
	}
	
	public Location findStartLocation(char[][] matrix,int rows,int cols)
	{
		for(int i =0 ; i < rows; i++)
		{
			for(int j =0 ; j < cols; j++)
			{
				if(matrix[i][j] == 's')
					return new Location(i,j);
			}
			//System.out.println("");
		}
		return null;
	}
	
	public Location findEndLocation(char[][] matrix,int rows,int cols)
	{
		for(int i =0 ; i < rows; i++)
		{
			for(int j =0 ; j < cols; j++)
			{
				if(matrix[i][j] == 'g')
					return new Location(i,j);
			}
			//System.out.println("");
		}
		return null;
	}
	//test main FileHandler
	public static void main(String[]ags)
	{
	    File file = new File("C:\\test\\level1.txt");
//	    try {
//			Matrix mat = new Matrix(file);
//			mat.print();
//		} catch (MatrixException e) {
//			// TODO Auto-generated catch block
//			e.PrintErrorMSG();
//		}
	}

	@Override
	public void print() {
		printMatrix();
		
	}
	
	@Override
	public String toString() {
		String[] rowsArr = new String[rows];
		String line;
		for (int i=0; i < rows; i++) {
			line = "";
			for (int j=0; j < cols; j++)
				line += matrix[i][j];
			rowsArr[i] = line;
		}
		return String.join(System.lineSeparator(), rowsArr);
	}
	
	@Override
	public String getRepr() {
		String matrixRepr;

		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			matrixRepr = (new HexBinaryAdapter()).marshal(md.digest(toString().getBytes()));
			return matrixRepr;

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "foo";
		}
	}
}
