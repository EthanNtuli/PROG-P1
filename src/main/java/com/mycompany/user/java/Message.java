/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.user.java;

import java.util.Random;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Message {
    private String messageID;
    private int messageNumber;
    private String messageHash;
    private String recipient;
    private String messageText;
    private String messageStatus;

    private static StringBuilder allSentMessages = new StringBuilder();
    private static int totalMessagesSent = 0;

    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash(this.messageID, messageNumber, messageText);
    }

    private String generateMessageID() {
        Random rand = new Random();
        long number = (long) (rand.nextDouble() * 9_000_000_000L) + 1_000_000_000L;
        return String.valueOf(number).substring(0, 10);
    }

    public boolean checkMessageID() {
        return messageID.length() == 10;
    }

    public String checkMessageLength() {
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        }
        int excess = messageText.length() - 250;
        return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
    }

    public String checkRecipientCell(String RecipientCell) {
        String regex = "^\\+27\\d{1,10}$";  //Regex101, 2024. Online Regex Tester and Debugger [online]. Available at: https://regex101.com/ [Accessed 11 October 2026].
        if (RecipientCell.matches(regex)) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    public static String createMessageHash(String messageID, int messageNumber, String message) {

        String firstTwoID = messageID.substring(0, 2);
        String userMessage = message.trim();

        int firstSpace = userMessage.indexOf(" ");
        String firstWord;

        if (firstSpace == -1) {
            firstWord = userMessage;
        } else {
            firstWord = userMessage.substring(0, firstSpace);
        }

        int lastSpace = userMessage.lastIndexOf(" ");

        String lastWord = userMessage.substring(lastSpace + 1);
        lastWord = lastWord.replaceAll("[^A-Za-z0-9]", "");

        String messageHash = firstTwoID + ":" + messageNumber + ":" + firstWord + lastWord;
        return messageHash.toUpperCase();
    }

    public String sentMessage(Scanner scanner) {
        System.out.println("\n------------------------------------------");
        System.out.println("What would you like to do with this message?");
        System.out.println("1. Send Message");
        System.out.println("2. Disregard Message");
        System.out.println("3. Store Message to send later");
        System.out.print("Enter choice: ");

        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            this.messageStatus = "Message successfully sent.";
            totalMessagesSent++;
            // NO ARRAYS: Append to the StringBuilder
            allSentMessages.append("- ").append(this.messageText).append("\n");
            return this.messageStatus;

        } else if (choice.equals("2")) {
            System.out.println("Press 0 to delete the message");
            System.out.print("Enter 0: ");
            String confirm = scanner.nextLine();
            if (confirm.equals("0")) {
                this.messageStatus = "Message deleted by user.";
            } else {
                this.messageStatus = "Deletion cancelled.";
            }
            return this.messageStatus;

        } else if (choice.equals("3")) {
            this.messageStatus = "Message successfully stored.";
            storeMessage();
            return this.messageStatus;

        } else {
            this.messageStatus = "Invalid option. Message discarded.";
            return this.messageStatus;
        }
    }

    public static int returnTotalMessagess() {
        return totalMessagesSent;
    }

    // NO ARRAYS: Return the StringBuilder contents as a String
    public static String printMessages() {
        if (allSentMessages.length() == 0) {
            return "No messages sent yet.";
        }
        return allSentMessages.toString();
    }

    public void storeMessage() {
        String jsonString = "{\n" +
                "  \"messageID\": \"" + messageID + "\",\n" +
                "  \"messageNumber\": " + messageNumber + ",\n" +
                "  \"recipient\": \"" + recipient + "\",\n" +
                "  \"messageText\": \"" + messageText + "\",\n" +
                "  \"messageHash\": \"" + messageHash + "\",\n" +
                "  \"status\": \"" + messageStatus + "\"\n" +
                "}";

        try {
            FileWriter writer = new FileWriter("messages.json", true);
            writer.write(jsonString + ",\n");
            writer.close();
            System.out.println("Message successfully stored in JSON.");
        } catch (IOException e) {
            System.out.println("Error saving to JSON: " + e.getMessage());
        }
    }

    public void printDetails() {
        System.out.println("\n--- Message Details ---");
        System.out.println("ID: " + messageID);
        System.out.println("Hash: " + messageHash);
        System.out.println("Recipient: " + recipient);
        System.out.println("Text: " + messageText);
        System.out.println("-----------------------");
    }

