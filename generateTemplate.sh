#!/usr/bin/env bash
source colored-echo
echo "****************copying auto gen class files to project location **********************************"
cp -p templates-code-gen/*.class target/classes/com/beth/infy/templates/.
echo "*****************Generating Template**********************************"
java -cp "target/infy-0.0.1-SNAPSHOT.jar:BOOT-INF/lib/*" -Dloader.main=com.beth.infy.templates.GenerateTemplate org.springframework.boot.loader.PropertiesLauncher