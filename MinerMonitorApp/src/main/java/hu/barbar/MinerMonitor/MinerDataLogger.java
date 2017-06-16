package hu.barbar.MinerMonitor;

import java.text.SimpleDateFormat;
import java.util.Date;

import hu.barbar.MinerMonitor.util.MinerInfos;
import hu.barbar.util.FileHandler;

public class MinerDataLogger {
	
	private static final String LOG_FILENAME_EXT = ".log";
	
	private static final String GPU_LOG_FILENAME_BASE = "gpu_";
	
	private static final String LOG_DATE_FORMAT = "yyyy-MM-dd HH:mm";
	
	
	private SimpleDateFormat sdf = null;
	
	private String logPath = null;

	
	/**
	 * 
	 * @param logPath without path separator at the end.
	 */
	public MinerDataLogger(String logPath) {
		super();
		this.logPath = logPath;
		sdf = new SimpleDateFormat(LOG_DATE_FORMAT);
	}
	
	
	public void process(MinerInfos mi){
		if(mi == null){
			return;
		}
		
		
		for(int i=0; i<mi.getGPUCount(); i++){
			
			String line = "\"" + sdf.format(new Date()) + ", ";
			line += mi.getGPUInfo(i).getTemperature() + ", "; 
			line += mi.getGPUInfo(i).getFanPct() + "\\n\"";
			
			if(FileHandler.appendToFile(generateLogFileNameForGpu(i), line)){
				// Success, do nothing
			}else{
				//TODO
				//Log.e("TempLogger2 :: Error while try to write temp log to file: " + temperatureLogFile);
			}
			
		}
		
	}
	
	private String generateLogFileNameForGpu(int gpuIdx){
		return logPath 
				//+ FileHandler.getPathSeparator() 
				+ GPU_LOG_FILENAME_BASE + gpuIdx + LOG_FILENAME_EXT;
	}
	
}
