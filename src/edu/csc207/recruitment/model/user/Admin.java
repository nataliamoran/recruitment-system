package edu.csc207.recruitment.model.user;


/**
 * This class represents an Admin, a type of User.
 */
public class Admin extends User {

    public Admin(String username, String password) {
        super(username, password, UserType.ADMIN);
    }
}
