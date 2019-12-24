package edu.csc207.recruitment.action.coordinator;

import edu.csc207.recruitment.model.company.*;
import edu.csc207.recruitment.tools.TestTools;
import edu.csc207.recruitment.actions.coordinator.HireApplicantAction;
import edu.csc207.recruitment.model.user.Applicant;
import edu.csc207.recruitment.model.user.Coordinator;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class HireApplicantActionTest {
    private Applicant josh;
    private Applicant jake;
    private Applicant jane;
    private Applicant natalia;
    private JobPosting workStudy;
    private JobPosting labWork;
    private Date may1;
    private Date may2;
    private Date may3;
    private Coordinator joon;
    private Company apple;
    private Application application1;
    private Application application2;
    private Application application3;
    private Application application4;


    @Before
    public void setUp() throws ParseException {
        List<String> interviewTypes = new ArrayList<>();
        interviewTypes.add("Phone");
        List<String> locations = new ArrayList<>();
        apple = new Company("companyName", interviewTypes, locations);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        may1 = formatter.parse("01/05/2019");
        may2 = formatter.parse("02/05/2019");
        may3 = formatter.parse("03/05/2019");
        Map<String, Integer> interviewTypesNums = new HashMap<>();
        interviewTypesNums.put("Phone", 1);
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        List<String> requiredDocuments = new ArrayList<>();
        JobPostingRequirements requirements = new JobPostingRequirements(may2, requiredDocuments,
                tags, interviewTypesNums);
        workStudy = new JobPosting("work study ", "Working and managing the lab space",
                apple, "location", may1, requirements);
        labWork = new JobPosting("lab work ", "Working and managing the lab space",
                apple, "location", may1, requirements);
        josh = new Applicant("Josh", "password", may2);
        jake = new Applicant("Jake", "password", may2);
        jane = new Applicant("Jane", "password", may2);
        natalia = new Applicant("Natalia", "password", may2);
        joon = new Coordinator("joon123", "password", apple, locations);


        application1 = new Application(workStudy, josh);
        application1.setApplicationStatus(ApplicationStatus.WAITING_FOR_FINALSTEP);
        application2 = new Application(workStudy, jake);
        application2.setApplicationStatus(ApplicationStatus.WAITING_FOR_FINALSTEP);
        application3 = new Application(workStudy, jane);
        application3.setApplicationStatus(ApplicationStatus.REJECTED);
        application4 = new Application(workStudy, natalia);


        workStudy.addApplication(application1);
        workStudy.addApplication(application2);
        workStudy.addApplication(application3);
        labWork.addApplication(application4);

        apple.addJobPosting(workStudy);
        apple.addJobPosting(labWork);

    }

    @Test
    public void testCollectParameters_greenPath() {
        //Arrange
        HireApplicantAction hire = new HireApplicantAction(joon, may3);
        TestTools.simulateInput(Arrays.asList(String.valueOf(workStudy.getId()), String.valueOf(application1.getId())));

        //Act
        hire.collectParameters();


    }

    @Test
    public void testPerformAction_greenPath() {
        //Arrange
        HireApplicantAction hire = new HireApplicantAction(joon, may3);
        TestTools.simulateInput(Arrays.asList(String.valueOf(workStudy.getId()), String.valueOf(application1.getId())));
        hire.collectParameters();

        //Act
        hire.performAction();

    }


}
