package edu.csc207.recruitment.actions.user;

import edu.csc207.recruitment.actions.base.AbstractAction;
import edu.csc207.recruitment.actions.base.Action;
import edu.csc207.recruitment.model.db.RecruitmentSystem;
import edu.csc207.recruitment.model.user.User;

/**
 * A parent class for all user actions containing the fields relevant to all user actions
 */
public abstract class UserAction extends AbstractAction implements Action {
    protected User user;
    protected RecruitmentSystem system;

    public UserAction(final String shortcut, final String description) {
        super(shortcut, description);
    }
}
