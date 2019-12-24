package edu.csc207.recruitment.action.user;

import edu.csc207.recruitment.tools.TestTools;
import edu.csc207.recruitment.actions.user.LoginAction;
import org.junit.Test;

import java.util.Arrays;

public class LoginActionTest {
    @Test
    public void testCollectParameters_greenPath() {
        //Arange
        LoginAction login = new LoginAction();
        TestTools.simulateInput(Arrays.asList("natalia1", "password", "01192019"));

        //Act
        login.collectParameters();
        //Assert
    }

//    @Test
//    public void testPerformAction_greenPath() {
//        //Arange
//        SignUpAction signUp = new SignUpAction();
//        TestTools.simulateInput(Arrays.asList("natalia2", "password", "1", "", "01192019", "Exit"));
//        signUp.collectParameters();
//        signUp.performAction();
//        LoginAction login = new LoginAction();
//        TestTools.simulateInput(Arrays.asList("natalia2", "password", "01192019", "Exit"));
//        login.collectParameters();
//
//        //Act
//        login.performAction();
//        //Assert
//    }
}
