package hu.barbar.MinerMonitor.util;

public class GPUInfo {
	
	private int temperature = MinerInfos.VALUE_UNDEFINED;
	
	private int fanPct = MinerInfos.VALUE_UNDEFINED;

	
	public GPUInfo(int temperature, int fanPct) {
		super();
		this.temperature = temperature;
		this.fanPct = fanPct;
	}

	
	public int getTemperature() {
		return temperature;
	}

	public int getFanPct() {
		return fanPct;
	}
	
	
	public String toString(){
		return "T: " + getTemperature() + "°C fan: " + getFanPct() + "%";
	}
	
	
	
}
