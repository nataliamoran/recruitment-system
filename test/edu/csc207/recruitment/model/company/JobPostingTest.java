package edu.csc207.recruitment.model.company;

import edu.csc207.recruitment.model.user.Applicant;
import edu.csc207.recruitment.model.user.Interviewer;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class JobPostingTest {

    @Test
    public void testJobPosting_NotClosed_NotFilled_greenPath() {
        //Arange
        Date openingDate = new Date();
        Date closingDate = new Date(Long.parseLong("1577836800000"));
        List<String> interviewTypes = new ArrayList<>();
        interviewTypes.add("Phone");
        List<String> locations = new ArrayList<>();
        Company company = new Company("companyName", interviewTypes, locations);
        Map<String, Integer> interviewTypesNums = new HashMap<>();
        interviewTypesNums.put("Phone", 1);
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        List<String> requiredDocuments = new ArrayList<>();
        JobPostingRequirements requirements = new JobPostingRequirements(closingDate,requiredDocuments,
                tags, interviewTypesNums);
        JobPosting job = new JobPosting("jobTitle", "jobDescription", company, "location",
                openingDate, requirements);
        Applicant applicant = new Applicant("Dr. Seuss", "hello", new Date());
        Application application = new Application(job, applicant);
        Interviewer interviewer = new Interviewer("interviewerName", "password", company, locations);
        Interview interview = new Interview(interviewer, application, "phone");

        //Act
        job.addApplication(application);
        job.addInterview(interview);

        //Assert
        assertEquals("jobTitle", job.getTitle());
        assertEquals("jobDescription", job.getDescription());
        assertEquals(false, job.isApplicationsDeadlinePassed(openingDate));
        assertEquals(false, job.isFilled());
        assertEquals(application, job.getApplications().get(0));
        assertEquals(interview, job.getInterviews().get(0));
    }

    @Test
    public void testJobPosting_Closed_Filled_greenPath() {
        //Arange
        List<String> interviewTypes = new ArrayList<>();
        interviewTypes.add("Phone");
        List<String> locations = new ArrayList<>();
        Company company = new Company("companyName", interviewTypes, locations);
        Date openingDate = new Date();
        Date closingDate1 = new Date();
        Date closingDate2 = new Date(Long.parseLong("1577836800000"));
        Map<String, Integer> interviewTypesNums = new HashMap<>();
        interviewTypesNums.put("Phone", 1);
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        List<String> requiredDocuments = new ArrayList<>();
        requiredDocuments.add("portfolio");
        JobPostingRequirements requirements = new JobPostingRequirements(closingDate1, requiredDocuments,
                tags, interviewTypesNums);
        JobPosting job1 = new JobPosting("jobTitle1", "jobDescription1", company, "location",
                openingDate, requirements);
        JobPosting job2 = new JobPosting("jobTitle2", "jobDescription2", company, "location",
                openingDate, requirements);
        Applicant applicant = new Applicant("Dr. Seuss", "hello", new Date());
        Application application = new Application(job1, applicant);
        application.setApplicationStatus(ApplicationStatus.HIRED);

        //Act
        job1.addApplication(application);

        //Assert
        Date dateAfterClosingDate2 = new Date(Long.parseLong("1577923200000"));
        assertEquals(true, job1.isFilled());
        assertEquals(true, job2.isApplicationsDeadlinePassed(dateAfterClosingDate2));
    }
}