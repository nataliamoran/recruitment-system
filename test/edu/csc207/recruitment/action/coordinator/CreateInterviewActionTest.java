package edu.csc207.recruitment.action.coordinator;

import edu.csc207.recruitment.model.company.*;
import edu.csc207.recruitment.tools.TestTools;
import edu.csc207.recruitment.actions.coordinator.CreateInterviewAction;
import edu.csc207.recruitment.model.db.RecruitmentSystemFactory;
import edu.csc207.recruitment.model.user.Applicant;
import edu.csc207.recruitment.model.user.Coordinator;
import edu.csc207.recruitment.model.user.Interviewer;
import edu.csc207.recruitment.model.user.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CreateInterviewActionTest {
    JobPosting job1;
    Company company;
    Coordinator coordinator;
    User interviewer;
    Applicant applicant;
    CreateInterviewAction createInterview;
    Application application;

    @Before
    public void setUp() throws ParseException {
        RecruitmentSystemFactory.setTestMode(true);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date jobPostedDate1 = formatter.parse("01/05/2019");
        Date jobClosedDate1 = formatter.parse("05/07/2019");
        List<String> interviewTypes = new ArrayList<>();
        interviewTypes.add("Phone");
        List<String> locations = new ArrayList<>();
        company = new Company("companyName", interviewTypes, locations);
        Map<String, Integer> interviewTypesNums = new HashMap<>();
        interviewTypesNums.put("Phone", 2);
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        List<String> requiredDocuments = new ArrayList<>();
        JobPostingRequirements requirements = new JobPostingRequirements(jobClosedDate1, requiredDocuments,
                tags, interviewTypesNums);
        job1 = new JobPosting(
                "Job", "Job", company, "location", jobPostedDate1, requirements);
        company.addJobPosting(job1);
        coordinator = new Coordinator("coordinator", "123456", company, locations);
        interviewer = new Interviewer("Interviewer", "123456", company, locations);
        RecruitmentSystemFactory.getRecruitmentSystem().addUser(interviewer);
        company.addStaff(interviewer);
        applicant = new Applicant("Applicant", "123456", jobPostedDate1);
        application = new Application(job1, applicant);
        job1.addApplication(application);
        application.setApplicationStatus(ApplicationStatus.WAITING_FOR_NEXT_INTERVIEW);

    }


    @Test
    public void testCollectParameters() {
        //Arrange

        createInterview = new CreateInterviewAction(coordinator);
        TestTools.simulateInput(Arrays.asList(Integer.toString(application.getId()), "Phone", "Interviewer"));

        //Act
        createInterview.collectParameters();

        //Assert
    }

    @Test
    public void testPerformAction() {
        createInterview = new CreateInterviewAction(coordinator);
        TestTools.simulateInput(Arrays.asList(Integer.toString(application.getId()), "Phone", "Interviewer"));
        createInterview.collectParameters();
        createInterview.performAction();
        Assert.assertEquals(ApplicationStatus.INTERVIEW_SCHEDULED, application.getApplicationStatus());
    }
}
