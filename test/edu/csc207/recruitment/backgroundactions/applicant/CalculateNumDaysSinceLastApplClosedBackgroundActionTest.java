package edu.csc207.recruitment.backgroundactions.applicant;

import edu.csc207.recruitment.model.company.Application;
import edu.csc207.recruitment.model.company.Company;
import edu.csc207.recruitment.model.company.JobPosting;
import edu.csc207.recruitment.model.company.JobPostingRequirements;
import edu.csc207.recruitment.model.user.Applicant;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;

public class CalculateNumDaysSinceLastApplClosedBackgroundActionTest {

    @Test
    public void testCalculateNumDaysSinceLastApplClosedAction_greenPath() throws java.text.ParseException {
        //Arange

        // Create dates
        SimpleDateFormat formatter = new SimpleDateFormat("MMddyyyy");
        Date openningDate = formatter.parse("01012019"); // 01 January, 2019
        Date applicationsDeadlineDate = formatter.parse("02012019"); // 01 February, 2019
        Date application1ClosedDate = formatter.parse("03012019"); // 01 March, 2019
        Date application2ClosedDate = formatter.parse("03022019"); // 02 March, 2019
        Date todayDate = formatter.parse("03032019"); // 03 March, 2019
        List<String> interviewTypes = new ArrayList<>();
        interviewTypes.add("Phone");
        List<String> locations = new ArrayList<>();
        Company company = new Company("companyName", interviewTypes, locations);

        // create an applicant
        Applicant applicant = new Applicant("username", "password", new Date());

        // create 2 job postings
        Map<String, Integer> interviewTypesNums = new HashMap<>();
        interviewTypesNums.put("Phone", 1);
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        List<String> requiredDocuments = new ArrayList<>();
        JobPostingRequirements requirements = new JobPostingRequirements(applicationsDeadlineDate, requiredDocuments,
                tags, interviewTypesNums);
        JobPosting job1 = new JobPosting("title", "description", company, "location",
                openningDate, // 01 January, 2019
                requirements); // 01 February, 2019
        JobPosting job2 = new JobPosting("title", "description", company, "location",
                openningDate, // 01 January, 2019
                requirements); // 01 February, 2019

        // create 2 applications
        Application application1 = new Application(job1, applicant);
        Application application2 = new Application(job2, applicant);

        // set different closed dates for 2 applications
        application1.setClosedDate(application1ClosedDate); // 01 March, 2019
        application2.setClosedDate(application2ClosedDate); // 02 March, 2019

        // add 2 applications to the applicant's account history
        applicant.getHistory().addApplication(application1);
        applicant.getHistory().addApplication(application2);

        // create action object
        CalculateNumDaysSinceLastApplClosedBackgroundAction calculate = new CalculateNumDaysSinceLastApplClosedBackgroundAction(applicant,
                todayDate); // 03 March, 2019

        //Act
        calculate.performAction();

        //Assert
        Assert.assertTrue(1 == applicant.getHistory().getSinceLastApplicationClosure());
    }
}
