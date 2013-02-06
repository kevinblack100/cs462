#!/bin/bash
sudo cp ./launch/tomcat7-default.txt /etc/default/tomcat7
sudo cp ./launch/tomcat7-server.xml /etc/tomcat7/server.xml
sudo cp ./launch/tomcat7-keystore.keystore /etc/tomcat7/.keystore
sudo sh -c 'echo "Kevin Black on Tomcat 7" > /var/lib/tomcat7/webapps/ROOT/index.html'
