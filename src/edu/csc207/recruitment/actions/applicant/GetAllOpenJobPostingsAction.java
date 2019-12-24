package edu.csc207.recruitment.actions.applicant;

import edu.csc207.recruitment.exceptions.input.InvalidInputException;
import edu.csc207.recruitment.exceptions.jobposting.JobPostingNotFoundException;
import edu.csc207.recruitment.model.company.JobPosting;
import edu.csc207.recruitment.model.db.RecruitmentSystemFactory;
import edu.csc207.recruitment.model.user.Applicant;
import edu.csc207.recruitment.tools.InputHandler;
import edu.csc207.recruitment.tools.OutputHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * this class is for getting all posted jobs.
 */
public class GetAllOpenJobPostingsAction extends ApplicantAction {

    private Map<String, List<JobPosting>> tagToJobs;
    private String tagChoice;
    private List<JobPosting> allOpenJobPostings;
    private String sortingByTagChoice;

    public GetAllOpenJobPostingsAction(Applicant applicant) {
        super("3", "To get all open job postings", applicant);
    }

    /**
     * Method to get an applicant's input on whether they want job postings to be sorted by tags or not.
     */
    public void collectParameters() {
        // reset fields before collecting values
        tagToJobs = new HashMap<>();
        tagChoice = null;
        sortingByTagChoice = null;
        // collecting values
        allOpenJobPostings = RecruitmentSystemFactory.getRecruitmentSystem().getAllOpenJobPostings();
        if (allOpenJobPostings.isEmpty()) {
            throw new JobPostingNotFoundException("Sorry, currently there are no open job postings.");
        }
        sortingByTagChoice = InputHandler.getValueFromUser(
                "Do you want to sort by tag? (Y / N)",
                "Show Open Job Postings: ");
        switch (sortingByTagChoice.toUpperCase()) {
            case "Y":
                sortJobPostingsByTags();
                System.out.println("Job postings tags:");
                tagToJobs.keySet().forEach(System.out::println);
                String tempChoice = InputHandler.getValueFromUser("Enter one of the tags listed above: ",
                        "Show Open Job Postings: ");
                if (tempChoice.startsWith("#")) {
                    tagChoice = tempChoice;
                } else {
                    tagChoice = "#" + tempChoice;
                }
                break;
            case "N":
                break;
            default:
                throw new InvalidInputException(String.format("Sorry, option %s does not exist.", sortingByTagChoice));
        }
    }

    /**
     * Method to print out all open job postings
     */
    public void performAction() {
        if (sortingByTagChoice == null) {
            return;
        }
        if (sortingByTagChoice.equalsIgnoreCase("N")) {
            OutputHandler.printJobPostingsList(allOpenJobPostings, "open");
            return;
        }
        if (!tagToJobs.containsKey(tagChoice)) {
            throw new InvalidInputException(String.format("Sorry, tag %s does not exist.", tagChoice));
        }
        OutputHandler.printJobPostingsList(tagToJobs.get(tagChoice), "open");
    }

    /**
     * Method to collect tags from all open job postings and to sort jobs per tags.
     */
    private void sortJobPostingsByTags() {
        for (final JobPosting job : allOpenJobPostings) {
            final List<String> tags = job.getRequirements().getTags();
            for (final String tag : tags) {
                if (!tagToJobs.containsKey(tag)) {
                    tagToJobs.put(tag, new ArrayList<>());
                }
                tagToJobs.get(tag).add(job);
            }
        }
    }


}



