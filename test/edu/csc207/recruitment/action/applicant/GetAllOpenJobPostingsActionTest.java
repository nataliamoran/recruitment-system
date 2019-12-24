package edu.csc207.recruitment.action.applicant;

import edu.csc207.recruitment.actions.applicant.GetAllOpenJobPostingsAction;
import edu.csc207.recruitment.exceptions.jobposting.JobPostingNotFoundException;
import edu.csc207.recruitment.model.company.Company;
import edu.csc207.recruitment.model.company.JobPosting;
import edu.csc207.recruitment.model.company.JobPostingRequirements;
import edu.csc207.recruitment.model.db.RecruitmentSystemFactory;
import edu.csc207.recruitment.model.user.Applicant;
import edu.csc207.recruitment.tools.TestTools;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GetAllOpenJobPostingsActionTest {
    private Date may1;
    private Date may2;
    private Date april1;
    private Date june1;
    private Date july1;
    private Applicant jane;
    private JobPosting labManager;
    private JobPosting researchAssistant;
    private JobPosting accountant;
    private JobPosting consultant;
    private Company company;

    @Before
    public void setUp() throws ParseException {
        RecruitmentSystemFactory.setTestMode(true);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        may1 = formatter.parse("01/05/2019");
        may2 = formatter.parse("02/05/2019");
        april1 = formatter.parse("01/04/2019");
        june1 = formatter.parse("01/06/2019");
        july1 = formatter.parse("01/07/2019");
        List<String> interviewTypes = new ArrayList<>();
        interviewTypes.add("Phone");
        List<String> locations = new ArrayList<>();
        company = new Company("companyName", interviewTypes, locations);
        Map<String, Integer> interviewTypesNums = new HashMap<>();
        interviewTypesNums.put("Phone", 1);
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        List<String> requiredDocuments = new ArrayList<>();
        JobPostingRequirements requirements = new JobPostingRequirements(june1, requiredDocuments,
                tags, interviewTypesNums);
        labManager = new JobPosting("Lab Manager", "Manages a lab", company,
                "location", april1, requirements);
        researchAssistant = new JobPosting("Research Assistant", "Researches", company,
                "location", may2, requirements);
        accountant = new JobPosting("Staff Accountant", "Crunches numbers", company,
                "location", april1, requirements);
        consultant = new JobPosting("Consultant", "Consults", company,
                "location", may2, requirements);
        company.addJobPosting(labManager);
        company.addJobPosting(researchAssistant);
        company.addJobPosting(accountant);
        company.addJobPosting(consultant);
        RecruitmentSystemFactory.getRecruitmentSystem().addCompany(company);
    }

    @Test
    public void testPerformAction_getNoJobs_shouldPass() {
        GetAllOpenJobPostingsAction action = new GetAllOpenJobPostingsAction(jane);
        RecruitmentSystemFactory.getRecruitmentSystem().setAllCompanies(new ArrayList<>());

        // Capture stdout
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer));

        // capture stdout
        try {
            action.collectParameters();
        } catch (JobPostingNotFoundException e) {
            System.out.println(e.getMessage());
        }
        action.performAction();

        // Stop capturing
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

        // Use captured content
        String content = buffer.toString();
        buffer.reset();
        Assert.assertEquals(String.format("Sorry, currently there are no open job postings.%n"), content);
    }

    @Test
    public void testPerformAction_getJobPostings_shouldPass() {
        RecruitmentSystemFactory.setTestMode(true);
        GetAllOpenJobPostingsAction action = new GetAllOpenJobPostingsAction(jane);
        List<String> interviewTypes = new ArrayList<>();
        interviewTypes.add("Phone");
        List<String> locations = new ArrayList<>();
        Company psychLab = new Company("Psychology Lab", interviewTypes, locations);
        psychLab.addJobPosting(labManager);
        psychLab.addJobPosting(researchAssistant);
        Company EY = new Company("EY", interviewTypes, locations);
        EY.addJobPosting(accountant);
        EY.addJobPosting(consultant);
        RecruitmentSystemFactory.getRecruitmentSystem().addCompany(psychLab);
        RecruitmentSystemFactory.getRecruitmentSystem().addCompany(EY);
        TestTools.simulateInput(Arrays.asList("N"));
        action.collectParameters();
        // Capture stdout
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer));

        // capture stdout
        action.performAction();

        // Stop capturing
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

        // Use captured content
        String content = buffer.toString();
        buffer.reset();
        String message1 = "Please see below the list of open job postings and their IDs.";
        String message2 = "Title: Lab Manager";
        String message3 = "ID:";
        String message4 = "Description: Manages a lab";
        String message5 = "Job Requirements:";
        String message6 = "Applications deadline: 06/01/2019";
        String message7 = "Documents: ";
        String message8 = "Skills:";
        String message9 = "Location: location";
        Assert.assertTrue(content.contains(message1));
        Assert.assertTrue(content.contains(message2));
        Assert.assertTrue(content.contains(message3));
        Assert.assertTrue(content.contains(message4));
        Assert.assertTrue(content.contains(message5));
        Assert.assertTrue(content.contains(message6));
        Assert.assertTrue(content.contains(message7));
        Assert.assertTrue(content.contains(message8));
        Assert.assertTrue(content.contains(message9));
    }
}
