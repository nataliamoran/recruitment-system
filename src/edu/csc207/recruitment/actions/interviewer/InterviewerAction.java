package edu.csc207.recruitment.actions.interviewer;

import edu.csc207.recruitment.actions.base.AbstractAction;
import edu.csc207.recruitment.actions.base.Action;
import edu.csc207.recruitment.model.user.Interviewer;

/**
 * An abstract class which is the parent class of list of interviewer actions.
 */
public abstract class InterviewerAction extends AbstractAction implements Action {
    protected final Interviewer interviewer;

    /**
     * Constructor of InterviewerAction which take interview as the only param.
     *
     * @param interviewer the interviewer who will implement the action.
     */
    public InterviewerAction(final String shortcut, final String description, final Interviewer interviewer) {
        super(shortcut, description);
        this.interviewer = interviewer;
    }

}
