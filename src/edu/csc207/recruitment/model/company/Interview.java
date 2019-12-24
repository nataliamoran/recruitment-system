package edu.csc207.recruitment.model.company;

import edu.csc207.recruitment.model.user.Interviewer;

public class Interview {
    /**
     * See class description.
     */
    private transient Interviewer interviewer;
    private transient Application application;
    private String interviewType;
    private String recommendation;
    private static int nextId = 0;
    private int id;

    public Interview(Interviewer interviewer, Application application, String interviewType) {
        this.interviewer = interviewer;
        this.application = application;
        this.interviewType = interviewType;
        this.id = nextId;
        nextId++;
    }

    public int getId() {
        return this.id;
    }

    public Interviewer getInterviewer() {
        return this.interviewer;
    }

    public Application getApplication() {
        return this.application;
    }

    public String getInterviewType() {
        return this.interviewType;
    }

    /**
     * If the recommendation is null there is no recommendation (bad), else the recommendation is good.
     *
     * @return The recommendation given by the Interviewer if the interviewee passes the interview.
     */
    public String getRecommendation() {
        return this.recommendation;
    }

    public void setRecommendation(String newRecommendation) {
        this.recommendation = newRecommendation;
    }

    public void setInterviewer(Interviewer interviewer) {
        this.interviewer = interviewer;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public static void setNextId(int id) {
        nextId = id;
    }


}