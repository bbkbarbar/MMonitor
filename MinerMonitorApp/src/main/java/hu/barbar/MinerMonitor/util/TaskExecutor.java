package hu.barbar.MinerMonitor.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import hu.barbar.MinerMonitor.util.exceptions.CouldNotRunCommandException;
import hu.barbar.util.FileHandler;

public class TaskExecutor {
	
	public static final String SCRIPT_FOLDER = "";
	public static final String BTN_PRESS_SCRIPT = "press.py";
	
	public static final int DURATION_UNDEFINED = -1;
	
	
	public boolean pressButton(int btnPin, long duration){
		
		String command = "sudo python " + SCRIPT_FOLDER + FileHandler.getPathSeparator() 
		                                + BTN_PRESS_SCRIPT
		                                + " -b " + Integer.toString(btnPin);
		
		if(duration > 0){
			command += " -d " + Long.toString(duration);
		}
		
		try {
			runCommand(command);
		} catch (CouldNotRunCommandException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	private static String runCommand(String cmd) throws CouldNotRunCommandException{
		
		String response = "_PROBLEM_";
		try {
			Process process = Runtime.getRuntime().exec(cmd);
			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			response = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			throw new CouldNotRunCommandException("cmd");
		}
		
		return response;
	}
	
}
