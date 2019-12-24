package edu.csc207.recruitment.managers;

import edu.csc207.recruitment.actions.referee.UploadReferenceLetterAction;
import edu.csc207.recruitment.actions.referee.ViewApplicantsAndPostsAction;
import edu.csc207.recruitment.model.user.Referee;

public class RefereeManager extends AbstractManager {

    /**
     * Initialize userOptions & backgroundActions for applicant sessions
     */
    public RefereeManager(final Referee referee) {
        addActionToUserOption(new ViewApplicantsAndPostsAction(referee));
        addActionToUserOption(new UploadReferenceLetterAction(referee));
    }
}
