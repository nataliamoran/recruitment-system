package edu.csc207.recruitment.backgroundactions.applicant;

import edu.csc207.recruitment.model.company.Application;
import edu.csc207.recruitment.model.user.AccountHistory;
import edu.csc207.recruitment.model.user.Applicant;

import java.util.Date;

public class CalculateNumDaysSinceLastApplClosedBackgroundAction extends ApplicantBackgroundAction {
    private final Date today;

    public CalculateNumDaysSinceLastApplClosedBackgroundAction(Applicant applicant, Date today) {
        super(applicant);
        this.today = today;
    }

    /**
     * Method to set sinceLastApplicationClosure to the number of days it has been since the last application closure.
     * If an applicant has no closed applications, the method does nothing.
     */
    public void performAction() {
        AccountHistory history = this.applicant.getHistory();
        if (history.getClosedApplications().isEmpty()) {
            return;
        }
        int numDays = history.getClosedApplications().get(0).getSinceApplicationClosure(today);
        for (final Application application : history.getClosedApplications()) {
            int numDaysSinceApplicationClosure = application.getSinceApplicationClosure(today);
            if (numDaysSinceApplicationClosure < numDays) {
                numDays = numDaysSinceApplicationClosure;
            }
        }
        history.setSinceLastApplicationClosure(numDays);
    }
}
