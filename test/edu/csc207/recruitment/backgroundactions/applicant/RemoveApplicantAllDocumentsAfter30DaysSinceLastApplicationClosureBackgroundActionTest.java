package edu.csc207.recruitment.backgroundactions.applicant;

import edu.csc207.recruitment.model.user.Applicant;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RemoveApplicantAllDocumentsAfter30DaysSinceLastApplicationClosureBackgroundActionTest {


    @Test
    public void testAutoDelete_No_Applications() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("MMddyyyy");
        Date todayDate = formatter.parse("03032019"); // 03 March, 2019

        // create an applicant
        Applicant applicant = new Applicant("username", "password", new Date());

        RemoveApplicantAllDocumentsAfter30DaysSinceLastApplicationClosureBackgroundAction autoDelete = new RemoveApplicantAllDocumentsAfter30DaysSinceLastApplicationClosureBackgroundAction(applicant);
        autoDelete.performAction();
        //should run, but do nothing
    }
}
