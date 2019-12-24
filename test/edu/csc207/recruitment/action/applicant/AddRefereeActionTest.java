package edu.csc207.recruitment.action.applicant;

import edu.csc207.recruitment.actions.applicant.AddRefereeAction;
import edu.csc207.recruitment.model.db.RecruitmentSystem;
import edu.csc207.recruitment.model.db.RecruitmentSystemFactory;
import edu.csc207.recruitment.model.user.Applicant;
import edu.csc207.recruitment.model.user.Referee;
import edu.csc207.recruitment.tools.TestTools;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

public class AddRefereeActionTest {
    private Date may1;
    private Applicant app1;
    private Referee ref1;

    @Before
    public void setUp() throws ParseException {
        RecruitmentSystemFactory.setTestMode(true);
        RecruitmentSystem recruitmentSystem = RecruitmentSystemFactory.getRecruitmentSystem();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        may1 = formatter.parse("01/05/2019");
        app1 = new Applicant("Jane", "secure", may1);
        ref1 = new Referee("ref1", "pass");
        recruitmentSystem.addUser(ref1);
        recruitmentSystem.addUser(app1);
    }

    @Test
    public void Test_CollectParameters() {
        //Arrange
        AddRefereeAction addRefereeAction = new AddRefereeAction(app1);

        //Act
        TestTools.simulateInput(Collections.singletonList(String.valueOf(ref1.getUsername())));
        addRefereeAction.collectParameters(); //should print "ref1"

    }

    @Test
    public void Test_PerformAction() {
        //Arrange
        AddRefereeAction addRefereeAction = new AddRefereeAction(app1);
        TestTools.simulateInput(Collections.singletonList(String.valueOf(ref1.getUsername())));
        addRefereeAction.collectParameters();

        //Act
        addRefereeAction.performAction();

    }
}
