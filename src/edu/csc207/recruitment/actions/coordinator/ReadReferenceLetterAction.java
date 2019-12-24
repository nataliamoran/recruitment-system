package edu.csc207.recruitment.actions.coordinator;

import edu.csc207.recruitment.document.DocumentDatabase;
import edu.csc207.recruitment.exceptions.input.InvalidInputException;
import edu.csc207.recruitment.model.db.RecruitmentSystemFactory;
import edu.csc207.recruitment.model.user.Applicant;
import edu.csc207.recruitment.model.user.Coordinator;
import edu.csc207.recruitment.model.user.User;
import edu.csc207.recruitment.model.user.UserType;
import edu.csc207.recruitment.tools.InputHandler;
import edu.csc207.recruitment.tools.OutputHandler;

import java.util.List;

public class ReadReferenceLetterAction extends CoordinatorAction {
    private Applicant applicant;
    private List<Integer> lettersIds;
    private List<User> allApplicants;

    /**
     * Constructor take on param- coordinator which is inherited from super class.
     *
     * @param coordinator the person who will implement this action.
     */
    public ReadReferenceLetterAction(final Coordinator coordinator) {
        super("7", "To view reference letters for an applicant", coordinator);
    }

    /**
     * Collect information based on the user input.
     */
    @Override
    public void collectParameters() {
        allApplicants = RecruitmentSystemFactory.getRecruitmentSystem().getUsersOfType(UserType.APPLICANT);
        OutputHandler.printUsersInfo(allApplicants, "Applicants");
        String username = InputHandler.getValueFromUser("Enter in the username for the applicant ",
                "Read reference letter: ");
        User user = RecruitmentSystemFactory.getRecruitmentSystem().getUser(username);
        if (!(user.getUserType() == UserType.APPLICANT)) {
            throw new InvalidInputException("This user is not an applicant.");
        }
        applicant = (Applicant) user;
        lettersIds = applicant.getReferenceLetterIds();
    }

    public void performAction() {
        if (lettersIds.size() == 0) {
            System.out.println("There are no reference letters for " + applicant.getUsername());
        }
        for (int did : lettersIds) {
            System.out.println("Reference letter for " + applicant.getUsername() + " :");
            DocumentDatabase.getInstance().getDocument(did).printContents();
        }
    }
}
