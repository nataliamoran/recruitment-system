package edu.csc207.recruitment.actions.applicant;

import edu.csc207.recruitment.model.user.Applicant;
import edu.csc207.recruitment.tools.OutputHandler;

public class GetAccountCreationDateAction extends ApplicantAction {

    public GetAccountCreationDateAction(Applicant applicant) {
        super("2", "To get account creation date", applicant);
    }

    public void collectParameters() {
    }

    /**
     * Method to print out the account creation date
     */
    public void performAction() {
        System.out.println("Account created on: " + OutputHandler.formatDate(applicant.getHistory().getCreationDate()));
    }

}
