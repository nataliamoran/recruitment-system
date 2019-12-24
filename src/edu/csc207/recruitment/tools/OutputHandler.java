package edu.csc207.recruitment.tools;

import edu.csc207.recruitment.model.company.Application;
import edu.csc207.recruitment.model.company.JobPosting;
import edu.csc207.recruitment.model.user.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Class for printing out methods
 */
public class OutputHandler {

    /**
     * Method to print out information about job postings.
     *
     * @param jobPostings a list of job postings to print out
     * @param jobStatus   open / closed
     */
    public static void printJobPostingsList(List<JobPosting> jobPostings, String jobStatus) {
        if (jobPostings == null || jobPostings.isEmpty()) {
            System.out.println(String.format("Sorry, currently there are no %s job postings.%n", jobStatus));
            return;
        }
        System.out.println(String.format("Please see below the list of %s job postings and their IDs.%n", jobStatus));
        for (JobPosting jobPosting : jobPostings) {
            System.out.println(String.format(
                    "Title: %s     ID: %s    Description: %s     Location: %s",
                    jobPosting.getTitle(), jobPosting.getId(), jobPosting.getDescription(), jobPosting.getLocation()));
            printJobPostingRequirements(jobPosting);
        }
    }

    /**
     * Method to print out information about applications.
     *
     * @param applications list of applications to be printed out
     */
    public static void printApplicationsList(List<Application> applications) {
        if (applications == null || applications.isEmpty()) {
            System.out.println(String.format("%n Sorry, at the moment there are no applications to display."));
            return;
        }
        System.out.println(String.format("%n Please see below a list of applications. %n"));
        for (Application application : applications) {
            System.out.println(String.format("Application ID: %s  Applicant Username: %s  Job Posting: %s",
                    application.getId(), application.getApplicant().getUsername(),
                    application.getJobPosting().getTitle()));
        }
    }

    /**
     * Method to print out information about users.
     *
     * @param users    list of users to be printed out
     * @param userType user type of users to be printed out
     */
    public static void printUsersInfo(List<User> users, String userType) {
        if (users == null || users.isEmpty()) {
            System.out.println(String.format("%n Sorry, at the moment there are no %s to display.", userType));
            return;
        }
        System.out.println(String.format("%nPlease see below a list of available %s. %n", userType));
        for (User user : users) {
            System.out.println(String.format("ID: %s    Username: %s", user.getId(), user.getUsername()));
        }
    }

    /**
     * Method to print out application info
     *
     * @param application application to print out
     */
    public static void printApplicationInfo(Application application) {
        System.out.println(String.format(
                "Application ID: %s%nApplication Status: %s%n   " +
                        "Job: %s%n   Description: %s%n   Applications Deadline: %s%n",
                application.getId(),
                application.getApplicationStatus(),
                application.getJobPosting().getTitle(),
                application.getJobPosting().getDescription(),
                formatDate(application.getJobPosting().getRequirements().getApplicationsDeadlineDate())));
    }

    /**
     * Method to print out information on required and attended interviews for a particular application
     *
     * @param application application which interviews info to be printed out
     */
    public static void printApplicationInterviewsInfo(Application application) {
        if (application.hasUndergoneAllInterviewRounds()) {
            System.out.println("All required interviews are already attended.");
            return;
        }
        System.out.println("Required and attended interviews for this application: ");
        for (Map.Entry<String, Integer> entry : application.getInterviewsTypesAttended().entrySet()) {
            System.out.println(String.format("%s Interviews Required: %s   %s Interviews Attended: %s",
                    entry.getKey(),
                    application.getJobPosting().getRequirements().getInterviewTypeLimit(entry.getKey()),
                    entry.getKey(),
                    entry.getValue()));
        }
    }

    /**
     * Method to print out jobs title and ID and their applications data.
     */
    public static void printJobPostingsWithApplications(Map<JobPosting, List<Application>> jobPostingsWithApplications) {
        for (Map.Entry<JobPosting, List<Application>> entry : jobPostingsWithApplications.entrySet()) {
            System.out.println(String.format("%n Job Posting Title: %s  Job Posting ID: %s",
                    entry.getKey().getTitle(), entry.getKey().getId()));
            for (Application application : entry.getValue()) {
                System.out.println(String.format("          Applicant Name: %s  Application ID: %s",
                        application.getApplicant().getUsername(),
                        application.getId()));
            }
        }
        if (jobPostingsWithApplications.isEmpty()) {
            System.out.println("Sorry, there are no relevant job postings.");
        }
    }

    /**
     * Method to print out a job posting's requirements.
     */
    public static void printJobPostingRequirements(JobPosting jobPosting) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String strDate = formatter.format(jobPosting.getRequirements().getApplicationsDeadlineDate());
        System.out.println(String.format(
                "Job Requirements: %n       Applications deadline: %s    Documents: %s    Skills: %s %n",
                strDate,
                String.join(", ", jobPosting.getRequirements().getRequiredDocuments()),
                String.join(", ", jobPosting.getRequirements().getTags())));
    }

    /**
     * Method to return a formatted string of the date value
     *
     * @param date the date to format
     * @return a string with the date formatted
     */
    public static String formatDate(Date date) {
        final SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        return formatter.format(date);
    }
}
