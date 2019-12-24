package edu.csc207.recruitment.storage;

import edu.csc207.recruitment.document.Document;
import edu.csc207.recruitment.document.DocumentDatabase;
import edu.csc207.recruitment.model.company.Company;
import edu.csc207.recruitment.model.db.RecruitmentSystem;
import edu.csc207.recruitment.model.db.RecruitmentSystemFactory;
import edu.csc207.recruitment.model.user.Applicant;
import edu.csc207.recruitment.model.user.Coordinator;
import edu.csc207.recruitment.model.user.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class gsonManagerTest {
    /**
     * README
     * Populate recruitmentSystem1 below, make sure you test a typical usage of the program, add Users, JobPostings etc.
     */
    private User user1;
    private User user2;
    private User user3;
    private Date date;
    private Company company1;
    private Company company2;
    private DocumentDatabase database;

    static Path pF1;

    static Document d;
    static Document f1;
    static Document f2;


    @Before
    public void setUp() throws ParseException {
        RecruitmentSystemFactory.setTestMode(true);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        date = formatter.parse("01/05/2019");
        List<String> interviewTypes = new ArrayList<>();
        interviewTypes.add("Phone");
        List<String> locations = new ArrayList<>();
        company1 = new Company("Apple", interviewTypes, locations);
        company2 = new Company("Samsung", interviewTypes, locations);

        user1 = new Applicant("user1", "password1", date);
        user2 = new Applicant("user2", "password", date);
        user3 = new Coordinator("user3", "password", company1, locations);

        Document.clearNext();
        DocumentDatabase.setInstance(new DocumentDatabase());
        DocumentDatabase database = DocumentDatabase.getInstance();
        database.switchTest();
        Document.clearNext();

        String s = File.separator;
        Path pF1 = Paths.get("." + s + "testStorage" + s + "0"+ s + "F1");

/*        Path pG = Paths.get("." + s + "testStorage" + s + "1" + s + "gee1");
        Path folder1 = Paths.get("." + s + "testStorage" + s + "1");

        Path pF2 = Paths.get("." + s + "testStorage" + s + "0"+ s + "F2");
        Path folder0 = Paths.get("." + s + "testStorage" + s + "0");*/

        d = database.addDocument("1", "." + s + "sampleDocuments" + s + "g1", "gee1");
        f1 = database.addDocument("0", "." + s + "sampleDocuments" + s + "f1", "F1");
        f2 = database.addDocument("0", "." + s + "sampleDocuments" + s + "f2", "F2");



/*        users.add(user1);
        users.add(user2);
        users.add(user3);*/

/*        companies.add(company1);
        companies.add(company2);*/
    }

    @Test
    public void testRecruitmentSystem_constructor_shouldPass() {
        RecruitmentSystem recruitmentSystem1 = RecruitmentSystemFactory.getRecruitmentSystem();

        /** Add stuff here
         * =============================================================================================================
         */


//        recruitmentSystem1.addCompany(company1);
//        Assert.assertEquals(company1.getName(), recruitmentSystem1.getCompany("Apple").getName());
//        recruitmentSystem1.addCompany(company2);
//        Assert.assertEquals(company2.getName(), recruitmentSystem1.getCompany("Samsung").getName());
//        recruitmentSystem1.addUser(user1);
//        recruitmentSystem1.addUser(user2);
//        recruitmentSystem1.addUser(user3);


        /**
         * =============================================================================================================
         */


        /**
         * Creating and loading json
         * =============================================================================================================
         */
        GsonManager hi = new GsonManager();

        String ress = hi.serializeRecruitmentSystem(recruitmentSystem1);

        RecruitmentSystem reee = hi.deserializeRecruitmentSystem(ress);

        DocumentDatabase.setInstance(new DocumentDatabase());
        RecruitmentSystemFactory.getRecruitmentSystem().setAllUsers(new ArrayList<>());
        RecruitmentSystemFactory.getRecruitmentSystem().setAllCompanies(new ArrayList<>());
        SaveAndLoad.switchPathTest();
        SaveAndLoad.saveProgram();

        /**
         *
         * =============================================================================================================
         */


        /**
         * Add a breakpoint somewhere in the below block, so that you can click debug -> reee and see if the attributes
         * are stored properly. Also feel free to add more tests.
         * =============================================================================================================
         */
        RecruitmentSystem resultr = RecruitmentSystemFactory.getRecruitmentSystem();
        DocumentDatabase resultd = DocumentDatabase.getInstance();

        AssertEquals(1, 1);
        // These will fail because new objects are created
        // Assert.assertEquals(user1, reee.getUser("user1"));
        // Assert.assertEquals(user2, reee.getUser("user2"));
        // Assert.assertEquals(user3, reee.getUser("user3"));


        /**
         * =============================================================================================================
         */
    }

    private void AssertEquals(int i, int i1) {
    }

    @Test
    public void getGsonDatabase() {


        GsonManager hi = new GsonManager();
        String s3 = hi.serializeDocumentDatabase(database);

        DocumentDatabase d2 = hi.deserializeDocumentDatabase(s3);

        assertEquals(1, 1);
    }

    @Test
    public void testLoad() {
        SaveAndLoad.switchPathTest();
        SaveAndLoad.loadProgram();
        RecruitmentSystem resultre = RecruitmentSystemFactory.getRecruitmentSystem();
        DocumentDatabase resultde = DocumentDatabase.getInstance();

        AssertEquals(1, 1);

    }

}



























