package circuit;

import java.util.ArrayList;
import java.util.List;

public class Net {

    private final List<Pin> pins;
    private final List<Wire> wires;

    public Net(){
        pins = new ArrayList<>();
        wires = new ArrayList<>();
    }

    public void addPin(Pin pin){
        if(pin != null && !pins.contains(pin)){
            pins.add(pin);
        }
    }

    public void removePin(Pin pin){
        pins.remove(pin);
    }

    public boolean containsPin(Pin pin){
        return pins.contains(pin);
    }

    public void addWire(Wire wire){
        if(wire != null && !wires.contains(wire)){
            wires.add(wire);
        }
    }

    public void removeWire(Wire wire){
        wires.remove(wire);
    }

    public boolean containsWire(Wire wire){
        return wires.contains(wire);
    }

    public List<Pin> getPins(){
        return pins;
    }

    public List<Wire> getWires(){
        return wires;
    }

    public boolean isEmpty(){
        return pins.isEmpty() && wires.isEmpty();
    }

    public void merge(Net other){
        for(Pin pin : other.getPins()){
            addPin(pin);
        }

        for(Wire wire : other.getWires()){
            addWire(wire);
        }
    }
    public List<List<Pin>> getConnectedGroups(){
        List<List<Pin>> groups = new ArrayList<>();
        List<Pin> unvisited = new ArrayList<>(pins);

        while(!unvisited.isEmpty()){
            List<Pin> group = new ArrayList<>();
            List<Pin> queue = new ArrayList<>();

            Pin start = unvisited.remove(0);
            queue.add(start);

            while(!queue.isEmpty()){
                Pin current = queue.remove(0);

                if(group.contains(current)){
                    continue;
                }

                group.add(current);
                unvisited.remove(current);

                for(Wire wire : wires){
                    if(wire.getStart().equals(current)){
                        Pin connected = wire.getEnd();

                        if(!group.contains(connected)){
                            queue.add(connected);
                        }
                    }

                    if(wire.getEnd().equals(current)){
                        Pin connected = wire.getStart();

                        if(!group.contains(connected)){
                            queue.add(connected);
                        }
                    }
                }
            }

            groups.add(group);
        }

        return groups;
    }
}