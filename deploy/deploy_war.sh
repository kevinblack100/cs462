#!/bin/bash
# Stop Tomcat
echo "> sudo service tomcat7 stop"
sudo service tomcat7 stop

# Remove old Webapps
echo "> sudo rm -rf /var/lib/tomcat7/webapps/cs462"
sudo rm -rf /var/lib/tomcat7/webapps/cs462

echo "> sudo rm -rf /var/lib/tomcat7/webapps/shop"
sudo rm -rf /var/lib/tomcat7/webapps/shop

echo "> sudo rm -rf /var/lib/tomcat7/webapps/driver"
sudo rm -rf /var/lib/tomcat7/webapps/driver

echo "> sudo rm -rf /var/lib/tomcat7/webapps/guild"
sudo rm -rf /var/lib/tomcat7/webapps/guild

# Install new Webapps
echo "> sudo cp ./deploy/shop.war /var/lib/tomcat7/webapps"
sudo cp ./deploy/shop.war /var/lib/tomcat7/webapps

echo "> sudo cp ./deploy/driver.war /var/lib/tomcat7/webapps"
sudo cp ./deploy/driver.war /var/lib/tomcat7/webapps

echo "> sudo cp ./deploy/guild.war /var/lib/tomcat7/webapps"
sudo cp ./deploy/guild.war /var/lib/tomcat7/webapps

