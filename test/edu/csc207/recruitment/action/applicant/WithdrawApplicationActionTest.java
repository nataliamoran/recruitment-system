package edu.csc207.recruitment.action.applicant;

import edu.csc207.recruitment.model.company.JobPostingRequirements;
import edu.csc207.recruitment.tools.TestTools;
import edu.csc207.recruitment.actions.applicant.ApplyForJobAction;
import edu.csc207.recruitment.actions.applicant.WithdrawApplicationAction;
import edu.csc207.recruitment.exceptions.application.ApplicationAlreadyClosedException;
import edu.csc207.recruitment.exceptions.application.ApplicationNotFoundException;
import edu.csc207.recruitment.model.db.RecruitmentSystemFactory;
import edu.csc207.recruitment.model.company.Company;
import edu.csc207.recruitment.model.company.JobPosting;
import edu.csc207.recruitment.model.user.Applicant;
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

public class WithdrawApplicationActionTest {

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
    private Company psychLab;

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
        psychLab = new Company("companyName", interviewTypes, locations);
        Map<String, Integer> interviewTypesNums = new HashMap<>();
        interviewTypesNums.put("Phone", 1);
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        List<String> requiredDocuments = new ArrayList<>();
        JobPostingRequirements requirements = new JobPostingRequirements(june1, requiredDocuments,
                tags, interviewTypesNums);
        labManager = new JobPosting("Lab Manager", "Manages a lab", psychLab,
                "location",april1, requirements);
        researchAssistant = new JobPosting("Research Assistant", "Researches", psychLab,
                "location", may2, requirements);
        accountant = new JobPosting("Staff Accountant", "Crunches numbers", psychLab,
                "location", april1, requirements);
        consultant = new JobPosting("Consultant", "Consults", psychLab,
                "location", may2, requirements);
        jane = new Applicant("Jane", "secure", new Date());
        psychLab.addJobPosting(labManager);
        psychLab.addJobPosting(researchAssistant);
        ApplyForJobAction applyAction = new ApplyForJobAction(jane, may1);
        RecruitmentSystemFactory.getRecruitmentSystem().setAllCompanies(new ArrayList<>());
        RecruitmentSystemFactory.getRecruitmentSystem().addCompany(psychLab);
        TestTools.simulateInput(Collections.singletonList(String.valueOf(labManager.getId())));

        // Capture stdout
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer));

        // capture stdout
        applyAction.collectParameters();
        applyAction.performAction();

        // Stop capturing
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        buffer.reset();
    }

    @Test
    public void testPerformAction_testWithdrawApplication_shouldPass() {
        int labManagerAppId = jane.getHistory().getOpenApplications().get(0).getId();
        TestTools.simulateInput(Collections.singletonList(String.valueOf(labManagerAppId)));
        WithdrawApplicationAction withdrawAction = new WithdrawApplicationAction(jane, may1);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer));

        // capture stdout
        withdrawAction.collectParameters();
        withdrawAction.performAction();

        // Stop capturing
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        buffer.reset();
        Assert.assertEquals(1, jane.getHistory().getClosedApplications().size());
    }

    @Test(expected = ApplicationNotFoundException.class)
    public void testPerformAction_testWithdrawNotFoundApplication_shouldThrowApplicationNotFoundException() {
        WithdrawApplicationAction withdrawAction = new WithdrawApplicationAction(jane, may1);
        TestTools.simulateInput(Collections.singletonList(String.valueOf(100000)));
        withdrawAction.collectParameters();
        withdrawAction.performAction();
    }

    @Test(expected = ApplicationAlreadyClosedException.class)
    public void testPerformAction_testWithdrawAlreadyClosedApplication_shouldThrowApplicationAlreadyClosedException() {
        int labManagerAppId = jane.getHistory().getOpenApplications().get(0).getId();
        WithdrawApplicationAction withdrawAction = new WithdrawApplicationAction(jane, may1);
        TestTools.simulateInput(Collections.singletonList(String.valueOf(labManagerAppId)));
        withdrawAction.collectParameters();
        withdrawAction.performAction();
        withdrawAction.performAction();
    }
}
