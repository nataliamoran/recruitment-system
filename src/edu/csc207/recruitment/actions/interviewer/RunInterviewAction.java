package edu.csc207.recruitment.actions.interviewer;

import edu.csc207.recruitment.exceptions.input.InvalidInputException;
import edu.csc207.recruitment.model.company.Application;
import edu.csc207.recruitment.model.company.ApplicationStatus;
import edu.csc207.recruitment.model.company.Interview;
import edu.csc207.recruitment.model.user.Interviewer;
import edu.csc207.recruitment.tools.InputHandler;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class RunInterviewAction extends InterviewerAction {
    private static final String INTERVIEW_RECOMMEND = "1";
    private static final String INTERVIEW_REJECT = "2";
    private static final List<String> VALID_OPTIONS = Arrays.asList(INTERVIEW_RECOMMEND, INTERVIEW_REJECT);
    private static final String RUN_INTERVIEW_PREFIX = "Run Interview";
    private Interview interviewToRun;
    private String rejectOrRecommendChoice;
    private String recommendation;
    private Date today;

    public RunInterviewAction(Interviewer interviewer, Date today) {
        super("2", "To run an interview", interviewer);
        this.today = today;
    }

    /**
     * Allows interviewer to choose from all their assigned interviews
     */
    @Override
    public void collectParameters() {
        interviewer.printIntervieweesInfo();
        if (interviewer.getAllInterviewsToRun().isEmpty()) {
            return;
        }
        final String interviewId = InputHandler.getValueFromUser(
                "To interview an applicant enter her/his interview ID: ",
                RUN_INTERVIEW_PREFIX);
        this.interviewToRun = interviewer.getInterviewWithId(interviewId);
        this.rejectOrRecommendChoice = InputHandler.getValueFromUser(
                "Enter 1 to write a recommendation for this applicant, enter 2 to reject the application.",
                RUN_INTERVIEW_PREFIX);
        if (INTERVIEW_RECOMMEND.equals(this.rejectOrRecommendChoice)) {
            this.recommendation = InputHandler.getValueFromUser(
                    "Please write a recommendation.",
                    RUN_INTERVIEW_PREFIX);
        }
    }

    /**
     * Allows user to choose whether they want to reject or accept, enter a recommendation,
     * and changes relevant statuses
     */
    @Override
    public void performAction() {
        if (interviewer.getAllInterviewsToRun().isEmpty()) {
            return;
        }
        if (INTERVIEW_RECOMMEND.equals(this.rejectOrRecommendChoice)) {
            interviewToRun.setRecommendation(recommendation);
        }
        if (!VALID_OPTIONS.contains(this.rejectOrRecommendChoice)) {
            throw new InvalidInputException("This option does not exist.");
        }
        final Application applicationToUpdate = interviewToRun.getApplication();
        applicationToUpdate.incrementAttendedInterviewsByOne(interviewToRun.getInterviewType());
        if (interviewToRun.getRecommendation() == null) {
            applicationToUpdate.setApplicationStatus(ApplicationStatus.REJECTED);
            applicationToUpdate.setClosedDate(today);
            System.out.println("Application has been rejected.");
        } else if (applicationToUpdate.hasUndergoneAllInterviewRounds()) {
            applicationToUpdate.setApplicationStatus(ApplicationStatus.WAITING_FOR_FINALSTEP);
            System.out.println("Applicant is now waiting to be hired.");
        } else {
            applicationToUpdate.setApplicationStatus(ApplicationStatus.WAITING_FOR_NEXT_INTERVIEW);
            System.out.println("Applicant is now waiting for the next interview");
        }
        interviewer.removeInterview(interviewToRun);
    }
}
