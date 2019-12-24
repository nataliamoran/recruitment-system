package edu.csc207.recruitment.model.user;

import edu.csc207.recruitment.model.company.Company;

import java.util.List;


public class Coordinator extends User {
    private transient Company company;
    private int companyId;
    private List<String> locations;

    public Coordinator(String username, String password, Company company, List<String> locations) {
        super(username, password, UserType.COORDINATOR);
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

    public int getCompanyId() {
        return companyId;
    }

    public void updateAllTransient(Company company) {
        this.company = company;
    }
}
