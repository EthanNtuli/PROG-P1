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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Registration");

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
        obj.registerUser(username, password, cellPhoneNumber);

        System.out.println("\n=== Login ===");

        System.out.print("Enter username: ");
        String loginUsername = scanner.nextLine();

        System.out.print("Enter password: ");
        String loginPassword = scanner.nextLine();

        obj.loginUser( firstName,  lastName,  username,  password,  loginUsername, loginPassword);
        obj.returnLoginStatus(loginResult);
   
        scanner.close();
    }
}