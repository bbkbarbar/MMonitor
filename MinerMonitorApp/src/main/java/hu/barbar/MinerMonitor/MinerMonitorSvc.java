package hu.barbar.MinerMonitor;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.simple.JSONObject;

import hu.barbar.MinerMonitor.util.MinerInfos;
import hu.barbar.http_helper.RequestHelper;
import hu.barbar.http_helper.util.Response;
import hu.barbar.util.FileHandler;

public class MinerMonitorSvc {
	
	private static final String CONFIG_JSON = "mm_config.json";
	
	//private static MinerMonitorSvc me = null;
	
	private static JSONObject config = null;
	
	private MinerInfos mi = null;
	
	public static void main(String[] args) {
		//me = 
		new MinerMonitorSvc();
	}
	
	
	private ArrayList<String> getMinerConsole(String host){
		
		String str2 = null;
		try {
			Response resp = RequestHelper.sendGet(host);
			String responseStr = resp.getResponseBody().toString();
			str2 = responseStr.replaceAll("<br>", "\n");
			System.out.println(str2);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		if(str2 == null){
			return null;
		}
		
		ArrayList<String> lines = new ArrayList<String>(Arrays.asList(str2.split("\n")));
		
		return lines;
	}
	
	
	public MinerMonitorSvc(){
		
		config = FileHandler.readJSON(CONFIG_JSON);
		String host = (String) config.get(ConfigKeys.MINER_HOST_WITH_PORT);
		
		System.out.println("Try to open host: |" + host + "|");
		ArrayList<String> respLines = getMinerConsole(host);
		
		ArrayList<String> linesWhatCointainsTotalHashRate = new ArrayList<String>();
		
		for(int i=0; i<respLines.size(); i++){
			if(respLines.get(i).contains("{\"result\": [\"")){
				System.out.println("\n\n" + respLines.get(i) + "\n\n");
				mi = new MinerInfos(respLines.get(i));
			}
			
			if(respLines.get(i).contains("Total Speed: ")){
				linesWhatCointainsTotalHashRate.add(respLines.get(i));
			}
		}
		
		if(linesWhatCointainsTotalHashRate.size()>0){
			String lastLine = linesWhatCointainsTotalHashRate.get(linesWhatCointainsTotalHashRate.size()-1);
			mi.setTotalHashRateFromLastLine(lastLine);
		}
		
		System.out.println(mi.toString());
	}
	
}
