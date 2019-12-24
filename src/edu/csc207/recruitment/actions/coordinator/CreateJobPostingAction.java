package edu.csc207.recruitment.actions.coordinator;

import edu.csc207.recruitment.exceptions.input.InvalidInputException;
import edu.csc207.recruitment.model.company.JobPosting;
import edu.csc207.recruitment.model.company.JobPostingRequirements;
import edu.csc207.recruitment.model.user.Coordinator;
import edu.csc207.recruitment.tools.InputHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class represents an action- create job posting among a list of coordinator actions.
 * When coordinator call this action, a new job is posted.
 */
public class CreateJobPostingAction extends CoordinatorAction {

    private String title;
    private String description;
    private String location;
    private Date postedDate;
    private Date applicationsDeadlineDate;
    private Map<String, Integer> interviewTypes;
    private List<String> tags;
    private List<String> requiredDocuments;
    private static final String PREFIX = "Create Job Posting";

    /**
     * Constructor take on param- coordinator which is inherited from super class.
     *
     * @param coordinator the person who will implement this action.
     */
    public CreateJobPostingAction(Coordinator coordinator) {
        super("2", "To create a new job posting", coordinator);
    }

    /**
     * Collect information base on the user input.
     * information collected including job title, description, post and close date.
     */
    @Override
    public void collectParameters() {
        interviewTypes = new HashMap<>();
        this.title = InputHandler.getValueFromUser(
                "Please enter Job Title:",
                PREFIX);
        this.description = InputHandler.getValueFromUser(
                "Please enter Job Description:",
                PREFIX);
        addLocation();
        addTags();
        addRequiredDocuments();
        this.postedDate = getDateFromUser("post date");
        this.applicationsDeadlineDate = getDateFromUser("applications deadline date");
        chooseInterviewTypes();
    }

    /**
     * A method for implementing the job creating action.
     */
    @Override
    public void performAction() {
        if (interviewTypes.isEmpty()) {
            return;
        }
        final JobPostingRequirements requirements = new JobPostingRequirements(
                this.applicationsDeadlineDate,
                this.requiredDocuments,
                this.tags,
                this.interviewTypes);
        final JobPosting job = new JobPosting(
                this.title,
                this.description,
                coordinator.getCompany(),
                this.location,
                this.postedDate,
                requirements);
        coordinator.getCompany().addJobPosting(job);
        System.out.println("New job posting has been created successfully");
    }


    /**
     * This method make sure the user's input is in the correct form.
     * IN addition it will catch the exception if the close date is prior to post date.
     *
     * @param dateType input strings from user.
     * @return a correct Date form from the user input.
     */
    private Date getDateFromUser(String dateType) {
        final SimpleDateFormat formatter = new SimpleDateFormat("MMddyyyy");
        while (true) {
            final String dateInput = InputHandler.getValueFromUser(String.format(
                    "Please enter this job's %s. The format should be MMDDYYYY (example: 07122018):",
                    dateType),
                    "Create Job Posting");
            try {
                final Date date = formatter.parse(dateInput);
                if (dateType.equals("close date") && date.before(this.postedDate)) {
                    System.out.println("Close date cannot be prior to posted date, please try again");
                    continue;
                }
                System.out.println(String.format("Date: [%s]", date.toString()));
                return date;
            } catch (ParseException e) {
                System.out.println("The date format is incorrect, please try again.");
            }
        }
    }

    /**
     * Method to get a number of interviews for every interview type from the user input and
     * save them in interview types map.
     */
    private void chooseInterviewTypes() {
        final List<String> interviewTypesNames = coordinator.getCompany().getInterviewTypes();
        System.out.println(
                "For every interview type, enter how many interviews of this type are required for this job posting");
        for (String interviewTypeName : interviewTypesNames) {
            final String interviewNumString = InputHandler.getValueFromUser(
                    String.format("Enter a number of %s interviews: ", interviewTypeName),
                    "Create Job Posting: ");
            try {
                final Integer interviewNumInt = Integer.parseInt(interviewNumString);
                this.interviewTypes.put(interviewTypeName, interviewNumInt);
            } catch (NumberFormatException e) {
                System.out.println("Sorry, invalid input. The input should be a number.");
                return;
            }
        }
    }

    /**
     * Method to collect tags from the user input.
     */
    private void addTags() {
        final String str = InputHandler.getValueFromUser(
                "Please enter job tags separated by space. You can write required skills as tags (e.g. #Java):",
                PREFIX);
        final List<String> tempTags = Arrays.asList(str.split(" +"));
        this.tags = new ArrayList<>();
        for (String tag : tempTags) {
            if (tag.startsWith("#")) {
                this.tags.add(tag);
            } else {
                this.tags.add("#" + tag);
            }
        }
    }

    /**
     * Method to collect a branch location from the user input.
     */
    private void addLocation() {
        this.location = InputHandler.getValueFromUser(
                "Please enter the location:",
                PREFIX);
        if (!coordinator.getLocations().contains(location)) {
            throw new InvalidInputException(String.format("Sorry, you don't work in the branch %s.", location));
        }
    }

    /**
     * Method to collect additional required documents from the user input.
     */
    private void addRequiredDocuments() {
        this.requiredDocuments = new ArrayList<>();
        String requiredDocumentsChoice = InputHandler.getValueFromUser(
                String.format("CV and Cover Letter are required for every job posting by default." +
                        "%nDo you wish to add more mandatory documents for this position? (Y/N)"),
                PREFIX);
        if (requiredDocumentsChoice.equalsIgnoreCase("Y")) {
            try {
                this.requiredDocuments = InputHandler.collectMultipleStrings(
                        "required documents");
            } catch (InvalidInputException e) {

            }
        }

    }
}
