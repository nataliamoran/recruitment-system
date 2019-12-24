package edu.csc207.recruitment.exceptions.input;

import edu.csc207.recruitment.exceptions.base.RecruitmentSystemException;

public class IncorrectPasswordException extends RecruitmentSystemException {
    public IncorrectPasswordException(String msg) {
        super(msg);
    }
}
