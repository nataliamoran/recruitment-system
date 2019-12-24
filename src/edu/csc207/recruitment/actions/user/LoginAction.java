package edu.csc207.recruitment.actions.user;

import edu.csc207.recruitment.exceptions.input.IncorrectPasswordException;
import edu.csc207.recruitment.managers.Manager;
import edu.csc207.recruitment.managers.ManagerFactory;
import edu.csc207.recruitment.model.db.RecruitmentSystemFactory;
import edu.csc207.recruitment.model.user.User;
import edu.csc207.recruitment.tools.InputHandler;

import java.util.Date;

public class LoginAction extends UserAction {
    private String username;
    private String password;
    private Date today;

    public LoginAction() {
        super("2", "To login");
    }

    /**
     * Collect username and password from a user input.
     * If username or password is invalid, then throws a relevant exception.
     */
    @Override
    public void collectParameters() {
        username = InputHandler.getValueFromUser("Enter username:", "Login: ");
        password = InputHandler.getValueFromUser("Enter password:", "Login: ");
        today = InputHandler.getDate();
    }

    /**
     * Method to login a user.
     * If user does not exist, then throws a relevant exception.
     */
    @Override
    public void performAction() {
        User user = RecruitmentSystemFactory.getRecruitmentSystem().getUser(username);
        if (user.getPassword().equals(password)) {
            Manager manager = ManagerFactory.createManager(user, today);
            manager.manage();
        } else {
            throw new IncorrectPasswordException(String.format("Password: %s is incorrect.", password));
        }
    }

}
