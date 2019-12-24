package edu.csc207.recruitment.exceptions.company;

import edu.csc207.recruitment.exceptions.base.RecruitmentSystemException;

public class CompanyNotFoundException extends RecruitmentSystemException {

    public CompanyNotFoundException(String msg) {
        super(msg);
    }
}
