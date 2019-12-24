package edu.csc207.recruitment.model.db;

import edu.csc207.recruitment.exceptions.company.CompanyNotFoundException;
import edu.csc207.recruitment.exceptions.jobposting.JobPostingNotFoundException;
import edu.csc207.recruitment.exceptions.user.UserNotFoundException;
import edu.csc207.recruitment.model.company.Company;
import edu.csc207.recruitment.model.company.JobPosting;
import edu.csc207.recruitment.model.user.User;
import edu.csc207.recruitment.model.user.UserType;

import java.util.ArrayList;
import java.util.List;

public class RecruitmentSystem {
    private List<User> users;
    private List<Company> companies;

    RecruitmentSystem() {
        users = new ArrayList<>();
        companies = new ArrayList<>();
    }


    public List<Company> getAllCompanies() {
        return this.companies;
    }

    public List<User> getAllUsers() {
        return this.users;
    }

    public void setAllCompanies(List<Company> companies) {
        this.companies = companies;
    }

    public void setAllUsers(List<User> users) {
        this.users = users;
    }

    /**
     * Add a user to users.
     *
     * @param user of User class.
     */
    public void addUser(User user) {
        users.add(user);
    }

    /**
     * Add a company to companies.
     *
     * @param company of Company class.
     */
    public void addCompany(Company company) {
        companies.add(company);
    }

    /**
     * Return the class, User, from users with the String,username, information.
     *
     * @param username of person trying to log in.
     * @return Returns class User from the StoreLogin document if username exists.
     * Else return null.
     */
    public User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        throw new UserNotFoundException(String.format("User %s not found in the Recruitment System.", username));
    }

    /**
     * Return the class, Company, from the list, companies,  with the company information.
     *
     * @param companyName - name of the company
     * @return Returns class Company from the Company document if company exists.
     * Else return null
     */

    public Company getCompany(String companyName) {
        for (Company company : companies) {
            if (company.getName().equals(companyName)) {
                return company;
            }
        }
        throw new CompanyNotFoundException("Company not found in the Recruitment System.");
    }

    /**
     * Method to find a job posting with its id
     * Throws exception if the job posting is not found
     *
     * @param jobId the id of a job posting
     * @return job posting
     */
    public JobPosting getJobPosting(String jobId) {
        JobPosting job;
        for (Company company : companies) {
            try {
                job = company.getJobPostingPerId(jobId);
                return job;
            } catch (JobPostingNotFoundException ignored) {
            }
        }
        throw new JobPostingNotFoundException("Job not found in the Recruitment System.");
    }

    /**
     * Method to filter users by their user type
     *
     * @param userType user type for filtering
     * @return list of filtered users
     */
    public List<User> getUsersOfType(final UserType userType) {
        final List<User> res = new ArrayList<>();
        for (final User user : this.users) {
            if (user.getUserType() == userType) {
                res.add(user);
            }
        }
        return res;
    }

    /**
     * Method to return all open job postings found in the recruitment system.
     *
     * @return a list of all open job postings
     */
    public List<JobPosting> getAllOpenJobPostings() {
        final List<JobPosting> openJobPostings = new ArrayList<>();
        for (final Company company : companies) {
            openJobPostings.addAll(company.getOpenJobPostings());
        }
        return openJobPostings;
    }
}