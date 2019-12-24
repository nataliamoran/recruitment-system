package edu.csc207.recruitment.model.company;

import edu.csc207.recruitment.exceptions.application.ApplicationAlreadyClosedException;
import edu.csc207.recruitment.exceptions.interview.InterviewIsNotRequiredException;
import edu.csc207.recruitment.model.user.Applicant;

import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * This class represents a job application, which stores information about the job posting, applicant username, and
 * application status, and whether the application is closed and when it closed. See class diagram.
 */
public class Application {
    private transient JobPosting job;
    private transient Applicant applicant;
    private List<Interview> interviews;
    private ApplicationStatus status;
    private Date closedDate;
    /**
     * A map from interview type => (number of interviews required, number of interviews attended)
     */
    private Map<String, Integer> interviewsTypesAttended;
    private static int nextId = 0;
    private int id;


    /**
     * In the constructor, the application closedDate is set to the job's closedDate (can be overwritten if want to
     * close the application early). Additionally, the application's status is default 'waiting for next interview'.
     *
     * @param job       the JobPosting associated with the application
     * @param applicant applicant associated with the application
     */
    public Application(JobPosting job, Applicant applicant) {
        this.job = job;
        this.applicant = applicant;
        this.closedDate = null;
        this.status = ApplicationStatus.WAITING_FOR_NEXT_INTERVIEW;
        this.interviewsTypesAttended = new HashMap<>();
        Map<String, Integer> interviewTypes = job.getRequirements().getInterviewTypeLimitsMap();
        for (Map.Entry<String, Integer> entry : interviewTypes.entrySet()) {
            this.interviewsTypesAttended.put(entry.getKey(), 0);
        }
        this.id = nextId;
        this.interviews = new ArrayList<>();
        nextId++;
    }

    public int getId() {
        return this.id;
    }

    /**
     * Set the date when the application is closed
     * Application can be closed if an applicant withdrawn this application or if the applicant was hired / rejected
     * Throws the relevant exception if the application is already closed
     */
    public void setClosedDate(Date date) {
        if (this.closedDate == null) {
            this.closedDate = date;
        } else {
            throw new ApplicationAlreadyClosedException("The application has already been closed.");
        }
    }


    public Date getClosedDate() {
        return closedDate;
    }

    /**
     * Method to check if the application is closed
     *
     * @return true if the application's closed dated is set (not equal to null), otherwise returns false
     */
    public boolean isClosed() {
        return this.closedDate != null;
    }

    /**
     * getSinceApplicationClosure() will return negative values if the application is not yet closed..
     *
     * @param today Date object representing today's Date
     * @return long object representing the number of days it has been since the application closure.
     */
    public int getSinceApplicationClosure(Date today) {
        return (int) ChronoUnit.DAYS.between(this.closedDate.toInstant(), today.toInstant());
    }

    public JobPosting getJobPosting() {
        return this.job;
    }

    public Applicant getApplicant() {
        return this.applicant;
    }

    public ApplicationStatus getApplicationStatus() {
        return this.status;
    }

    public void setApplicationStatus(ApplicationStatus status) {
        this.status = status;
    }

    public Map<String, Integer> getInterviewsTypesAttended() {
        return interviewsTypesAttended;
    }

    public Integer getNumInterviewsAttendedPerInterviewType(String interviewType) {
        return interviewsTypesAttended.get(interviewType);
    }

    /**
     * Increment the attendance number for a particular interview type by one
     * If for this type all required interviews are already attended, throws a relevant exception
     *
     * @param interviewType type of interview ti be attended
     */
    public void incrementAttendedInterviewsByOne(String interviewType) {
        Integer numInterviewsRequired = job.getRequirements().getInterviewTypeLimit(interviewType);
        Integer numInterviewsAttended = interviewsTypesAttended.get(interviewType) + 1;
        if (numInterviewsAttended > numInterviewsRequired) {
            throw new InterviewIsNotRequiredException(String.format("All required %s interviews are already attended.",
                    interviewType));
        }
        interviewsTypesAttended.replace(interviewType, numInterviewsAttended);
    }


    public List<Interview> getInterviews() {
        return this.interviews;
    }

    public void setInterview(Interview interview) {
        this.interviews.add(interview);
    }

    /**
     * Method to check if the application has already undergone all required interviews or not.
     *
     * @return true if the application has undergone all required interviews otherwise false
     */
    public Boolean hasUndergoneAllInterviewRounds() {
        for (Map.Entry<String, Integer> entry : interviewsTypesAttended.entrySet()) {
            if (job.getRequirements().getInterviewTypeLimit(entry.getKey()) > entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method to check if a particular interview type requires attendance
     *
     * @param interviewType type of interview
     * @return true if the interview type exists and requires attendance, false otherwise
     */
    public Boolean requiresInterviewAttendancePerInterviewType(String interviewType) {
        return interviewsTypesAttended.containsKey(interviewType) &&
                job.getRequirements().getInterviewTypeLimit(interviewType) > interviewsTypesAttended.get(interviewType);
    }

    public void updateAllTransient() {
        for (Interview interview : this.interviews) {
            interview.setApplication(this);
        }
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public void setJob(JobPosting job) {
        this.job = job;
    }


    public static void setNextId(int id) {
        nextId = id;
    }


}
