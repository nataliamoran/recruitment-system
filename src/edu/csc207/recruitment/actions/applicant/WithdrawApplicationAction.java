package edu.csc207.recruitment.actions.applicant;

import edu.csc207.recruitment.exceptions.application.ApplicationAlreadyClosedException;
import edu.csc207.recruitment.exceptions.application.ApplicationNotFoundException;
import edu.csc207.recruitment.model.company.Application;
import edu.csc207.recruitment.model.company.ApplicationStatus;
import edu.csc207.recruitment.model.user.Applicant;
import edu.csc207.recruitment.tools.InputHandler;
import edu.csc207.recruitment.tools.OutputHandler;

import java.util.Date;

public class WithdrawApplicationAction extends ApplicantAction {
    private final Date today;
    private int applicationId;

    public WithdrawApplicationAction(Applicant applicant, Date today) {
        super("8", "To withdraw application", applicant);
        this.today = today;
    }

    /**
     * Method to collect an ID of the application to be withdrawn
     */
    public void collectParameters() {
        OutputHandler.printApplicationsList(applicant.getHistory().getOpenApplications());
        if (applicant.getHistory().getOpenApplications() == null ||
                applicant.getHistory().getOpenApplications().isEmpty()) {
            return;
        }
        try {
            this.applicationId = Integer.parseInt(InputHandler.getValueFromUser(
                    "Enter the application id", "Withdraw application"));
        } catch (NumberFormatException e) {
            System.out.println("Application id has to be numbers");
        }
    }

    /**
     * This method first tries to find the application with the given id in the current applications. If the
     * application has been closed previously, or the application is not found, the method throws corresponding
     * exceptions.
     */
    public void performAction() {
        // try to find the application with the given id in current applications
        for (Application application : applicant.getHistory().getOpenApplications()) {
            if (application.getId() == this.applicationId) {
                application.setClosedDate(today);
                application.setApplicationStatus(ApplicationStatus.WITHDRAWN);
                System.out.println(String.format("Application #%s is withdrawn.", this.applicationId));
                return;
            }
        }
        // If the application with the given id is not in current applications, look for it in past applications,
        // if found, inform user that the applicant is already closed.
        for (Application application : applicant.getHistory().getClosedApplications()) {
            if (application.getId() == this.applicationId) {
                throw new ApplicationAlreadyClosedException("Application is already closed.");
            }
        }
        throw new ApplicationNotFoundException("Application is not found.");
    }
}
