package edu.csc207.recruitment.exceptions.input;


import edu.csc207.recruitment.exceptions.base.RecruitmentSystemException;

public class EmptyInputException extends RecruitmentSystemException {

    public EmptyInputException(String msg) {
        super(msg);
    }
}
