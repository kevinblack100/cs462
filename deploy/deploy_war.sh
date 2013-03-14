#!/bin/bash
# Stop Tomcat
echo "> sudo service tomcat7 stop"
sudo service tomcat7 stop

# Remove old Webapps
echo "> sudo rm -rf /var/lib/tomcat7/webapps/cs462/"
sudo rm -rf /var/lib/tomcat7/webapps/cs462/

echo "> sudo rm -rf /var/lib/tomcat7/webapps/shop/"
sudo rm -rf /var/lib/tomcat7/webapps/shop/

echo "> sudo rm -rf /var/lib/tomcat7/webapps/driver/"
sudo rm -rf /var/lib/tomcat7/webapps/driver/

# Install new Webapps
echo "> sudo cp ./deploy/shop.war /var/lib/tomcat7/webapps/"
sudo cp ./deploy/shop.war /var/lib/tomcat7/webapps/

echo "> sudo cp ./deploy/driver.war /var/lib/tomcat7/webapps/"
sudo cp ./deploy/driver.war /var/lib/tomcat7/webapps/

# Restart Tomcat
echo "> sudo service tomcat7 start"
sudo service tomcat7 start
