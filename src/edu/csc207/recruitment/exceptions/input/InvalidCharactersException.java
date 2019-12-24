package edu.csc207.recruitment.exceptions.input;

import edu.csc207.recruitment.exceptions.base.RecruitmentSystemException;

public class InvalidCharactersException extends RecruitmentSystemException {
    public InvalidCharactersException(String format) {
        super(format);
    }
}

