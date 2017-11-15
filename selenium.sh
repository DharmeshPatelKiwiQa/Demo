#!/bin/bash
mkdir -p selenium
cd selenium
curl "http://selenium-release.storage.googleapis.com/2.53.1/selenium-server-standalone-2.53.1.jar" -o "selenium-server-standalone-2.53.1.jar"
java -jar selenium-server-standalone-2.53.1.jar

