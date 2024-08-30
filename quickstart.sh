#!/bin/bash -p

if [ $# -eq 0 ]; then
    echo "Usage: $0 <project_name>"
    exit 1
fi

mvn archetype:generate -DarchetypeGroupId=com.flowlogix.archetypes -DarchetypeArtifactId=starter \
  -DarchetypeVersion=23 -DbaseType=payara -DinteractiveMode=false \
  -DgroupId=com.example -Dpackage=com.sample -DartifactId=$1
  
echo "Upgrading to latest versions"
mvn -f $1 versions:use-latest-releases versions:update-parent versions:update-properties \
  -DprocessAllModules=true -DgenerateBackupPoms=false \
  -Dmaven.version.rules=https://raw.githubusercontent.com/flowlogix/base-pom/main/versions-ruleset.xml
