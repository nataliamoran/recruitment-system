package edu.csc207.recruitment.exceptions.application;

import edu.csc207.recruitment.exceptions.base.RecruitmentSystemException;

public class ApplicationNotFoundException extends RecruitmentSystemException {
    public ApplicationNotFoundException(String msg) {
        super(msg);
    }
}
