package components;

import circuit.Pin;
import util.Vector2;
import java.util.ArrayList;
import java.util.List;
public abstract class Component {
    private Vector2 position;
    private double rotation;
    private final List<Pin> pins;

    protected Component(Vector2 position){
        this.rotation = 0;
        this.position = position;
        this.pins = new ArrayList<>();
    }

    protected void addPin(Pin pin){
        pins.add(pin);
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

    public List<Pin> getPins(){
        return this.pins;
    }
}