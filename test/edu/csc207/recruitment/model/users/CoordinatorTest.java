package edu.csc207.recruitment.model.users;

import edu.csc207.recruitment.model.company.Company;
import edu.csc207.recruitment.model.user.Coordinator;
import edu.csc207.recruitment.model.user.UserType;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CoordinatorTest {
    private List<String> interviewTypes = new ArrayList<>();
    private List<String> locations = new ArrayList<>();
    private Company EY = new Company("companyName", interviewTypes, locations);


    @Test
    public void testConstructor_shouldPass() {
        interviewTypes.add("Phone");
        List<String> locations = new ArrayList<>();
        Coordinator anna = new Coordinator("Anna", "loveEY", EY, locations);
        Assert.assertEquals(EY, anna.getCompany());
        Assert.assertEquals(UserType.COORDINATOR, anna.getUserType());
    }
}
