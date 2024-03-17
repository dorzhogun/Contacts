package org.example.components;

import org.example.model.Contact;
import org.example.repositories.ContactRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ContactComponent {
    private final ContactRepository contactRepository;
    private final ContactStoreComponent contactStoreComponent;

    public ContactComponent(ContactRepository contactRepository, ContactStoreComponent contactStoreService) {
        this.contactRepository = contactRepository;
        this.contactStoreComponent = contactStoreService;
    }

    public String getProfile() {
        return contactStoreComponent.getProfile();
    }

    public void add(String s) {
        Contact contact = Contact.parseContact(s);
        contactRepository.add(contact);
    }

    public List<Contact> getAll() {
        return contactRepository.getAll();
    }

    public Contact removeByEmail(String email) {
        Contact contact = contactRepository.remove(email);
        if (contact == null) {
            throw new RuntimeException("Contact not found");
        }
        return contact;
    }

    public void saveInFile() {
        try {
            contactStoreComponent.saveInFile();
        } catch (IOException e) {
            throw new RuntimeException("Save file exception", e);
        }
    }
}
