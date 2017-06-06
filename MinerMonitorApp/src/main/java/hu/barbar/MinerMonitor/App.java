package hu.barbar.MinerMonitor;

import hu.barbar.http_helper.RequestHelper;
import hu.barbar.http_helper.util.Response;

public class App {
	
	
	public static void main(String[] args) {
		try {
			Response resp = RequestHelper.sendGet("barbarhome.ddns.net:3333");
			
			System.out.println(resp.getResponseBody().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
