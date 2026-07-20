package components;
import util.Vector2;

public abstract class Component {
    private Vector2 position;
    private double rotation;
    protected Component(Vector2 position){
        this.rotation = 0;
        this.position = position;
    }

    public Vector2 getPosition(){
        return this.position;
    }

    public void setPosition(Vector2 position){
        this.position = position;
    }

    public double getRotation(){
        return this.rotation;
    }

    public void setRotation(double rotation){
        this.rotation = rotation;
    }
}