package hu.barbar.MinerMonitor;

import java.text.SimpleDateFormat;
import java.util.Date;

import hu.barbar.MinerMonitor.util.MinerInfos;
import hu.barbar.comm.ftphelper.FTPHelper;
import hu.barbar.util.FileHandler;

public class MinerDataLogger {
	
	private static final String LOG_FILENAME_EXT = ".log";
	
	private static final String GPU_LOG_FILENAME_BASE = "gpu_";
	
	private static final String LOG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	
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
		
		String completeLine = sdf.format(new Date());
		completeLine += "," + mi.getTotalHashRate().split(" ")[0];
		for(int i=0; i<mi.getGPUCount(); i++){
			completeLine += "," + mi.getGPUInfo(i).getTemperature();
			completeLine += "," + mi.getGPUInfo(i).getFanPct();
		}
		completeLine += "";
		
		//TODO get LogFile from config JSON
		String logFile = "/var/www/html/ul/gpuinfos.csv";
		FileHandler.appendToFile(logFile, completeLine);
		
		if( FTPHelper.upload("ftp.atw.hu", "barbarminer", "Astra16i", "/var/www/html/ul/", "gpuinfos.csv") ){
			System.out.println("\nFTP upload failed..\n");
		}
		
		// Save separated logs per GPU-s
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
