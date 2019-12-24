package edu.csc207.recruitment.model.user;

import edu.csc207.recruitment.exceptions.interview.InterviewNotFoundException;
import edu.csc207.recruitment.model.company.Company;
import edu.csc207.recruitment.model.company.Interview;

import java.util.ArrayList;
import java.util.List;

public class Interviewer extends User {
    private transient Company company;
    private int companyId;
    private List<Interview> interviewsToRun;
    private List<String> locations;

    public Interviewer(String username, String password, Company company, List<String> locations) {
        super(username, password, UserType.INTERVIEWER);
        this.interviewsToRun = new ArrayList<>();
        this.company = company;
        this.companyId = company.getId();
        this.locations = locations;
    }

    public List<String> getLocations() {
        return this.locations;
    }

    public Company getCompany() {
        return this.company;
    }

    public List<Interview> getAllInterviewsToRun() {
        return this.interviewsToRun;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void addInterview(Interview interview) {
        this.interviewsToRun.add(interview);
    }

    public void removeInterview(Interview interview) {
        this.interviewsToRun.remove(interview);
    }

    /**
     * Get interview by id
     *
     * @param id - the id of the interview
     * @return the Interview with the id
     */
    public Interview getInterviewWithId(String id) {
        for (Interview interview : interviewsToRun) {
            if (Integer.toString(interview.getId()).equals(id)) {
                return interview;
            }
        }
        throw new InterviewNotFoundException(String.format("Interview with id %s not found", id));
    }

    /**
     * Method to print out applicants usernames and
     * their interviews IDs for all interviewees assigned to the interviewer.
     */
    public void printIntervieweesInfo() {
        if (interviewsToRun.isEmpty()) {
            System.out.println(System.lineSeparator() + "Currently, there are no interviewees assigned to you.");
            return;
        }
        System.out.println(System.lineSeparator() + "Please see below the list of interviewees assigned to you.");
        for (Interview interview : interviewsToRun) {
            System.out.println(String.format("Applicant: %s    Interview ID:%s",
                    interview.getApplication().getApplicant().getUsername(), interview.getId()));
        }
    }

    public void updateAllTransient(Company company) {
        this.company = company;
        for (Interview inter : this.interviewsToRun) {
            inter.setInterviewer(this);
        }
    }
}
