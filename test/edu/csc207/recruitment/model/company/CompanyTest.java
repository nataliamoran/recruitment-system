package edu.csc207.recruitment.model.company;

import edu.csc207.recruitment.exceptions.jobposting.JobPostingNotFoundException;
import edu.csc207.recruitment.model.user.Coordinator;
import edu.csc207.recruitment.model.user.Interviewer;
import edu.csc207.recruitment.model.user.UserType;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class CompanyTest {

    @Test
    public void testCompany_greenPath() {
        //Arrange
        Date openingDate = new Date();
        long mill = Long.parseLong("1577836800000");
        Date closingDate = new Date(mill);
        List<String> interviewTypes = new ArrayList<>();
        interviewTypes.add("Phone");
        List<String> locations = new ArrayList<>();
        Company company = new Company("company", interviewTypes, locations);
        Map<String, Integer> interviewTypesNums = new HashMap<>();
        interviewTypesNums.put("Phone", 1);
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        List<String> requiredDocuments = new ArrayList<>();
        JobPostingRequirements requirements = new JobPostingRequirements(closingDate, requiredDocuments,
                tags, interviewTypesNums);
        JobPosting job1 = new JobPosting("job1", "description1", company,
                "location", openingDate, requirements);
        JobPosting job2 = new JobPosting("job2", "description2", company,
                "location", openingDate, requirements);
        Coordinator coordinator = new Coordinator("coordinator", "password", company, locations);
        Interviewer interviewer = new Interviewer("interviewer", "password", company, locations);

        //Act
        company.addStaff(interviewer);
        company.addStaff(coordinator);
        company.addJobPosting(job1);
        company.addJobPosting(job2);
        String job1ID = Integer.toString(job1.getId());

        //Assert
        assertEquals("company", company.getName());
        assertEquals(job1, company.getJobPostingPerId(job1ID));
        assertEquals(interviewer, company.getStaffPerUserType(UserType.INTERVIEWER).get(0));
        assertEquals(2, company.getAllJobPostings().size());
        assertEquals(2, company.getAllStaff().size());
    }

    @Test
    public void testGetAllStaff_shouldPass() {
        //Arrange
        List<String> interviewTypes = new ArrayList<>();
        List<String> locations = new ArrayList<>();
        Company company = new Company("company", interviewTypes, locations);
        Coordinator coordinator = new Coordinator("coordinator", "password", company, locations);
        Interviewer interviewer = new Interviewer("interviewer", "password", company, locations);
        company.addStaff(interviewer);
        company.addStaff(coordinator);
        //Act and Assert
        Assert.assertEquals(2, company.getAllStaff().size());
    }

    @Test(expected = JobPostingNotFoundException.class)
    public void testGetJobPosting_shouldThrowJobPostingNotFoundException() {
        //Arrange
        List<String> interviewTypes = new ArrayList<>();
        interviewTypes.add("Phone");
        List<String> locations = new ArrayList<>();
        Company company = new Company("companyName", interviewTypes, locations);

        //Act
        company.getJobPostingPerId("1");

        //Assert

    }


}