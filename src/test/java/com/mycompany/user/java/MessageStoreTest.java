/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
/**
 *
 * @author Anele
 */

package com.mycompany.user.java;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class MessageStoreTest {

    private MessageStore store;

    // Test data Messages 1-5 from the brief (IDs are fixed so hashes are predictable).
    @BeforeEach
    public void setUp() {
        store = new MessageStore();
        store.addMessage("1000000000", 1, "+27834557896", "Did you get the cake?", "Sent");
        store.addMessage("2000000000", 2, "+27838884567",
                "Where are you? You are late! I have asked you to be on time.", "Stored");
        store.addMessage("3000000000", 3, "+27834484567", "Yohoooo, I am at your gate.", "Disregard");
        store.addMessage("0838884567", 4, "0838884567", "It is dinner time!", "Sent");
        store.addMessage("5000000000", 5, "+27838884567", "Ok, I am leaving without you.", "Stored");
    }

    @Test
    public void testSentMessagesArrayPopulated() {
        assertEquals(Arrays.asList("Did you get the cake?", "It is dinner time!"), store.getSentMessages());
    }

    @Test
    public void testDisregardedAndStoredArraysPopulated() {
        assertEquals(List.of("Yohoooo, I am at your gate."), store.getDisregardedMessages());
        assertEquals(2, store.getStoredMessages().size());
    }

    @Test
    public void testHashesAndIDsArraysPopulated() {
        assertEquals(5, store.getMessageHashes().size());
        assertEquals(5, store.getMessageIDs().size());
    }

    @Test
    public void testLongestMessage() {
        assertEquals("Where are you? You are late! I have asked you to be on time.",
                store.longestStoredMessage());
    }

    @Test
    public void testSearchByMessageID() {
        assertEquals("It is dinner time!", store.getMessageByID("0838884567"));
    }

    @Test
    public void testSearchByRecipient() {
        assertEquals(Arrays.asList("Where are you? You are late! I have asked you to be on time.",
                "Ok, I am leaving without you."), store.searchByRecipient("+27838884567"));
    }

    @Test
    public void testDeleteByHash() {
        assertEquals("Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.",
                store.deleteByHash("20:2:WHERETIME"));
        assertFalse(store.getMessageHashes().contains("20:2:WHERETIME"));
        assertEquals(1, store.getStoredMessages().size());
    }

    @Test
    public void testDeleteUnknownHash() {
        assertEquals("No message found with that hash.", store.deleteByHash("00:0:NOPE"));
    }

    @Test
    public void testReportContainsHashRecipientAndMessage() {
        String report = store.displayReport();
        assertTrue(report.contains("20:2:WHERETIME"));
        assertTrue(report.contains("+27838884567"));
        assertTrue(report.contains("It is dinner time!"));
        assertFalse(report.contains("Yohoooo"));
    }

    
    @Test
    public void testJsonStoreAndLoad(@TempDir java.nio.file.Path dir) {
        String file = dir.resolve("test-messages.json").toString();
        new Message("9000000001", 1, "+27834557896", "Say \"hi\", please!").storeMessage(file);
        new Message("9000000002", 2, "+27838884567", "Second message").storeMessage(file);

        MessageStore loaded = new MessageStore();
        assertEquals(2, loaded.loadStoredFromJson(file));
        assertEquals("Say \"hi\", please!", loaded.getMessageByID("9000000001"));
        assertEquals("+27838884567", loaded.getRecipientByID("9000000002"));
        assertEquals(2, loaded.getStoredMessages().size());
    }

    @Test
    public void testLongestMessageDetails() {
        String details = store.longestStoredMessageDetails();
        assertTrue(details.contains("+27838884567"));
        assertTrue(details.contains("20:2:WHERETIME"));
        assertTrue(details.contains("Where are you?"));
    }

    @Test
    public void testDisplayStoredMessages() {
        String shown = store.displayStoredMessages();
        assertTrue(shown.contains("Ok, I am leaving without you."));
        assertFalse(shown.contains("Did you get the cake?"));
    }
}