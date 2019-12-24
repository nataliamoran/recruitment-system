package edu.csc207.recruitment.actions.admin;

import edu.csc207.recruitment.actions.base.AbstractAction;
import edu.csc207.recruitment.actions.base.Action;
import edu.csc207.recruitment.model.user.Admin;

/**
 * A parent class for all actions relevant for admin,
 * requires the admin as a field
 */
public abstract class AdminAction extends AbstractAction implements Action {
    protected Admin admin;

    public AdminAction(final String shortcut, final String description, Admin admin) {
        super(shortcut, description);
        this.admin = admin;
    }
}
