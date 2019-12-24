package edu.csc207.recruitment.backgroundactions.interviewer;

import edu.csc207.recruitment.backgroundactions.base.BackgroundAction;
import edu.csc207.recruitment.model.user.Interviewer;


/**
 * A parent class for all background actions relevant to interviewer,
 * requiring the interviewer as a field
 */
public abstract class InterviewerBackgroundAction implements BackgroundAction {
    protected final Interviewer interviewer;

    InterviewerBackgroundAction(final Interviewer interviewer) {
        this.interviewer = interviewer;
    }

}
