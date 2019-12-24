package edu.csc207.recruitment.managers;

import edu.csc207.recruitment.actions.user.ForgotPasswordAction;
import edu.csc207.recruitment.actions.user.LoginAction;
import edu.csc207.recruitment.actions.user.SignUpAction;


public class UserManager extends AbstractManager {

    /**
     * Initialize userOptions for all initial users
     */
    public UserManager() {
        super();
        printExitOption = true;
        addActionToUserOption(new SignUpAction());
        addActionToUserOption(new LoginAction());
        addActionToUserOption(new ForgotPasswordAction());
    }


}