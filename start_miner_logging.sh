#!/bin/bash

loggerAppPath=/home/pi/mmonitor
loggerAppJar=mmonitor_b5.jar
ftpUploaderPath=/home/pi/mmonitor/scripts
ftpUploaderScript=loop.sh


sudo kill $(ps aux | grep "mmonitor" | awk '{print $2}')
sudo kill $(ps aux | grep "mmonitor" | awk '{print $2}')
sudo kill $(ps aux | grep "mmonitor" | awk '{print $2}')
sudo kill $(ps aux | grep "mmonitor" | awk '{print $2}')
sudo kill $(ps aux | grep "mmonitor" | awk '{print $2}')
echo "MinerMonitor jar closed."


cd ${loggerAppPath}
sudo nohup java -jar ${loggerAppJar} &
echo "MinerMonitor jar started: " + $loggerAppJar

sleep 1

echo "Start script to upload GPU logs to ftp.."
cd ${ftpUploaderPath}
sudo nohup ${ftpUploaderPath}/${ftpUploaderScript} &
echo "Ftp uploader script started.."