package edu.csc207.recruitment.action.user;

import edu.csc207.recruitment.tools.TestTools;
import edu.csc207.recruitment.actions.user.SignUpAction;
import org.junit.Test;

import java.util.Arrays;

public class SignUpActionTest {

    @Test
    public void testCollectParameters_greenPath() {
        //Arange
        SignUpAction signUp = new SignUpAction();
        TestTools.simulateInput(Arrays.asList("natalia3", "password", "1", "", "01192019"));

        //Act
        signUp.collectParameters();
        //Assert
    }

//    @Test
//    public void testPerformAction_greenPath() {
//        //Arange
//        SignUpAction signUp = new SignUpAction();
//        TestTools.simulateInput(Arrays.asList("natalia4", "password", "1", "", "01192019", "Exit"));
//        signUp.collectParameters();
//
//        //Act
//        signUp.performAction();
//        //Assert
//    }

}
