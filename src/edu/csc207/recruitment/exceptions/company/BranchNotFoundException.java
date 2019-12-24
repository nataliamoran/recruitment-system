package edu.csc207.recruitment.exceptions.company;

import edu.csc207.recruitment.exceptions.base.RecruitmentSystemException;

public class BranchNotFoundException extends RecruitmentSystemException {
    public BranchNotFoundException(String msg) {
        super(msg);
    }
}
