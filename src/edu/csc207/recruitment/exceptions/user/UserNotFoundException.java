package edu.csc207.recruitment.exceptions.user;

import edu.csc207.recruitment.exceptions.base.RecruitmentSystemException;

public class UserNotFoundException extends RecruitmentSystemException {
    public UserNotFoundException(String format) {
        super(format);
    }
}
