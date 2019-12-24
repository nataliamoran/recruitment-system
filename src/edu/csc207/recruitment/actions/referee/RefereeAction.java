package edu.csc207.recruitment.actions.referee;

import edu.csc207.recruitment.actions.base.AbstractAction;
import edu.csc207.recruitment.actions.base.Action;
import edu.csc207.recruitment.model.user.Referee;

/**
 * Parent class for all referee actions
 * Requires a referee
 */
public abstract class RefereeAction extends AbstractAction implements Action {
    protected final Referee referee;

    public RefereeAction(final String shortcut, final String description, final Referee referee) {
        super(shortcut, description);
        this.referee = referee;
    }
}
