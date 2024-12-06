import org.example.PasswordManager;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Map;
import static org.junit.Assert.*;

public class PasswordManagerTest {
    private PasswordManager passwordManager;

    @Before
    public void setUp() {
        passwordManager = new PasswordManager();
    }


    @Test
    public void testGetUser() {
        passwordManager.addUser("test_user", "test_password123");
        Map<String, String> userRecord = passwordManager.getUser("test_user");
        assertNotNull("User record should be retrieved.", userRecord);
        assertEquals("Username should match.", "test_user", userRecord.get("username"));
        assertNotNull("Salt should not be null.", userRecord.get("salt"));
        assertNotNull("Hash should not be null.", userRecord.get("hash"));
    }



    @Test
    public void testPasswordFileIntegrity() {
        passwordManager.addUser("user1", "password1");
        passwordManager.addUser("user2", "password2");

        Map<String, String> user1 = passwordManager.getUser("user1");
        assertNotNull("User1 record should exist.", user1);
        assertTrue("Password1 should be verified.",
                passwordManager.verifyPassword("password1", user1.get("salt"), user1.get("hash")));

        Map<String, String> user2 = passwordManager.getUser("user2");
        assertNotNull("User2 record should exist.", user2);
        assertTrue("Password2 should be verified.",
                passwordManager.verifyPassword("password2", user2.get("salt"), user2.get("hash")));
    }

    @Test
    public void testValidPassword() {
        assertTrue(passwordManager.proActivePasswordChecker("Valid@123"));
    }

    @Test
    public void testShortPassword() {
        assertFalse(passwordManager.proActivePasswordChecker("Short1@"));
    }

    @Test
    public void testNoLetter() {
        assertFalse(passwordManager.proActivePasswordChecker("12345678@"));
    }

    @Test
    public void testNoNumber() {
        assertFalse(passwordManager.proActivePasswordChecker("Password@"));
    }

    @Test
    public void testNoUppercase() {
        assertFalse(passwordManager.proActivePasswordChecker("password1@"));
    }

    @Test
    public void testNoLowercase() {
        assertFalse(passwordManager.proActivePasswordChecker("PASSWORD1@"));
    }

    @Test
    public void testNoSpecialCharacter() {
        assertFalse(passwordManager.proActivePasswordChecker("Password1"));
    }

    @Test
    public void testContainsSpace() {
        assertFalse(passwordManager.proActivePasswordChecker("Password 1@"));
    }

    @Test
    public void testContainsPunctuation() {
        assertFalse(passwordManager.proActivePasswordChecker("Password1@!"));
    }

    @Test
    public void testPasswordNotInWorstPasswordsList() {
        PasswordManager pm = new PasswordManager();
        assertTrue(pm.proActivePasswordChecker("Strong@123"));
    }
}
