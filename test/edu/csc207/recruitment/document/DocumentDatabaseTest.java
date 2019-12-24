package edu.csc207.recruitment.document;

import edu.csc207.recruitment.document.Document;
import edu.csc207.recruitment.document.DocumentDatabase;
import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class DocumentDatabaseTest {
    static Path pG;
    static Path folder0;
    static Path folder1;
    static Path pF1;
    static Path pF2;

    static DocumentDatabase database;

    static Document d;
    static Document f1;
    static Document f2;

    @BeforeClass
    public static void setup() {
        Document.clearNext();
        DocumentDatabase.setInstance(new DocumentDatabase());
        database = DocumentDatabase.getInstance();
        database.switchTest();
        Document.clearNext();

        String s = File.separator;
        pG = Paths.get("." + s + "testStorage" + s + "1" + s + "gee1");
        folder1 = Paths.get("." + s + "testStorage" + s + "1");
        pF1 = Paths.get("." + s + "testStorage" + s + "0"+ s + "F1");
        pF2 = Paths.get("." + s + "testStorage" + s + "0"+ s + "F2");
        folder0 = Paths.get("." + s + "testStorage" + s + "0");



        d = database.addDocument("1", "." + s+ "sampleDocuments" + s + "g1", "gee1");
        f1 = database.addDocument("0", "." + s + "sampleDocuments" + s + "f1", "F1");
        f2 = database.addDocument("0", "." + s + "sampleDocuments" + s + "f2", "F2");

    }


    @Test
    public void addMultipleDocumentsAndCheckExistence() {
        try {
            assertNotNull(d);
            assertEquals("1", d.getOwnerUsername());
            assertEquals(0, d.getDocumentId());
            assertEquals("gee1", d.getName());
            assertEquals("day1", d.getContents().get(0));
            assertTrue(Files.exists(pG));

            assertNotNull(f1);
            assertNotNull(f2);
            assertEquals("0", f1.getOwnerUsername());
            assertEquals(1, f1.getDocumentId());
            assertEquals("0", f2.getOwnerUsername());
            assertEquals(2, f2.getDocumentId());
            assertEquals("F1", f1.getName());
            assertEquals("F2", f2.getName());
            assertTrue(f1.getContents().size() > 0);
            assertTrue(f2.getContents().size() > 0);

            assertTrue(Files.exists(pF1));
            assertTrue(Files.exists(pF2));


        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }

    @Test
    public void getDocument() {
        try {

            assertNotNull(database.getDocument(0));
            assertNotNull(database.getDocument(1));
            assertNotNull(database.getDocument(2));
            assertNotNull(database.getDocument(0, "1"));
            assertNotNull(database.getDocument(1, "0"));
            assertNotNull(database.getDocument(2, "0"));
            assertNotNull(database.getDocument("gee1", "1"));
            assertNotNull(database.getDocument("F1", "0"));
            assertNotNull(database.getDocument("F2","0"));

        }

        catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }

    @Test
    public void removeDocument() {
        try {
            try{
                database.removeDocument(0);
            }
            catch (Exception e) {
                e.printStackTrace();
                fail();
            }

            assertTrue(Files.exists(pG)); // File still exists!
            assertNull(database.getDocument(0));
            try {
                database.removeDocument("F1", "0");
            }
            catch (Exception e) {
                e.printStackTrace();
                fail();
            }

            assertTrue(Files.exists(pF1));
            assertNull(database.getDocument(1));
            try {
                database.removeDocument(2, "0");
            }
            catch (Exception e) {
                e.printStackTrace();
                fail();
            }

            assertTrue(Files.exists(pF2));
            assertNull(database.getDocument(2));



        }
        catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void getCVCL() {
        database.addDocument("U1", "./sampleDocuments/CL", "CL");
        database.addDocument("U1", "./sampleDocuments/CV1", "CV1");

        // Document cl = database.getCoverLetter("U1");
        // Document cv = database.getCV("U1");
        // assertEquals("CL", cl.getName());
        // assertEquals("CV1", cv.getName());
    }

    @AfterClass
    public static void teardown() {
        try {
            Files.delete(pG);
            Files.delete(folder1);
            Files.delete(pF1);
            Files.delete(pF2);
            Files.delete(folder0);
        }
        catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

}
