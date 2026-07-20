package circuit;
import components.Component;
import java.util.ArrayList;
import java.util.List;

public class Circuit {
    private final List<Component> components;
    private final List<Net> nets;

    public Circuit(){
        components = new ArrayList<>();
        nets = new ArrayList<>();
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
    public List<Component> getComponents(){
        return components;
    }
    public List<Net> getNets(){
        return nets;
    }
}