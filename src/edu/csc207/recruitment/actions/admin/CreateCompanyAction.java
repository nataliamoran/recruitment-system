package edu.csc207.recruitment.actions.admin;

import edu.csc207.recruitment.exceptions.company.CompanyNotFoundException;
import edu.csc207.recruitment.exceptions.input.InvalidInputException;
import edu.csc207.recruitment.model.company.Company;
import edu.csc207.recruitment.model.db.RecruitmentSystemFactory;
import edu.csc207.recruitment.model.user.Admin;
import edu.csc207.recruitment.tools.InputHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for an action allowing an admin to create a company
 */
public class CreateCompanyAction extends AdminAction {

    private String companyName;
    private List<String> interviewTypes;
    private List<String> locations;

    public CreateCompanyAction(Admin admin) {
        super("1", "Create a company", admin);
        companyName = null;
        interviewTypes = new ArrayList<>();
        locations = new ArrayList<>();
    }

    /**
     * Method to collect a company title and the company's interview types
     */
    public void collectParameters() {
        companyName = InputHandler.getValueFromUser("Enter company name:", "Create Company: ");
        checkCompanyNameDuplicate(companyName);
        interviewTypes = InputHandler.collectMultipleStrings("interview types");
        locations = InputHandler.collectMultipleStrings("branch locations");
    }

    /**
     * Method to create a new company
     */
    public void performAction() {
        if (companyName == null || interviewTypes.isEmpty() || locations.isEmpty()) {
            System.out.println("Sorry, a company cannot be created without a name, branches or interview types.");
            return;
        }
        Company company = new Company(companyName, interviewTypes, locations);
        RecruitmentSystemFactory.getRecruitmentSystem().addCompany(company);
        System.out.println(String.format("Thank you, company '%s' is successfully added to Recruitment System.",
                companyName));
    }

    /**
     * Method to check if a company with this name already exists
     *
     * @param companyName company name to check for duplication
     */
    private void checkCompanyNameDuplicate(String companyName) {
        // duplicate company name
        try {
            RecruitmentSystemFactory.getRecruitmentSystem().getCompany(companyName);
            throw new InvalidInputException(String.format("A company with the name '%s' already exists.",
                    companyName));

            // company name is not a duplicate
        } catch (CompanyNotFoundException ignored) {
        }
    }
}


