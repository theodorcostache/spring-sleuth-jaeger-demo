package de.costache.tracing.greet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class GreetController {

    private static final String template = "Hello, %s!";
    private final Logger logger = LoggerFactory.getLogger(GreetController.class);

    private GreetingProxy feignClient;

    @Value("${upstream.url}")
    private String serviceUrl;

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    public GreetController(GreetingProxy feignClient) {
        this.feignClient = feignClient;
    }

    @RequestMapping("/greet")
    public Greet get(@RequestParam(value = "name", defaultValue = "World") String name,
                     @RequestParam(value = "delay", defaultValue = "100") long delay,
                     @RequestParam(value = "failIn", defaultValue = "") String failIn)
            throws InterruptedException {

        logger.info("Incoming call. Processing...");
        logger.info("Simulating some work...");
        Thread.sleep(delay);

        if (failIn.equals(applicationName)){
            methodThatFails();
        }

        if (serviceUrl != null) {
            logger.info("Using service as proxy and delegating the call");
            return feignClient.greet(name, delay, failIn);
        } else {
            logger.info("Proxy disabled, returning content directly");
            return new Greet(UUID.randomUUID().toString(), String.format(template, name));
        }
    }

    private void methodThatFails() {
        throw new RuntimeException("Random fail");
    }
}