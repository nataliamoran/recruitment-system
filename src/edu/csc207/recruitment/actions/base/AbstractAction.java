package edu.csc207.recruitment.actions.base;

public abstract class AbstractAction implements Action {
    private final String description;
    private final String shortcut;

    public AbstractAction(final String shortcut, final String description) {
        this.shortcut = shortcut;
        this.description = description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getShortcut() {
        return shortcut;
    }

}
