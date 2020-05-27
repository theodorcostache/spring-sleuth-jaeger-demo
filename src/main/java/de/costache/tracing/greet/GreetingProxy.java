package de.costache.tracing.greet;

import feign.Param;
import feign.RequestLine;
import org.springframework.web.bind.annotation.RequestParam;


public interface GreetingProxy {

    @RequestLine("GET /greet?name={name}&delay={delay}")
    Greet greet(@Param(value = "name") String name, @Param(value = "delay") long delay);

}