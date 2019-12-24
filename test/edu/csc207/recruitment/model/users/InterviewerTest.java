package edu.csc207.recruitment.model.users;

import edu.csc207.recruitment.model.company.*;
import edu.csc207.recruitment.model.user.Applicant;
import edu.csc207.recruitment.model.user.Interviewer;
import edu.csc207.recruitment.model.user.UserType;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class InterviewerTest {

    @Test
    public void testInterviewer_greenPath() {
        //Arange
        List<String> interviewTypes = new ArrayList<>();
        interviewTypes.add("Phone");
        List<String> locations = new ArrayList<>();
        Company EY = new Company("companyName", interviewTypes, locations);
        Applicant applicant = new Applicant("username", "password", new Date());
        Interviewer interviewer = new Interviewer("Maggie", "scotiaRules", EY, locations);
        Date openingDate = new Date();
        Date closingDate = new Date(Long.parseLong("1577836800000"));
        Company company = new Company("companyName", interviewTypes, locations);
        Map<String, Integer> interviewTypesNums = new HashMap<>();
        interviewTypesNums.put("Phone", 1);
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        List<String> requiredDocuments = new ArrayList<>();
        JobPostingRequirements requirements = new JobPostingRequirements(closingDate, requiredDocuments,
                tags, interviewTypesNums);
        JobPosting job = new JobPosting("jobTitle", "jobDescription", company,
                "location", openingDate, requirements);
        Application application = new Application(job, applicant);
        Interview interview1 = new Interview(interviewer, application, "In Person");
        Interview interview2 = new Interview(interviewer, application, "Phone");

        //Act
        interviewer.addInterview(interview1);
        interviewer.addInterview(interview2);
        interviewer.removeInterview(interview2);

        //Assert
        Assert.assertEquals(EY, interviewer.getCompany());
        Assert.assertEquals(UserType.INTERVIEWER, interviewer.getUserType());
        Assert.assertEquals(1, interviewer.getAllInterviewsToRun().size());

    }
}

