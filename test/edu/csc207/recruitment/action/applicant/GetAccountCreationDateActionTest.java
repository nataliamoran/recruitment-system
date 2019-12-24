package edu.csc207.recruitment.action.applicant;

import edu.csc207.recruitment.actions.applicant.GetAccountCreationDateAction;
import edu.csc207.recruitment.model.user.Applicant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetAccountCreationDateActionTest {
    private Date may1;
    private Applicant jane;

    @Before
    public void setUp() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        may1 = formatter.parse("01/05/2019");
        jane = new Applicant("Jane", "secure", may1);
    }

    @Test
    public void testPerformAction_getCreationDate_shouldPass() {
        GetAccountCreationDateAction action = new GetAccountCreationDateAction(jane);
        action.collectParameters();
        // Capture stdout
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer));

        // capture stdout
        action.performAction();

        // Stop capturing
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

        // Use captured content
        String content = buffer.toString();
        buffer.reset();
        String message = "Account created on: 05-01-2019";
        Assert.assertTrue(content.contains(message));
    }
}

