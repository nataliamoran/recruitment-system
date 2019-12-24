package edu.csc207.recruitment.backgroundactions.interviewer;

import edu.csc207.recruitment.model.user.Interviewer;

public class PrintOutInterviewsToRunDataBackgroundAction extends InterviewerBackgroundAction {

    public PrintOutInterviewsToRunDataBackgroundAction(final Interviewer interviewer) {
        super(interviewer);
    }

    /**
     * Automatically prints out interviewees data when an interviewer logs in
     */
    public void performAction() {
        interviewer.printIntervieweesInfo();
    }
}
