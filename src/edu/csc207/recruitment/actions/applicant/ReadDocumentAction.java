package edu.csc207.recruitment.actions.applicant;

import edu.csc207.recruitment.document.Document;
import edu.csc207.recruitment.document.DocumentDatabase;
import edu.csc207.recruitment.exceptions.document.DocumentNotFoundException;
import edu.csc207.recruitment.model.user.Applicant;
import edu.csc207.recruitment.tools.InputHandler;

/**
 * Prints out the content of a document owned by the applicant
 */
public class ReadDocumentAction extends ApplicantAction {
    private Document document;

    public ReadDocumentAction(Applicant applicant) {
        super("6", "To print the contents of a document", applicant);
    }

    /**
     * Gets the document from DocumentDatabase
     */
    public void collectParameters() {
        if (applicant.getDocumentsNames().isEmpty()) {
            System.out.println("Sorry, no available documents");
            return;
        }
        System.out.println("Available documents:");
        for (final String documentName : applicant.getDocumentsNames()) {
            System.out.println("   " + documentName);
        }
        final String docname = InputHandler.getValueFromUser("Enter the name of your document", "Read document");
        document = DocumentDatabase.getInstance().getDocument(docname, applicant.getUsername());
        if (document == null) {
            throw new DocumentNotFoundException(String.format("Document with name %s not found", docname));
        }
    }

    /**
     * Prints out the content of this document
     */
    public void performAction() {
        if (document == null) {
            return;
        }
        document.printContents();
    }
}
