#!/bin/bash
[ -d downloads/windows/jdk-14.0.1 ] && PATH=downloads/windows/jdk-14.0.1/bin:$PATH
[ -d downloads/windows/gradle-6.4 ] && PATH=downloads/windows/gradle-6.4/bin:$PATH
[ -d downloads/windows/jaeger-1.17.1-windows-amd64 ] && PATH=downloads/windows/jaeger-1.17.1-windows-amd64:$PATH

echo "Using java: $(which java)"
echo "Using gradle: $(which gradle)"
echo "Using jaeger: $(which jaeger-all-in-one)"

gradle clean build

PIDS=()

NAME=SERVICE0 java -Xms512m -Xmx2G -XX:+UseG1GC -jar build/libs/open-tracing-demo-0.0.1-SNAPSHOT.jar > service0.log & 
PIDS+=($!)

jaeger-all-in-one --collector.zipkin.http-port=9411 2>jaeger.log &
PIDS+=($!)

for i in `seq 1 3`;
do
    PORT=808$i NAME=SERVICE$i SERVICE_URL=http://localhost:808$(($i-1)) java -Xms512m -Xmx2G -XX:MaxGCPauseMillis=100 -XX:+UseG1GC -jar build/libs/open-tracing-demo-0.0.1-SNAPSHOT.jar > service$i.log & 
    PIDS+=($!)
done   

read -n 1 -s -r -p "Press any key to continue"

for i in "${PIDS[@]}"
do
   kill $i
done
