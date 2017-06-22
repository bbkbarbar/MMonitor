#!/bin/bash

loggerAppPath=/home/pi/mmonitor
loggerAppJar=mmonitor_b2.jar
ftpUploaderPath=/home/pi/mmonitor/scripts
ftpUploaderScript=loop.sh


sudo kill $(ps aux | grep "mmonitor" | awk '{print $2}')
sudo kill $(ps aux | grep "mmonitor" | awk '{print $2}')
sudo kill $(ps aux | grep "mmonitor" | awk '{print $2}')
sudo kill $(ps aux | grep "mmonitor" | awk '{print $2}')
sudo kill $(ps aux | grep "mmonitor" | awk '{print $2}')
echo "MinerMonitor jar closed."