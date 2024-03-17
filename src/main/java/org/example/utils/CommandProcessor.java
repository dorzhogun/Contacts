package org.example.utils;

import org.example.components.ContactComponent;
import org.example.components.ContactPrinter;
import org.example.config.AppConfig;
import org.example.model.Commands;
import org.example.model.Contact;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.text.MessageFormat;
import java.util.Scanner;

public class CommandProcessor {
    ApplicationContext context;

    public CommandProcessor() {
        this.context = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    public void processCommand(String input) {
        Commands commands;
        try {
            commands = Commands.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.err.println("Unknown command");
            return;
        }

        ContactPrinter contactPrinter = context.getBean(ContactPrinter.class);
        ContactComponent contactComponent = context.getBean(ContactComponent.class);

        switch (commands) {
            case ADD:
                System.out.println("Input contact in format(fullName;phoneNumber;email): ");
                contactComponent.add(new Scanner(System.in).nextLine());
                break;
            case LIST:
                System.out.println("List contacts:");
                contactPrinter.print(contactComponent.getAll());
                break;
            case DELETE:
                System.out.println("Input email for remove");
                String email = new Scanner(System.in).nextLine();
                Contact contact = contactComponent.removeByEmail(email);
                System.out.println(MessageFormat.format("Contact removed: {0}", contact));
                break;
            case SAVE:
                contactComponent.saveInFile();
                System.out.println("Complete save contacts in file");
                break;
            default:
                System.err.print("Unsupported command");
        }
    }
}

