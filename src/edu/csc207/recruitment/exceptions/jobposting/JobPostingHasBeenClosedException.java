package edu.csc207.recruitment.exceptions.jobposting;

import edu.csc207.recruitment.exceptions.base.RecruitmentSystemException;

public class JobPostingHasBeenClosedException extends RecruitmentSystemException {
    public JobPostingHasBeenClosedException(String msg) {
        super(msg);
    }
}
