package edu.csc207.recruitment.model.company;

import edu.csc207.recruitment.exceptions.application.ApplicationNotFoundException;
import edu.csc207.recruitment.exceptions.jobposting.JobPostingNotFoundException;
import edu.csc207.recruitment.model.user.User;
import edu.csc207.recruitment.model.user.UserType;

import java.util.ArrayList;
import java.util.List;

public class Company {
    /**
     * A Company class.
     * The staff attribute is a Map that takes locations (String) as keys and a list of users (User) as values.
     */
    private final String name;
    private List<JobPosting> jobs;
    private final List<String> locations;
    private List<String> interviewTypes;
    private transient List<User> staff;
    private static int nextId = 0;
    private int id;

    public Company(String name, List<String> interviewTypes, List<String> locations) {
        this.name = name;
        this.jobs = new ArrayList<>();
        this.locations = locations;
        this.staff = new ArrayList<>();
        this.interviewTypes = interviewTypes;
        this.id = nextId;
        nextId++;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    /**
     * Return all unique staff within the company.
     *
     * @return a list of unique staff
     */
    public List<User> getAllStaff() {
        return this.staff;
    }

    /**
     * Return all different locations of the company.
     *
     * @return a list of unique locations
     */
    public List<String> getLocations() {
        return this.locations;
    }

    public List<JobPosting> getAllJobPostings() {
        return this.jobs;
    }

    public void addJobPosting(JobPosting job) {
        jobs.add(job);

    }

    public List<String> getInterviewTypes() {
        return interviewTypes;
    }

    /**
     * Method to add a new user to the staff
     *
     * @param user new member of staff
     */
    public void addStaff(User user) {
        this.staff.add(user);
    }

    /**
     * Method to search for a job posting with a specific id
     * Throws exception if neither of the company's job postings has this id
     *
     * @param jobId id of the job posting to be found
     * @return job posting
     */
    public JobPosting getJobPostingPerId(String jobId) {
        for (JobPosting job : this.jobs) {
            if (Integer.toString(job.getId()).equals(jobId)) {
                return job;
            }
        }
        throw new JobPostingNotFoundException("Job posting is not found.");
    }

    /**
     * Method to search for users per their user type
     *
     * @return list of users filtered per the user type
     */
    public List<User> getStaffPerUserType(UserType userType) {
        List<User> users = new ArrayList<>();
        for (User user : this.staff) {
            if (user.getUserType() == userType) {
                users.add(user);
            }
        }
        return users;
    }

    /**
     * Method to get a list of open job postings.
     */
    public List<JobPosting> getOpenJobPostings() {
        List<JobPosting> openJobs = new ArrayList<>();
        for (JobPosting jobPosting : jobs) {
            if (jobPosting.isOpen()) {
                openJobs.add(jobPosting);
            }
        }
        return openJobs;
    }

    /**
     * Method to find an application submitted for one of the company's jobs with the application's ID.
     *
     * @param applicationId ID of the application to search for
     * @return application found, throw a relevant error otherwise
     */
    public Application getApplicationPerID(String applicationId) {
        Application application = null;
        for (JobPosting job : this.jobs) {
            try {
                application = job.getApplication(applicationId);
                return application;
            } catch (ApplicationNotFoundException e) {
            }
        }
        throw new ApplicationNotFoundException("Application is not found.");
    }

    /**
     * Method to filter the company's applications per their status
     *
     * @param status filter status
     * @return applications filtered per given status
     */
    public List<Application> getApplicationsPerStatus(ApplicationStatus status) {
        List<Application> applications = new ArrayList<>();
        for (JobPosting job : this.jobs) {
            applications.addAll(job.getApplicationsPerStatus(status));
        }
        return applications;
    }

    public void updateTransient() {
        this.staff = new ArrayList<>();
        for (JobPosting job : this.jobs) {
            job.setCompany(this);
            job.updateAllTransient();
        }
    }

    public static void setNextId(int id) {
        nextId = id;
    }

}
