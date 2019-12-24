package edu.csc207.recruitment.managers;

import edu.csc207.recruitment.model.user.*;

import java.util.Date;

public class ManagerFactory {

    /**
     * Creates a manager for the user based on the user type
     *
     * @param user  the user
     * @param today Today's date, relevant only if the user is Applicant
     * @return the manager for the user
     */
    public static Manager createManager(final User user, final Date today) {
        final UserType userType = user.getUserType();
        Manager manager = null;
        if (userType.equals(UserType.APPLICANT)) {
            manager = new ApplicantManager((Applicant) user, today);
        } else if (userType.equals(UserType.COORDINATOR)) {
            manager = new CoordinatorManager((Coordinator) user, today);
        } else if (userType.equals(UserType.INTERVIEWER)) {
            manager = new InterviewerManager((Interviewer) user, today);
        } else if (userType.equals(UserType.ADMIN)) {
            manager = new AdminManager((Admin) user);
        } else if (userType.equals(UserType.REFEREE)) {
            manager = new RefereeManager((Referee) user);
        }
        return manager;
    }
}
