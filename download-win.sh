#!/bin/bash

mkdir -p downloads/windows

cd downloads/windows

[ -d jdk-14.0.1 ] || curl -OL https://download.java.net/java/GA/jdk14.0.1/664493ef4a6946b186ff29eb326336a2/7/GPL/openjdk-14.0.1_windows-x64_bin.zip

# unzip and remove the downloader zip
if [ -f openjdk-14.0.1_windows-x64_bin.zip ]; then
    unzip openjdk-14.0.1_windows-x64_bin.zip
    rm -rf openjdk-14.0.1_windows-x64_bin.zip
fi

[ -d gradle-6.4 ] || curl -k -L GET https://services.gradle.org/distributions/gradle-6.4-bin.zip > gradle-6.4-bin.zip

if [ -f gradle-6.4-bin.zip ]; then
    unzip gradle-6.4-bin.zip
    rm -rf gradle-6.4-bin.zip
fi 

[ -d jaeger-1.17.1-windows-amd64 ] || curl -OL https://github.com/jaegertracing/jaeger/releases/download/v1.17.1/jaeger-1.17.1-windows-amd64.tar.gz

if [ -f jaeger-1.17.1-windows-amd64.tar.gz ]; then
    tar xvf jaeger-1.17.1-windows-amd64.tar.gz
    rm -rf jaeger-1.17.1-windows-amd64.tar.gz
fi