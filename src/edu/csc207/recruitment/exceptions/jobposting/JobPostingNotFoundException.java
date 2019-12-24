package edu.csc207.recruitment.exceptions.jobposting;

import edu.csc207.recruitment.exceptions.base.RecruitmentSystemException;

public class JobPostingNotFoundException extends RecruitmentSystemException {
    public JobPostingNotFoundException(String msg) {
        super(msg);
    }
}
