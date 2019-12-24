Recruitment System
=========================

# Project Installation

1. Create a new project in IntelliJ
2. Clone the project from GIT https://markus.teach.cs.toronto.edu/git/csc207-2019-05/group_0129
3. Set up IDE
4. Mark 'src' directory as Sources Root
5. Mark 'test' directory as Test Sources Root
6. Set dependencies:
    File > Project Structure > Libraries > '+' > Java >
    > Choose 'gson-2.8.5.jar' and 'json-20180813.jar' from the directory 'group_0129/./lib' >
    > Apply > OK
7. To resolve 'import org.junit.*;' in tests, select 'org.junit.*;' and press Alt + Enter
8. Set Working Directory for both Application and JUnit:
    i) Run > Edit Configurations > Application > Main > Configuration >
        > Set 'group_0129/phase2' directory as the Working Directory
    ii) Run > Edit Configurations > JUnit > All in group_0129 > Configuration >
        > Set 'group_0129/phase2' directory as the Working Directory

=========================

# HIRING Notes

To hire an Applicant for a specific Job Posting:

    (1) a Coordinator needs to login after the Applications Deadline date
        (session date should be provided during the login)

    (2) All applications for this Job Posting should be in their final statuses:
            'Waiting For Final Step', 'Rejected' or 'Withdrawn'.

    (3) There should be multiple 'Waiting For Final Step' applications.
            - If there is a single 'Waiting For Final Step' application
            (other applications are 'Rejected' or 'Withdrawn'),
            then this Applicant will be hired automatically on the next login
            (if the login session date is after the Applications Deadline).

=========================

# Usage Manual

1. Check UML diagrams in 'group_0129/./docs' directory

2. Run Main


## Sign up as an Admin and create a Company. Only Admin can create new companies.

3. Press '1' to 'Sign up'

4. Type in your username and then password

5. Press '4' to choose your user type: Admin

6. Enter the session date with the format MMDDYYYY

7. Press '1' to choose to create a company and enter the information requested in the command line

8. After creating a company you may wish to create new users by the Admin or to exit the Admin session by typing 'Exit'
and to sign up as a new user.



## New User Flow

9. Press '1' to 'Sign up'

10. Type in your username and then password

11. Choose your user type: applicant, coordinator, interviewer, admin or referee
by pressing '1', '2', '3', '4' or '5' respectively

12. If your user type is coordinator or interviewer, type in your company name

13. If your user type is coordinator or interviewer, enter locations of branches where you work

13. Then enter the session date with the format MMDDYYYY

14. A menu of actions, depending on your user type, will be printed out in the command line

15. Chose an action, type in the corresponding number and press enter



## Returning User Flow

9. Press '2' to 'Login'

10. Type in your username, then password and then enter the session date with the format MMDDYYYY

11. A menu of actions, depending on your user type, will be printed out in the command line

12. Chose an action, type in the corresponding number and press enter


=========================

# CLI

- When any user type logs in, open job postings are check automatically:
    - If there are job postings which applications deadline has already passed and
      there is a single applicant waiting for the final step (all other applicants are already rejected or
      withdrew their applications),
      then this applicant is automatically hired.


## Applicant Flow

- When an applicant logs in, her / his applications statuses update is printed out in the command line.

- When an applicant logs in, the number of days since last application closure is calculated.
If it is equal to or greater than 30 and if the applicant does not have open applications,
the applicant's documents are removed.

- #1 Upload documents
    - Enter the document title
    - For the file you want to upload from your computer, enter the file path

- #2 Get account creation date
    - The date of account creation will be printed out in the command line.

- #3 Get all open job postings
    - Choose if you want to see job postings sorted by their tags. If yes, enter the tag.
    - Information on open job postings will be printed out in the command line.

- #4 Get all applications data
    - Choose to see your closed or open applications - information will be printed out in the command line.

- #5 Get number of days since last application closed
    - If you have closed applications, the date since last application closure will be printed out in the command line
      Otherwise, the notification 'You have no closed applications.' will be printed out.

