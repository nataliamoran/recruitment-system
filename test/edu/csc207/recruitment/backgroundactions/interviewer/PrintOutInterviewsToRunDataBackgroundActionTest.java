package edu.csc207.recruitment.backgroundactions.interviewer;


import edu.csc207.recruitment.model.company.*;
import edu.csc207.recruitment.model.db.RecruitmentSystemFactory;
import edu.csc207.recruitment.model.user.Applicant;
import edu.csc207.recruitment.model.user.Interviewer;
import edu.csc207.recruitment.model.user.User;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PrintOutInterviewsToRunDataBackgroundActionTest {

    private User interviewer;


    @Before
    public void setUp() throws ParseException {
        RecruitmentSystemFactory.setTestMode(true);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date jobPostedDate1 = formatter.parse("01/05/2019");
        Date jobClosedDate1 = formatter.parse("05/07/2019");
        List<String> interviewTypes = new ArrayList<>();
        interviewTypes.add("Phone");
        List<String> locations = new ArrayList<>();
        Company company = new Company("companyName", interviewTypes, locations);
        Map<String, Integer> interviewTypesNums = new HashMap<>();
        interviewTypesNums.put("Phone", 1);
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        List<String> requiredDocuments = new ArrayList<>();
        JobPostingRequirements requirements = new JobPostingRequirements(jobClosedDate1, requiredDocuments,
                tags, interviewTypesNums);
        JobPosting job1 = new JobPosting(
                "Job", "Job", company, "location", jobPostedDate1, requirements);
        company.addJobPosting(job1);
        interviewer = new Interviewer("Interviewer", "123456", company, locations);
        RecruitmentSystemFactory.getRecruitmentSystem().addUser(interviewer);
        Applicant applicant = new Applicant("Applicant", "123456", jobPostedDate1);
        Application application = new Application(job1, applicant);
        job1.addApplication(application);
        application.setApplicationStatus(ApplicationStatus.INTERVIEW_SCHEDULED);
        Interview interview = new Interview((Interviewer) interviewer, application, "In Person");
        ((Interviewer) interviewer).addInterview(interview);
    }

    @Test
    public void testPerformAction_ShouldPrint() {
        PrintOutInterviewsToRunDataBackgroundAction PrintOutInterviewsToRunDataBackgroundAction = new
                PrintOutInterviewsToRunDataBackgroundAction((Interviewer) interviewer);
        PrintOutInterviewsToRunDataBackgroundAction.performAction();
    }
}
