#!/bin/bash
# tomcat needs a jdk
sudo apt-get -y install openjdk-6-jdk
export JAVA_HOME=/usr/lib/jvm/java-6-openjdk
sudo apt-get -y install tomcat7
./luanch/config_tomcat.sh
sudo service tomcat7 start
