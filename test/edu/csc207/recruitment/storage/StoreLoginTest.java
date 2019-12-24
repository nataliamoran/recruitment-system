package edu.csc207.recruitment.storage;


import edu.csc207.recruitment.exceptions.input.InvalidCharactersException;
import edu.csc207.recruitment.exceptions.user.UserAlreadyExistsException;
import edu.csc207.recruitment.storage.StoreLogin;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.Assert.*;


public class StoreLoginTest {
    StoreLogin l;

    // If the test fails, be sure to delete everything in the logins file

    @Test
    public void testEverything() {
        String s = File.separator;
        Path p = Paths.get("." + s + "testStorage" + s + "logins");
        l = new StoreLogin(p);

        assertTrue(Files.isRegularFile(Paths.get("." + s + "testStorage" + s + "logins")));

        try {
            l.addUser("user1", "password1");
            l.addUser("user2", "password1");
            l.addUser("User3", "Password3");
        } catch (Exception e) {
            fail();
        }

        try {
            l.addUser("user1", "password1");
            l.addUser("user1", "password2");
            fail();
        } catch (UserAlreadyExistsException e) {
        }

        try {
            l.addUser("User3", "Pass%word3");
            l.addUser("User3,", "Password3");
            fail();
        } catch (InvalidCharactersException e) {
        }

        ArrayList<String> u = l.getUsernames();
        assertEquals(3, u.size());
        assertTrue(u.contains("user1"));
        assertTrue(u.contains("user2"));
        assertTrue(u.contains("User3"));

        assertTrue(l.verifyLogin("user1", "password1"));
        assertTrue(l.verifyLogin("user2", "password1"));
        assertTrue(l.verifyLogin("User3", "Password3"));

        assertFalse(l.verifyLogin("user2", "password2"));
        assertFalse(l.verifyLogin("User3,", "Password3"));
        try {
            PrintWriter pw = new PrintWriter("." + s + "testStorage" + s + "logins");
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println("No file");
            fail();
        }
    }

}