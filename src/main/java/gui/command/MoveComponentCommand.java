package gui.command;

import components.Component;
import util.Vector2;

public class MoveComponentCommand implements Command {

    private final Component component;
    private final Vector2 oldPosition;
    private final Vector2 newPosition;

    public MoveComponentCommand(Component component, Vector2 oldPosition, Vector2 newPosition){
        this.component = component;
        this.oldPosition = oldPosition;
        this.newPosition = newPosition;
    }

    @Override
    public void execute(){
        component.setPosition(newPosition);
    }

    @Override
    public void undo(){
        component.setPosition(oldPosition);
    }
}