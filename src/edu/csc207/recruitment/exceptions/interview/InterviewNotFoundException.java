package edu.csc207.recruitment.exceptions.interview;

import edu.csc207.recruitment.exceptions.base.RecruitmentSystemException;

public class InterviewNotFoundException extends RecruitmentSystemException {
    public InterviewNotFoundException(String msg) {
        super(msg);
    }
}
