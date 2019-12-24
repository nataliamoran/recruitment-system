package edu.csc207.recruitment.exceptions.document;

import edu.csc207.recruitment.exceptions.base.RecruitmentSystemException;

public class DocumentNotFoundException extends RecruitmentSystemException {
    public DocumentNotFoundException(String format) {
        super(format);
    }
}
