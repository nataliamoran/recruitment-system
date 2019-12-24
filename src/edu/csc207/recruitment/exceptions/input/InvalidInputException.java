package edu.csc207.recruitment.exceptions.input;

import edu.csc207.recruitment.exceptions.base.RecruitmentSystemException;

public class InvalidInputException extends RecruitmentSystemException {
    public InvalidInputException(String msg) {
        super(msg);
    }
}
