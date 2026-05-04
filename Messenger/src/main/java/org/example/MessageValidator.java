package org.example;

import org.example.Entity.MessageEntity;

public class MessageValidator {
    private static MessageValidator instance;

    private MessageValidator() {}

    public static MessageValidator getInstance() {
        if (instance == null) {
            instance = new MessageValidator();
        }
        return instance;
    }

    public boolean isValid(MessageEntity msg) {
        return msg.getReceiver() != null &&
                msg.getMessage() != null &&
                !msg.getReceiver().isEmpty() &&
                !msg.getMessage().isEmpty();
    }
}
