/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.user.java;

import java.util.Scanner;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    // Test 1: Message ID is created
    @Test
    public void testMessageIDGenerated() {
        Message message = new Message(1, "+27718693002", "Hi Mike, can you join us for dinner tonight?");

        assertNotNull(message.getMessageID());
        assertEquals(10, message.getMessageID().length());
        assertTrue(message.checkMessageID());
    }

    // Test 2: Message 2 data, user selects Discard
    @Test
    public void testMessage2Discard() {
        Message message2 = new Message(2, "08575975889", "Hi Keegan, did you receive the payment?");

        int totalBefore = Message.returnTotalMessagess();

        // Select Discard (option 2), then enter 0 to confirm deletion
        Scanner input = new Scanner("2\n0\n");
        String result = message2.sentMessage(input);

        assertEquals("Message deleted by user.", result);
        assertEquals(totalBefore, Message.returnTotalMessagess());
    }

    // Test 3: Message length success
    @Test
    public void testMessageLengthSuccess() {
        Message message = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message ready to send.", message.checkMessageLength());
    }

    // Test 4: Message length failure (260 characters = 10 over the limit)
    @Test
    public void testMessageLengthFailure() {
        String longText = "a".repeat(260);
        Message message = new Message(0, "+27718693002", longText);
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.",
                message.checkMessageLength());
    }

    // Test 5: Recipient number success
    @Test
    public void testRecipientNumberSuccess() {
        Message message = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Cell phone number successfully captured.",
                message.checkRecipientCell("+27718693002"));
    }

    // Test 6: Recipient number failure
    @Test
    public void testRecipientNumberFailure() {
        Message message = new Message(1, "08575975889", "Hi Keegan, did you receive the payment?");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                message.checkRecipientCell("08575975889"));
    }

    // Test 7: Message hash
    @Test
    public void testMessageHash() {
        String hash = Message.createMessageHash("0012345678", 0, "Hi Mike, can you join us for dinner tonight?");
        assertEquals("00:0:HITONIGHT", hash);
    }

    // Test 8: Send message (option 1)
    @Test
    public void testSendMessage() {
        Message message = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message successfully sent.", message.sentMessage(new Scanner("1\n")));
    }

    // Test 9: Store message (option 3)
    @Test
    public void testStoreMessage() {
        Message message = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message successfully stored.", message.sentMessage(new Scanner("3\n")));
    }

    // Test 10: Return total number sent
    @Test
    public void testReturnTotalNumberSent() {
        int before = Message.returnTotalMessagess();

        new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?")
                .sentMessage(new Scanner("1\n"));
        new Message(1, "08575975889", "Hi Keegan, did you receive the payment?")
                .sentMessage(new Scanner("1\n"));

        assertEquals(before + 2, Message.returnTotalMessagess());
    }
}