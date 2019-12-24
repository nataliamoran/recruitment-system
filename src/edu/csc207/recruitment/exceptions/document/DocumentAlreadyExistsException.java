package edu.csc207.recruitment.exceptions.document;

import edu.csc207.recruitment.exceptions.base.RecruitmentSystemException;

public class DocumentAlreadyExistsException extends RecruitmentSystemException {
    public DocumentAlreadyExistsException(String this_document_already_exists) {
        super(this_document_already_exists);
    }
}
