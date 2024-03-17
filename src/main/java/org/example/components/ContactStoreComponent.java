package org.example.components;

import org.example.model.Contact;
import org.example.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContactStoreComponent {
    private final ContactRepository contactRepository;
    @Value("${path.file.save}")
    private String pathToSave;
    @Value("${spring.profiles.active}")
    private String profile;
    @Value("${path.file.init}")
    private String pathToInit;

    public ContactStoreComponent(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    static List<String> getResourceFileAsString() throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try (InputStream is = classLoader.getResourceAsStream("default-contacts.txt")) {
            if (is == null) {
                throw new IOException();
            }
            try (InputStreamReader isr = new InputStreamReader(is);
                 BufferedReader reader = new BufferedReader(isr)) {
                return reader.lines().toList();
            }
        }
    }

    public void saveInFile() throws IOException {
        List<Contact> contacts = contactRepository.getAll();
        String stringToSave = contacts.stream()
                .map(contact -> MessageFormat.format("{0};{1};{2}", contact.fullName(), contact.phoneNumber(), contact.email()))
                .collect(Collectors.joining(System.lineSeparator()));
        Files.writeString(Path.of(pathToSave), stringToSave);
    }

    public void initFromFile() throws IOException {
        List<String> strings = contactRepository.getAll().isEmpty() ? getResourceFileAsString() :
                Files.readAllLines(Path.of(pathToInit));
        strings.stream().map(Contact::parseContact).forEach(contactRepository::add);
    }

    public String getProfile() {
        return profile;
    }
}
