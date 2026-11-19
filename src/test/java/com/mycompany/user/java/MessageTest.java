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
    @Test   //Java Brains, 2019. JUnit 5 Basics [YouTube playlist]. Available at: https://www.youtube.com/watch?v=2E3WqYupx7c&list=PLqq-6Pq4ITTa4ad5JISViSb2FVG8Vwa4o [Accessed 6 October 2026].
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
        Scanner input = new Scanner("2\n0\n");   //Oracle, 2024. Scanner (Java Platform SE 8) [online]. Java Platform API Specification. Available at: https://docs.oracle.com/javase/8/docs/api/java/util/Scanner.html [Accessed 17 October 2026].
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
        String longText = "a".repeat(260);   //Oracle, 2024. String (Java Platform SE 8) [online]. Java Platform API Specification. Available at: https://docs.oracle.com/javase/8/docs/api/java/lang/String.html [Accessed 17 October 2026].
        Message message = new Message(0, "+27718693002", longText);
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.",
                message.checkMessageLength());
    }

    // Test 5: Recipient number success
    @Test
    public void testRecipientNumberSuccess() {
        Message message = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Cell phone number successfully captured.",
                message.checkRecipientCell("+27718693002"));   //Regex101, 2024. Online Regex Tester and Debugger [online]. Available at: https://regex101.com/ [Accessed 11 October 2026].
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


/*Reference list:
Java Brains, 2019. JUnit 5 Basics [YouTube playlist]. Available at: https://www.youtube.com/watch?v=2E3WqYupx7c&list=PLqq-6Pq4ITTa4ad5JISViSb2FVG8Vwa4o [Accessed 14 September 2026].

NetBeans, 2024. JUnit 5 Tutorial [online]. Apache NetBeans. Available at: https://netbeans.apache.org/tutorial/main/kb/docs/java/junit-intro/ [Accessed 14 September 2026].

Oracle, 2024. Scanner (Java Platform SE 8) [online]. Java Platform API Specification. Available at: https://docs.oracle.com/javase/8/docs/api/java/util/Scanner.html [Accessed 17 October 2026].

Oracle, 2024. String (Java Platform SE 8) [online]. Java Platform API Specification. Available at: https://docs.oracle.com/javase/8/docs/api/java/lang/String.html [Accessed 17 October 2026].

Regex101, 2024. Online Regex Tester and Debugger [online]. Available at: https://regex101.com/ [Accessed 11 October 2026].

Stack Overflow, 2023. How to fix "java: package org.junit.jupiter.api does not exist" [online]. Available at: https://stackoverflow.com/questions/75092698/ [Accessed 14 September 2026].
*/