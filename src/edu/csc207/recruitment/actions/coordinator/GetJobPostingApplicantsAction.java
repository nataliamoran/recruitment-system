package edu.csc207.recruitment.actions.coordinator;

import edu.csc207.recruitment.model.company.Application;
import edu.csc207.recruitment.model.company.JobPosting;
import edu.csc207.recruitment.model.user.Coordinator;
import edu.csc207.recruitment.tools.InputHandler;
import edu.csc207.recruitment.tools.OutputHandler;

import java.util.List;

public class GetJobPostingApplicantsAction extends CoordinatorAction {
    private JobPosting jobPosting = null;

    public GetJobPostingApplicantsAction(Coordinator coordinator) {
        super("4", "To get all applications for a job posting", coordinator);
    }

    /**
     * Method to print out open job postings and let the coordinator to choose a job posting with its ID.
     */
    @Override
    public void collectParameters() {
        final List<JobPosting> openJobPostings = coordinator.getCompany().getOpenJobPostings();
        OutputHandler.printJobPostingsList(openJobPostings, "open");
        if (openJobPostings.isEmpty()) {
            return;
        }
        String jobPostingId = InputHandler.getValueFromUser(
                "To see a job posting applications, enter the job posting ID:",
                "Get Job Posting Applications: ");
        jobPosting = coordinator.getCompany().getJobPostingPerId(jobPostingId);
    }

    /**
     * Method to print out all applications for the chosen job posting.
     */
    @Override
    public void performAction() {
        if (jobPosting == null) {
            return;
        }
        List<Application> applications = jobPosting.getApplications();
        if (applications.isEmpty()) {
            System.out.println("Sorry, there are no applications for this job posting.");
            return;
        }
        for (Application application : applications) {
            System.out.println(String.format(
                    "Applicant's name: %s    Application status: %s",
                    application.getApplicant().getUsername(),
                    application.getApplicationStatus()));
        }
    }

}


