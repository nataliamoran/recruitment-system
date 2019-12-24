package edu.csc207.recruitment.exceptions.interview;

import edu.csc207.recruitment.exceptions.base.RecruitmentSystemException;

public class InterviewIsNotRequiredException extends RecruitmentSystemException {
    public InterviewIsNotRequiredException(String msg) {
        super(msg);
    }
}
