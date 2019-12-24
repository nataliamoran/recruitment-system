package edu.csc207.recruitment.backgroundactions.user;

import edu.csc207.recruitment.model.company.Application;
import edu.csc207.recruitment.model.company.ApplicationStatus;
import edu.csc207.recruitment.model.company.Company;
import edu.csc207.recruitment.model.company.JobPosting;
import edu.csc207.recruitment.model.db.RecruitmentSystemFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HireBackgroundAction extends UserBackgroundAction {

    private final Date today;

    public HireBackgroundAction(final Date today) {
        this.today = today;
    }

    /**
     * Method to loop through all open job postings stored in the Recruitment System and
     * if any job posting has only one candidate waiting for the final step, hire this candidate.
     */
    public void performAction() {
        List<Company> allCompanies = RecruitmentSystemFactory.getRecruitmentSystem().getAllCompanies();
        List<JobPosting> allOpenJobPostings = new ArrayList<>();
        for (final Company company : allCompanies) {
            List<JobPosting> jobs = company.getOpenJobPostings();
            allOpenJobPostings.addAll(jobs);
        }
        for (final JobPosting job : allOpenJobPostings) {
            if (job.isReadyForHiring(today)) {
                hireSingleCandidate(job);
            }
        }
    }

    /**
     * Method to check number of applicants waiting for final step in a specific job.
     * If number of applicants waiting for final step is 1 then hire.
     *
     * @param job the job to check
     */
    private void hireSingleCandidate(JobPosting job) {
        final List<Application> applicationsForHiring =
                job.getApplicationsPerStatus(ApplicationStatus.WAITING_FOR_FINALSTEP);
        if (applicationsForHiring.size() == 1) {
            Application applicationToHire = applicationsForHiring.get(0);
            applicationToHire.setApplicationStatus(ApplicationStatus.HIRED);
        }
    }

}
