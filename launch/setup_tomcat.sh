#!/bin/bash
# tomcat needs a jdk
echo "sudo apt-get -y install openjdk-6-jdk"
sudo apt-get -y install openjdk-6-jdk

echo "export JAVA_HOME=/usr/lib/jvm/java-6-openjdk"
export JAVA_HOME=/usr/lib/jvm/java-6-openjdk

echo "sudo apt-get -y install tomcat7"
sudo apt-get -y install tomcat7

echo "./launch/config_tomcat.sh"
./launch/config_tomcat.sh

echo "sudo service tomcat7 start"
sudo service tomcat7 start
