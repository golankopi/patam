package server;
import logic.Matrix;
import managment.CacheManager;
import managment.FileCacheManager;
import managment.Solver;
import exceptions.*;

import java.io.*;
import java.util.*;

public class PipeSimpleClientHandler implements ClientHandler {
	
	private CacheManager cacheManager;
	private Solver solver;
	
	@Override
	public void handle(InputStream is, OutputStream os)  {

        PrintWriter outTC = new PrintWriter(os);
        BufferedReader inFClient = new BufferedReader(new InputStreamReader(is));
        String line;
        ArrayList<String> lines = new ArrayList<String>();
        String level;
        try {
            while (!(line = inFClient.readLine()).equals("done")) {
                lines.add(line);
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println(lines);
        level = String.join(System.lineSeparator(), lines);
        //TODO change to a problem intead of matrix
    	Matrix m;
		try {
			m = new Matrix(level);
		} catch (MatrixException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			return;
		}
        cacheManager = new FileCacheManager(m.getRepr());
        String solvedLevel;
        try {
			 solvedLevel = cacheManager.load();
		} catch (IOException e1) {
			solvedLevel = null;
		}
        
        if (solvedLevel == null) {
        	//TODO solve and use solution.toString();
        	solvedLevel = "0,7,2\r\n" + 
        			"1,7,3\r\n" + 
        			"1,5,1\r\n" + 
        			"1,2,1\r\n" + 
        			"1,0,1\r\n" + 
        			"2,7,2";
	
	        try {
				cacheManager.save(solvedLevel);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        else {
        	System.out.println("Awesome! level found in cache!");
        }
        System.out.println("going to return:");
        System.out.println(solvedLevel);
        
        for(String solvedLine : solvedLevel.split(System.lineSeparator())) {
        	outTC.println(solvedLine);
            System.out.println("sending:");
            System.out.println(solvedLine);
        }
        outTC.println("done");
        outTC.flush();
        try {
			inFClient.close();
	        outTC.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
	}

}