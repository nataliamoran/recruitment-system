package edu.csc207.recruitment.document;

import edu.csc207.recruitment.exceptions.document.DocumentAlreadyExistsException;
import edu.csc207.recruitment.exceptions.document.DocumentNotFoundException;

import java.util.HashMap;

public class UserDocuments {
    /**
     * Handles the individual documents for a single user, which can be retrieved by name or document name. Note that
     * the name retrieval only gets the first document with that name encountered, and thus is less accurate than the id
     * retrieval.
     */
    private HashMap<Integer, Document> contents;
    private final String ownerUsername;

    public UserDocuments(String ownerUsername) {
        this.ownerUsername = ownerUsername;
        this.contents = new HashMap<>();
    }

    /**
     * Adds a Document
     *
     * @param document
     * @throws DocumentAlreadyExistsException
     */
    public void addDocument(Document document) throws DocumentAlreadyExistsException {
        int documentId = document.getDocumentId();
        if (this.contents.containsKey(documentId)) {
            throw new DocumentAlreadyExistsException(String.format("Document %s already exists", document.getName()));
        }
        this.contents.put(documentId, document);
    }

    /**
     * Gets Document by id
     *
     * @param documentId
     * @return
     * @throws DocumentNotFoundException
     */
    public Document getDocument(int documentId) throws DocumentNotFoundException {
        if (this.contents.containsKey(documentId)) {
            return this.contents.get(documentId);
        }
        throw new DocumentNotFoundException(String.format("Document with id %s not found", documentId));

    }

    /**
     * gets Document by id
     *
     * @param name
     * @return
     * @throws DocumentNotFoundException
     */
    public Document getDocument(String name) throws DocumentNotFoundException {
        for (Integer id : this.contents.keySet()) {
            Document document = this.contents.get(id);
            if (document.getName().equals(name)) {
                return document;
            }
        }
        throw new DocumentNotFoundException(String.format("Document with name %s not found", name));
    }

    /**
     * Removes access to document from hre
     *
     * @param documentId
     * @throws DocumentNotFoundException
     */
    public void removeDocument(int documentId) throws DocumentNotFoundException {
        if (this.contents.containsKey(documentId)) {
            this.contents.remove(documentId);
            return;
        }
        throw new DocumentNotFoundException(String.format("Document with id %s not found", documentId));
    }

    /**
     * @return map of document ids and Documents
     */
    public HashMap<Integer, Document> getContents() {
        return this.contents;
    }

    public int size() {
        return this.contents.keySet().size();
    }
}
