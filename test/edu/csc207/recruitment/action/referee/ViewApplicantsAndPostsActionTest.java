package edu.csc207.recruitment.action.referee;

import edu.csc207.recruitment.actions.referee.ViewApplicantsAndPostsAction;
import edu.csc207.recruitment.model.company.Application;
import edu.csc207.recruitment.model.company.Company;
import edu.csc207.recruitment.model.company.JobPosting;
import edu.csc207.recruitment.model.company.JobPostingRequirements;
import edu.csc207.recruitment.model.db.RecruitmentSystem;
import edu.csc207.recruitment.model.db.RecruitmentSystemFactory;
import edu.csc207.recruitment.model.user.*;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ViewApplicantsAndPostsActionTest {
    JobPosting job1;
    Company company;
    Applicant applicant;
    Application application;
    Referee referee;

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
        interviewTypesNums.put("Phone", 1);
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        List<String> requiredDocuments = new ArrayList<>();
        JobPostingRequirements requirements = new JobPostingRequirements(jobClosedDate1, requiredDocuments,
                tags, interviewTypesNums);
        job1 = new JobPosting(
                "Job", "Job", company, "location", jobPostedDate1, requirements);
        company.addJobPosting(job1);
        applicant = new Applicant("Applicant", "123456", jobPostedDate1);
        application = new Application(job1, applicant);
        job1.addApplication(application);
        applicant.getHistory().addApplication(application);
        referee = new Referee("TestReferee", "password");
        referee.addApplicant(applicant);

    }

    @Test
    public void TestCollectParameters() {
        //Arrange
        ViewApplicantsAndPostsAction viewApplicantsAndPostsAction = new ViewApplicantsAndPostsAction(referee);
        //Act
        viewApplicantsAndPostsAction.collectParameters(); //should pass
    }

    @Test
    public void TestPerformAction() {
        //Arrange
        ViewApplicantsAndPostsAction viewApplicantsAndPostsAction = new ViewApplicantsAndPostsAction(referee);
        viewApplicantsAndPostsAction.collectParameters();

        //Act
        viewApplicantsAndPostsAction.performAction(); //Should print username   job title    date

    }
}
