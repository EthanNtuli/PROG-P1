/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
/**
 *
 * @author Anele
 */

package com.mycompany.user.java;

import java.util.Scanner;

/**
 * QuickChat Part 3: register, log in, then send messages, and manage stored messages.
 * Part 1 (User.java) is used exactly as submitted.
 */
public class Main {

    private static final String REGISTERED = "Registration successful! You may now log in.";
    private static final int MAX_LOGIN_ATTEMPTS = 3;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // ---- Part 1: registration (repeats until it succeeds) ----
        String firstName, lastName, username, password;
        User obj;
        String registerResult;
        do {
            System.out.println("Registration");
            firstName = ask(scanner, "Enter first name: ");
            lastName = ask(scanner, "Enter last name: ");
            username = ask(scanner, "Enter username (must contain '_' and be no more than 5 characters): ");
            password = ask(scanner, "Enter password (8+ chars, a capital letter, a number, a special character): ");
            String cellPhoneNumber = ask(scanner, "Enter cell phone number (e.g. +27838968976): ");

            obj = new User(firstName, lastName, username, password, cellPhoneNumber);
            registerResult = obj.registerUser();
        } while (!registerResult.equals(REGISTERED));

        // ---- Part 1: login (the result is now really used) ----
        boolean isLoggedIn = false;
        for (int attempt = 1; attempt <= MAX_LOGIN_ATTEMPTS && !isLoggedIn; attempt++) {
            String loginUsername = ask(scanner, "Enter username: ");
            String loginPassword = ask(scanner, "Enter password: ");
            isLoggedIn = obj.loginUser(firstName, lastName, username, password, loginUsername, loginPassword);
        }

        // ---- Part 2: only a successful login opens QuickChat ----
        if (!isLoggedIn) {
            System.out.println("Too many failed login attempts. Goodbye.");
            scanner.close();
            return;
        }

        System.out.println("\nWelcome to QuickChat.");
        int numMessages = readInt(scanner, "How many messages do you wish to enter? ");
        boolean messagesEntered = false;
        MessageStore store = new MessageStore();
        boolean running = true;

        // The application runs until the user chooses Quit.
        while (running) {
            System.out.println("\n--- MENU ---");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages");
            int choice = readInt(scanner, "Choose an option (1-4): ");

            if (choice == 1) {
                if (messagesEntered) {
                    System.out.println("You have already entered your " + numMessages + " messages.");
                } else {
                    sendMessages(scanner, numMessages, store);
                    messagesEntered = true;
                }
            } else if (choice == 2) {
                System.out.println("Coming Soon.");
            } else if (choice == 3) {
                System.out.println("Exiting application. Goodbye!");
                running = false;
            } else if (choice == 4) {
                storedMessagesMenu(scanner, store, username);
            } else {
                System.out.println("Invalid option. Please enter 1, 2, 3 or 4.");
            }
        }

        System.out.println("Total messages sent: " + Message.returnTotalMessagess());
        scanner.close();
    }

    // The for loop runs exactly the number of messages the user chose at the start.
    private static void sendMessages(Scanner scanner, int numMessages, MessageStore store) {
        for (int i = 0; i < numMessages; i++) {
            System.out.println("\nMessage " + (i + 1) + " of " + numMessages);

            // Recipient: ask until the cell number is valid.
            String recipient;
            Message tempMsg;
            do {
                recipient = ask(scanner, "Enter recipient cell number (e.g. +27838968976): ");
                tempMsg = new Message(i, recipient, "");
                System.out.println(tempMsg.checkRecipientCell(recipient));
            } while (!tempMsg.isRecipientValid(recipient));

            // Message text: ask until it is 250 characters or fewer.
            String msgText;
            Message msg;
            do {
                msgText = ask(scanner, "Enter message text (max 250 characters): ");
                msg = new Message(i, recipient, msgText);
                System.out.println(msg.checkMessageLength());
            } while (!msg.isMessageLengthValid());

            // The user chooses first; details are shown AFTER the choice.
            String status = msg.sentMessage(scanner);
            System.out.println("\nResult: " + status);
            if (msg.getFlag().equals(Message.FLAG_SENT)) {
                msg.printDetails();
            }
            store.addMessage(msg); // fills the Sent / Disregarded / Stored arrays plus hashes and IDs
        }
        System.out.println("\nTotal messages sent: " + Message.returnTotalMessagess());
    }


    // Part 3: the fourth menu option, using the arrays in MessageStore.
    private static void storedMessagesMenu(Scanner scanner, MessageStore store, String sender) {
        int loaded = store.loadStoredFromJson(Message.DEFAULT_JSON_FILE);
        System.out.println("Loaded " + loaded + " stored message(s) from " + Message.DEFAULT_JSON_FILE);
        boolean back = false;
        while (!back) {
            System.out.println("\n--- STORED MESSAGES ---");
            System.out.println("1) Display sender and recipient of stored messages");
            System.out.println("2) Display the longest stored message");
            System.out.println("3) Search for a message ID");
            System.out.println("4) Search messages for a recipient");
            System.out.println("5) Delete a message using its hash");
            System.out.println("6) Display report");
            System.out.println("0) Back to main menu");
            int choice = readInt(scanner, "Choose an option: ");

            if (choice == 1) {
                System.out.println(store.displaySenderAndRecipient(sender));
            } else if (choice == 2) {
                System.out.println(store.longestStoredMessage());
            } else if (choice == 3) {
                String id = ask(scanner, "Enter message ID: ");
                System.out.println("Recipient: " + store.getRecipientByID(id));
                System.out.println("Message: " + store.getMessageByID(id));
            } else if (choice == 4) {
                java.util.List<String> found = store.searchByRecipient(ask(scanner, "Enter recipient number: "));
                System.out.println(found.isEmpty() ? "No messages found." : String.join("\n", found));
            } else if (choice == 5) {
                System.out.println(store.deleteByHash(ask(scanner, "Enter message hash: ").toUpperCase()));
            } else if (choice == 6) {
                System.out.println(store.displayReport());
            } else if (choice == 0) {
                back = true;
            } else {
                System.out.println("Please choose a listed option.");
            }
        }
    }

    private static String ask(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    // Reads a whole number without crashing on bad input.
    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            try {
                return Integer.parseInt(ask(scanner, prompt));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }
}


/*Reference list:
Java Brains, 2019. JUnit 5 Basics [YouTube playlist]. Available at: https://www.youtube.com/watch?v=2E3WqYupx7c&list=PLqq-6Pq4ITTa4ad5JISViSb2FVG8Vwa4o [Accessed 14 September 2026].

NetBeans, 2024. JUnit 5 Tutorial [online]. Apache NetBeans. Available at: https://netbeans.apache.org/tutorial/main/kb/docs/java/junit-intro/ [Accessed 14 September 2026].

Oracle, 2024. Lesson: Regular Expressions [online]. The Java Tutorials. Available at: https://docs.oracle.com/javase/tutorial/essential/regex/ [Accessed 14 September 2026].

Regex101, 2024. Online Regex Tester and Debugger [online]. Available at: https://regex101.com/ [Accessed 14 September 2026].

Stack Overflow, 2023. How to fix "java: package org.junit.jupiter.api does not exist" [online]. Available at: https://stackoverflow.com/questions/75092698/ [Accessed 14 September 2026].
*/