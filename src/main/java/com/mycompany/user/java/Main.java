/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Anele
 */
package com.mycompany.user.java;

import java.util.Scanner;

public class Main {
    
    //Runs the console flow: collects registration details, then attempts a login.
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Registration");         //Java Brains, 2019. JUnit 5 Basics [YouTube playlist]. Available at: https://www.youtube.com/watch?v=2E3WqYupx7c&list=PLqq-6Pq4ITTa4ad5JISViSb2FVG8Vwa4o [Accessed 14 September 2026].

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter username (must contain '_' and be no more than 5 characters): ");
        String username = scanner.nextLine();

        System.out.print("Enter password (8+ chars, a capital letter, a number, a special character): ");
        String password = scanner.nextLine();

        System.out.print("Enter cell phone number (e.g. +27838968976): ");
        String cellPhoneNumber = scanner.nextLine();

        User obj = new User(firstName, lastName, username, password, cellPhoneNumber);
        obj.registerUser();
        String registerResult = obj.registerUser();
        String loginUsername = "";
        String loginPassword = "";
        
        if(registerResult.equals("Registration successful! You may now log in.")){
        System.out.print("Enter username: ");
        loginUsername = scanner.nextLine();           //Java Brains, 2019. JUnit 5 Basics [YouTube playlist]. Available at: https://www.youtube.com/watch?v=2E3WqYupx7c&list=PLqq-6Pq4ITTa4ad5JISViSb2FVG8Vwa4o [Accessed 14 September 2026].
        
        System.out.print("Enter password: ");
        loginPassword = scanner.nextLine();
        }else {
            System.out.println("Password or Username are invalid.");
        }
        
        System.out.println(obj.returnLoginStatus(firstName, lastName, username, password, loginUsername, loginPassword));
        
}

// --- PART 2: MENU, LOOPS & MESSAGES ---
        if (isLoggedIn) {
            System.out.println("\nWelcome to the Chat App!");
            boolean running = true;
            
            while (running) {
                // Exact Menu from Rubric
                System.out.println("\n--- MENU ---");
                System.out.println("1) Send Messages");
                System.out.println("2) Show recently sent messages");
                System.out.println("3) Quit");
                System.out.print("Choose an option (1-3): ");
                
                String choice = scanner.nextLine();

                if (choice.equals("1")) {
                    System.out.print("How many messages do you want to send? ");
                    int numMessages = 0;
                    
                    try {
                        numMessages = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number. Returning to menu.");
                        continue;
                    }
                    
                    // For Loop for sending messages
                    for (int i = 1; i <= numMessages; i++) {
                        System.out.println("\nMessage " + i + " of " + numMessages);
                        
                        // Validate Recipient Cell
                        String recipient = "";
                        Message tempMsg = null;
                        boolean validRecipient = false;
                        
                        while (!validRecipient) {
                            System.out.print("Enter recipient cell number (include international code, e.g., +27838968976): ");
                            recipient = scanner.nextLine();
                            
                            // Create temp object just to test the recipient
                            tempMsg = new Message(i, recipient, ""); 
                            if (tempMsg.checkRecipientCell()) {
                                validRecipient = true;
                            } else {
                                System.out.println("Cell phone number is incorrectly formatted or does not contain an international code.");
                            }
                        }
                        
                        // Validate Message Length (< 250 characters)
                        String msgText = "";
                        boolean validLength = false;
                        
                        while (!validLength) {
                            System.out.print("Enter message text (max 250 characters): ");
                            msgText = scanner.nextLine();
                            
                            if (msgText.length() <= 250) {
                                validLength = true;
                            } else {
                                System.out.println("Please enter a message of less than 250 characters.");
                            }
                        }
                        
                        // Now create the final message object
                        Message msg = new Message(i, recipient, msgText);
                        
                        // Send and Save
                        String status = msg.sentMessage();
                        System.out.println(status);
                        
                        msg.printMessageDetails();
                        msg.saveToJson(); 
                    }
                    
                } else if (choice.equals("2")) {
                    System.out.println("Coming Soon.");
                } else if (choice.equals("3")) {
                    System.out.println("Exiting application. Goodbye!");
                    System.out.println("Total messages sent: " + Message.getTotalMessagesSent());
                    Message.closeJsonFile(); // Properly closes the JSON array
                    running = false; 
                } else {
                    System.out.println("Invalid option. Please enter 1, 2, or 3.");
                }
            }
        }
        
        scanner.close();
    }
}


/*Reference list:
Java Brains, 2019. JUnit 5 Basics [YouTube playlist]. Available at: https://www.youtube.com/watch?v=2E3WqYupx7c&list=PLqq-6Pq4ITTa4ad5JISViSb2FVG8Vwa4o [Accessed 14 September 2026].

NetBeans, 2024. JUnit 5 Tutorial [online]. Apache NetBeans. Available at: https://netbeans.apache.org/tutorial/main/kb/docs/java/junit-intro/ [Accessed 14 September 2026].

Oracle, 2024. Lesson: Regular Expressions [online]. The Java Tutorials. Available at: https://docs.oracle.com/javase/tutorial/essential/regex/ [Accessed 14 September 2026].

Regex101, 2024. Online Regex Tester and Debugger [online]. Available at: https://regex101.com/ [Accessed 14 September 2026].

Stack Overflow, 2023. How to fix "java: package org.junit.jupiter.api does not exist" [online]. Available at: https://stackoverflow.com/questions/75092698/ [Accessed 14 September 2026].
*/