#!/bin/bash
mkdir -p RELEASES/
rm -vrf RELEASES/*
find -type f -path "*/target/*shaded.jar" ! -path "./target/*" ! -name "*NMS*" -exec sh -c 'cp $1 RELEASES ' _ '{}' \;
find . -path "./RELEASES/*-*-shaded.jar" -print | awk '{f=$1;sub("-shaded","");print "mv "f" "$0}' | sh
find . -path "./RELEASES/*-*.jar" -print | awk '{f=$1;sub("-(([0-9])+(\.){0,1})+.jar","");print "mv "f" "$1 ".jar"}' | sh
echo "All compiled projects can be found in RELEASES folder!"
#mvn clean -q
echo "Maven clean task has been executed."
