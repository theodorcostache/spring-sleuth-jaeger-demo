package de.costache.tracing.greet;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="greeting", url = "${upstream.url}")
public interface GreetingProxy {

    @RequestMapping(method = RequestMethod.GET, value = "/greet")
    Greet greet(@RequestParam(value = "name", defaultValue = "World") String name, @RequestParam(value = "delay") long delay);
}