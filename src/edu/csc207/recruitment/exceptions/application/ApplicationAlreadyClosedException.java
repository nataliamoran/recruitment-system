package edu.csc207.recruitment.exceptions.application;

import edu.csc207.recruitment.exceptions.base.RecruitmentSystemException;

public class ApplicationAlreadyClosedException extends RecruitmentSystemException {
    public ApplicationAlreadyClosedException(String msg) {
        super(msg);
    }
}