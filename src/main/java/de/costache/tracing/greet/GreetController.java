package de.costache.tracing.greet;

import java.util.UUID;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import brave.Tracer;
import feign.Client;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;

@RestController
public class GreetController {

    private static final String template = "Hello, %s!";
    private final Logger logger = LoggerFactory.getLogger(GreetController.class);
    private final Encoder encoder;
    private final Client client;
    private final Decoder decoder;

    private GreetingProxy feignClient;
    private Tracer tracer;

    @Value("${upstream.url}")
    private String serviceUrl;

    @Autowired
    public GreetController(
            Decoder decoder, Encoder encoder, Client client, Tracer tracer) {

        this.encoder = encoder;
        this.client = client;
        this.decoder = decoder;
        this.tracer = tracer;
    }

    @PostConstruct
    public void init() {
        logger.info("Service url: {}", serviceUrl);
        if (!StringUtils.isEmpty(serviceUrl)) {
            this.feignClient = Feign.builder().client(client)
                    .encoder(encoder)
                    .decoder(decoder)
                    .requestInterceptor(new BasicAuthRequestInterceptor("user", "user"))
                    .target(GreetingProxy.class, serviceUrl);
        }
    }

    @RequestMapping("/greet")
    public Greet get(@RequestParam(value = "name", defaultValue = "World") String name,
                     @RequestParam(value = "delay", defaultValue = "100") long delay)
            throws InterruptedException {

        logger.info("Incoming call. Processing...");
        logger.info("Simulating some work...");
        Thread.sleep(delay);
        generateRandomError();

        if (feignClient != null) {
            logger.info("Using service as proxy and delegating the call");
            return feignClient.greet(name, delay);
        } else {
            logger.info("Proxy disabled, returning content directly", serviceUrl);
            return new Greet(UUID.randomUUID().toString(), String.format(template, name));
        }
    }

    private void generateRandomError() {
        if (Math.round(Math.random() * 10) % 5 == 0) {
            throw new RuntimeException("Random fail");
        }
    }
}