package edu.csc207.recruitment.exceptions.input;

import edu.csc207.recruitment.exceptions.base.RecruitmentSystemException;

public class IncorrectCredentialsException extends RecruitmentSystemException {
    public IncorrectCredentialsException(String msg) {
        super(msg);
    }
}
