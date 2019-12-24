package edu.csc207.recruitment.model.users;

import edu.csc207.recruitment.model.user.Applicant;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class ApplicantTest {

    @Test
    public void testApplicant_greenPath() {
        //Arange
        Applicant applicant = new Applicant("username", "password", new Date());
        //Act

        //Assert
        Assert.assertEquals(new Date(), applicant.getHistory().getCreationDate());

    }
}
