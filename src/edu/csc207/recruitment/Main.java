package edu.csc207.recruitment;

import edu.csc207.recruitment.exceptions.base.RecruitmentSystemException;
import edu.csc207.recruitment.managers.UserManager;
import edu.csc207.recruitment.storage.SaveAndLoad;

public class Main {

    public static void main(String[] args) {
        SaveAndLoad.loadProgram();
        try {
            UserManager userManager = new UserManager();
            userManager.manage();

        } catch (RecruitmentSystemException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

    }
}

