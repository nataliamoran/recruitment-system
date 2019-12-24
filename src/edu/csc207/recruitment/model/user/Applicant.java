package edu.csc207.recruitment.model.user;

import edu.csc207.recruitment.model.company.Application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class represents an Applicant, a type of User.
 */
public class Applicant extends User {
    private AccountHistory history;
    private List<String> documents;
    private ArrayList<Integer> referenceLetters;


    public Applicant(String username, String password, Date creationDate) {
        super(username, password, UserType.APPLICANT);
        this.history = new AccountHistory(creationDate);
        this.referenceLetters = new ArrayList<>();
        this.documents = new ArrayList<>();
    }

    public AccountHistory getHistory() {
        return this.history;
    }

    public void updateAllTransient() {
        this.history.updateAllTransient(this);
    }

    public void replaceApplication(Application application) {
        this.history.replaceApplication(application);
    }

    public void addReferenceLetterId(int n) {
        this.referenceLetters.add(n);
    }

    public List<Integer> getReferenceLetterIds() {
        return this.referenceLetters;
    }

    public List<String> getDocumentsNames() {
        return this.documents;
    }

    /**
     * Method to add a new document name to the list of the applicant's documents.
     *
     * @param docName the name of the document
     */
    public void addDocumentName(String docName) {
        this.documents.add(docName);
    }

    /**
     * Method to remove all documents names from the list of the applicant's documents.
     */
    public void removeAllDocumentsNames() {
        this.documents = new ArrayList<>();
    }
}
