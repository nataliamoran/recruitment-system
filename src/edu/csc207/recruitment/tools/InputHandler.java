package edu.csc207.recruitment.tools;

import edu.csc207.recruitment.exceptions.input.InvalidInputException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * InputHandler handles all user input so we can change it if needed for testing purposes
 */
public class InputHandler {
    private final static String EMPTY_STRING = "";
    private Scanner scanner;
    private final static InputHandler instance = new InputHandler(new Scanner(System.in));
    private static String USER_INPUT_STOP = "stop";

    private InputHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public static InputHandler getInstance() {
        return instance;
    }

    /**
     * Get a non empty line from the user
     *
     * @return a non empty string input from the user
     */
    public static String nextLine() {
        String userInput = EMPTY_STRING;
        while (EMPTY_STRING.equals(userInput)
                || userInput.replaceAll("\\s+", "").equals("")
                || userInput.startsWith("--")) {
            userInput = instance.scanner.nextLine();
        }
        return userInput;
    }

    /**
     * Prints a pretty message to the user with a prefix and previous/default value and gets an input from the user.
     *
     * @param msg    the main message to print
     * @param prefix the prefix, usually indicating the action the user is doing
     * @return the user's input if non-empty, otherwise defaultValue
     */
    public static String getValueFromUser(String msg, String prefix) {
        String msgToPrint = String.format("%s[%s] %s%s%s",
                ConsoleColors.RED_BRIGHT, prefix,
                ConsoleColors.BLUE, msg,
                ConsoleColors.WHITE);
        System.out.println(msgToPrint);
        String newVal = InputHandler.nextLine();
        return newVal;
    }

    /**
     * Get a date from the session
     *
     * @return a date for the session
     */
    public static Date getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("MMddyyyy");
        String userInput = null;
        Date res = null;
        while (userInput == null) {
            System.out.println("Please write today's date. The format should be MMDDYYYY.");
            userInput = InputHandler.nextLine();
            try {
                res = formatter.parse(userInput);
            } catch (ParseException e) {
                System.out.println("The date format is incorrect, please try again.");
                userInput = null;
            }
        }
        System.out.println(String.format("Date: [%s]", res.toString()));
        return res;
    }

    /**
     * This method is used for tests to replace the keyboard with a simulated input
     *
     * @param scanner a scanner with the simulated input
     */
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }


    /**
     * Method to collect a few pieces of information (String) until the user enters "stop".
     *
     * @param subject the subject matter regarding which users are prompted to enter
     */
    public static List<String> collectMultipleStrings(String subject) {
        List<String> items = new ArrayList<>();
        System.out.println(String.format("%nPlease enter %s - as many as you wish. ", subject));
        String userInput = "";
        while (!USER_INPUT_STOP.equalsIgnoreCase(userInput)) {
            userInput = getValueFromUser(String.format(
                    "If there are no more %s to add, write '%s':", subject, USER_INPUT_STOP),
                    String.format("Enter %s.", subject));
            if (!USER_INPUT_STOP.equalsIgnoreCase(userInput)) {
                items.add(userInput);
            }
        }
        if (items.isEmpty()) {
            throw new InvalidInputException(String.format("Sorry, %s are required.", subject));
        }
        return items;
    }
}
