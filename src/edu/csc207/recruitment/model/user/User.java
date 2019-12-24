package edu.csc207.recruitment.model.user;

public abstract class User {
    private final String username;
    private final UserType userType;
    private final int id;
    private String password;
    private static int nextId = 0;

    public User(final String username, final String password, final UserType userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.id = nextId;
        nextId++;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return this.id;
    }

    public UserType getUserType() {
        return this.userType;
    }

    public static void setNextId(int nextId) {
        User.nextId = nextId;
    }
}

