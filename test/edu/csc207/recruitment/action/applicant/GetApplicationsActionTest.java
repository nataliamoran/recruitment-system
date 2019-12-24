package edu.csc207.recruitment.action.applicant;

import edu.csc207.recruitment.actions.applicant.GetApplicationsAction;
import edu.csc207.recruitment.model.company.*;
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

public class GetApplicationsActionTest {
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
    private Application janeAccountantApp;
    private Application janeConsultantApp;

    @Before
    public void setUp() throws ParseException {
        List<String> interviewTypes = new ArrayList<>();
        interviewTypes.add("Phone");
        List<String> locations = new ArrayList<>();
        Company psychLab = new Company("companyName", interviewTypes, locations);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        may1 = formatter.parse("01/05/2019");
        may2 = formatter.parse("02/05/2019");
        april1 = formatter.parse("01/04/2019");
        june1 = formatter.parse("01/06/2019");
        july1 = formatter.parse("01/07/2019");
        Map<String, Integer> interviewTypesNums = new HashMap<>();
        interviewTypesNums.put("Phone", 1);
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        List<String> requiredDocuments = new ArrayList<>();
        JobPostingRequirements requirements = new JobPostingRequirements(june1, requiredDocuments,
                tags, interviewTypesNums);
        labManager = new JobPosting("Lab Manager", "Manages a lab", psychLab,
                "location", april1, requirements);
        researchAssistant = new JobPosting("Research Assistant", "Researches", psychLab,
                "location", may2, requirements);
        accountant = new JobPosting("Staff Accountant", "Crunches numbers", psychLab,
                "location", april1, requirements);
        consultant = new JobPosting("Consultant", "Consults", psychLab,
                "location", may2, requirements);
        jane = new Applicant("Jane", "secure", may1);
        janeAccountantApp = new Application(accountant, jane);
        janeConsultantApp = new Application(consultant, jane);
        janeConsultantApp.setApplicationStatus(ApplicationStatus.INTERVIEW_SCHEDULED);
        jane.getHistory().addApplication(janeAccountantApp);
        jane.getHistory().addApplication(janeConsultantApp);
        psychLab.addJobPosting(labManager);
        psychLab.addJobPosting(researchAssistant);
    }

    @Test
    public void testPerformAction_getCurrentApplicationsStatus_shouldPass() {
        TestTools.simulateInput(Arrays.asList("1"));
        GetApplicationsAction action = new GetApplicationsAction(jane);
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
        String message1 = "Application ID:";
        String message2 = "  Job: Staff Accountant" + System.lineSeparator();
        String message3 = "Description: Crunches numbers" + System.lineSeparator();
        String message4 = "Application Status: WAITING_FOR_NEXT_INTERVIEW" + System.lineSeparator();
        String message5 = "  Job: Consultant" + System.lineSeparator();
        String message6 = "Description: Consults" + System.lineSeparator();
        String message7 = "Application Status: INTERVIEW_SCHEDULED" + System.lineSeparator();

        Assert.assertTrue(content.contains(message1));
        Assert.assertTrue(content.contains(message2));
        Assert.assertTrue(content.contains(message3));
        Assert.assertTrue(content.contains(message4));
        Assert.assertTrue(content.contains(message5));
        Assert.assertTrue(content.contains(message6));
        Assert.assertTrue(content.contains(message7));
    }
}
