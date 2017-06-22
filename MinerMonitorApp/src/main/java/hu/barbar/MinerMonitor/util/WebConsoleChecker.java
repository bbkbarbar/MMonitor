package hu.barbar.MinerMonitor.util;

import hu.barbar.MinerMonitor.MinerConsoleChecker;
import hu.barbar.MinerMonitor.MinerDataLogger;

public class WebConsoleChecker extends Thread {

	private long delayBetweenChecksInSeconds = -1;
	
	private String minerConsoleHost = null; 
	
	private MinerInfos minerInfos = null;
	
	private boolean needToRun = false;
	
	private MinerDataLogger mdl = null;
	
	
	public WebConsoleChecker(MinerInfos minerInfosToUpdate, String minerConsoleHost, long delayBetweenChecksInSeconds){
		System.out.println("Miner console will be checked on every " + delayBetweenChecksInSeconds + " seconds..");
		this.delayBetweenChecksInSeconds = delayBetweenChecksInSeconds;
		this.minerConsoleHost = minerConsoleHost;
		this.minerInfos = minerInfosToUpdate;
		mdl = new MinerDataLogger("/var/www/html/ul/", "gpuinfos.csv");
	}
	
	@Override
	public synchronized void start() {
		this.needToRun = true;
		super.start();
	}
	
	public void finish(){
		this.needToRun = false;
	}
	
	public void run() {
		
		while(needToRun){
			
			minerInfos = MinerConsoleChecker.getInfosFromWebConsole(minerConsoleHost);
			mdl.process(minerInfos);
			//System.out.println("Minerinfos updated: " + minerInfos);
			
			waitBeforeNextCheck();
		}
		
	}

	private void waitBeforeNextCheck(){
		try {
			Thread.sleep(delayBetweenChecksInSeconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
