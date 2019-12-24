package edu.csc207.recruitment.document;

import edu.csc207.recruitment.exceptions.base.RecruitmentSystemIOException;
import edu.csc207.recruitment.exceptions.document.DocumentAlreadyExistsException;
import edu.csc207.recruitment.exceptions.document.DocumentNotFoundException;
import edu.csc207.recruitment.exceptions.document.FilesNotFoundException;
import edu.csc207.recruitment.exceptions.user.UserNotFoundException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;

public class DocumentDatabase {
    private HashMap<String, UserDocuments> docs;
    private String STORAGE_PATH = "." + File.separator + "storage";
    private String TEST_PATH = "." + File.separator + "testStorage";
    private String OG_PATH = "." + File.separator + "storage";
    private static DocumentDatabase instance = new DocumentDatabase();

    /**
     * Class for storing all documents and enforcing ownership in the Recruitment System
     */
    public DocumentDatabase() {
        docs = new HashMap<>();
    }

    /**
     * @return singleton DocumentDatabase instance
     */
    public static DocumentDatabase getInstance() {
        return instance;
    }

    /**
     * Switches to run data
     */
    public void switchStorage() {
        STORAGE_PATH = OG_PATH;
    }

    /**
     * Switches to test data
     */
    public void switchTest() {
        STORAGE_PATH = TEST_PATH;
    }

    /**
     * @param username
     * @return PersonalDocument object hander for all documents belonging to username
     */
    public UserDocuments getUserDocuments(String username) {
        if (!this.docs.containsKey(username)) {
            this.docs.put(username, new UserDocuments(username));
        }
        return this.docs.get(username);
    }

    public int size() {
        int s = 0;
        for (UserDocuments d : this.docs.values()) {
            s += d.size();
        }
        return s;
    }


    /**
     * Adds a document to the system. Note all document additions must be done here
     *
     * @param ownerUsername owner of document
     * @param path          original location of document
     * @param name          name of document to be renamed to
     * @return Document created
     * @throws RecruitmentSystemIOException
     * @throws DocumentAlreadyExistsException
     */
    public Document addDocument(String ownerUsername, String path, String name)
            throws RecruitmentSystemIOException, DocumentAlreadyExistsException {
        try {
            Path oldPath = Paths.get(path);
            if (!Files.exists(oldPath)) {
                throw new FilesNotFoundException(String.format("File not found at %s", path));
            }
            Path newPath = this.getPersonalPath(ownerUsername);
            Path finalPath = this.getFilePath(ownerUsername, name);
            if (!Files.exists(newPath)) {
                Files.createDirectories(newPath);
            }
            Files.copy(oldPath, finalPath, StandardCopyOption.REPLACE_EXISTING);
            Document d = new Document(ownerUsername, finalPath.toString(), name);
            updateUserDocuments(d);
            return d;
        } catch (IOException e) {
            throw new RecruitmentSystemIOException("IO exception");
        }
    }


    private void updateUserDocuments(Document document) throws DocumentAlreadyExistsException {
        String owner = document.getOwnerUsername();
        if (!this.docs.containsKey(owner)) {
            UserDocuments e = new UserDocuments(owner);
            this.docs.put(owner, e);
        }
        this.docs.get(owner).addDocument(document);
    }

    public Document getDocument(String name, String ownerUsername) {
        if (!this.docs.containsKey(ownerUsername)) {
            throw new UserNotFoundException(String.format("Sorry, user %s is not found.", ownerUsername));
        }
        if (this.docs.get(ownerUsername) == null) {
            throw new DocumentNotFoundException("Sorry, this applicant has no uploaded documents.");
        }
        return this.docs.get(ownerUsername).getDocument(name);
    }

    /**
     * @param documentId
     * @return Document according to id
     */
    public Document getDocument(int documentId) {
        for (String n : this.docs.keySet()) {
            try {
                Document document = this.docs.get(n).getDocument(documentId);
                if (document != null) {
                    return document;
                }
            } catch (DocumentNotFoundException e) {
            }
        }
        return null;
    }

    /**
     * @param documentId
     * @param ownerUsername
     * @return Document with id belonging to username
     */
    public Document getDocument(int documentId, String ownerUsername) {
        if (!this.docs.containsKey(ownerUsername)) {
            return null;
        }
        return this.docs.get(ownerUsername).getDocument(documentId);
    }


    /**
     * Removes document everywhere
     *
     * @param documentId
     * @throws DocumentNotFoundException
     */
    public void removeDocument(int documentId) throws DocumentNotFoundException {
        Document document = this.getDocument(documentId);
        if (document != null) {
            String ownerUsername = document.getOwnerUsername();
            this.docs.get(ownerUsername).removeDocument(documentId);
        }
    }

    /**
     * Removes document everywhere
     *
     * @param name
     * @param ownerUsername
     * @throws DocumentNotFoundException
     */
    public void removeDocument(String name, String ownerUsername) throws DocumentNotFoundException {
        Document document = this.getDocument(name, ownerUsername);
        if (document != null) {
            int documentId = document.getDocumentId();
            this.docs.get(ownerUsername).removeDocument(documentId);
        }
    }

    /**
     * Removes document everywhere, has to be owned by username
     *
     * @param documentId
     * @param ownerUsername
     * @throws DocumentNotFoundException
     */
    public void removeDocument(int documentId, String ownerUsername) throws DocumentNotFoundException {
        this.docs.get(ownerUsername).removeDocument(documentId);
    }

    /**
     * Be careful with this, testing use only
     *
     * @param documentDatabase
     */
    public static void setInstance(DocumentDatabase documentDatabase) {
        instance = documentDatabase;
    }

    private Path getFilePath(String ownerUsername, String name) {
        Path partialPath = Paths.get(ownerUsername);
        Path namePath = Paths.get(name);
        Path newPath = Paths.get(STORAGE_PATH).resolve(partialPath);
        return newPath.resolve(namePath);
    }

    private Path getPersonalPath(String ownerUsername) {
        Path partialPath = Paths.get(ownerUsername);
        return Paths.get(STORAGE_PATH).resolve(partialPath);
    }

    public Document addReferenceLetter(String refereeUsername, String applicantUsername, String path)
            throws RecruitmentSystemIOException, DocumentAlreadyExistsException {
        int i = 0;
        while (true) {
            try {
                Document document = addDocument(refereeUsername, path, applicantUsername + "~" + i);
                return document;
            } catch (DocumentAlreadyExistsException d) {
                i += 1;
            } catch (FilesNotFoundException e) {
                throw new FilesNotFoundException(String.format("File not found at %s", path));
            } catch (RecruitmentSystemIOException f) {
                throw new RecruitmentSystemIOException("IO exception");
            }
        }
    }


}
