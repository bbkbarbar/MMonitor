package hu.barbar.MinerMonitor.util;

import java.util.ArrayList;

public class MinerInfos {
	
	public static final int VALUE_UNDEFINED = -1;
	
	
	private String minerVersion = null;
	
	private int uptimeMinutes = VALUE_UNDEFINED;
	
	private String coinName = null;
	
	private ArrayList<GPUInfo> gpuInfos = null;
	
	private String totalHashRate = null;
	
	
	/**
	 * Create instance from web-console line.
	 * <br>Input example:
	 * <_html><_head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></_head><body bgcolor="#808080" style="font-family: monospace;">{"result": ["9.3 - ETH", "1125", "73558;1200;0", "21309;26030;26218", "0;0;0", "off;off;off", "66;54;67;77;66;49", "eu2.ethermine.org:14444", "0;0;0;0"]}
	 * @param line
	 */
	public MinerInfos(String line){
		
		String[] arr = line.split(" ");
		
		// Miner version
		if(arr.length>=7){
			this.minerVersion = arr[7].substring(2);
		}
		
		// Coin name
		if(arr.length>=9){
			this.coinName = arr[9].substring(0, arr[9].indexOf('"'));
		}
		
		// Uptime in minutes
		if(arr.length>=10){
			String s = arr[10];
			String[] a = s.split("\"");
			if(a.length>=1){
				this.uptimeMinutes = Integer.valueOf(a[1]);
			}
		}
		
		// GPU infos (temperature, cooler state)
		if(arr.length>=15){
			String s = arr[15].substring(1);
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
