/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

/**
 *
 * @author Anele
 */
package com.mycompany.user.java;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testCheckUserName_CorrectlyFormatted() {
        assertTrue(User.checkUserName("kyl_1"));
    }

    @Test
    public void testCheckUserName_IncorrectlyFormatted() {
        assertFalse(User.checkUserName("kyle!!!!!!!"));
    }

    @Test
    public void testCheckPasswordComplexity_MeetsRequirements() {
        assertTrue(User.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testCheckPasswordComplexity_DoesNotMeetRequirements() {
        assertFalse(User.checkPasswordComplexity("password"));
    }

    @Test
    public void testCheckCellPhoneNumber_CorrectlyFormatted() {
        assertTrue(User.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testCheckCellPhoneNumber_IncorrectlyFormatted() {
        assertFalse(User.checkCellPhoneNumber("08966553"));
    }

    @Test
    public void testLoginUser_Successful() {
        User login = new User("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginUser_Failed() {
        User login = new User("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertFalse(login.loginUser("kyl_1", "wrongPassword"));
    }

    @Test
    public void testRegisterUser_UsernameIncorrectlyFormatted() {
        User login = new User("Kyle", "Smith", "kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976");
        String expected = "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        assertEquals(expected, login.registerUser("kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976"));
    }

    @Test
    public void testRegisterUser_PasswordDoesNotMeetRequirements() {
        User login = new User("Kyle", "Smith", "kyl_1", "password", "+27838968976");
        String expected = "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        assertEquals(expected, login.registerUser("kyl_1", "password", "+27838968976"));
    }

    @Test
    public void testRegisterUser_CellPhoneIncorrectlyFormatted() {
        User login = new User("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "08966553");
        String expected = "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        assertEquals(expected, login.registerUser("kyl_1", "Ch&&sec@ke99!", "08966553"));
    }

    @Test
    public void testRegisterUser_Successful() {
        User login = new User("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Registration successful! You may now log in.",
                login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976"));
    }

    @Test
    public void testReturnLoginStatus_Successful() {
        User login = new User("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        login.loginUser("kyl_1", "Ch&&sec@ke99!");
        assertEquals("Welcome Kyle, Smith it is great to see you.", login.returnLoginStatus());
    }

    @Test
    public void testReturnLoginStatus_Failed() {
        User login = new User("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        login.loginUser("kyl_1", "wrongPassword");
        assertEquals("Username or password incorrect, please try again.", login.returnLoginStatus());
    }
}