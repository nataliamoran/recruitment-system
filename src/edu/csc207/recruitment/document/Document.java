package edu.csc207.recruitment.document;

import edu.csc207.recruitment.exceptions.base.RecruitmentSystemIOException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Document {
    /**
     * Individual documents, which can be accessed by id. Note that this class stores the path to the file rather than
     * the file itself
     */
    private String path;
    private static int nextId;
    private String name;
    private final String ownerUsername;
    private final int documentId;

    /**
     * Creates a document
     *
     * @param ownerUsername the username of the owner
     * @param path          the path that the document is stored in
     * @param name          the name of this document
     */

    public Document(String ownerUsername, String path, String name) {
        this.path = path;
        this.name = name;
        this.ownerUsername = ownerUsername;
        this.documentId = nextId;
        nextId++;
    }

    /**
     * @return Path where the data is stored
     */
    public Path getPath() {
        return Paths.get(this.path);
    }

    /**
     * @return the name of this document
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return username for the document original owner
     */
    public String getOwnerUsername() {
        return this.ownerUsername;
    }

    /**
     * @return unique id of document
     */
    public int getDocumentId() {
        return this.documentId;
    }


    /**
     * @returns a list of the string representation of the contents of this file
     */
    public ArrayList<String> getContents() throws RecruitmentSystemIOException {
        ArrayList<String> lines = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(path));
            while (reader.ready()) {
                lines.add(reader.readLine());

            }
            reader.close();
            return lines;
        } catch (IOException e) {
            try {
                reader.close();
            } catch (IOException f) {
                throw new RecruitmentSystemIOException(f.getMessage());
            }
            return null;
        }
    }

    /**
     * Prints out the file
     */
    public void printContents() {
        ArrayList<String> lines = this.getContents();
        for (String l : lines) {
            System.out.println(l);
        }
    }

    public static void clearNext() {
        nextId = 0;
    }

    public static void setNextId(int n) {
        nextId = n;
    }
}
