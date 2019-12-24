package edu.csc207.recruitment.managers;

import edu.csc207.recruitment.actions.base.Action;
import edu.csc207.recruitment.backgroundactions.base.BackgroundAction;
import edu.csc207.recruitment.exceptions.base.ActionWithShortcutAlreadyExists;
import edu.csc207.recruitment.exceptions.base.RecruitmentSystemException;
import edu.csc207.recruitment.storage.SaveAndLoad;
import edu.csc207.recruitment.tools.ConsoleColors;
import edu.csc207.recruitment.tools.InputHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class AbstractManager implements Manager {
    protected final List<BackgroundAction> backgroundActions = new ArrayList<>();
    protected boolean printExitOption = true;
    private final Map<String, Action> userOptions = new HashMap<>();
    private final static String USER_INPUT_EXIT = "Exit";

    /**
     * Performs background actions
     * Gets commands from the user
     * until the user inputs the exit message
     */
    public void manage() {
        String userInput = null;
        for (BackgroundAction backgroundAction : backgroundActions) {
            backgroundAction.performAction();
        }
        while (!USER_INPUT_EXIT.equalsIgnoreCase(userInput)) {
            userInput = getUserMenuOption();
            performUserAction(userInput);
        }
        SaveAndLoad.saveProgram();
    }


    /**
     * Collect parameters for the action and perform the action based on choice made in manage
     * If the choice does not exist, it will be ignored
     * If exception is thrown, message will be printed
     *
     * @param choice: the action the user selected
     */
    private void performUserAction(String choice) {
        Action action = userOptions.get(choice);
        if (action == null) {
            if (!choice.equalsIgnoreCase("Exit")) {
                System.out.println(String.format("Choice '%s' does not exist, please try again", choice));
            }
            return;
        }
        try {
            action.collectParameters();
            action.performAction();
        } catch (RecruitmentSystemException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Print a menu with all available options and get choice
     *
     * @return the selected choice from the user
     */
    private String getUserMenuOption() {
        System.out.println();
        for (Action action : userOptions.values()) {
            System.out.println(String.format(" %s%s, %spress %s.",
                    ConsoleColors.BLUE, action.getDescription(),
                    ConsoleColors.RED_BRIGHT, action.getShortcut()));
        }
        if (printExitOption) {
            System.out.println(String.format(" %sTo exit, %s type %s",
                    ConsoleColors.BLUE, ConsoleColors.RED_BRIGHT, USER_INPUT_EXIT));
        }
        System.out.println(ConsoleColors.YELLOW_BRIGHT);
        String userInput = InputHandler.nextLine();
        System.out.println("You selected " + userInput + System.lineSeparator());
        return userInput;
    }

    /**
     * Method to check shortcut duplication and to add a new action to userOptions
     * A new action is added to userOptions only if there is no other action with the same shortcut
     *
     * @param action action to be added to userOptions
     */
    protected void addActionToUserOption(Action action) {
        if (userOptions.containsKey(action.getShortcut())) {
            throw new ActionWithShortcutAlreadyExists(
                    String.format("Action '%s' can't be added because action with shortcut '%s' already exist: %s",
                            action.getClass(),
                            action.getShortcut(),
                            userOptions.get(action.getShortcut()).getClass()));
        }
        userOptions.put(action.getShortcut(), action);
    }

}


