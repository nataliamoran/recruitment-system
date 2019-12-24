package edu.csc207.recruitment.actions.applicant;

import edu.csc207.recruitment.exceptions.input.InvalidInputException;
import edu.csc207.recruitment.model.company.Application;
import edu.csc207.recruitment.model.user.Applicant;
import edu.csc207.recruitment.tools.InputHandler;
import edu.csc207.recruitment.tools.OutputHandler;

import java.util.ArrayList;
import java.util.Arrays;

public class GetApplicationsAction extends ApplicantAction {
    private final String CURRENT = "1";
    private final String PAST = "2";
    private String applicationType;

    public GetApplicationsAction(Applicant applicant) {
        super("4", "To get all applications, current and past", applicant);
    }

    /**
     * Method to get the application type from the user input. Has to be either Current or Past Applications.
     */
    @Override
    public void collectParameters() {
        applicationType = InputHandler.getValueFromUser(String.format(
                "Enter the application type: %n - for current applications, press %s%n - for past applications, press %s%n",
                CURRENT,
                PAST),
                "Get Applications");
        if (!new ArrayList<>(Arrays.asList(CURRENT, PAST)).contains(applicationType)) {
            throw new InvalidInputException(
                    "Invalid application type input, please start the get applications process again.");
        }
    }

    /**
     * Method to print out applications.
     */
    @Override
    public void performAction() {
        switch (applicationType) {
            case CURRENT:
                if (this.applicant.getHistory().getOpenApplications().isEmpty()) {
                    System.out.println("No current applications are found.");
                }
                for (Application application : this.applicant.getHistory().getOpenApplications()) {
                    OutputHandler.printApplicationInfo(application);
                    OutputHandler.printApplicationInterviewsInfo(application);
                }
                break;
            case PAST:
                if (this.applicant.getHistory().getClosedApplications().isEmpty()) {
                    System.out.println("No past applications are found.");
                }
                for (Application application : this.applicant.getHistory().getClosedApplications()) {
                    OutputHandler.printApplicationInfo(application);
                }
                break;
        }
    }

}
