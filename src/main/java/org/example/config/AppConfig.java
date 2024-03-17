package org.example.config;

import org.springframework.context.annotation.*;

@ComponentScan("org.example")
@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {
}
