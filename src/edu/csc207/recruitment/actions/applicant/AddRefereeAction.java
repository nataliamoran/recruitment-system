package edu.csc207.recruitment.actions.applicant;


import edu.csc207.recruitment.model.db.RecruitmentSystemFactory;
import edu.csc207.recruitment.model.user.Applicant;
import edu.csc207.recruitment.model.user.Referee;
import edu.csc207.recruitment.model.user.User;
import edu.csc207.recruitment.model.user.UserType;
import edu.csc207.recruitment.tools.InputHandler;
import edu.csc207.recruitment.tools.OutputHandler;

import java.util.List;

public class AddRefereeAction extends ApplicantAction {
    private List<User> allReferees;
    private Referee referee;

    public AddRefereeAction(Applicant applicant) {
        super("9", "To add a referee", applicant);
    }

    /**
     * Adds all referee to a list
     */
    @Override
    public void collectParameters() {
        allReferees = RecruitmentSystemFactory.getRecruitmentSystem().getUsersOfType(UserType.REFEREE);
        if (allReferees.isEmpty()) {
            System.out.println("No available referee, please tell them to make an account");
        }
        OutputHandler.printUsersInfo(allReferees, "Referees");
        final String choice = InputHandler.getValueFromUser("Enter referee username: ", "AddReferee");
        referee = (Referee) RecruitmentSystemFactory.getRecruitmentSystem().getUser(choice);
    }

    /**
     * Prints all referee and applicant chooses by entering username
     * Informs user if list is empty
     */
    @Override
    public void performAction() {
        if (referee == null) {
            return;
        }
        if (referee.getApplicantList().contains(this.applicant)) {
            System.out.println("Already a referee for you");
            return;
        }
        referee.addApplicant(this.applicant);
        System.out.println("Successfully added");
    }

}
