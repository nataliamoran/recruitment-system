package edu.csc207.recruitment.action.coordinator;

import edu.csc207.recruitment.model.company.*;
import edu.csc207.recruitment.tools.TestTools;
import edu.csc207.recruitment.actions.coordinator.GetJobPostingApplicantsAction;
import edu.csc207.recruitment.model.user.Applicant;
import edu.csc207.recruitment.model.user.Coordinator;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GetJobPostingApplicantsActionTest {

    JobPosting job1;
    Company company;
    Coordinator coordinator;
    Applicant applicant;
    Application application;
    GetJobPostingApplicantsAction getJobPostingApplicantsAction;

    @Before
    public void setUp() throws ParseException {
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
        coordinator = new Coordinator("coordinator", "123456", company, locations);
        applicant = new Applicant("Applicant", "123456", jobPostedDate1);
        application = new Application(job1, applicant);
        job1.addApplication(application);
        application.setApplicationStatus(ApplicationStatus.WAITING_FOR_NEXT_INTERVIEW);
    }

    @Test
    public void testCollectParametersAndPerform() {
        //Arrange
        getJobPostingApplicantsAction = new GetJobPostingApplicantsAction(coordinator);
        TestTools.simulateInput(Arrays.asList(Integer.toString(job1.getId())));
        getJobPostingApplicantsAction.collectParameters();
        getJobPostingApplicantsAction.performAction();
    }
}
