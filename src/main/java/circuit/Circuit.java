package circuit;
import components.Component;
import java.util.ArrayList;
import java.util.List;
//test on new git push aftter refactoring and deleting old branch
public class Circuit {
    private final List<Component> components;
    private final List<Net> nets;
    private final List<Wire> wires;

    public Circuit(){
        components = new ArrayList<>();
        nets = new ArrayList<>();
        wires = new ArrayList<>();
    }

    public void addComponent(Component component){
        if(!components.contains(component)){
            components.add(component);
        }
    }
    public void addNet(Net net){
        if(!nets.contains(net)){
            nets.add(net);
        }
    }
    public void addWire(Wire wire){
        wires.add(wire);
    }
    public List<Component> getComponents(){
        return components;
    }
    public List<Net> getNets(){
        return nets;
    }
    public List<Wire> getWires(){
        return wires;
    }
}
