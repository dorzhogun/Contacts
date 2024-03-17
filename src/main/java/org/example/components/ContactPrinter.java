package org.example.components;

import org.example.model.Contact;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContactPrinter {
    public void print(List<Contact> contacts) {
        contacts.forEach(System.out::println);
    }
}
