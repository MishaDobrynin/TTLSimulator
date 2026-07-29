package gui.selection;

import components.Component;

/**
 * Stores the current editor selection.
 *
 * SelectionManager is responsible only for remembering
 * what object is selected. It does not determine what
 * should be selected or how selection is rendered.
 */
public class SelectionManager {
    private Component selectedComponent;

    /**
     * Creates an empty selection.
     */
    public SelectionManager() {
        selectedComponent = null;
    }

    /**
     * Selects a component.
     */
    public void select(Component component) {
        selectedComponent = component;
    }

    /**
     * Clears the current selection.
     */
    public void clearSelection() {
        selectedComponent = null;
    }

    /**
     * Returns the selected component.
     */
    public Component getSelectedComponent() {
        return selectedComponent;
    }

    /**
     * Returns whether anything is selected.
     */
    public boolean hasSelection() {
        return selectedComponent != null;
    }

    /**
     * Returns true if the given component is selected.
     */
    public boolean isSelected(Component component) {
        return selectedComponent == component;
    }
}
