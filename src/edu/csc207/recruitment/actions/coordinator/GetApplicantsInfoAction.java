package edu.csc207.recruitment.actions.coordinator;

import edu.csc207.recruitment.document.Document;
import edu.csc207.recruitment.document.DocumentDatabase;
import edu.csc207.recruitment.exceptions.input.InvalidInputException;
import edu.csc207.recruitment.model.company.Application;
import edu.csc207.recruitment.model.company.JobPosting;
import edu.csc207.recruitment.model.db.RecruitmentSystemFactory;
import edu.csc207.recruitment.model.user.Applicant;
import edu.csc207.recruitment.model.user.Coordinator;
import edu.csc207.recruitment.model.user.User;
import edu.csc207.recruitment.model.user.UserType;
import edu.csc207.recruitment.tools.InputHandler;
import edu.csc207.recruitment.tools.OutputHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to allow a coordinator to see an applicant's documents and jobs which the applicant applied for
 */
public class GetApplicantsInfoAction extends CoordinatorAction {
    private static final String APPLICANTS_DOCUMENTS = "1";
    private static final String INFO_APPLIED_JOBS = "2";
    private Applicant applicant;
    private String applicantUsername;
    private String actionOptionChoice;
    private String documentTitle;
    private static final String PREFIX = "Applicant Info";

    public GetApplicantsInfoAction(Coordinator coordinator) {
        super("3",
                "To get all applicants info",
                coordinator);
    }

    /**
     * Method to allow a coordinator to choose an applicant and whether to see documents or jobs applied for
     */
    @Override
    public void collectParameters() {
        final List<User> applicants = RecruitmentSystemFactory
                .getRecruitmentSystem()
                .getUsersOfType(UserType.APPLICANT);
        OutputHandler.printUsersInfo(applicants, "applicants");
        if (applicants.isEmpty()) {
            return;
        }
        this.applicantUsername = InputHandler.getValueFromUser(
                "To see an applicant's info, enter the applicant's username: ",
                PREFIX);
        this.applicant = (Applicant) RecruitmentSystemFactory.getRecruitmentSystem().getUser(this.applicantUsername);
        actionOptionChoice = InputHandler.getValueFromUser(
                String.format("%nEnter %s to access the applicant's documents" +
                                "%nEnter %s to access job(s) applied for in your company.",
                        APPLICANTS_DOCUMENTS,
                        INFO_APPLIED_JOBS),
                PREFIX);
        if (actionOptionChoice.equals(APPLICANTS_DOCUMENTS)) {
            System.out.println("Please see below the applicant's documents.");
            applicant.getDocumentsNames().forEach(System.out::println);
            this.documentTitle = InputHandler.getValueFromUser(
                    "Enter the title of the document you want to see: ",
                    PREFIX);
        }
    }

    /**
     * Method to show requested information (applicant's documents or jobs applied for)
     */
    @Override
    public void performAction() {
        if (this.applicant == null) {
            return;
        }
        switch (actionOptionChoice) {
            case APPLICANTS_DOCUMENTS:
                Document doc = DocumentDatabase.getInstance().getDocument(documentTitle, applicantUsername);
                doc.printContents();
                break;
            case INFO_APPLIED_JOBS:
                final List<JobPosting> jobsAppliedInCoordinatorCompany = getJobPostingsInCompany();
                OutputHandler.printJobPostingsList(jobsAppliedInCoordinatorCompany, "relevant");
                break;
            default:
                throw new InvalidInputException(String.format("Sorry, option '%s' does not exist.", actionOptionChoice));
        }
    }

    /**
     * Method to filter out job postings open at the coordinator's company which an applicant applied for
     *
     * @return list of filtered job postings
     */
    private List<JobPosting> getJobPostingsInCompany() {
        final List<JobPosting> jobsAppliedInCoordinatorCompany = new ArrayList<>();
        for (final Application application : applicant.getHistory().getOpenApplications()) {
            JobPosting job = application.getJobPosting();
            if (coordinator.getCompany().equals(job.getCompany())) {
                jobsAppliedInCoordinatorCompany.add(job);
            }
        }
        return jobsAppliedInCoordinatorCompany;
    }
}

