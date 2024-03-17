package org.example;

import org.example.config.AppConfig;
import org.example.components.ContactComponent;
import org.example.utils.CommandProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        ContactComponent contactComponent = context.getBean(ContactComponent.class);

        System.out.println("Active profile is: " + contactComponent.getProfile());

        CommandProcessor commandProcessor = new CommandProcessor();

        boolean appRunning = true;
        while (appRunning) {
            try {
                System.out.println(System.lineSeparator() + "Input command(ADD,LIST,DELETE,SAVE,EXIT): ");
                String input = new Scanner(System.in).nextLine().toUpperCase();

                if (input.equals("EXIT")) {
                    appRunning = false;
                    continue;
                }

                commandProcessor.processCommand(input);

            } catch (RuntimeException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}