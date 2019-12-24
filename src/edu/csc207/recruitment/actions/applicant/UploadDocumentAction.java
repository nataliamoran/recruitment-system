package edu.csc207.recruitment.actions.applicant;

import edu.csc207.recruitment.document.DocumentDatabase;
import edu.csc207.recruitment.exceptions.document.FilesNotFoundException;
import edu.csc207.recruitment.model.user.Applicant;
import edu.csc207.recruitment.tools.InputHandler;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Action to upload documents to the database
 */
public class UploadDocumentAction extends ApplicantAction {
    private final String userName;
    private String docName;
    private String filePath;
    private final static String PREFIX = "Upload Documents";

    /**
     * Takes the applicant, the date, and current date in the constructor
     *
     * @param applicant applicant
     */
    public UploadDocumentAction(Applicant applicant) {
        super("1", "To upload applicant documents to the database", applicant);
        userName = applicant.getUsername();
    }

    /**
     * Collects parameters from user
     */
    public void collectParameters() {
        docName = InputHandler.getValueFromUser(
                "Enter the name of the document you want to upload. It is advisable to upload CV and Cover Letter.",
                PREFIX);
        filePath = InputHandler.getValueFromUser("Enter the file path to your text document: ", PREFIX);
        if (!(Files.isRegularFile(Paths.get(filePath)))) {
            throw new FilesNotFoundException("This is not a valid file path.");
        }
    }

    /**
     * Adds the document
     */
    public void performAction() {
        DocumentDatabase.getInstance().addDocument(userName, filePath, docName);
        applicant.addDocumentName(docName);
        System.out.println(String.format("%s is successfully added.", docName));
    }
}
