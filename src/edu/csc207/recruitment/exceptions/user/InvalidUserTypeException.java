package edu.csc207.recruitment.exceptions.user;

import edu.csc207.recruitment.exceptions.base.RecruitmentSystemException;

public class InvalidUserTypeException extends RecruitmentSystemException {
    public InvalidUserTypeException(String msg) {
        super(msg);
    }
}
