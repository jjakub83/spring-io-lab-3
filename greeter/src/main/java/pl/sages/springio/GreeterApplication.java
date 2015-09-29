package pl.sages.springio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableConfigurationProperties(GreeterProperties.class)
public class GreeterApplication {

    public static void main(String[] args) {
        SpringApplication.run(GreeterApplication.class, args);
    }

    @Autowired
    GreeterProperties props;

    @RequestMapping("/{who}")
    public String greet(@PathVariable String who) {
        return String.format(props.getTemplate(), who);
    }
}

