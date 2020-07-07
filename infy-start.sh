#!/usr/bin/env bash
source colored-echo

echo "*************************************starting server on local host**********************"
echo "*************************************building and installing the  project **********************"

mvn clean install

mvn exec:java -Dexec.mainClass=com.beth.infy.InfyApplication

echo "****************************server stared***********************************"
echo "*********************health check on http://localhost:8080/api/v1/health*********************"