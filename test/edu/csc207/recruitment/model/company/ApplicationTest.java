package edu.csc207.recruitment.model.company;

import edu.csc207.recruitment.exceptions.application.ApplicationAlreadyClosedException;
import edu.csc207.recruitment.model.user.Applicant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ApplicationTest {
    private JobPosting job1;
    private JobPosting job2;
    private Date today;
    private Date jobPostedDate1;
    private Date jobClosedDate1;
    private Date jobPostedDate2;
    private Date jobClosedDate2;
    private Applicant jane;

    /**
     * Set up documents and an Applicant for testing.
     */
    @Before
    public void setUp() throws ParseException {
        List<String> interviewTypes = new ArrayList<>();
        interviewTypes.add("Phone");
        List<String> locations = new ArrayList<>();
        Company company = new Company("companyName", interviewTypes, locations);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        jobPostedDate1 = formatter.parse("01/05/2019");
        jobClosedDate1 = formatter.parse("05/07/2019");
        jobPostedDate2 = formatter.parse("01/06/2019");
        jobClosedDate2 = formatter.parse("02/06/2019");
        Map<String, Integer> interviewTypesNums = new HashMap<>();
        interviewTypesNums.put("Phone", 1);
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        List<String> requiredDocuments = new ArrayList<>();
        JobPostingRequirements requirements1 = new JobPostingRequirements(jobClosedDate1, requiredDocuments,
                tags, interviewTypesNums);
        JobPostingRequirements requirements2 = new JobPostingRequirements(jobClosedDate2, requiredDocuments,
                tags, interviewTypesNums);
        job1 = new JobPosting(
                "Staff Accountant", "Role for innovation.", company, "location",
                jobPostedDate1, requirements1);
        job2 = new JobPosting(
                "Data Scientist", "Analyzing and visualizing data.", company, "location",
                jobPostedDate2, requirements2);
        today = formatter.parse("28/06/2019");
        Date accountCreationDate = formatter.parse("01/01/2019");
        jane = new Applicant("Jane", "secure", accountCreationDate);
    }

    @Test
    public void testConstructor_shouldPass() {
        Application application1 = new Application(job1, jane);
        Assert.assertEquals(job1, application1.getJobPosting());
        Assert.assertEquals(ApplicationStatus.WAITING_FOR_NEXT_INTERVIEW, application1.getApplicationStatus());
        Assert.assertEquals("Jane", application1.getApplicant().getUsername());
        Assert.assertNull(application1.getClosedDate());
    }

    @Test
    public void testClosedDate_testCloseBeforeCurrentClosedDate_shouldPass() {
        Application application1 = new Application(job1, jane);
        application1.setClosedDate(today);
        Assert.assertEquals(today, application1.getClosedDate());
        Assert.assertTrue(application1.isClosed());
        Assert.assertEquals(0, application1.getSinceApplicationClosure(today));
    }

    @Test(expected = ApplicationAlreadyClosedException.class)
    public void testClosedDate_closeAfterCurrentClosedDate_shouldThrowApplicationAlreadyExistsException() {
        Application application2 = new Application(job2, jane);
        application2.setClosedDate(today);
        application2.setClosedDate(today);
    }
}

