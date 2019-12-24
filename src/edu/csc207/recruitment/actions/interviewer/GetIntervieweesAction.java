package edu.csc207.recruitment.actions.interviewer;

import edu.csc207.recruitment.model.user.Interviewer;

public class GetIntervieweesAction extends InterviewerAction {

    public GetIntervieweesAction(Interviewer interviewer) {
        super("1", "To see a list of interviewees assigned to you", interviewer);
    }

    @Override
    public void collectParameters() {

    }

    /**
     * Method to print out interviewees assigned to the particular interviewer
     */
    @Override
    public void performAction() {
        interviewer.printIntervieweesInfo();
    }

}
