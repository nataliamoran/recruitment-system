package edu.csc207.recruitment.action.applicant;

import edu.csc207.recruitment.model.company.JobPostingRequirements;
import edu.csc207.recruitment.tools.TestTools;
import edu.csc207.recruitment.actions.applicant.ApplyForJobAction;
import edu.csc207.recruitment.exceptions.jobposting.JobPostingHasBeenClosedException;
import edu.csc207.recruitment.exceptions.jobposting.JobPostingNotFoundException;
import edu.csc207.recruitment.model.db.RecruitmentSystemFactory;
import edu.csc207.recruitment.model.company.Company;
import edu.csc207.recruitment.model.company.JobPosting;
import edu.csc207.recruitment.model.user.Applicant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ApplyForJobActionTest {
    private Date may1;
    private Date may2;
    private Date april1;
    private Date june1;
    private Date july1;
    private Applicant jane;
    private JobPosting labManager;
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
        labManager = new JobPosting("Lab manager", "Manages a lab", company,
                "location", april1, requirements);
        jane = new Applicant("Jane", "secure", may1);
    }

    @Test
    public void testPerformAction_applyForJob_shouldPass() {
        TestTools.simulateInput(Collections.singletonList(String.valueOf(labManager.getId())));
        List<String> interviewTypes = new ArrayList<>();
        interviewTypes.add("Phone");
        List<String> locations = new ArrayList<>();
        Company psychLab = new Company("companyName", interviewTypes, locations);
        psychLab.addJobPosting(labManager);
        RecruitmentSystemFactory.getRecruitmentSystem().addCompany(psychLab);
        ApplyForJobAction action = new ApplyForJobAction(jane, may1);

        // Capture stdout
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer));

        // stdout will be flushed
        action.collectParameters();
        action.performAction();

        buffer.reset();
        Assert.assertEquals(1, labManager.getApplications().size());
        Assert.assertEquals(1, jane.getHistory().getOpenApplications().size());
        Assert.assertEquals(labManager, jane.getHistory().getOpenApplications().get(0).getJobPosting());
        Assert.assertEquals("Jane", labManager.getApplications().get(0).getApplicant().getUsername());
    }

    @Test(expected = JobPostingNotFoundException.class)
    public void testPerformAction_applyForNonExistingJobId_shouldThrowJobPostingNotFoundException() {
        TestTools.simulateInput(Collections.singletonList("10000000"));
        List<String> interviewTypes = new ArrayList<>();
        interviewTypes.add("Phone");
        List<String> locations = new ArrayList<>();
        Company psychLab = new Company("companyName", interviewTypes, locations);
        psychLab.addJobPosting(labManager);
        RecruitmentSystemFactory.getRecruitmentSystem().addCompany(psychLab);
        ApplyForJobAction action = new ApplyForJobAction(jane, may1);
        action.collectParameters();
        action.performAction();
    }

    @Test(expected = JobPostingHasBeenClosedException.class)
    public void testPerformAction_applyForClosedJob_shouldPassButPrint() {
        TestTools.simulateInput(Collections.singletonList(String.valueOf(labManager.getId())));
        List<String> interviewTypes = new ArrayList<>();
        interviewTypes.add("Phone");
        List<String> locations = new ArrayList<>();
        Company psychLab = new Company("companyName", interviewTypes, locations);
        psychLab.addJobPosting(labManager);
        RecruitmentSystemFactory.getRecruitmentSystem().addCompany(psychLab);
        ApplyForJobAction action = new ApplyForJobAction(jane, july1);
        // stdout will be flushed
        action.collectParameters();
        action.performAction();
    }
}
