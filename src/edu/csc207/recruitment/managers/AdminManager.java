package edu.csc207.recruitment.managers;

import edu.csc207.recruitment.actions.admin.CreateCompanyAction;
import edu.csc207.recruitment.actions.admin.CreateUserAction;
import edu.csc207.recruitment.model.user.Admin;


public class AdminManager extends AbstractManager {


    /**
     * Initialize userOptions & backgroundActions for admin sessions
     */
    public AdminManager(final Admin admin) {
        addActionToUserOption(new CreateCompanyAction(admin));
        addActionToUserOption(new CreateUserAction());
    }
}
