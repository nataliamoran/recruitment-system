package edu.csc207.recruitment.model.company;

import edu.csc207.recruitment.model.user.Applicant;
import edu.csc207.recruitment.model.user.Interviewer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class InterviewTest {
    private Application application1;
    private Interviewer interviewer1;

    /**
     * Set up Interview for testing
     */
    @Before
    public void setUp() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date jobPostedDate1 = formatter.parse("01/05/2019");
        Date jobClosedDate1 = formatter.parse("01/06/2019");
        List<String> interviewTypes = new ArrayList<>();
        interviewTypes.add("Phone");
        List<String> locations = new ArrayList<>();
        Company apple = new Company("companyName", interviewTypes, locations);
        Map<String, Integer> interviewTypesNums = new HashMap<>();
        interviewTypesNums.put("Phone", 1);
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        List<String> requiredDocuments = new ArrayList<>();
        JobPostingRequirements requirements = new JobPostingRequirements(jobClosedDate1, requiredDocuments,
                tags, interviewTypesNums);
        JobPosting job1 = new JobPosting("Secretary", "Works at a office", apple, "location",
                jobPostedDate1, requirements);
        Applicant josephine = new Applicant("Josephine", "pass", new Date());
        application1 = new Application(job1, josephine);
        interviewer1 = new Interviewer("user1", "password1", apple, locations);
    }

    @Test
    public void testInterviewTest_testConstructor_shouldPass() {
        Interview firstInterview = new Interview(interviewer1, application1, "In Person");
        firstInterview.setRecommendation("he will pass");
        Assert.assertEquals(interviewer1, firstInterview.getInterviewer());
        Assert.assertEquals(application1, firstInterview.getApplication());
        Assert.assertEquals("In Person", firstInterview.getInterviewType());
        Assert.assertEquals("he will pass", firstInterview.getRecommendation());
        firstInterview.setRecommendation("he won't pass");
        Assert.assertEquals("he won't pass", firstInterview.getRecommendation());
    }

    @Test
    public void testInterviewTest_havingDifferentInterviewType_shouldPass() {
        Interview firstInterview = new Interview(interviewer1, application1, "In Person");
        Interview secondInterview = new Interview(interviewer1, application1, "Phone");
        Assert.assertEquals("Phone", secondInterview.getInterviewType());
        Assert.assertEquals("In Person", firstInterview.getInterviewType());
    }
}


