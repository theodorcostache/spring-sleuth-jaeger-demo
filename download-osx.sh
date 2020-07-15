#!/bin/bash
mkdir downloads
cd downloads

[ -d jdk-11.0.2.jdk ] || curl -OL https://download.java.net/java/GA/jdk11/9/GPL/openjdk-11.0.2_osx-x64_bin.tar.gz

# unzip and remove the downloader zip
if [ -f openjdk-11.0.2_osx-x64_bin.tar.gz ]; then
   
    tar xvzf openjdk-11.0.2_osx-x64_bin.tar.gz
    rm -rf openjdk-11.0.2_osx-x64_bin.tar.gz
fi

[ -d gradle-6.4 ] || curl -k -L GET https://services.gradle.org/distributions/gradle-6.4-bin.zip > gradle-6.4-bin.zip

if [ -f gradle-6.4-bin.zip ]; then
    unzip gradle-6.4-bin.zip
    rm -rf gradle-6.4-bin.zip
fi

[ -d jaeger-1.18.1-darwin-amd64 ] || curl -OL https://github.com/jaegertracing/jaeger/releases/download/v1.18.1/jaeger-1.18.1-darwin-amd64.tar.gz

if [ -f jaeger-1.18.1-darwin-amd64.tar.gz ]; then
    tar xvf jaeger-1.18.1-darwin-amd64.tar.gz
    rm -rf jaeger-1.18.1-darwin-amd64.tar.gz
fi