package edu.csc207.recruitment.storage;

import edu.csc207.recruitment.document.Document;
import edu.csc207.recruitment.document.DocumentDatabase;
import edu.csc207.recruitment.model.company.Application;
import edu.csc207.recruitment.model.company.Company;
import edu.csc207.recruitment.model.company.Interview;
import edu.csc207.recruitment.model.company.JobPosting;
import edu.csc207.recruitment.model.db.RecruitmentSystem;
import edu.csc207.recruitment.model.db.RecruitmentSystemFactory;
import edu.csc207.recruitment.model.user.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Saves and loads json data
 */
public class SaveAndLoad {
    private static String s = File.separator;
    private static final Path path = Paths.get("." + s + "docs" + s + "jsonStringsDONOTTOUCH");
    private static final Path testPath = Paths.get("." + s + "testStorage" + s + "jsonStrings");
    private static Path currentPath = path;

    private static int applicationNextId = 0;
    private static int userNextId = 0;
    private static int jobPostingNextId = 0;
    private static int documentNextId = 0;
    private static int companyNextId = 0;
    private static int interviewNextId = 0;


    /**
     * Switches to usual run data
     */
    public static void switchPathRun() {
        currentPath = path;
    }

    /**
     * Switches to test data
     */
    public static void switchPathTest() {
        currentPath = testPath;
    }

    /**
     * Saves current state of program to currentPath
     */
    public static void saveProgram() {
        GsonManager gs = new GsonManager();
        String resstr = gs.serializeRecruitmentSystem(RecruitmentSystemFactory.getRecruitmentSystem());
        String docstr = gs.serializeDocumentDatabase(DocumentDatabase.getInstance());
        try {
            PrintWriter writer = new PrintWriter(new File(currentPath.toString()));
            writer.print(resstr);
            writer.print(System.lineSeparator());
            writer.print(docstr);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads saved program state from currentPath
     */
    public static void loadProgram() {
        try {
            GsonManager gs = new GsonManager();
            List<String> lines = Files.lines(currentPath).collect(Collectors.toList());
            String resstr = lines.get(0);
            String docstr = lines.get(1);
            RecruitmentSystem rec = gs.deserializeRecruitmentSystem(resstr);
            DocumentDatabase doc = gs.deserializeDocumentDatabase(docstr);

            documentNextId = doc.size();
            DocumentDatabase.setInstance(doc);
            Document.setNextId(documentNextId + 2);

            companyLoop(rec.getAllCompanies(), rec.getAllUsers());
            userLoop(rec.getAllCompanies(), rec.getAllUsers());
            jobPostingLoop(rec.getAllCompanies(), rec.getAllUsers());


            RecruitmentSystemFactory.getRecruitmentSystem().setAllCompanies(rec.getAllCompanies());
            RecruitmentSystemFactory.getRecruitmentSystem().setAllUsers(rec.getAllUsers());
            System.out.println(String.format("DB loaded from '%s'", currentPath));
        } catch (NoSuchFileException e) {
            System.out.println(String.format("Could not load file '%s'", currentPath));
        } catch (IOException e) {
            System.out.println(String.format("Could not load file '%s'", currentPath));
        }

    }

    private static void userLoop(List<Company> companies, List<User> users) {
        for (User user : users) {
            if (user.getUserType() == UserType.INTERVIEWER) {
                Interviewer interviewer = (Interviewer) user;
                interviewer.updateAllTransient(findCompany(companies, interviewer.getCompanyId()));
            } else if (user.getUserType() == UserType.COORDINATOR) {
                Coordinator coord = (Coordinator) user;
                coord.updateAllTransient(findCompany(companies, coord.getCompanyId()));
            } else if (user.getUserType() == UserType.APPLICANT) {
                Applicant app = (Applicant) user;
                app.updateAllTransient();
            }
        }
        userNextId = users.size();
        User.setNextId(userNextId + 2);
    }

    private static void companyLoop(List<Company> companies, List<User> users) {
        for (Company company : companies) {
            company.updateTransient();
            for (User user : users) {
                if (user.getUserType() == UserType.INTERVIEWER) {
                    Interviewer interviewer = (Interviewer) user;
                    if (interviewer.getCompanyId() == company.getId())
                        company.addStaff(user);
                } else if (user.getUserType() == UserType.COORDINATOR) {
                    Coordinator coordinator = (Coordinator) user;
                    if (coordinator.getCompanyId() == company.getId())
                        company.addStaff(user);
                }
            }
        }
        companyNextId = companies.size();
        Company.setNextId(companyNextId + 2);
    }

    private static Company findCompany(List<Company> companies, int id) {
        return getCompanyDict(companies).get(id);
    }

    private static HashMap<Integer, Company> getCompanyDict(List<Company> companies) {
        HashMap<Integer, Company> result = new HashMap<>();
        for (Company company : companies) {
            result.put(company.getId(), company);
        }
        return result;
    }

    private static HashMap<Application, Applicant> getJobPostingDict(List<User> users) {
        HashMap<Application, Applicant> result = new HashMap<>();
        for (User u : users) {
            if (u.getUserType() == UserType.APPLICANT) {
                Applicant applicant = (Applicant) u;
                List<Application> jobs = applicant.getHistory().getApplications();
                for (Application application : jobs) {
                    result.put(application, applicant);
                }
            }
        }
        return result;
    }


    private static void jobPostingLoop(List<Company> companies, List<User> users) {
        HashMap<Applicant, Application> applicants = new HashMap<>();
        for (Company comp : companies) {
            List<JobPosting> jobs = comp.getAllJobPostings();
            jobPostingNextId += jobs.size();


            HashMap<Application, Applicant> appd = getJobPostingDict(users);
            for (JobPosting job : jobs) {
                List<Application> applications = job.getApplications();
                for (Application app : applications) {
                    for (Application personalapp : appd.keySet()) {
                        if (personalapp.getId() == app.getId()) {
                            applicationNextId += 1;
                            Applicant applicant = appd.get(personalapp);
                            app.setApplicant(applicant);
                            personalapp.setApplicant(applicant);
                            applicants.put(applicant, personalapp);
                            app.setJob(job);
                            personalapp.setJob(job);
                        }
                    }
                }

                interviewNextId += job.getInterviews().size();
            }
        }
        for (Applicant applicant : applicants.keySet()) {
            applicant.replaceApplication(applicants.get(applicant));
        }
        Interview.setNextId(interviewNextId + 2);
        JobPosting.setNextId(jobPostingNextId + 2);
        Application.setNextId(applicationNextId + 2);
    }

}
