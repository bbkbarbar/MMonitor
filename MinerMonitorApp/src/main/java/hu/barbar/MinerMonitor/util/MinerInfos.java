package hu.barbar.MinerMonitor.util;

import java.util.ArrayList;

public class MinerInfos {
	
	public static final int VALUE_UNDEFINED = -1;
	
	private static final int IDX_OF_MINER_VERSION = 4;
	private static final int IDX_OF_COIN_NAME = 6;
	private static final int IDX_OF_UPTIME_IN_MINUTES = 7;
	private static final int IDX_OF_GPU_INFOS = 12;
	
	
	private String minerVersion = null;
	
	private int uptimeMinutes = VALUE_UNDEFINED;
	
	private String coinName = null;
	
	private ArrayList<GPUInfo> gpuInfos = null;
	
	private String totalHashRate = null;
	
	
	/**
	 * Create instance from web-console line.
	 * <br>Input example:
	 * <html><body bgcolor="#000000" style="font-family: monospace;">{"result": ["9.5 - ETH", "1528", "76925;1715;0", "24676;26131;26117", "0;0;0", "off;off;off", "67;47;68;64;67;42", "eu2.ethermine.org:14444", "0;0;0;0"]}
	 * @param line
	 */
	public MinerInfos(String line){
		
		String[] arr = line.split(" ");
		
		// For debug only
		/*
		for(int i=0; i<arr.length; i++){
			System.out.println(i + ": |" + arr[i] + "|");
		}
		/**/
		
		// Miner version
		if(arr.length>=IDX_OF_MINER_VERSION){
			this.minerVersion = arr[IDX_OF_MINER_VERSION].substring(2);
		}
		
		// Coin name
		if(arr.length>=IDX_OF_COIN_NAME){
			this.coinName = arr[IDX_OF_COIN_NAME].substring(0, arr[IDX_OF_COIN_NAME].indexOf('"'));
		}
		
		
		// Uptime in minutes
		if(arr.length>=IDX_OF_UPTIME_IN_MINUTES){
			String s = arr[IDX_OF_UPTIME_IN_MINUTES];
			String[] a = s.split("\"");
			if(a.length>=1){
				this.uptimeMinutes = Integer.valueOf(a[1]);
			}
		}
		
		// GPU infos (temperature, cooler state)
		if(arr.length>=IDX_OF_GPU_INFOS){
			String s = arr[IDX_OF_GPU_INFOS].substring(1);
			String gpuInfoLine = s.substring(0, s.indexOf('\"'));
			String gpuInfoParts[] = gpuInfoLine.split(";");
			
			gpuInfos = new ArrayList<GPUInfo>();
			for(int i=0; i<gpuInfoParts.length; i+=2){
				try{
					GPUInfo gi = new GPUInfo(Integer.valueOf(gpuInfoParts[i]), Integer.valueOf(gpuInfoParts[i+1]));
					gpuInfos.add(gi);
				}catch(Exception e){}
			}
			
		}
		
		/**/
	}
	
	
	public int getGPUCount(){
		if(this.gpuInfos == null){
			return VALUE_UNDEFINED;
		}
		return this.gpuInfos.size();
	}
		
	public String getMinerVersion() {
		return minerVersion;
	}

	public String getCoinName() {
		return coinName;
	}

	public void setTotalHashRate(String totalHashRate){
		this.totalHashRate = totalHashRate;
	}

	public void setTotalHashRateFromLastLine(String lastLine){
		String[] parts = lastLine.split(" ");
		if(parts.length>= 6){
			this.setTotalHashRate(parts[5] + " " + parts[6]);
		}
	}
	
	public String getTotalHashRate(){
		return this.totalHashRate;
	}
	
	public ArrayList<GPUInfo> getGpuInfos() {
		return gpuInfos;
	}

	public GPUInfo getGPUInfo(int gpuId){
		ArrayList<GPUInfo> infos = this.getGpuInfos();
		if(infos == null){
			return null;
		}
		if(infos.size() > gpuId){
			return null;
		}
		return infos.get(gpuId);
	}
	
	public String getUptimeHumanReadble(){
		int hours = (this.uptimeMinutes / 60);
		int minutes = this.uptimeMinutes % 60;
		return hours + ":" + minutes;
	}
	

	public String getGPUInfoLine(){
		if(this.gpuInfos == null){
			return null;
		}
		String res = "";
		for(int i=0; i<this.getGPUCount(); i++){
			res += "GPU" + i + ": {" + this.getGpuInfos().get(i).toString() + (i<this.getGPUCount()-1?"} ":"}");
		}
		return res;
	}
	
	public String toString(){
		return 
				"Uptime: " + this.getUptimeHumanReadble() 
				+ (totalHashRate == null?"":" Total speed: " + this.totalHashRate)
				+ " Coin: " + this.coinName + " Ver: " + this.minerVersion + " Gpu infos: " + getGPUInfoLine();
	}
}
