package edu.csc207.recruitment.managers;

import edu.csc207.recruitment.actions.applicant.*;
import edu.csc207.recruitment.backgroundactions.applicant.CalculateNumDaysSinceLastApplClosedBackgroundAction;
import edu.csc207.recruitment.backgroundactions.applicant.RemoveApplicantAllDocumentsAfter30DaysSinceLastApplicationClosureBackgroundAction;
import edu.csc207.recruitment.backgroundactions.applicant.UpdateApplicantBackgroundAction;
import edu.csc207.recruitment.backgroundactions.user.HireBackgroundAction;
import edu.csc207.recruitment.model.user.Applicant;

import java.util.Date;

public class ApplicantManager extends AbstractManager {

    /**
     * Initialize userOptions & backgroundActions for applicant sessions
     */
    public ApplicantManager(final Applicant applicant, final Date today) {
        addActionToUserOption(new UploadDocumentAction(applicant));
        addActionToUserOption(new ReadDocumentAction(applicant));
        addActionToUserOption(new GetAllOpenJobPostingsAction(applicant));
        addActionToUserOption(new GetApplicationsAction(applicant));
        addActionToUserOption(new GetAccountCreationDateAction(applicant));
        addActionToUserOption(new GetNumDaysSinceLastAppClosedAction(applicant));
        addActionToUserOption(new ApplyForJobAction(applicant, today));
        addActionToUserOption(new WithdrawApplicationAction(applicant, today));
        addActionToUserOption(new AddRefereeAction(applicant));

        backgroundActions.add(new CalculateNumDaysSinceLastApplClosedBackgroundAction(applicant, today));
        backgroundActions.add(new RemoveApplicantAllDocumentsAfter30DaysSinceLastApplicationClosureBackgroundAction(applicant));
        backgroundActions.add(new HireBackgroundAction(today));
        backgroundActions.add(new UpdateApplicantBackgroundAction(applicant));

    }


}


