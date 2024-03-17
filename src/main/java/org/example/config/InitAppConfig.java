package org.example.config;

import org.example.components.ContactStoreComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Configuration
@PropertySource("classpath:application-init.properties")
@Profile("init")
public class InitAppConfig {

    private final ContactStoreComponent contactStoreComponent;

    public InitAppConfig(ContactStoreComponent contactStoreComponent) {
        this.contactStoreComponent = contactStoreComponent;
    }

    @PostConstruct
    public void init() throws IOException {
        contactStoreComponent.initFromFile();
    }
}
