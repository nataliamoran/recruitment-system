package edu.csc207.recruitment.backgroundactions.applicant;

import edu.csc207.recruitment.backgroundactions.base.BackgroundAction;
import edu.csc207.recruitment.model.user.Applicant;

/**
 * A parent class for all background actions relevant to applicant,
 * requiring the applicant as a field
 */
public abstract class ApplicantBackgroundAction implements BackgroundAction {

    protected final Applicant applicant;

    ApplicantBackgroundAction(final Applicant applicant) {
        this.applicant = applicant;
    }
}
