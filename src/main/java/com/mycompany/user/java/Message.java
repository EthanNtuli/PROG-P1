/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
/**
 *
 * @author Anele
 */

package com.mycompany.user.java;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Message {

    // Shared message strings. Main compares against these constants, so the two can never drift apart.
    public static final String CELL_SUCCESS = "Cell phone number successfully captured.";
    public static final String CELL_FAILURE = "Cell phone number is incorrectly formatted or does not "
            + "contain an international code. Please correct the number and try again.";
    public static final String LENGTH_OK = "Message ready to send.";
    public static final String DEFAULT_JSON_FILE = "messages.json";
    public static final String FLAG_SENT = "Sent";
    public static final String FLAG_STORED = "Stored";
    public static final String FLAG_DISREGARD = "Disregard";

    private static final int MAX_LENGTH = 250;

    private static int totalMessagesSent = 0;
    private static final StringBuilder allSentMessages = new StringBuilder();

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String messageStatus = "";
    private String flag = "";

    // Normal constructor: the ID is generated automatically.
    public Message(int messageNumber, String recipient, String messageText) {
        this(generateMessageID(), messageNumber, recipient, messageText);
    }

    // Constructor with a supplied ID so tests can use predictable data.
    public Message(String messageID, int messageNumber, String recipient, String messageText) {
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash(messageID, messageNumber, messageText);
    }

    // Builds a random ten-digit ID using substring
    private static String generateMessageID() {
        Random rand = new Random(); // Oracle, 2024. Random (Java Platform SE 8) [online]. Java Platform API Specification.
        long number = (long) (rand.nextDouble() * 9_000_000_000L) + 1_000_000_000L;
        return String.valueOf(number).substring(0, 10); // Oracle, 2024. String (Java Platform SE 8) [online].
    }

    public boolean checkMessageID() {
        return messageID.length() == 10;
    }

    public String checkMessageLength() {
        int length = messageText.length();
        if (length <= MAX_LENGTH) {
            return LENGTH_OK;
        }
        return "Message exceeds 250 characters by " + (length - MAX_LENGTH) + "; please reduce the size.";
    }

    public String checkRecipientCell(String cell) {
        return isRecipientValid(cell) ? CELL_SUCCESS : CELL_FAILURE;
    }

    // True when the number starts with + and an international code, then no more than ten digits.
    public boolean isRecipientValid(String cell) {
        String regex = "^\\+\\d{1,3}\\d{1,10}$"; // Regex101, 2024. Online Regex Tester and Debugger [online].
        return cell.matches(regex);
    }

    // True when the message is 250 characters or fewer.
    public boolean isMessageLengthValid() {
        return messageText.length() <= MAX_LENGTH;
    }

    public static String createMessageHash(String messageID, int messageNumber, String text) {
        String firstTwoID = messageID.substring(0, 2);
        String trimmed = text.trim();

        int firstSpace = trimmed.indexOf(' ');
        String firstWord = (firstSpace == -1) ? trimmed : trimmed.substring(0, firstSpace);
        String lastWord = trimmed.substring(trimmed.lastIndexOf(' ') + 1);

        firstWord = removePunctuation(firstWord);
        lastWord = removePunctuation(lastWord);

        return (firstTwoID + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }

    private static String removePunctuation(String word) {
        return word.replaceAll("[^A-Za-z0-9]", ""); // Regex101, 2024.
    }

    // Lets the user send, disregard or store the message and returns the outcome.
    public String sentMessage(Scanner scanner) {
        System.out.println("\n-------------------------------------------");
        System.out.println("What would you like to do with this message?");
        System.out.println("1. Send Message");
        System.out.println("2. Disregard Message");
        System.out.println("3. Store Message to send later");
        System.out.print("Enter choice: ");

        String choice = scanner.nextLine().trim();

        if (choice.equals("1")) {
            messageStatus = "Message successfully sent.";
            flag = FLAG_SENT;
            totalMessagesSent++;
            allSentMessages.append("- ").append(messageText).append("\n");
        } else if (choice.equals("2")) {
            System.out.println("Press 0 to delete the message");
            System.out.print("Enter 0: ");
            if (scanner.nextLine().trim().equals("0")) {
                messageStatus = "Message deleted by user.";
                flag = FLAG_DISREGARD;
            } else {
                messageStatus = "Deletion cancelled.";
                flag = "";
            }
        } else if (choice.equals("3")) {
            messageStatus = "Message successfully stored.";
            flag = FLAG_STORED;
            storeMessage();
        } else {
            messageStatus = "Invalid option. Message discarded.";
            flag = FLAG_DISREGARD;
        }
        return messageStatus;
    }

    public static int returnTotalMessagess() {
        return totalMessagesSent;
    }

    public static String printMessages() {
        if (allSentMessages.length() == 0) {
            return "No messages sent yet.";
        }
        return allSentMessages.toString();
    }

    public void storeMessage() {
        storeMessage(DEFAULT_JSON_FILE);
    }

    public void storeMessage(String fileName) {
        List<Message> all = readStoredMessages(fileName);
        all.add(this);
        try (FileWriter writer = new FileWriter(fileName)) { // Oracle, 2024. FileWriter (Java Platform SE 8) [online].
            writer.write(toJsonArray(all));
            System.out.println("Message successfully stored in JSON.");
        } catch (IOException e) {
            System.out.println("Error saving to JSON: " + e.getMessage());
        }
    }

    // Turns a list of messages into a JSON array string.
    private static String toJsonArray(List<Message> messages) {
        StringBuilder json = new StringBuilder("[\n");
        for (int i = 0; i < messages.size(); i++) {
            Message m = messages.get(i);
            json.append("  {\n")
                .append("    \"messageID\": \"").append(escape(m.messageID)).append("\",\n")
                .append("    \"messageNumber\": ").append(m.messageNumber).append(",\n")
                .append("    \"recipient\": \"").append(escape(m.recipient)).append("\",\n")
                .append("    \"messageText\": \"").append(escape(m.messageText)).append("\",\n")
                .append("    \"messageHash\": \"").append(escape(m.messageHash)).append("\",\n")
                .append("    \"flag\": \"").append(escape(m.flag)).append("\"\n")
                .append("  }");
            json.append(i < messages.size() - 1 ? ",\n" : "\n"); // comma only BETWEEN objects
        }
        return json.append("]").toString();
    }

    private static String escape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
    }

    // Reads the JSON array from disk into a list of messages.
    // JSON array format follows the JSON specification: json.org, 2024. Introducing JSON [online]. Available at: https://www.json.org
    public static List<Message> readStoredMessages(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            String json = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
            return parseJsonArray(json);
        } catch (IOException | RuntimeException e) {
            System.out.println("Could not read " + fileName + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Small hand-written parser for the flat objects written by toJsonArray.
    private static List<Message> parseJsonArray(String json) {
        List<Message> messages = new ArrayList<>();
        int[] pos = {0};
        while (true) {
            int start = json.indexOf('{', pos[0]);
            if (start == -1) {
                break;
            }
            pos[0] = start + 1;
            String id = "", recipient = "", text = "", flagValue = "";
            int number = 0;

            while (true) {
                skipSpacesAndCommas(json, pos);
                if (json.charAt(pos[0]) == '}') {
                    pos[0]++;
                    break;
                }
                String key = readString(json, pos);
                skipSpacesAndCommas(json, pos);
                pos[0]++; // skip ':'
                skipSpacesAndCommas(json, pos);

                if (json.charAt(pos[0]) == '"') {
                    String value = readString(json, pos);
                    if (key.equals("messageID")) { id = value; }
                    else if (key.equals("recipient")) { recipient = value; }
                    else if (key.equals("messageText")) { text = value; }
                    else if (key.equals("flag")) { flagValue = value; }
                } else {
                    int end = pos[0];
                    while (json.charAt(end) != ',' && json.charAt(end) != '}' && json.charAt(end) != '\n') {
                        end++;
                    }
                    if (key.equals("messageNumber")) {
                        number = Integer.parseInt(json.substring(pos[0], end).trim());
                    }
                    pos[0] = end;
                }
            }
            Message m = new Message(id, number, recipient, text);
            m.flag = flagValue;
            messages.add(m);
        }
        return messages;
    }

    private static void skipSpacesAndCommas(String json, int[] pos) {
        while (pos[0] < json.length() && (Character.isWhitespace(json.charAt(pos[0])) || json.charAt(pos[0]) == ',')) {
            pos[0]++;
        }
    }

    // Reads a quoted JSON string starting at pos and leaves pos just after the closing quote.
    private static String readString(String json, int[] pos) {
        StringBuilder value = new StringBuilder();
        pos[0]++; // skip opening quote
        while (json.charAt(pos[0]) != '"') {
            char c = json.charAt(pos[0]);
            if (c == '\\') {
                pos[0]++;
                char next = json.charAt(pos[0]);
                value.append(next == 'n' ? '\n' : next);
            } else {
                value.append(c);
            }
            pos[0]++;
        }
        pos[0]++; // skip closing quote
        return value.toString();
    }

    public void printDetails() {
        System.out.println("\n--- Message Details ---");
        System.out.println("ID: " + messageID);
        System.out.println("Hash: " + messageHash);
        System.out.println("Recipient: " + recipient);
        System.out.println("Text: " + messageText);
        System.out.println("-----------------------");
    }

    public String getMessageID() { return messageID; }
    public int getMessageNumber() { return messageNumber; }
    public String getMessageHash() { return messageHash; }
    public String getMessageText() { return messageText; }
    public String getRecipient() { return recipient; }
    public String getFlag() { return flag; }
}


/*Reference list:
Oracle, 2024. FileWriter (Java Platform SE 8) [online]. Java Platform API Specification. Available at: https://docs.oracle.com/javase/8/docs/api/java/io/FileWriter.html [Accessed 17 October 2026].

Oracle, 2024. Lesson: Regular Expressions [online]. The Java Tutorials. Available at: https://docs.oracle.com/javase/tutorial/essential/regex/ [Accessed 6 October 2026].

Oracle, 2024. Random (Java Platform SE 8) [online]. Java Platform API Specification. Available at: https://docs.oracle.com/javase/8/docs/api/java/util/Random.html [Accessed 17 October 2026].

Oracle, 2024. StringBuilder (Java Platform SE 8) [online]. Java Platform API Specification. Available at: https://docs.oracle.com/javase/8/docs/api/java/lang/StringBuilder.html [Accessed 17 October 2026].

Oracle, 2024. String (Java Platform SE 8) [online]. Java Platform API Specification. Available at: https://docs.oracle.com/javase/8/docs/api/java/lang/String.html [Accessed 17 October 2026].

Oracle, 2024. The if-then and if-then-else Statements [online]. The Java Tutorials. Available at: https://docs.oracle.com/javase/tutorial/java/nutsandbolts/if.html [Accessed 17 October 2026].

Regex101, 2024. Online Regex Tester and Debugger [online]. Available at: https://regex101.com/ [Accessed 11 October 2026].
*/
