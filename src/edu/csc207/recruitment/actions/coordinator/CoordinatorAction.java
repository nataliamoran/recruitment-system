package edu.csc207.recruitment.actions.coordinator;

import edu.csc207.recruitment.actions.base.AbstractAction;
import edu.csc207.recruitment.actions.base.Action;
import edu.csc207.recruitment.model.user.Coordinator;

/**
 * An abstract class which is the super class for a list of coordinator actions.
 */
public abstract class CoordinatorAction extends AbstractAction implements Action {
    protected final Coordinator coordinator;

    /**
     * A constructor take coordinator as param.
     *
     * @param coordinator the coordinator who will implement the action.
     */
    public CoordinatorAction(final String shortcut, final String description, final Coordinator coordinator) {
        super(shortcut, description);
        this.coordinator = coordinator;
    }

}
