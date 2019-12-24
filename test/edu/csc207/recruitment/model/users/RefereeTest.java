package edu.csc207.recruitment.model.users;

import edu.csc207.recruitment.model.user.Applicant;
import edu.csc207.recruitment.model.user.Referee;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class RefereeTest {

    @Test
    public void TestReferee() {
        //Arrange
        Referee referee = new Referee("TestReferee", "password");
        Applicant applicant = new Applicant("TestApplicant", "password", new Date());
        referee.addApplicant(applicant);

        //Assert
        Assert.assertEquals(applicant, referee.getApplicantList().get(0));

    }
}
