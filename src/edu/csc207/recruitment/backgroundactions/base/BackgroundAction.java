package edu.csc207.recruitment.backgroundactions.base;

/**
 * An interface which all background actions should implement
 */
public interface BackgroundAction {
    /**
     * Method that performs the background action
     * after insuring that all parameters for the background action are set properly.
     * If parameters are invalid, then throws a relevant exception.
     */
    void performAction();
}
