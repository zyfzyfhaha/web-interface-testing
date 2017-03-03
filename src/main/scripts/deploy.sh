#!/usr/bin/env bash
source='/cheguo/product/cls-service/source';
service='/cheguo/product/cls-service/service';

echo 'Starting Cleaning Source Folder...';
echo '==================================================';
cd $source
svn update

echo 'Starting Checking Out And Packge';
echo '==================================================';
mvn clean package -Dmaven.test.skip=true -P dev -P filter-resource

echo 'Clean And Move Service Folder...';
echo '==================================================';
cd $service
rm -rf *
mv $source/cls-bss/trunk/target/cls-bss-1.0-SNAPSHOT-release/* $service/

cd $service/bin
chmod 777 *
sh restart.sh

echo '==================================================';
echo 'Service Started Successfully!';
echo '==================================================';

tail -f $service/logs/cls-bss.log -n 200