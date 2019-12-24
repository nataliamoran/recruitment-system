package edu.csc207.recruitment.managers;

import edu.csc207.recruitment.actions.coordinator.*;
import edu.csc207.recruitment.backgroundactions.user.HireBackgroundAction;
import edu.csc207.recruitment.model.user.Coordinator;

import java.util.Date;

public class CoordinatorManager extends AbstractManager {

    /**
     * Initialize userOptions & backgroundActions for coordinator sessions
     */
    public CoordinatorManager(final Coordinator coordinator, final Date today) {
        addActionToUserOption(new CreateInterviewAction(coordinator));
        addActionToUserOption(new CreateJobPostingAction(coordinator));
        addActionToUserOption(new GetApplicantsInfoAction(coordinator));
        addActionToUserOption(new GetJobPostingApplicantsAction(coordinator));
        addActionToUserOption(new HireApplicantAction(coordinator, today));
        addActionToUserOption(new CloseJobPostingAction(coordinator, today));
        addActionToUserOption(new ReadReferenceLetterAction(coordinator));
        backgroundActions.add(new HireBackgroundAction(today));
    }

}
