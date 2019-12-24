package edu.csc207.recruitment.exceptions.base;

/**
 * This is the basic exception of Recruitment System
 * All other exceptions of the project should inherit from it
 */
public class RecruitmentSystemException extends RuntimeException {
    public RecruitmentSystemException(String message) {
        super(message);
    }
}
