/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
/**
 *
 * @author Anele
 */
package com.mycompany.user.java;

import java.util.ArrayList;
import java.util.List;

public class MessageStore {

    private final ArrayList<String> messageIDs = new ArrayList<>();
    private final ArrayList<String> messageHashes = new ArrayList<>();
    private final ArrayList<String> recipients = new ArrayList<>();
    private final ArrayList<String> messageTexts = new ArrayList<>();
    private final ArrayList<String> flags = new ArrayList<>();

    private final ArrayList<String> sentMessages = new ArrayList<>();
    private final ArrayList<String> disregardedMessages = new ArrayList<>();
    private final ArrayList<String> storedMessages = new ArrayList<>();

    // Adds one message to every relevant array.
    public void addMessage(String id, int number, String recipient, String text, String flag) {
        messageIDs.add(id);
        messageHashes.add(Message.createMessageHash(id, number, text));
        recipients.add(recipient);
        messageTexts.add(text);
        flags.add(flag);

        if (flag.equalsIgnoreCase(Message.FLAG_SENT)) {
            sentMessages.add(text);
        } else if (flag.equalsIgnoreCase(Message.FLAG_STORED)) {
            storedMessages.add(text);
        } else {
            disregardedMessages.add(text);
        }
    }

    // Adds a message the user just handled; messages with no outcome (cancelled deletion) are skipped.
    public void addMessage(Message message) {
        if (message.getFlag().isEmpty()) {
            return;
        }
        addMessage(message.getMessageID(), message.getMessageNumber(), message.getRecipient(),
                message.getMessageText(), message.getFlag());
    }
    
    public int loadStoredFromJson(String fileName) {
        int added = 0;
        for (Message m : Message.readStoredMessages(fileName)) {
            if (!messageIDs.contains(m.getMessageID())) {
                addMessage(m.getMessageID(), m.getMessageNumber(), m.getRecipient(),
                        m.getMessageText(), Message.FLAG_STORED);
                added++;
            }
        }
        return added;
    }

    //sender and recipient of every stored message.
    public String displaySenderAndRecipient(String sender) {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < messageTexts.size(); i++) {
            if (flags.get(i).equalsIgnoreCase(Message.FLAG_STORED)) {
                out.append("Sender: ").append(sender).append(" | Recipient: ")
                   .append(recipients.get(i)).append("\n");
            }
        }
        return out.length() == 0 ? "No stored messages." : out.toString();
    }

    public String longestStoredMessage() {
        String longest = "";
        for (int i = 0; i < messageTexts.size(); i++) {
            boolean stored = flags.get(i).equalsIgnoreCase(Message.FLAG_STORED);
            if (stored && messageTexts.get(i).length() > longest.length()) {
                longest = messageTexts.get(i);
            }
        }
        return longest.isEmpty() ? "No stored messages." : longest;
    }


    // Rubric: "details" of the longest stored message (recipient, hash and text).
    public String longestStoredMessageDetails() {
        int longestIndex = -1;
        for (int i = 0; i < messageTexts.size(); i++) {
            boolean stored = flags.get(i).equalsIgnoreCase(Message.FLAG_STORED);
            if (stored && (longestIndex == -1 || messageTexts.get(i).length() > messageTexts.get(longestIndex).length())) {
                longestIndex = i;
            }
        }
        if (longestIndex == -1) {
            return "No stored messages.";
        }
        return "Recipient: " + recipients.get(longestIndex) + " | Hash: " + messageHashes.get(longestIndex)
                + " | Message: " + messageTexts.get(longestIndex);
    }

    // Shows every stored message (read from the JSON file) to the user.
    public String displayStoredMessages() {
        StringBuilder out = new StringBuilder("--- Stored Messages ---\n");
        int count = 0;
        for (int i = 0; i < messageTexts.size(); i++) {
            if (flags.get(i).equalsIgnoreCase(Message.FLAG_STORED)) {
                count++;
                out.append(count).append(". ID: ").append(messageIDs.get(i))
                   .append(" | Recipient: ").append(recipients.get(i))
                   .append(" | Message: ").append(messageTexts.get(i)).append("\n");
            }
        }
        return count == 0 ? "No stored messages." : out.toString();
    }

    public String getMessageByID(String id) {
        int index = messageIDs.indexOf(id);
        return index == -1 ? "Message ID not found." : messageTexts.get(index);
    }

    public String getRecipientByID(String id) {
        int index = messageIDs.indexOf(id);
        return index == -1 ? "Message ID not found." : recipients.get(index);
    }

    public List<String> searchByRecipient(String recipient) {
        List<String> found = new ArrayList<>();
        for (int i = 0; i < messageTexts.size(); i++) {
            boolean kept = !flags.get(i).equalsIgnoreCase(Message.FLAG_DISREGARD);
            if (kept && recipients.get(i).equals(recipient)) {
                found.add(messageTexts.get(i));
            }
        }
        return found;
    }

    public String deleteByHash(String hash) {
        int index = messageHashes.indexOf(hash);
        if (index == -1) {
            return "No message found with that hash.";
        }
        String text = messageTexts.get(index);
        String flag = flags.get(index);

        messageIDs.remove(index);
        messageHashes.remove(index);
        recipients.remove(index);
        messageTexts.remove(index);
        flags.remove(index);

        if (flag.equalsIgnoreCase(Message.FLAG_SENT)) {
            sentMessages.remove(text);
        } else if (flag.equalsIgnoreCase(Message.FLAG_STORED)) {
            storedMessages.remove(text);
        } else {
            disregardedMessages.remove(text);
        }
        return "Message: \"" + text + "\" successfully deleted.";
    }

    public String displayReport() {
        StringBuilder report = new StringBuilder("--- Message Report ---\n");
        int count = 0;
        for (int i = 0; i < messageTexts.size(); i++) {
            if (!flags.get(i).equalsIgnoreCase(Message.FLAG_DISREGARD)) {
                count++;
                report.append(count).append(". Hash: ").append(messageHashes.get(i))
                      .append(" | Recipient: ").append(recipients.get(i))
                      .append(" | Message: ").append(messageTexts.get(i)).append("\n");
            }
        }
        return count == 0 ? "No messages to report." : report.toString();
    }

    public List<String> getSentMessages() { return sentMessages; }
    public List<String> getDisregardedMessages() { return disregardedMessages; }
    public List<String> getStoredMessages() { return storedMessages; }
    public List<String> getMessageHashes() { return messageHashes; }
    public List<String> getMessageIDs() { return messageIDs; }
}

/*Reference list:
Java Brains, 2019. JUnit 5 Basics [YouTube playlist]. Available at: https://www.youtube.com/watch?v=2E3WqYupx7c&list=PLqq-6Pq4ITTa4ad5JISViSb2FVG8Vwa4o [Accessed 14 September 2026].

NetBeans, 2024. JUnit 5 Tutorial [online]. Apache NetBeans. Available at: https://netbeans.apache.org/tutorial/main/kb/docs/java/junit-intro/ [Accessed 14 September 2026].

Oracle, 2024. Lesson: Regular Expressions [online]. The Java Tutorials. Available at: https://docs.oracle.com/javase/tutorial/essential/regex/ [Accessed 14 September 2026].

Regex101, 2024. Online Regex Tester and Debugger [online]. Available at: https://regex101.com/ [Accessed 14 September 2026].

Stack Overflow, 2023. How to fix "java: package org.junit.jupiter.api does not exist" [online]. Available at: https://stackoverflow.com/questions/75092698/ [Accessed 14 September 2026].
*/