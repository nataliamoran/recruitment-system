package edu.csc207.recruitment.actions.referee;

import edu.csc207.recruitment.document.Document;
import edu.csc207.recruitment.document.DocumentDatabase;
import edu.csc207.recruitment.exceptions.document.FilesNotFoundException;
import edu.csc207.recruitment.exceptions.input.InvalidInputException;
import edu.csc207.recruitment.model.db.RecruitmentSystemFactory;
import edu.csc207.recruitment.model.user.Applicant;
import edu.csc207.recruitment.model.user.Referee;
import edu.csc207.recruitment.model.user.User;
import edu.csc207.recruitment.model.user.UserType;
import edu.csc207.recruitment.tools.InputHandler;
import edu.csc207.recruitment.tools.OutputHandler;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Class to allow a referee to upload a reference letter for an applicant.
 */
public class UploadReferenceLetterAction extends RefereeAction {
    private String applicantUsername;
    private String filePath;
    private Applicant applicant;

    public UploadReferenceLetterAction(Referee referee) {
        super("2", "To upload your reference letter", referee);
    }

    /**
     * Method to collect an applicant username and a document path from the referee input.
     */
    @Override
    public void collectParameters() {
        applicantUsername = null;
        filePath = null;
        if (referee.getApplicantList().isEmpty()) {
            System.out.println(
                    "No applicants to upload a letter for at this time, please tell them to add you as a referee.");
            return;
        }
        OutputHandler.printUsersInfo(referee.getApplicantList(), "Applicant");
        applicantUsername = InputHandler.getValueFromUser(
                "Enter the username of the applicant you are submitting a reference letter for",
                "Reference Letter Submission: ");
        User user = RecruitmentSystemFactory.getRecruitmentSystem().getUser(applicantUsername);
        if (!(user.getUserType() == UserType.APPLICANT)) {
            throw new InvalidInputException("This user is not an applicant.");
        }
        if (!referee.getApplicantList().contains(user)) {
            throw new InvalidInputException("Sorry, this applicant is not linked to your account.");
        }

        applicant = (Applicant) user;

        filePath = InputHandler.getValueFromUser(
                "Enter the file path to your text document",
                "Reference Letter Submission: ");
        if (!(Files.isRegularFile(Paths.get(filePath)))) {
            throw new FilesNotFoundException("This is not a valid file path.");
        }

    }

    /**
     * Method to submit a reference letter for an applicant.
     */
    @Override
    public void performAction() {
        if (applicantUsername == null || filePath == null) {
            return;
        }
        Document referenceLetter = DocumentDatabase.getInstance().addReferenceLetter(referee.getUsername(),
                applicantUsername, filePath);

        applicant.addReferenceLetterId(referenceLetter.getDocumentId());

        System.out.println(String.format(
                "Reference letter for %s with reference number %s is added.",
                applicantUsername,
                referenceLetter.getName().split("~")[1]
        ));
    }

}
