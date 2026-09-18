/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.user.java;

/**
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
    
    //creates a new user and stores the details given at registration.
    public User(String firstName, String lastName, String username,
            String password, String cellPhoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
    }
    //Checks that the username contains an underscore and is no more than five characters long.
    
    public boolean checkUserName(String username) { 
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
    
    //Checks that the password meets the complexity rules using a regular expression
    public boolean checkPasswordComplexity(String password) {  
        String regex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+=-]).{8,}$";   //Regex101, 2024. Online Regex Tester and Debugger [online]. Available at: https://regex101.com/ [Accessed 14 September 2026].
        boolean PC;

        if (password.matches(regex)) {
            System.out.println("Passwird successfully captured");
            PC = true; 
        } else {
            System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight, a capital letter, a number, and a special character.");
            PC = false;
        }
        System.out.println("Password check: " + PC);
        return PC;
    }
    
    //Checks that the cell phone number starts with +27|0 and has no more than ten digits after it.
    public boolean checkCellPhoneNumber(String cellPhoneNumber) { 
        String regex = "^\\+27\\d{1,10}$";  //Regex101, 2024. Online Regex Tester and Debugger [online]. Available at: https://regex101.com/ [Accessed 14 September 2026].
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

    //Runs the three checks in order and returns the message matching the first one that fails, or a success message.
    public String registerUser() {
        String result;

        if (!checkUserName(username)) {
            result = "Username is not correctly formatted; please ensure that your "
                    + "username contains an underscore and is no more than five "
                    + "characters in length.";
        } else if (!checkPasswordComplexity(password)) {  //NetBeans, 2024. JUnit 5 Tutorial [online]. Apache NetBeans. Available at: https://netbeans.apache.org/tutorial/main/kb/docs/java/junit-intro/ [Accessed 14 September 2026].
            result = "Password is not correctly formatted; please ensure that the "
                    + "password contains at least eight characters, a capital letter, "
                    + "a number, and a special character.";
        } else if (!checkCellPhoneNumber(cellPhoneNumber)) {                        //The reason i added the if even though it wasnt mandatory was 
            result = "Cell number is incorrectly formatted or does not contain an " //the logic behind it is that without the if a user could login even though the cell numbers are incorrectly formated
                    + "international code; please correct the number and try again.";
        } else {
            result = "Registration successful! You may now log in.";
        }

        System.out.println("\n" + result);
        return result;
    }

    //Compares the entered login details against the registered details and returns whether they match or not.
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
        System.out.println(result);
        return login;
    }

    //Returns a simple sucess or failure message based on the outcome of loginUser.
    public String returnLoginStatus(String firstName, String lastName, String username, String password, String loginUsername, String loginPassword) {
        

        if (loginUser(firstName, lastName, username, password, loginUsername, loginPassword)) {
            return "Login was successful";
        } else {
            return "Login was Unsuccessful";
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
