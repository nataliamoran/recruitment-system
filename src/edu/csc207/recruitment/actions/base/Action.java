package edu.csc207.recruitment.actions.base;

/**
 * Action - Main interface for actions the program can do
 */
public interface Action {
    /**
     * Collect all relevant parameters for this action from user input
     */
    void collectParameters();

    /**
     * Method that performs the actual action
     * after insuring that all parameters for the action are set properly.
     * If parameters are invalid, then throws a relevant exception.
     */
    void performAction();

    /**
     * Returns a user-friendly description of this action
     *
     * @return action description
     */
    String getDescription();

    /**
     * Returns a shortcut key that a user needs  to click to trigger the action
     *
     * @return shortcut key
     */
    String getShortcut();
}
