package edu.csc207.recruitment.managers;

import edu.csc207.recruitment.tools.TestTools;
import edu.csc207.recruitment.model.company.Company;
import edu.csc207.recruitment.model.user.Applicant;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ManagersTest {

    @Test
    public void testUserManager() {
        //Arange

        //Act
        UserManager userManager = new UserManager();
        //Assert
        Assert.assertNotNull(userManager);
    }

    @Test
    public void testApplicantManager() {
        //Arange
        Applicant applicant = new Applicant("username", "password", new Date());
        List<String> interviewTypes = new ArrayList<>();
        interviewTypes.add("Phone");
        List<String> locations = new ArrayList<>();
        Company company = new Company("companyName", interviewTypes, locations);
        //Act
        ApplicantManager applicantManager = new ApplicantManager(applicant, new Date());
        //Assert
        Assert.assertNotNull(applicantManager);
    }

    @Test
    public void testManage_userTypeExit_shouldReturn() {
        // Arrange
        TestTools.simulateInput(Arrays.asList("Exit"));
        UserManager userManager = new UserManager();
        // Act

        userManager.manage();
        // Assert
    }
}
