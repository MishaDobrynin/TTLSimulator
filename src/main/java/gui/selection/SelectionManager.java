package gui.selection;

import components.Component;
import circuit.Wire;

public class SelectionManager {

    private Component selectedComponent;
    private Wire selectedWire;

    public SelectionManager(){
        selectedComponent = null;
        selectedWire = null;
    }

    public void select(Component component){
        selectedComponent = component;
        selectedWire = null;
    }

    public void select(Wire wire){
        selectedWire = wire;
        selectedComponent = null;
    }

    public void clearSelection(){
        selectedComponent = null;
        selectedWire = null;
    }

    public Component getSelectedComponent(){
        return selectedComponent;
    }

    public Wire getSelectedWire(){
        return selectedWire;
    }

    public boolean hasSelection(){
        return selectedComponent != null
                || selectedWire != null;
    }

    public boolean hasComponentSelection(){
        return selectedComponent != null;
    }

    public boolean hasWireSelection(){
        return selectedWire != null;
    }

    public boolean isSelected(Component component){
        return selectedComponent == component;
    }

    public boolean isSelected(Wire wire){
        return selectedWire == wire;
    }
}