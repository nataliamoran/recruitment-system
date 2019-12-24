/*
package document;

import edu.csc207.recruitment.actions.applicant.UploadDocumentAction;
import edu.csc207.recruitment.model.user.Applicant;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class UploadDocumentActionTest {
    private Applicant applicant = new Applicant("user1", "pass1", new Date());
    private DocumentDatabase documentDatabase = DocumentDatabase.getInstance();


    @Test
    public void Collect_ALL_Parameters_Test() {
        UploadDocumentAction uploadDocumentAction = new UploadDocumentAction(applicant);
        uploadDocumentAction.collectParameters();
        Assert.assertNotNull(uploadDocumentAction.getDocName());
        Assert.assertNotNull(uploadDocumentAction.getFilePath());
    }

    @Test
    public void PerformAction_Test() {
        UploadDocumentAction uploadDocumentAction = new UploadDocumentAction(applicant);
        uploadDocumentAction.performAction();
        Assert.assertNotNull(documentDatabase.getDocument(uploadDocumentAction.getDocName(), uploadDocumentAction.getUserName()));

    }
}
*/
