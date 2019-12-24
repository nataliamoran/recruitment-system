package edu.csc207.recruitment.backgroundactions.applicant;

import edu.csc207.recruitment.document.Document;
import edu.csc207.recruitment.document.DocumentDatabase;
import edu.csc207.recruitment.model.user.Applicant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Remove applicant documents 30 days after last application was closed
 */
public class RemoveApplicantAllDocumentsAfter30DaysSinceLastApplicationClosureBackgroundAction extends ApplicantBackgroundAction {
    private static final int MAX_DAYS_TO_KEEP_DOCUMENT = 30;

    public RemoveApplicantAllDocumentsAfter30DaysSinceLastApplicationClosureBackgroundAction(Applicant applicant) {
        super(applicant);
    }

    /**
     * Method to delete applicant's documents if more than 30 days has passes since
     * the applicant's last application closure
     */
    @Override
    public void performAction() {
        if (belowMaxDaysPassedSinceClosure(applicant) || hasOpenApplications(applicant)) {
            System.out.println("[Remove obsolete documents] No documents to be removed");
            return;
        }
        final Map<Integer, Document> relevantDocuments =
                DocumentDatabase.getInstance().getUserDocuments(applicant.getUsername()).getContents();
        final List<Integer> documentIdsToDelete = new ArrayList<>(relevantDocuments.keySet());
        for (final Integer id : documentIdsToDelete) {
            DocumentDatabase.getInstance().removeDocument(id);
        }
        applicant.removeAllDocumentsNames();
    }

    private boolean belowMaxDaysPassedSinceClosure(Applicant applicant) {
        return applicant.getHistory().getSinceLastApplicationClosure() == null ||
                applicant.getHistory().getSinceLastApplicationClosure() < MAX_DAYS_TO_KEEP_DOCUMENT;
    }

    private boolean hasOpenApplications(Applicant applicant) {
        return !applicant.getHistory().getOpenApplications().isEmpty();
    }
}
