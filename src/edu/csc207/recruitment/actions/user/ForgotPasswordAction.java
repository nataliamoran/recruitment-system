package edu.csc207.recruitment.actions.user;

import edu.csc207.recruitment.model.db.RecruitmentSystemFactory;
import edu.csc207.recruitment.model.user.User;
import edu.csc207.recruitment.tools.InputHandler;

public class ForgotPasswordAction extends UserAction {
    private String username;

    public ForgotPasswordAction() {
        super("3", "To restore password");
    }

    /**
     * Collect the username from user input.
     */
    @Override
    public void collectParameters() {
        username = InputHandler.getValueFromUser("Enter username:", "Restore Password: ");
    }

    /**
     * Method that prints out the password of the user.
     * If the user does not exist, then throws a relevant exception.
     */
    @Override
    public void performAction() {
        User user = RecruitmentSystemFactory.getRecruitmentSystem().getUser(username);
        System.out.println(String.format("Password: %s", user.getPassword()));
    }

}
