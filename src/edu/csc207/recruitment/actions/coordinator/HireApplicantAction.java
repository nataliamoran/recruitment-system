package edu.csc207.recruitment.actions.coordinator;

import edu.csc207.recruitment.model.company.Application;
import edu.csc207.recruitment.model.company.ApplicationStatus;
import edu.csc207.recruitment.model.company.JobPosting;
import edu.csc207.recruitment.model.user.Coordinator;
import edu.csc207.recruitment.tools.InputHandler;
import edu.csc207.recruitment.tools.OutputHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to allow a coordinator to hire an applicant
 */
public class HireApplicantAction extends CoordinatorAction {
    private Map<JobPosting, List<Application>> jobsApplicationsForHiring = new HashMap<>();
    private JobPosting jobToFill = null;
    private Application applicationToAccept = null;
    private final Date today;


    public HireApplicantAction(Coordinator coordinator, Date today) {
        super("5", "To hire an applicant", coordinator);
        this.today = today;
    }

    /**
     * Method to print out jobs ready to be filled and their final round applications and
     * to get Coordinator's input to choose a job to fill and a candidate to hire.
     */
    @Override
    public void collectParameters() {
        final List<JobPosting> jobs = coordinator.getCompany().getOpenJobPostings();
        for (JobPosting job : jobs) {
            if (job.isReadyForHiring(this.today)) {
                List<Application> applications = job.getApplicationsPerStatus(ApplicationStatus.WAITING_FOR_FINALSTEP);
                if (!applications.isEmpty()) {
                    jobsApplicationsForHiring.put(job, applications);
                }
            }
        }
        OutputHandler.printJobPostingsWithApplications(jobsApplicationsForHiring);
        if (!jobsApplicationsForHiring.isEmpty()) {
            final String idOfJobToFill = InputHandler.getValueFromUser(String.format(
                    "%nPlease enter the ID of the job you want to hire for."),
                    "Hire Applicant");
            final String idOfApplicationToHire = InputHandler.getValueFromUser(
                    "Please enter the ID of the application to accept.",
                    "Hire Applicant");
            this.jobToFill = coordinator.getCompany().getJobPostingPerId(idOfJobToFill);
            this.applicationToAccept = jobToFill.getApplication(idOfApplicationToHire);
        }
    }

    /**
     * Method to hire the chosen applicant and reject all other final round applicants.
     */
    @Override
    public void performAction() {
        if (this.jobToFill == null || this.applicationToAccept == null) {
            return;
        }
        final List<Application> applications = jobsApplicationsForHiring.get(jobToFill);
        for (Application application : applications) {
            if (application.getId() == applicationToAccept.getId()) {
                application.setApplicationStatus(ApplicationStatus.HIRED);
            } else {
                application.setApplicationStatus(ApplicationStatus.REJECTED);
            }
            application.setClosedDate(today);
        }
        System.out.println("You have successfully hired " + applicationToAccept.getApplicant().getUsername());
    }


}


