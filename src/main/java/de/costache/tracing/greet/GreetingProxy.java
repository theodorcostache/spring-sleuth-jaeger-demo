package de.costache.tracing.greet;

import feign.RequestLine;


public interface GreetingProxy {

    @RequestLine("GET /greet")
    Greet greeting();

}