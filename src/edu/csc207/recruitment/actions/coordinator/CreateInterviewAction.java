package edu.csc207.recruitment.actions.coordinator;

import edu.csc207.recruitment.exceptions.interview.InterviewIsNotRequiredException;
import edu.csc207.recruitment.exceptions.user.UserNotFoundException;
import edu.csc207.recruitment.model.company.Application;
import edu.csc207.recruitment.model.company.ApplicationStatus;
import edu.csc207.recruitment.model.company.Interview;
import edu.csc207.recruitment.model.db.RecruitmentSystemFactory;
import edu.csc207.recruitment.model.user.Coordinator;
import edu.csc207.recruitment.model.user.Interviewer;
import edu.csc207.recruitment.model.user.User;
import edu.csc207.recruitment.model.user.UserType;
import edu.csc207.recruitment.tools.InputHandler;
import edu.csc207.recruitment.tools.OutputHandler;

import java.util.List;

public class CreateInterviewAction extends CoordinatorAction {
    private Application chosenApplication;
    private Interviewer chosenInterviewer;
    private String chosenInterviewType;

    public CreateInterviewAction(Coordinator coordinator) {
        super("1", "To create a new interview", coordinator);
    }

    /**
     * Method to collect an application, an interviewer and an interview type for a new interview
     */
    @Override
    public void collectParameters() {
        //reset fields
        chosenApplication = null;
        chosenInterviewer = null;
        chosenInterviewType = null;
        //collect fields
        final List<Application> applicationsWaitingForInterview =
                coordinator.getCompany().getApplicationsPerStatus(ApplicationStatus.WAITING_FOR_NEXT_INTERVIEW);
        OutputHandler.printApplicationsList(applicationsWaitingForInterview);
        if (applicationsWaitingForInterview.isEmpty()) {
            return;
        }
        //choose application
        chooseApplication();

        // choose interview type
        chooseInterviewType();

        // choose interviewer
        chooseInterviewer();
    }

    /**
     * Method to create a new interview
     */
    @Override
    public void performAction() {
        if (chosenInterviewer == null || chosenApplication == null || chosenInterviewType == null) {
            return;
        }
        Interview interview = new Interview(chosenInterviewer, chosenApplication, chosenInterviewType);
        chosenApplication.getJobPosting().addInterview(interview);
        chosenInterviewer.addInterview(interview);
        chosenApplication.setInterview(interview);
        chosenApplication.setApplicationStatus(ApplicationStatus.INTERVIEW_SCHEDULED);
        System.out.println(String.format("Interview is created. Interview type: %s  Interviewee: %s  Interviewer: %s",
                chosenInterviewType, chosenApplication.getApplicant().getUsername(), chosenInterviewer.getUsername()));
    }


    /**
     * Allows a coordinator to choose an application to schedule an interview for
     */
    private void chooseApplication() {
        String applicationID = InputHandler.getValueFromUser(
                "Enter ID of the application to schedule an interview for its applicant: ",
                "Creates Interview: ");
        chosenApplication = coordinator.getCompany().getApplicationPerID(applicationID);
    }

    /**
     * Allows a coordinator to choose an interview type
     */
    private void chooseInterviewType() {
        OutputHandler.printApplicationInterviewsInfo(chosenApplication);
        chosenInterviewType = InputHandler.getValueFromUser(
                "Enter the type of interview you wish to create: ",
                "Creates Interview: ");
        if (!chosenApplication.requiresInterviewAttendancePerInterviewType(chosenInterviewType)) {
            throw new InterviewIsNotRequiredException("Sorry, chosen interview type does not require attendance.");
        }
    }

    /**
     * Allows a coordinator to choose an interviewer for the interview
     */
    private void chooseInterviewer() {
        final List<User> interviewers = coordinator.getCompany().getStaffPerUserType(UserType.INTERVIEWER);
        OutputHandler.printUsersInfo(interviewers, "interviewers");
        if (interviewers.isEmpty()) {
            return;
        }
        String interviewerName = InputHandler.getValueFromUser(
                "Enter interviewer username to assign her/him to this interview: ",
                "Create Interview: ");
        final User user = RecruitmentSystemFactory.getRecruitmentSystem().getUser(interviewerName);
        if (user.getUserType() != UserType.INTERVIEWER) {
            throw new UserNotFoundException(String.format(
                    "Interviewer with username %s does not exist",
                    interviewerName));
        } else {
            chosenInterviewer = (Interviewer) user;
        }
    }
}
