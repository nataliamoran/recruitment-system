package edu.csc207.recruitment.model.company;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class JobPostingRequirements {
    private List<String> requiredDocuments;
    private Date applicationsDeadlineDate;
    private List<String> tag;
    private Map<String, Integer> interviewTypeLimitsMap;

    /**
     * A constructor of job posting requiredDocuments.
     *
     * @param applicationsDeadlineDate the date which is the application deadline.
     * @param interviewTypeLimitsMap   the number of each interview type required for this job posting
     */
    public JobPostingRequirements(Date applicationsDeadlineDate, List<String> requiredDocuments, List<String> tag,
                                  Map<String, Integer> interviewTypeLimitsMap) {
        this.applicationsDeadlineDate = applicationsDeadlineDate;
        this.requiredDocuments = new ArrayList<>();
        this.requiredDocuments.add("CV");
        this.requiredDocuments.add("Cover Letter");
        this.requiredDocuments.addAll(requiredDocuments);
        this.interviewTypeLimitsMap = interviewTypeLimitsMap;
        this.tag = tag;
    }

    public List<String> getRequiredDocuments() {
        return this.requiredDocuments;
    }

    public List<String> getTags() {
        return tag;
    }

    public void setTags(List<String> tagCollection) {
        this.tag = tagCollection;
    }

    public Date getApplicationsDeadlineDate() {
        return this.applicationsDeadlineDate;
    }

    public Map<String, Integer> getInterviewTypeLimitsMap() {
        return interviewTypeLimitsMap;
    }

    public Integer getInterviewTypeLimit(final String interviewType) {
        return interviewTypeLimitsMap.get(interviewType);
    }
}
