package hu.barbar.MinerMonitor;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.simple.JSONObject;

import hu.barbar.MinerMonitor.util.MinerInfos;
import hu.barbar.MinerMonitor.util.WebConsoleChecker;
import hu.barbar.http_helper.RequestHelper;
import hu.barbar.http_helper.util.Response;
import hu.barbar.util.FileHandler;

public class MinerMonitorSvc {
	
	private static final String CONFIG_JSON = "mm_config.json";
	
	//private static MinerMonitorSvc me = null;
	
	private boolean needToRun = false;
	
	private static JSONObject config = null;
	
	private String minerConsoleHost = null;
	
	private long webConsoleCheckingFrequencyInSec = 120; //TODO create a constant for this defualt value
	private long timeofLastCheck = 0;
	
	private MinerInfos mi = null;

	
	
	public static void main(String[] args) {
		//me = 
		new MinerMonitorSvc();
	}
	
	
	
	public MinerMonitorSvc(){
		
		needToRun = true;
		
		config = FileHandler.readJSON(CONFIG_JSON);
		
		minerConsoleHost = (String) config.get(ConfigKeys.MINER_HOST_WITH_PORT);
		webConsoleCheckingFrequencyInSec = (Long) config.get(ConfigKeys.INTERVAL_FOR_CHECK_WEB_CONSOLE);

		WebConsoleChecker wcs = new WebConsoleChecker(mi, minerConsoleHost, webConsoleCheckingFrequencyInSec);
		wcs.start();
		
		
	}
	
}
