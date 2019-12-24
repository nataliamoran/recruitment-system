package edu.csc207.recruitment.model.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Referee extends User {
    private Map<String, List<Integer>> references; // Map of usernames to list of document ids
    private List<User> applicantList;

    public Referee(String username, String password) {
        super(username, password, UserType.REFEREE);
        applicantList = new ArrayList<>();
    }

    public void addApplicant(Applicant applicant) {
        applicantList.add(applicant);

    }

    public List<User> getApplicantList() {
        return this.applicantList;
    }
}
