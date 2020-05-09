package de.costache.tracing.greet;

import brave.Tracer;
import feign.Client;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
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
    public Greet get(@RequestParam(value = "name", defaultValue = "World") String name) {

        logger.info("Incoming call. Processing...");
        long count = getCount();

        if (feignClient != null) {
            logger.info("Using service as proxy and delegating the call");
            return feignClient.greeting();
        } else {
            logger.info("Proxy disabled, returning content directly", serviceUrl);
            return new Greet(count, String.format(template, name));
        }
    }

    private long getCount() {
        long value = counter.incrementAndGet();
        if (Math.round(Math.random() * 10) % 5 == 0) {
            throw new RuntimeException("Random fail");
        }
        return value;
    }
}