- #6 Print your documents content
    - Enter the title of the document you want to see - its content will be printed out in the command line.

- #7 Apply for a job posting
    - A list of open job postings and their IDs will be printed out in the command line.
    - Enter the ID of the job for which you want to apply.

- #8 Withdraw application
    - Information on your applications and their IDs will be printed out in the command line.
    - Enter the ID of the application which you want to withdraw.

- #9 Add a referee
    - A list of available referees will be printed out in the command line.
    - Add a referee to your list of referees to allow her / him to post a reference for you.

- `Exit` will logout from the applicant menu back to signup / login.



## Coordinator Flow

- #1 Create a new interview
    - A list of applications, waiting for the next interview, and their IDs will be printed out in the command line.
    - Enter the ID of the application for which you want to schedule an interview.
    - Information on required interview types will be printed out in the command line.
    - Choose the type of the interview.
    - A list of available interviewees and their IDs will be printed out in the command line.
    - Choose the interviewer.
    - An interview will be created.

- #2 Create a new job posting
    - Type in the job's title.
    - Type in the job's description.
    - Type in the branch location. Coordinator can create jobs only for branches where she / he works.
    - Enter tags. You can enter required skills as tags (e.g. #java).
    - Enter required documents. CV and Cover Letter are mandatory documents for every job posting by default.
    - Enter the job creation date with the format MMDDYYYY.
    - Enter the job's applications deadline date with the format MMDDYYYY.
    - Enter how many interviews of types available in your company are required for this particular job posting.
    - A new job will be created.

- #3 Get all applicants info
    - A list of applicants will be printed out in the command line.
    - Type in the username of the applicant whose data you want to view.
    - You can either see this applicant's documents or job applications - type in '1' or '2' respectively.
    - If '1' is chosen, enter the title of the document to see - its content will be printed out in the command line.

- #4 Get all applications for the job posting
    - A list of job postings and their IDs will be printed out in the command line.
    - Type in the job posting ID, applications for which you want to view.
    - Info on applications will be printed out in the command line.

- #5 Hire an applicant
    - A list of job postings, where multiple candidates are waiting to be hired, will be printed out in the command line.
    - Enter the ID of the job posting.
    - Choose the applicant to hire. Other applications will be automatically rejected.

- #6 Close a job posting
    - A list of open job postings and their IDs will be printed out in the command line.
    - Type in the ID of the job posting ID which you want to close.
    - The job posting will be closed and all applications will be rejected.

- #6 Close a job posting
    - A list of applicants will be printed out in the command line.
    - Type in the username of the applicant to see her / his reference letters.
    - Reference letters will be printed out in the command line.

- `Exit` will logout from the coordinator menu back to signup / login



## Interviewer Flow

- When an interviewer logs in,
  a list of applicants awaiting an interview with this interviewer is printed out in the command line.

- #1 See a list of interviewees assigned to this interviewer
    - A list of applicants awaiting an interview and their IDs will be printed out in the command line.

- #2 Run an interview
    - A list of applicants awaiting an interview and their IDs will be printed out in the command line.
    - Choose the applicant.
    - Choose if you want to write a recommendation for this applicant or to reject her/his application -
      type in '1' or '2' respectively.
    - If '1' is chosen, enter a recommendation.
      Writing a recommendation implies that an applicant passed the interview.

- `Exit` will logout from the interviewer menu back to signup / login



## Admin Flow

- #1 Create a company
    - Enter a company title.
    - Enter interview types available in this company.
    - Enter the company's branches locations.
    - A new company will be created.

- #2 Create a user
    - For creating a new user please see the Sign Up instruction - the actions flows are very similar.

- `Exit` will logout from the admin menu back to signup / login



## Referee Flow

- #1 View applications for which a reference is required
    - A list of applicants who added this referee to their referees list and job postings for which they applied will
    be printed out in the command line.

- #2 Upload your reference letter
    - Choose an applicant to upload a reference for.
    - Enter the path for the Reference Letter file to be uploaded.

- `Exit` will logout from the referee menu back to signup / login