package edu.csc207.recruitment.actions.applicant;

import edu.csc207.recruitment.exceptions.jobposting.JobPostingAlreadyAppliedException;
import edu.csc207.recruitment.exceptions.jobposting.JobPostingHasBeenClosedException;
import edu.csc207.recruitment.model.company.Application;
import edu.csc207.recruitment.model.company.JobPosting;
import edu.csc207.recruitment.model.db.RecruitmentSystemFactory;
import edu.csc207.recruitment.model.user.Applicant;
import edu.csc207.recruitment.tools.InputHandler;
import edu.csc207.recruitment.tools.OutputHandler;

import java.util.Date;
import java.util.List;

public class ApplyForJobAction extends ApplicantAction {
    private String jobId;
    private final Date today;

    public ApplyForJobAction(Applicant applicant, Date today) {
        super("7", "To apply for a job posting", applicant);
        this.today = today;
        this.jobId = null;
    }

    /**
     * Method to collect an ID of the job posting for which a user wants to apply
     */
    public void collectParameters() {
        List<JobPosting> allOpenJobPostings = RecruitmentSystemFactory.getRecruitmentSystem().getAllOpenJobPostings();
        OutputHandler.printJobPostingsList(allOpenJobPostings, "open");
        if (allOpenJobPostings.isEmpty()) {
            return;
        }
        this.jobId = InputHandler.getValueFromUser(
                "Enter the job id that you would like to apply for:",
                "Apply for job");
    }

    /**
     * Method to check if a chosen job posting is still open and if yes, to create a new application for this job
     */
    public void performAction() {
        if (jobId == null) {
            return;
        }
        JobPosting job = RecruitmentSystemFactory.getRecruitmentSystem().getJobPosting(this.jobId);
        if (job.isApplicationsDeadlinePassed(today)) {
            throw new JobPostingHasBeenClosedException("Job posting has already been closed.");
        }
        for (Application application : applicant.getHistory().getApplications()) {
            if (job.getId() == application.getJobPosting().getId() && !application.isClosed()) {
                throw new JobPostingAlreadyAppliedException("You can't apply for the job twice.");
            }
        }
        Application newApplication = new Application(job, this.applicant);
        applicant.getHistory().addApplication(newApplication);
        job.addApplication(newApplication);
        System.out.println("You have successfully applied to " + job.getTitle());
    }

}
