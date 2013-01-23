#!/bin/bash
# tomcat needs a jdk
sudo apt-get -y install openjdk-6-jdk
sudo apt-get -y install tomcat7
sudo cp ./launch/tomcat7-default.txt /etc/default/tomcat7
sudo cp ./launch/tomcat7-server.xml /etc/tomcat7/server.xml
sudo sh -c 'echo "Kevin Black on tomcat 7" > /var/lib/tomcat7/webapps/ROOT/index.html'
sudo service tomcat7 start
