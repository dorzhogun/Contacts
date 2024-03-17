package org.example.model;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Contact(String fullName, String phoneNumber, String email) {

    private static final Pattern CONTACT_PATTERN = Pattern
            .compile("^([\\p{IsAlphabetic} ]+);(\\+?[1-9]\\d{7,14});(\\S+@\\S+\\.\\S+)$");
    private static final String INPUT_ERROR = "Ошибка ввода. Пример: Иванов Иван Иванович;+890999999;someEmail@example.example";

    public static Contact parseContact(String value) {
        Matcher matcher = CONTACT_PATTERN.matcher(value);
        if (!matcher.find()) {
            throw new IllegalArgumentException(INPUT_ERROR);
        }
        return new Contact(matcher.group(1).trim(), matcher.group(2).trim(), matcher.group(3).trim());
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0}, {1}, {2}", fullName, phoneNumber, email);
    }
}
