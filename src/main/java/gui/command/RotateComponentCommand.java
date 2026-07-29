package gui.command;

import components.Component;

public class RotateComponentCommand implements Command {

    private final Component component;
    private final double oldRotation;
    private final double newRotation;

    public RotateComponentCommand(Component component, double oldRotation, double newRotation){
        this.component = component;
        this.oldRotation = oldRotation;
        this.newRotation = newRotation;
    }

    @Override
    public void execute(){
        component.setRotation(newRotation);
    }

    @Override
    public void undo(){
        component.setRotation(oldRotation);
    }
}