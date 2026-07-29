package circuit;

import components.Component;
import util.Vector2;

public class Pin {
    private final Component owner;
    private final Vector2 localPosition;

    public Pin(Component owner, Vector2 localPosition){
        this.owner = owner;
        this.localPosition = localPosition;
    }

    public Component getOwner(){
        return owner;
    }

    public Vector2 getLocalPosition(){
        return localPosition;
    }

    public Vector2 getWorldPosition(){
        double angle = Math.toRadians(owner.getRotation());

        double rotatedX = localPosition.getX() * Math.cos(angle) - localPosition.getY() * Math.sin(angle);
        double rotatedY = localPosition.getX() * Math.sin(angle) + localPosition.getY() * Math.cos(angle);

        return owner.getPosition().add(new Vector2(rotatedX, rotatedY));
    }
}