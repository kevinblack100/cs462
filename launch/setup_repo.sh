#!/bin/bash
echo "> sudo apt-get update"
sudo apt-get update

echo "> sudo apt-get -y install git"
sudo apt-get -y install git

echo "> cd ~"
cd ~

echo "> rm -rf repors/cs462/"
rm -rf repos/cs462/

echo "> mkdir repos"
mkdir repos

echo "> cd repos"
cd repos

echo "> git clone https://github.com/kevinblack100/cs462.git"
git clone https://github.com/kevinblack100/cs462.git
