# Distributed tracing example using Spring Cloud Sleuth and Jaeger 

This is a demo service that uses spring cloud sleuth for tracing and pushes traces to jaeger. 

Using the start script ([see usage](#markdown-header-usage)) you can bring up four instances of this service. Three of them are acting as proxies and just delegate the call upstream. 

To simulate real life scenarios, the service throws errors at random points in time and in the call chain.

The proxy services are running on the following ports: 8083, 8082, 8081
The service actually serving the data is accessible on port: 8080

The call chain is as follows: 

http://localhost:8083/greet -> http://localhost:8082/greet -> http://localhost:8081/greet -> http://localhost:8080/greet

Jaeger UI: http://localhost:16686/

![](media/demo.gif)

## Usage

### Windows 
This demo requires java, gradle and jaeger-all-in-one to be installed. To download these dependencies just run:
```
./download-win.sh
```

Once the dependencies have been downloaded, start four instances of the service using:

```
./start-win.sh
```


