package edu.csc207.recruitment.managers;

import edu.csc207.recruitment.actions.interviewer.GetIntervieweesAction;
import edu.csc207.recruitment.actions.interviewer.RunInterviewAction;
import edu.csc207.recruitment.backgroundactions.interviewer.PrintOutInterviewsToRunDataBackgroundAction;
import edu.csc207.recruitment.backgroundactions.user.HireBackgroundAction;
import edu.csc207.recruitment.model.user.Interviewer;

import java.util.Date;

public class InterviewerManager extends AbstractManager {

    /**
     * Initialize userOptions & backgroundActions for interviewer sessions
     */
    public InterviewerManager(Interviewer interviewer, Date today) {
        addActionToUserOption(new GetIntervieweesAction(interviewer));
        addActionToUserOption(new RunInterviewAction(interviewer, today));

        backgroundActions.add(new PrintOutInterviewsToRunDataBackgroundAction(interviewer));
        backgroundActions.add(new HireBackgroundAction(today));
    }
}
