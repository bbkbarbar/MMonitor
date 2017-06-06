package hu.barbar.MinerMonitor;

import java.util.ArrayList;
import java.util.Arrays;

import hu.barbar.MinerMonitor.util.MinerInfos;
import hu.barbar.http_helper.RequestHelper;
import hu.barbar.http_helper.util.Response;

public class MinerConsoleChecker {

	
	private static ArrayList<String> getMinerConsole(String host){
		
		String str2 = null;
		try {
			Response resp = RequestHelper.sendGet(host);
			String responseStr = resp.getResponseBody().toString();
			str2 = responseStr.replaceAll("<br>", "\n");
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
	
	
	public static MinerInfos getInfosFromWebConsole(String host){
		if(host == null){
			return null;
		}
		MinerInfos mi = null;
		
		System.out.println("Try to get miner infos from web-console..");
		System.out.println("Open host: |" + host + "|");
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
		
		System.out.println("Last infos:\n" + mi.toString());
		
		return mi;
	}
	
}
