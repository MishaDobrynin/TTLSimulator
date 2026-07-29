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
        return this.owner;
    }

    public Vector2 getLocalPosition(){
        return this.localPosition;
    }

    public Vector2 getWorldPosition(){
        return owner.getPosition().add(localPosition);
    }

}