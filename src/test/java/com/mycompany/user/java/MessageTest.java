/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.user.java;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Scanner;

public class MessageTest {

    @Test
    public void testMessageIDGenerated() {
        Message message = new Message(1, "+27718693002", "Hi Mike, can you join us for dinner tonight?");

        assertNotNull(message.getMessageID());
        assertEquals(10, message.getMessageID().length());
        assertTrue(message.checkMessageID());
    
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
}