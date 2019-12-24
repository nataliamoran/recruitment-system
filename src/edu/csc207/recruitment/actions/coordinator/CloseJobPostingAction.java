package edu.csc207.recruitment.actions.coordinator;

import edu.csc207.recruitment.exceptions.application.ApplicationAlreadyClosedException;
import edu.csc207.recruitment.model.company.Application;
import edu.csc207.recruitment.model.company.ApplicationStatus;
import edu.csc207.recruitment.model.company.JobPosting;
import edu.csc207.recruitment.model.user.Coordinator;
import edu.csc207.recruitment.tools.InputHandler;
import edu.csc207.recruitment.tools.OutputHandler;

import java.util.Date;
import java.util.List;

public class CloseJobPostingAction extends CoordinatorAction {

    private JobPosting jobPosting = null;
    private final Date closingDate;

    public CloseJobPostingAction(final Coordinator coordinator, final Date closingDate) {
        super("6", "To close a job posting", coordinator);
        this.closingDate = closingDate;
    }

    /**
     * Method to print out all open job postings and let a coordinator pick one of them to close.
     */

    @Override
    public void collectParameters() {
        OutputHandler.printJobPostingsList(coordinator.getCompany().getOpenJobPostings(), "open");
        String jobPostingId = InputHandler.getValueFromUser(
                "To close a job posting, enter the job posting ID:",
                "Close Job Posting: ");
        jobPosting = coordinator.getCompany().getJobPostingPerId(jobPostingId);
    }

    /**
     * Method to close a job posting and reject its applications.
     */
    @Override
    public void performAction() {
        if (jobPosting == null) {
            return;
        }
        if (jobPosting.isOpen()) {
            jobPosting.setJobPostingIsOpen(false);
            final List<Application> applications = jobPosting.getApplications();
            for (Application application : applications) {
                try {
                    application.setClosedDate(this.closingDate);
                    application.setApplicationStatus(ApplicationStatus.REJECTED);
                } catch (ApplicationAlreadyClosedException e) {
                }
            }
            System.out.println(String.format(
                    "Job posting '%s' is closed. Applications for this job posting are rejected.",
                    jobPosting.getTitle()));
        } else {
            System.out.println(String.format(
                    "Sorry, the job posting '%s' cannot be closed because it is already either closed or filled.",
                    jobPosting.getTitle()));
        }
    }


}
