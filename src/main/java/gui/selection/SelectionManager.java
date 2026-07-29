package gui.selection;

import components.Component;

public class SelectionManager {

    private Component selectedComponent;

    public SelectionManager() {
        selectedComponent = null;
    }

    public void select(Component component) {
        selectedComponent = component;
    }

    public void clearSelection() {
        selectedComponent = null;
    }

    public Component getSelectedComponent() {
        return selectedComponent;
    }

    public boolean hasSelection() {
        return selectedComponent != null;
    }

    public boolean isSelected(Component component) {
        return selectedComponent == component;
    }
}