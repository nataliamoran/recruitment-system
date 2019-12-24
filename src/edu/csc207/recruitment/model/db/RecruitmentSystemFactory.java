package edu.csc207.recruitment.model.db;

public class RecruitmentSystemFactory {
    private static boolean testMode = false;
    private static RecruitmentSystem recruitmentSystemProd = new RecruitmentSystem();
    private static RecruitmentSystem recruitmentSystemTest;

    /**
     * Method to get a recruitment system DB instance based on running mode
     *
     * @return a recruitment system DB instance
     */
    public static RecruitmentSystem getRecruitmentSystem() {
        if (testMode) {
            return recruitmentSystemTest;
        } else {
            return recruitmentSystemProd;
        }
    }

    /**
     * Method to set running mode to test/prod
     *
     * @param value true for test mode, false for prod mode
     */
    public static void setTestMode(boolean value) {
        testMode = value;
        recruitmentSystemTest = new RecruitmentSystem();
    }

}
