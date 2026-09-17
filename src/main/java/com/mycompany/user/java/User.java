/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.user.java;

/**
 * Handles registration and login for a chat app user.
 *
 * @author Anele
 */
public class User {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellPhoneNumber;
    private boolean loginSuccessful;

    public User(String firstName, String lastName, String username,
            String password, String cellPhoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
    }

    public static boolean checkUserName(String username) { //Ron fix
        boolean UN;

        if (username.contains("_") && username.length() <= 5) {
            System.out.println("Username successfuly captured");
            UN = true;
        } else {
            System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than fuve characters in length");
            UN = false;
        }
        System.out.println("\nUsername check: " + UN);
        return UN;
    }

    public static boolean checkPasswordComplexity(String password) {  //Ron Fix
        String regex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+=-]).{8,}$";
        boolean PC;

        if (password.equals(regex)) {
            System.out.println("Passwird successfully captured");
            PC = true; 
        } else {
            System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight, a capital letter, a number, and a special character.");
            PC = false;
        }
        System.out.println("Password check: " + PC);
        return PC;
    }

    public static boolean checkCellPhoneNumber(String cellPhoneNumber) { //Ron Fix
        String regex = "^\\+27\\d{1,10}$";
        boolean CN;
        if(cellPhoneNumber.matches(regex)){
            System.out.println("Cell phone number successfully addded.");
            CN = true;
        } else{
            System.out.println("Cell phone number incorrectly formatted or does not contain international.");
            CN = false;
        }
        System.out.println("Cell phone number check: " + CN);
        return CN;
    }

    public String registerUser() { //Ron fix
        String result;

        if (!checkUserName(username)) {
            result = "Username is not correctly formatted; please ensure that your "
                    + "username contains an underscore and is no more than five "
                    + "characters in length.";
        } else if (!checkPasswordComplexity(password)) {
            result = "Password is not correctly formatted; please ensure that the "
                    + "password contains at least eight characters, a capital letter, "
                    + "a number, and a special character.";
        } else if (!checkCellPhoneNumber(cellPhoneNumber)) {
            result = "Cell number is incorrectly formatted or does not contain an "
                    + "international code; please correct the number and try again.";
        } else {
            result = "Registration successful! You may now log in.";
        }

        System.out.println("\n" + result);
        return result;
    }

    public boolean loginUser(String firstName, String lastName, String username, String password, String loginUsername, String loginPassword) { //Sir check, Ron fix (entered)
        
        boolean login = true;
        String result;
        
        if(loginUsername.equals(username)&& loginPassword.equals(password)){
            login = true;
            result = "welcome " + firstName + "" + lastName + ".It is great to see you once again.";
        }else {
            login = false;
            result = "Your Password or Username is invalid, please try again.";
        }
        System.out.println("result");
        return login;
    }

    public String returnLoginStatus(String firstName, String lastName, String username, String password, String loginUsername, String loginPassword) {
        

        if (loginUser(firstName, lastName, username, password, loginUsername, loginPassword)) {
            return "Login was successful";
        } else {
            return "Login was Unsuccessful";
        }
    }
}
