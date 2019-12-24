package edu.csc207.recruitment.backgroundactions.applicant;

import edu.csc207.recruitment.model.company.Application;
import edu.csc207.recruitment.model.company.ApplicationStatus;
import edu.csc207.recruitment.model.user.Applicant;
import edu.csc207.recruitment.tools.OutputHandler;


public class UpdateApplicantBackgroundAction extends ApplicantBackgroundAction {

    public UpdateApplicantBackgroundAction(Applicant applicant) {
        super(applicant);
    }

    /**
     * This method checks the status of the interviewee regarding their status of the application after they
     * have done their interviews. Whether or not they have been accepted or not to the next round of
     * interviews, they will be notified.
     */

    @Override
    public void performAction() {
        if (!applicant.getHistory().getApplications().isEmpty()) {
            System.out.println(String.format("%nPlease see below your applications' latest status. %n"));
        }
        for (final Application application : applicant.getHistory().getApplications()) {
            if (application.getApplicationStatus() != ApplicationStatus.WITHDRAWN) {
                OutputHandler.printApplicationInfo(application);
            }
        }
    }
}
