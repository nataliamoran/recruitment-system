package edu.csc207.recruitment.model.user;

import edu.csc207.recruitment.model.company.Application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class contains information regarding an Applicant's previous jobs, current jobs, date created,
 * and time since last application closed.
 */
public class AccountHistory {
    private final Date creationDate;
    private List<Application> applications;
    private static int nextId = 0;
    private int id;
    private Integer sinceLastApplicationClosure;

    /**
     * AccountHistory represents the account activity history of an applicant. When initialized, the creationDate
     * is set to the date when the Applicant class is initialized.
     */
    public AccountHistory(Date creationDate) {
        this.creationDate = creationDate;
        this.applications = new ArrayList<>();
        this.sinceLastApplicationClosure = null;
        this.id = nextId;
        nextId++;
    }

    public int getId() {
        return this.id;
    }

    public void setSinceLastApplicationClosure(int numDays) {
        this.sinceLastApplicationClosure = numDays;
    }

    public Integer getSinceLastApplicationClosure() {
        return this.sinceLastApplicationClosure;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    /**
     * Method to get the applicant's all past applications
     *
     * @return applicant's all past applications
     */
    public List<Application> getClosedApplications() {
        List<Application> closedApplications = new ArrayList<>();
        for (Application application : this.applications) {
            if (application.isClosed()) {
                closedApplications.add(application);
            }
        }
        return closedApplications;
    }

    /**
     * Method to get the applicant's all current applications
     *
     * @return applicant's all current applications
     */
    public List<Application> getOpenApplications() {
        List<Application> openApplications = new ArrayList<>();
        for (Application application : this.applications) {
            if (!application.isClosed()) {
                openApplications.add(application);
            }
        }
        return openApplications;
    }

    /**
     * Add an application to the applicant's applications.
     *
     * @param application of Application class.
     */
    public void addApplication(Application application) {
        this.applications.add(application);
    }

    public List<Application> getApplications() {
        return this.applications;
    }

    public void updateAllTransient(Applicant applicant) {
        for (Application app : this.applications) {
            app.setApplicant(applicant);
            app.updateAllTransient();
        }
    }

    public void replaceApplication(Application anew) {
        Application oldApplication = null;
        for (Application aold : this.applications) {
            if (aold.getId() == anew.getId()) {
                oldApplication = aold;
            }
        }
        if (oldApplication != null) {
            this.applications.remove(oldApplication);
            this.applications.add(anew);
        }
    }

    public static void setNextId(int id) {
        nextId = id;
    }


}
