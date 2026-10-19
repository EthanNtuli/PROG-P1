/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.user.java;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testMessageIDLength() {
        Message msg = new Message(1, "Test message", "+27838968976");
        assertEquals(2, msg.getMessageID().length(), "Message ID must be 2 characters long");
    }

    @Test
    public void testMessageIDIsNumeric() {
        Message msg = new Message(1, "Test message", "+27838968976");
        String id = msg.getMessageID();
        boolean isNumeric = id.matches("[0-9]+");
        assertTrue(isNumeric, "Message ID should only contain numeric characters");
    }

    @Test
    public void testMessageHashCreation_MultipleWords() {
        Message msg = new Message(1, "Hi Mike, can you join us for dinner tonight?", "+27838968976");
        String expectedHash = msg.getMessageID() + ":1:HITONIGHT?";
        assertEquals(expectedHash, msg.getMessageHash(), "Hash must be uppercase and follow format: ID:Num:FIRSTLAST");
    }

    @Test
    public void testMessageHashCreation_SingleWord() {
        Message msg = new Message(2, "Hello", "+27838968976");
        String expectedHash = msg.getMessageID() + ":2:HELLOHELLO";
        assertEquals(expectedHash, msg.getMessageHash(), "Single word hash should repeat the word");
    }

    @Test
    public void testMessageHashCreation_ExtraSpaces() {
        Message msg = new Message(3, "  Hi   there  ", "+27838968976");
        String expectedHash = msg.getMessageID() + ":3:HITHERE";
        assertEquals(expectedHash, msg.getMessageHash(), "Hash should trim and handle extra spaces correctly");
    }

    @Test
    public void testMessageNumberIncrement() {
        Message msg1 = new Message(1, "First", "+27838968976");
        Message msg2 = new Message(5, "Fifth", "+27838968976");
        
        assertTrue(msg1.getMessageHash().contains(":1:"), "Hash should contain message number 1");
        assertTrue(msg2.getMessageHash().contains(":5:"), "Hash should contain message number 5");
    }

    @Test
    public void testMessageTextDisplay() {
        String text = "This is a test message";
        Message msg = new Message(1, text, "+27838968976");
        assertEquals(text, msg.getMessageText(), "Message text should match what was entered");
    }

    @Test
    public void testMessageRecipient() {
        String recipient = "+27838968976";
        Message msg = new Message(1, "Test", recipient);
        assertEquals(recipient, msg.getRecipient(), "Recipient number should be stored correctly");
    }

    @Test
    public void testEmptyMessageText() {
        Message msg = new Message(1, "", "+27838968976");
        assertNotNull(msg.getMessageHash(), "Hash should still be generated even if message is empty");
        assertTrue(msg.getMessageHash().contains(":1:"), "Hash should still contain the message number");
    }
}