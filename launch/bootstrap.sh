#!/bin/bash
echo "> sudo apt-get update"
sudo apt-get update

echo "> sudo apt-get -y install git"
sudo apt-get -y install git

echo "> cd ~"
cd ~

echo "> mkdir repos"
mkdir repos

echo "> cd repos"
cd repos

echo "> git clone https://github.com/kevinblack100/cs462.git"
git clone https://github.com/kevinblack100/cs462.git

echo "> cd cs462"
cd cs462

echo "> ./launch/setup_tomcat.sh"
./launch/setup_tomcat.sh
