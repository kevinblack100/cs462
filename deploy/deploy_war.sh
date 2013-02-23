#!/bin/bash
echo "> sudo service tomcat7 stop"
sudo service tomcat7 stop

echo "> sudo rm -rf /var/lib/tomcat7/webapps/cs462/"
sudo rm -rf /var/lib/tomcat7/webapps/cs462/

echo "> sudo cp ./deploy/cs462.war /var/lib/tomcat7/webapps/"
sudo cp ./deploy/cs462.war /var/lib/tomcat7/webapps/

echo "> sudo service tomcat7 start"
sudo service tomcat7 start
