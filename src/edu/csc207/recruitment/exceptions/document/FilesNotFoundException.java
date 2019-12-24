package edu.csc207.recruitment.exceptions.document;

import edu.csc207.recruitment.exceptions.base.RecruitmentSystemException;

public class FilesNotFoundException extends RecruitmentSystemException {
    public FilesNotFoundException(String format) {
        super(format);
    }
}
