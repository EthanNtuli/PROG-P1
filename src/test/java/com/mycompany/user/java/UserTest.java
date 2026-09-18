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

        String firstName = "", lastName = "", username = "kyl_1", password = "", cellPhoneNumber = "";
        User obj = new User(firstName, lastName, username, password, cellPhoneNumber);
        assertTrue(obj.checkUserName(username));
    }

    @Test
    public void testCheckUserName_IncorrectlyFormatted() {
        User obj = new User("", "", "kyle!!!!!!!", "", "");
        assertFalse(obj.checkUserName("kyle!!!!!!!"));
    }

    @Test
    public void testCheckPasswordComplexity_MeetsRequirements() {
        User obj = new User("", "", "kyle!!!!!!!", "", "");

        assertTrue(obj.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testCheckPasswordComplexity_DoesNotMeetRequirements() {

        User obj = new User("", "", "kyle!!!!!!!", "", "");

        assertFalse(obj.checkPasswordComplexity("password"));
    }

    @Test
    public void testCheckCellPhoneNumber_CorrectlyFormatted() {
        User obj = new User("", "", "kyle!!!!!!!", "", "+27838968976");

        assertTrue(obj.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testCheckCellPhoneNumber_IncorrectlyFormatted() {
        User obj = new User("", "", "kyle!!!!!!!", "", "08966553");

        assertFalse(obj.checkCellPhoneNumber("08966553"));
    }

    @Test
    public void testLoginUser_Successful() {
        User login = new User("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(login.loginUser("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginUser_Failed() {
        User login = new User("Kyle", "Smith", "kyl_1", "wrongPassword", "+27838968976");
        assertFalse(login.loginUser("Kyle", "Smith", "kyl_1", "wrongPassword", "ofolf", "dnuanrr"));
    }

    @Test
    public void testRegisterUser_UsernameIncorrectlyFormatted() {
        User login = new User("Kyle", "Smith", "kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976");
        String expected = "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        assertEquals(expected, login.registerUser());
    }

    @Test
    public void testRegisterUser_PasswordDoesNotMeetRequirements() {
        User login = new User("Kyle", "Smith", "kyl_1", "password", "+27838968976");
        String expected = "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        assertEquals(expected, login.registerUser());
    }

    @Test
    public void testRegisterUser_CellPhoneIncorrectlyFormatted() {
        User login = new User("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "08966553");
        String expected = "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        assertEquals(expected, login.registerUser());
    }

    @Test
    public void testRegisterUser_Successful() {
        User login = new User("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Registration successful! You may now log in.",
                login.registerUser());
    }

    @Test
    public void testReturnLoginStatus_Successful() {
        User login = new User("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        login.loginUser("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "kyl_1", "Ch&&sec@ke99!");
        assertEquals("Login was successful", login.returnLoginStatus("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testReturnLoginStatus_Failed() {
        User login = new User("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        login.loginUser("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "kyl_1", "wrongPassword");
        assertEquals("Login was Unsuccessful", login.returnLoginStatus("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "kyl_1", "wrongPassword"));
    }
}
