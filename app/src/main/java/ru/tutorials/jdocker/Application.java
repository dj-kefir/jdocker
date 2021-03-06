package ru.tutorials.jdocker;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tutorials.jdocker.models.User;
import ru.tutorials.jdocker.repositories.UserRepository;

import java.util.stream.Stream;

@EnableCircuitBreaker
@EnableHystrix
@EnableEurekaClient
@RestController
@SpringBootApplication
@Slf4j
public class Application {

    @Autowired
    UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner initDb() {
        return (args) -> {
            Stream.of(User.builder().name("Igor").build(),
                    User.builder().name("Alexandr").build()).forEach(userRepository::save);

            log.info("БД проинициаизирована!");
        };
    }

    @RequestMapping("/")
    public String isAlive() {
        return "it's ok";
    }

    public String fallbackMethod() {
        return "Всё пропало";
    }

    @HystrixCommand(fallbackMethod = "fallbackMethod")
    @RequestMapping("/to-read")
    public String toRead() {
        throw new RuntimeException();
    }

    @RefreshScope
    @RestController
    class MessageRestController {

        @Value("${myProperty:Hello default}")
        private String myProperty;

        @RequestMapping("/message")
        String getMessage() {
            return this.myProperty;
        }
    }
}
