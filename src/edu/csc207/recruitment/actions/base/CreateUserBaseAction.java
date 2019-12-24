package edu.csc207.recruitment.actions.base;

import edu.csc207.recruitment.exceptions.company.BranchNotFoundException;
import edu.csc207.recruitment.exceptions.company.CompanyNotFoundException;
import edu.csc207.recruitment.exceptions.input.InvalidInputException;
import edu.csc207.recruitment.exceptions.user.UserNotFoundException;
import edu.csc207.recruitment.managers.Manager;
import edu.csc207.recruitment.managers.ManagerFactory;
import edu.csc207.recruitment.model.company.Company;
import edu.csc207.recruitment.model.db.RecruitmentSystemFactory;
import edu.csc207.recruitment.model.user.*;
import edu.csc207.recruitment.tools.InputHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateUserBaseAction extends AbstractAction implements Action {
    private static final String APPLICANT = "1";
    private static final String COORDINATOR = "2";
    private static final String INTERVIEWER = "3";
    private static final String ADMIN = "4";
    private static final String REFEREE = "5";
    private static final String PREFIX = "Create a user";
    private String username;
    private String password;
    private UserType userType;
    private Company company;
    private Date today;
    private List<String> locations;
    private boolean goToUserManagement;

    public CreateUserBaseAction(final String shortcut, final String description, final boolean goToUserManagement) {
        super(shortcut, description);
        locations = new ArrayList<>();
        this.goToUserManagement = goToUserManagement;
    }


    /**
     * Method to collect username, password, company title and user type from a user input.
     * If username, password, company title or user type are invalid, then throws a relevant exception.
     */
    @Override
    public void collectParameters() {
        username = InputHandler.getValueFromUser("Enter username:", PREFIX);
        checkUsernameDuplicate(username);
        checkValidString(username);
        password = InputHandler.getValueFromUser("Enter password:", PREFIX);
        checkValidString(password);
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("Choose the user type: %n"));
        stringBuilder.append(String.format("- for applicant press %s %n", APPLICANT));
        stringBuilder.append(String.format("- for coordinator press %s %n", COORDINATOR));
        stringBuilder.append(String.format("- for interviewer press %s %n", INTERVIEWER));
        stringBuilder.append(String.format("- for admin press %s %n", ADMIN));
        stringBuilder.append(String.format("- for referee press %s %n", REFEREE));
        userType = stringToUserType(InputHandler.getValueFromUser(stringBuilder.toString(), PREFIX));
        today = InputHandler.getDate();
        if (userType == UserType.APPLICANT || userType == UserType.ADMIN || userType == UserType.REFEREE) {
            return;
        }
        chooseCompanyAndBranches();
    }

    /**
     * Method to create an account for a user.
     */
    @Override
    public void performAction() {
        //Creating user
        User user = createUser(userType);
        if (goToUserManagement) {
            Manager manager = ManagerFactory.createManager(user, today);
            manager.manage();
        }
    }

    /**
     * Method to create a user based on the UserType
     *
     * @param userType user type chosen by a new user
     * @return new user
     */
    private User createUser(UserType userType) {
        User user;
        switch (userType) {
            case APPLICANT:
                user = new Applicant(username, password, today);
                break;
            case COORDINATOR:
                user = new Coordinator(username, password, company, locations);
                company.addStaff(user);
                break;
            case INTERVIEWER:
                user = new Interviewer(username, password, company, locations);
                company.addStaff(user);
                break;
            case ADMIN:
                user = new Admin(username, password);
                break;
            case REFEREE:
                user = new Referee(username, password);
                break;
            default:
                throw new InvalidInputException("Sorry, invalid user type input.");
        }
        RecruitmentSystemFactory.getRecruitmentSystem().addUser(user);
        return user;
    }

    /**
     * Method to determine a user's type based on their input
     *
     * @param str user type input by a user
     * @return UserType equal to the user input
     */
    private static UserType stringToUserType(String str) {
        UserType userType;
        switch (str) {
            case APPLICANT:
                userType = UserType.APPLICANT;
                break;
            case COORDINATOR:
                userType = UserType.COORDINATOR;
                break;
            case INTERVIEWER:
                userType = UserType.INTERVIEWER;
                break;
            case ADMIN:
                userType = UserType.ADMIN;
                break;
            case REFEREE:
                userType = UserType.REFEREE;
                break;
            default:
                throw new InvalidInputException("Sorry, invalid user type input.");
        }
        return userType;
    }

    /**
     * Method to check if a user with this username already exists
     *
     * @param username username to check for duplication
     */
    private void checkUsernameDuplicate(String username) {
        // duplicate username
        try {
            RecruitmentSystemFactory.getRecruitmentSystem().getUser(username);
            throw new InvalidInputException(String.format("A user with the username '%s' already exists.",
                    username));

            // username is not a duplicate
        } catch (UserNotFoundException e) {

        }
    }

    /**
     * Method for a user to choose the company and branches where they work.
     */
    private void chooseCompanyAndBranches() {
        if (RecruitmentSystemFactory.getRecruitmentSystem().getAllCompanies().isEmpty()) {
            throw new CompanyNotFoundException("There are no companies yet. Ask the Admin to create a company.");
        }
        String companyTitle = InputHandler.getValueFromUser(
                "Enter the company name:",
                "Sign up: ");
        company = RecruitmentSystemFactory.getRecruitmentSystem().getCompany(companyTitle);
        locations = InputHandler.collectMultipleStrings("branches");
        List<String> companyBranches = company.getLocations();
        for (String location : locations) {
            if (!companyBranches.contains(location)) {
                throw new BranchNotFoundException(String.format("Sorry, the company %s does not have the branch %s.",
                        companyTitle, location));
            }
        }
    }

    /**
     * Method to check if a string contains alphanumeric characters only.
     *
     * @param s string to validate
     */
    private void checkValidString(String s) {
        if (!s.matches("[a-zA-Z0-9]+")) {
            throw new InvalidInputException("Usernames and passwords may only contain alphanumeric characters");
        }
    }
}
