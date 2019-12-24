package edu.csc207.recruitment.storage;

import edu.csc207.recruitment.exceptions.base.RecruitmentSystemIOException;
import edu.csc207.recruitment.exceptions.input.InvalidCharactersException;
import edu.csc207.recruitment.exceptions.user.UserAlreadyExistsException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Stores usernames and passwords, and allows you to verify a login
 */
public class StoreLogin {
    private final Path userData;

    /**
     * Stores the usernames and corresponding passwords in a file
     *
     * @param userData the path to file that files are to be stored in, and to retrieve content
     */
    public StoreLogin(Path userData) {
        this.userData = userData;
    }

    public StoreLogin(String path) {
        this(Paths.get(path));
    }

    public StoreLogin() {
        this(Paths.get("./testStorage/logins"));
    }

    /**
     * Adds a user with a username and password
     *
     * @param username
     * @param password
     */
    public void addUser(final String username, final String password) throws
            InvalidCharactersException, UserAlreadyExistsException, RecruitmentSystemIOException {
        if (!(checkValidCharacters(username) && checkValidCharacters(password))) {
            throw new InvalidCharactersException("Username and password may only contain alpha-numeric characters");
        }
        if (getUsernames().contains(username)) {
            throw new UserAlreadyExistsException(String.format("Username %s already exists", username));
        }
        String s = username + "," + password;
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(userData.toFile(), true));
            writer.newLine();
            writer.write(s);
            writer.close();
        } catch (IOException e) {
            throw new RecruitmentSystemIOException("IO exception");
        } catch (NullPointerException f) {
            f.printStackTrace();
        }
    }

    /**
     * @param username
     * @param password
     * @return if this username and password combination matches what is stored
     */
    public boolean verifyLogin(String username, String password) {
        HashMap<String, String> l = getLogins();
        return l.containsKey(username) && l.get(username).equals(password);
    }

    /**
     * @return a list of all the usernames currently stored
     */
    public ArrayList<String> getUsernames() {
        HashMap<String, String> l = getLogins();
        ArrayList<String> u = new ArrayList<>();
        u.addAll(l.keySet());
        return u;
    }

    private boolean checkValidCharacters(String s) {
        return s.matches("[a-zA-Z0-9]+");
    }

    private HashMap<String, String> getLogins() {
        HashMap<String, String> userMap = new HashMap<>();
        try {
            List<String> lines = Files.lines(userData).collect(Collectors.toList());
            for (String line : lines) {
                if (!line.equals("")) {
                    String[] split = line.split(",");
                    userMap.put(split[0], split[1]);
                }
            }
            return userMap;
        } catch (IOException e) {
            throw new RecruitmentSystemIOException("IO exception");
        }

    }
}
