package edu.csc207.recruitment.actions.referee;

import edu.csc207.recruitment.model.company.Application;
import edu.csc207.recruitment.model.company.JobPosting;
import edu.csc207.recruitment.model.user.Applicant;
import edu.csc207.recruitment.model.user.Referee;
import edu.csc207.recruitment.model.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Allows the referee to see their applicants job postings and end dates
 */
public class ViewApplicantsAndPostsAction extends RefereeAction {

    public ViewApplicantsAndPostsAction(Referee referee) {
        super("1", "To view the job postings your applicants would like you to be a referee for", referee);
    }

    /**
     * Collects the referee's applicants and their job postings
     */
    @Override
    public void collectParameters() {
    }

    /**
     * Displays relevant jobs, applicant, and end date.
     */
    @Override
    public void performAction() {
        List<User> applicants = referee.getApplicantList();
        List<Application> applications = new ArrayList<>();
        if (applicants.isEmpty()) {
            System.out.println("Your applicants may not have added you as a referee yet, please give them your username");
            return;
        }
        for (User applicant : applicants) {
            Applicant castedApplicant = (Applicant) applicant;
            applications.addAll(castedApplicant.getHistory().getOpenApplications());
        }
        if (applications.isEmpty()) {
            System.out.println("Your applicants have no applications at this time");
            return;
        }
        for (Application application : applications) {
            JobPosting jobPosting = application.getJobPosting();
            System.out.println(String.format("Applicant username: %s    Job Posting: %s   Applications Deadline: %s",
                    application.getApplicant().getUsername(),
                    jobPosting.getTitle(),
                    jobPosting.getRequirements().getApplicationsDeadlineDate()));
        }
    }

}
