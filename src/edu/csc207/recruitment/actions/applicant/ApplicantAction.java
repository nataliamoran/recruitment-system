package edu.csc207.recruitment.actions.applicant;

import edu.csc207.recruitment.actions.base.AbstractAction;
import edu.csc207.recruitment.actions.base.Action;
import edu.csc207.recruitment.model.user.Applicant;

/**
 * A parent class for all actions relevant for applicant,
 * requires the applicant as a field
 */
public abstract class ApplicantAction extends AbstractAction implements Action {
    protected Applicant applicant;

    public ApplicantAction(final String shortcut, final String description, Applicant applicant) {
        super(shortcut, description);
        this.applicant = applicant;
    }
}
