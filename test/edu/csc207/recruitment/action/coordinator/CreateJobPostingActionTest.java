package edu.csc207.recruitment.action.coordinator;

import edu.csc207.recruitment.tools.TestTools;
import edu.csc207.recruitment.actions.coordinator.CreateJobPostingAction;
import edu.csc207.recruitment.model.company.Company;
import edu.csc207.recruitment.model.user.Coordinator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreateJobPostingActionTest {

    @Test
    public void collectParameters() {
        List<String> interviewTypes = new ArrayList<>();
        interviewTypes.add("Phone");
        List<String> locations = new ArrayList<>();
        locations.add("location");
        Company company = new Company("companyName", interviewTypes, locations);
        Coordinator coordinator = new Coordinator("a", "b", company, locations);
        CreateJobPostingAction jbposting = new CreateJobPostingAction(coordinator);
        TestTools.simulateInput(Arrays.asList("consultant", " data analytics", "location", "tag1", "CV", "stop",
                " 07072018" , " 07072019", "1"));
        jbposting.collectParameters();
        jbposting.performAction();
        assertEquals("consultant", company.getAllJobPostings().get(0).getTitle());

    }

    @Test
    public void performAction() {
        List<String> interviewTypes = new ArrayList<>();
        interviewTypes.add("Phone");
        List<String> locations = new ArrayList<>();
        locations.add("location");
        Company company = new Company("companyName", interviewTypes, locations);
        Coordinator coordinator = new Coordinator("a", "b", company, locations);
        CreateJobPostingAction jbposting = new CreateJobPostingAction(coordinator);
        TestTools.simulateInput(Arrays.asList("consultant", " data analytics", "location", "tag1", "CV", "stop",
                " 07072018" , " 07072019", "1"));
        jbposting.collectParameters();
        jbposting.performAction();
        for (int i =0; i < company.getAllJobPostings().size(); i++ ) {
            if (company.getAllJobPostings().get(i).getTitle().equals("consultant")) {
                assertTrue(true);
            }
        }

    }

    @Test
    public void getDescription() {
        List<String> interviewTypes = new ArrayList<>();
        interviewTypes.add("Phone");
        List<String> locations = new ArrayList<>();
        Company company = new Company("companyName", interviewTypes, locations);
        Coordinator coordinator = new Coordinator("a", "b", company, locations);
        CreateJobPostingAction jbposting = new CreateJobPostingAction(coordinator);
        assertEquals("To create a new job posting", jbposting.getDescription());


    }

    @Test
    public void getShortcut() {
        List<String> interviewTypes = new ArrayList<>();
        interviewTypes.add("Phone");
        List<String> locations = new ArrayList<>();
        Company company = new Company("companyName", interviewTypes, locations);
        Coordinator coordinator = new Coordinator("a", "b", company, locations);
        CreateJobPostingAction jbposting = new CreateJobPostingAction(coordinator);
        assertEquals("2", jbposting.getShortcut());
    }
}