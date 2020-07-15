package de.costache.tracing.greet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.annotation.ContinueSpan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class GreetController {

    private static final String template = "Hello, %s!";
    private final Logger logger = LoggerFactory.getLogger(GreetController.class);

    private RestTemplate restTemplate;

    @Value("${upstream.url}")
    private String serviceUrl;

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    public GreetController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping("/greet")
    @ContinueSpan(log="getGreet")
    public Greet getGreet(@RequestParam(value = "name", defaultValue = "World") String name,
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
            return callService(name, delay, failIn);
        } else {
            logger.info("Not running as proxy, returning content directly");
            return new Greet(UUID.randomUUID().toString(), String.format(template, name));
        }
    }

    private Greet callService(String name, long delay, String failIn){
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("delay", Long.toString(delay));
        params.put("failIn", failIn);

        return restTemplate.getForObject(serviceUrl + "/greet?name={name}&delay={delay}&failIn={failIn}", Greet.class, params);
    }

    private void methodThatFails() {
        throw new RuntimeException("Random fail");
    }
}