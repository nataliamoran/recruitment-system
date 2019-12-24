package edu.csc207.recruitment.action.interviewer;

import edu.csc207.recruitment.tools.TestTools;
import edu.csc207.recruitment.actions.interviewer.RunInterviewAction;
import edu.csc207.recruitment.model.company.*;
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

public class RunInterviewActionTest {
    JobPosting job1;
    Company company;
    Coordinator coordinator;
    User interviewer;
    Applicant applicant;
    Application application;
    Interview interview;

    @Before
    public void setUp() throws ParseException {
        RecruitmentSystemFactory.setTestMode(true);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date jobPostedDate1 = formatter.parse("01/05/2019");
        Date jobClosedDate1 = formatter.parse("05/07/2019");
        List<String> interviewTypes = new ArrayList<>();
        interviewTypes.add("Phone");
        interviewTypes.add("In Person");
        List<String> locations = new ArrayList<>();
        company = new Company("companyName", interviewTypes, locations);
        Map<String, Integer> interviewTypesNums = new HashMap<>();
        interviewTypesNums.put("Phone", 1);
        interviewTypesNums.put("In Person", 1);
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
        applicant = new Applicant("Applicant", "123456", jobPostedDate1);
        application = new Application(job1, applicant);
        job1.addApplication(application);
        application.setApplicationStatus(ApplicationStatus.INTERVIEW_SCHEDULED);
        interview = new Interview((Interviewer) interviewer, application, "In Person");
        ((Interviewer) interviewer).addInterview(interview);

    }

    @Test
    public void TestCollectParameters() {

        RunInterviewAction runInterviewAction = new RunInterviewAction((Interviewer) interviewer, new Date());
        TestTools.simulateInput(Arrays.asList((Integer.toString(interview.getId())), "1", "Good"));
        runInterviewAction.collectParameters();

    }

    @Test

    public void TestPerformAction() {
        RunInterviewAction runInterviewAction = new RunInterviewAction((Interviewer) interviewer, new Date());
        TestTools.simulateInput(Arrays.asList((Integer.toString(interview.getId())), "1", "Good"));
        runInterviewAction.collectParameters();
        runInterviewAction.performAction();
        Assert.assertEquals(application.getApplicationStatus(), ApplicationStatus.WAITING_FOR_NEXT_INTERVIEW);
        Assert.assertEquals(interview.getRecommendation(), "Good");

    }
}
