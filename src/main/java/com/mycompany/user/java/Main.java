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
}



/*Reference list:
Java Brains, 2019. JUnit 5 Basics [YouTube playlist]. Available at: https://www.youtube.com/watch?v=2E3WqYupx7c&list=PLqq-6Pq4ITTa4ad5JISViSb2FVG8Vwa4o [Accessed 14 September 2026].

NetBeans, 2024. JUnit 5 Tutorial [online]. Apache NetBeans. Available at: https://netbeans.apache.org/tutorial/main/kb/docs/java/junit-intro/ [Accessed 14 September 2026].

Oracle, 2024. Lesson: Regular Expressions [online]. The Java Tutorials. Available at: https://docs.oracle.com/javase/tutorial/essential/regex/ [Accessed 14 September 2026].

Regex101, 2024. Online Regex Tester and Debugger [online]. Available at: https://regex101.com/ [Accessed 14 September 2026].

Stack Overflow, 2023. How to fix "java: package org.junit.jupiter.api does not exist" [online]. Available at: https://stackoverflow.com/questions/75092698/ [Accessed 14 September 2026].
*/