package edu.csc207.recruitment.model.company;

import edu.csc207.recruitment.exceptions.application.ApplicationNotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class represent a created posted job.
 * this class has job ID, job title, description, requirements.. list of fields related to create a job.
 */
public class JobPosting {


    private static int nextId = 0;
    private int id;
    private final String title;
    private final String description;
    private String location;
    private transient Company company;
    private final Date postedDate;
    private JobPostingRequirements requirements;
    private boolean jobPostingIsOpen;
    private List<Application> applications;
    private List<Interview> interviews;

    /**
     * A constructor of job posting.
     *
     * @param title        a job title
     * @param description  job description
     * @param postedDate   the date this job has been posted.
     * @param requirements requirements for this job posting
     */
    public JobPosting(String title, String description, Company company, String location, Date postedDate,
                      JobPostingRequirements requirements) {
        this.id = nextId;
        ++nextId;
        this.title = title;
        this.description = description;
        this.company = company;
        this.location = location;
        this.postedDate = postedDate;
        this.requirements = requirements;
        this.jobPostingIsOpen = true;
        this.applications = new ArrayList<>();
        this.interviews = new ArrayList<>();
    }

    public List<Application> getApplications() {
        return this.applications;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return this.location;
    }

    public Company getCompany() {
        return this.company;
    }

    public JobPostingRequirements getRequirements() {
        return this.requirements;
    }

    public List<Interview> getInterviews() {
        return interviews;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public boolean isOpen() {
        return jobPostingIsOpen && !isFilled();
    }

    public void setJobPostingIsOpen(boolean value) {
        this.jobPostingIsOpen = value;
    }

    /**
     * check if this job is filled with candidate.
     *
     * @return true if it's fiiled, otherwise return false.
     */
    public Boolean isFilled() {
        for (Application application : applications) {
            if (application.getApplicationStatus() == ApplicationStatus.HIRED) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to add the application
     *
     * @param application Application that is being applied to the job
     */

    public void addApplication(Application application) {
        this.applications.add(application);
    }


    /**
     * Method for adding the interview
     *
     * @param interview Interview that is being done with the job
     */
    public void addInterview(Interview interview) {
        this.interviews.add(interview);
    }

    /**
     * Method to get an application with its ID.
     *
     * @param applicationId ID of the application to return
     * @return application Application of the applicant
     */
    public Application getApplication(String applicationId) {
        for (Application application : this.applications) {
            if (Integer.toString(application.getId()).equals(applicationId)) {
                return application;
            }
        }
        throw new ApplicationNotFoundException("Application is not found.");
    }

    /**
     * Check if this job has been closed.
     *
     * @return true if the job is closed, otherwise, return false.
     */
    public Boolean isApplicationsDeadlinePassed(Date today) {

        return (today.after(requirements.getApplicationsDeadlineDate()));
    }

    /**
     * Method to determine if candidates are still being interviewed for this job posting or
     * if some candidate already can be hired. Today's date has to be passed the deadline for
     * the coordinator to hire.
     *
     * @param today the session date
     * @return true if some candidate can be hired, otherwise false
     */
    public Boolean isReadyForHiring(Date today) {
        Application applicationToHire = null;
        if (!isOpen() || !requirements.getApplicationsDeadlineDate().before(today) || isFilled()) {
            return false;
        }
        for (Application application : this.applications) {
            if (application.getApplicationStatus() == ApplicationStatus.INTERVIEW_SCHEDULED ||
                    application.getApplicationStatus() == ApplicationStatus.WAITING_FOR_NEXT_INTERVIEW) {
                return false;
            }
            if (application.getApplicationStatus() == ApplicationStatus.WAITING_FOR_FINALSTEP) {
                applicationToHire = application;
            }
        }
        return applicationToHire != null;
    }

    /**
     * Method to filter applications per their status.
     *
     * @param status application status to use for filtering
     * @return list of applications with the specified status
     */
    public List<Application> getApplicationsPerStatus(ApplicationStatus status) {
        List<Application> applications = new ArrayList<>();
        for (Application application : this.applications) {
            if (application.getApplicationStatus() == status) {
                applications.add(application);
            }
        }
        return applications;
    }

    public void updateAllTransient() {
        for (Application app : this.applications) {
            app.setJob(this);
        }
    }

    public static void setNextId(int id) {
        nextId = id;
    }


}

