#!/bin/bash
sudo apt-get update
sudo apt-get -y install apache2
sudo sh -c 'echo "Kevin Black" > /var/www/index.html'
sudo apachectl start
