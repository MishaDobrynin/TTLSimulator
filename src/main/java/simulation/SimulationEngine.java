package simulation;

import circuit.Circuit;
import circuit.Net;
import circuit.Pin;
import components.Component;
import components.GroundNode;
import components.InputNode;
import components.NMOS;
import components.PMOS;
import components.PowerNode;

public class SimulationEngine {
    private static final int MAX_ITERATIONS = 100;

    private long currentTime;


    public SimulationEngine(){
        currentTime = 0;
    }


    public long getCurrentTime(){
        return currentTime;
    }


    public void step(long timeStep, Circuit circuit){
        currentTime += timeStep;

        simulate(circuit);
    }


    public void simulate(Circuit circuit){
        resetVoltages(circuit);

        boolean changed;
        int iterations = 0;

        do{
            changed = false;

            changed |= driveSources(circuit);
            changed |= propagateNMOS(circuit);
            changed |= propagatePMOS(circuit);

            iterations++;

        }while(changed && iterations < MAX_ITERATIONS);
    }


    private void resetVoltages(Circuit circuit){
        for(Net net : circuit.getNets()){
            net.setVoltage(Voltage.FLOATING);
        }
    }


    private boolean driveSources(Circuit circuit){
        boolean changed = false;

        for(Component component : circuit.getComponents()){

            if(component instanceof PowerNode){
                changed |= drivePin(circuit, component.getPins().getFirst(), Voltage.HIGH);
            }
            else if(component instanceof GroundNode){
                changed |= drivePin(circuit, component.getPins().getFirst(), Voltage.LOW);
            }
            else if(component instanceof InputNode inputNode){
                changed |= drivePin(circuit, inputNode.getPins().getFirst(), inputNode.getVoltage());
            }
        }

        return changed;
    }


    private boolean propagateNMOS(Circuit circuit){
        boolean changed = false;

        for(Component component : circuit.getComponents()){

            if(component instanceof NMOS nmos){

                Net gate = circuit.getNet(nmos.getGate());
                Net source = circuit.getNet(nmos.getSource());
                Net drain = circuit.getNet(nmos.getDrain());

                if(gate == null || source == null || drain == null){
                    continue;
                }

                if(gate.getVoltage() == Voltage.HIGH){
                    changed |= connect(source, drain);
                }
            }
        }

        return changed;
    }


    private boolean propagatePMOS(Circuit circuit){
        boolean changed = false;

        for(Component component : circuit.getComponents()){

            if(component instanceof PMOS pmos){

                Net gate = circuit.getNet(pmos.getGate());
                Net source = circuit.getNet(pmos.getSource());
                Net drain = circuit.getNet(pmos.getDrain());

                if(gate == null || source == null || drain == null){
                    continue;
                }

                if(gate.getVoltage() == Voltage.LOW){
                    changed |= connect(source, drain);
                }
            }
        }

        return changed;
    }


    private boolean drivePin(Circuit circuit, Pin pin, Voltage voltage){
        Net net = circuit.getNet(pin);

        if(net == null){
            return false;
        }

        Voltage current = net.getVoltage();

        if(current == Voltage.FLOATING){
            net.setVoltage(voltage);
            return true;
        }

        if(current != voltage){
            net.setVoltage(Voltage.CONFLICT);
            return true;
        }

        return false;
    }


    private boolean connect(Net first, Net second){
        Voltage firstVoltage = first.getVoltage();
        Voltage secondVoltage = second.getVoltage();

        if(firstVoltage == secondVoltage){
            return false;
        }

        if(firstVoltage == Voltage.CONFLICT || secondVoltage == Voltage.CONFLICT){

            if(firstVoltage != Voltage.CONFLICT){
                first.setVoltage(Voltage.CONFLICT);
            }

            if(secondVoltage != Voltage.CONFLICT){
                second.setVoltage(Voltage.CONFLICT);
            }

            return true;
        }

        if(firstVoltage == Voltage.FLOATING){
            first.setVoltage(secondVoltage);
            return true;
        }

        if(secondVoltage == Voltage.FLOATING){
            second.setVoltage(firstVoltage);
            return true;
        }

        first.setVoltage(Voltage.CONFLICT);
        second.setVoltage(Voltage.CONFLICT);

        return true;
    }
}
