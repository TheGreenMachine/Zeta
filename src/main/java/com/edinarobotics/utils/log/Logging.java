package com.edinarobotics.utils.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Logging {

	private PrintWriter writer;
	private String filename;

	public static Logging instance;
	
	private Logging(String name) {

		filename = name;
		
		try {
			File file = new File("/home/lvuser" + filename + ".csv");
			if (file.exists()) file.delete();
			writer = new PrintWriter(new FileWriter("/home/lvuser/" + filename + ".csv", false), true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//"/home/lvuser/Output6.txt"
	}
	
	public void log(String whatToWrite) {
		
		try {
			writer.println(whatToWrite);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void close() {
		try {
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public static Logging getInstance(String name){
        if (instance == null){
            instance = new Logging(name);
        }
        else {
            instance.close();
            instance = new Logging(name);
        }
        return instance;
    }
	
}
