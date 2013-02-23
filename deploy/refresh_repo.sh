#!/bin/bash
echo "> cd ~/repos/cs462/"
cd ~/repos/cs462/

echo "> git branch deploy"
git branch deploy

echo "> git checkout deploy"
git checkout deploy

echo "> git pull origin deploy"
git pull origin deploy

echo "> ./deploy/deploy_war.sh"
./deploy/deploy_war.sh

echo "> git checkout master"
git checkout master
