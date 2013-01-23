#!/bin/bash
sudo apt-get update
sudo apt-get -y install git
cd ~
mkdir repos
cd repos
git clone https://github.com/kevinblack100/cs462.git
git pull origin master
./launch/setup_tomcat.sh
