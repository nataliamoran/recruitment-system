package edu.csc207.recruitment.model.users;

import edu.csc207.recruitment.model.company.Application;
import edu.csc207.recruitment.model.company.Company;
import edu.csc207.recruitment.model.company.JobPosting;
import edu.csc207.recruitment.model.company.JobPostingRequirements;
import edu.csc207.recruitment.model.user.AccountHistory;
import edu.csc207.recruitment.model.user.Applicant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class AccountHistoryTest {
    private JobPosting job1;
    private JobPosting job2;
    private Date jobPostedDate1;
    private Date jobClosedDate1;
    private Date jobPostedDate2;
    private Date jobClosedDate2;
    private Date application1ClosedDate;
    private Date accountCreationDate;
    private Date today;
    private Application application1;
    private Application application2;
    private Company company;

    /**
     * Set up documents and an Applicant for testing.
     */
    @Before
    public void setUp() throws ParseException {
        List<String> interviewTypes = new ArrayList<>();
        interviewTypes.add("Phone");
        List<String> locations = new ArrayList<>();
        company = new Company("companyName", interviewTypes, locations);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        accountCreationDate = formatter.parse("01/01/2019");
        jobPostedDate1 = formatter.parse("01/05/2019");
        jobClosedDate1 = formatter.parse("05/07/2019");
        jobPostedDate2 = formatter.parse("01/06/2019");
        jobClosedDate2 = formatter.parse("02/06/2020");
        application1ClosedDate = formatter.parse("05/06/2019");
        today = formatter.parse("28/06/2019");
        Map<String, Integer> interviewTypesNums = new HashMap<>();
        interviewTypesNums.put("Phone", 1);
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        List<String> requiredDocuments = new ArrayList<>();
        JobPostingRequirements requirements1 = new JobPostingRequirements(jobClosedDate1, requiredDocuments,
                tags, interviewTypesNums);
        JobPostingRequirements requirements2 = new JobPostingRequirements(jobClosedDate2, requiredDocuments,
                tags, interviewTypesNums);
        job1 = new JobPosting("Staff Accountant", "Role for innovation.", company, "location",
                jobPostedDate1, requirements1);
        job2 = new JobPosting("Data Scientist", "Analyzing and visualizing data.", company,
                "location", jobPostedDate2, requirements2);
        Applicant jane = new Applicant("Jane", "secure", accountCreationDate);
        application1 = new Application(job1, jane);
        application1.setClosedDate(application1ClosedDate);
        application2 = new Application(job2, jane);
    }

    @Test
    public void testConstructor_shouldPass() {
        AccountHistory history = new AccountHistory(today);
        Assert.assertTrue(history.getOpenApplications().isEmpty());
        Assert.assertTrue(history.getClosedApplications().isEmpty());
        Assert.assertEquals(0, ChronoUnit.DAYS.between(today.toInstant(),
                history.getCreationDate().toInstant()));
        Assert.assertNull(history.getSinceLastApplicationClosure());
    }

    @Test
    public void testAddApplication_add2Applications_shouldPass() {
        AccountHistory history = new AccountHistory(accountCreationDate);
        history.addApplication(application1);
        history.addApplication(application2);
        Assert.assertEquals(1, history.getClosedApplications().size());
        Assert.assertEquals(1, history.getOpenApplications().size());
    }
}

