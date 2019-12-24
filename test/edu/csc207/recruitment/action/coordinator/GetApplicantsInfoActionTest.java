package edu.csc207.recruitment.action.coordinator;

import edu.csc207.recruitment.model.company.*;
import edu.csc207.recruitment.tools.TestTools;
import edu.csc207.recruitment.actions.coordinator.GetApplicantsInfoAction;
import edu.csc207.recruitment.model.user.Applicant;
import edu.csc207.recruitment.model.user.Coordinator;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GetApplicantsInfoActionTest {
    private GetApplicantsInfoAction getApplicantsInfoAction;


    @Before
    public void setUp() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date jobPostedDate1 = formatter.parse("01/05/2019");
        Date jobClosedDate1 = formatter.parse("05/07/2019");
        List<String> interviewTypes = new ArrayList<>();
        interviewTypes.add("Phone");
        List<String> locations = new ArrayList<>();
        Company company = new Company("Google", interviewTypes, locations);
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
        Coordinator coordinator = new Coordinator("coordinator", "123456", company, locations);
        Applicant applicant = new Applicant("Applicant", "123456", jobPostedDate1);
        Applicant applicant2 = new Applicant("Joon", "123456", jobPostedDate1);
        Application application = new Application(job1, applicant);
        Application application2 = new Application(job1, applicant2);
        job1.addApplication(application);
        job1.addApplication(application2);
        application.setApplicationStatus(ApplicationStatus.WAITING_FOR_NEXT_INTERVIEW);
        getApplicantsInfoAction = new GetApplicantsInfoAction(coordinator);
    }

    @Test
    public void testCollectParameters() {
        //Arrange
        TestTools.simulateInput(Arrays.asList("Applicant", "1"));
        getApplicantsInfoAction.collectParameters();
    }

    @Test
    public void testPerformAction_case1_shouldPass() {
        //Arrange
        TestTools.simulateInput(Arrays.asList("Applicant", "1"));
        getApplicantsInfoAction.collectParameters();
        getApplicantsInfoAction.performAction();

    }

}
