package edu.csc207.recruitment.actions.applicant;

import edu.csc207.recruitment.model.user.Applicant;

public class GetNumDaysSinceLastAppClosedAction extends ApplicantAction {

    public GetNumDaysSinceLastAppClosedAction(Applicant applicant) {
        super("5", "To get number of days since last application closed", applicant);
    }

    public void collectParameters() {
    }

    /**
     * Method to check if a user has closed applications and if yes,
     * to print out the number of days since last application closure
     */
    public void performAction() {
        final Integer numDaysSinceLastAppClosure = applicant.getHistory().getSinceLastApplicationClosure();
        if (numDaysSinceLastAppClosure == null) {
            System.out.println("You have no closed applications.");
        } else {
            System.out.println("Number of days since last application closed: " + numDaysSinceLastAppClosure);
        }
    }
}

