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

import java.util.PriorityQueue;

public class SimulationEngine {

    private static final int MAX_ITERATIONS = 100;

    private long currentTime;

    private final PriorityQueue<SimulationEvent> eventQueue;

    public SimulationEngine(){
        currentTime = 0;
        eventQueue = new PriorityQueue<>();
    }

    public long getCurrentTime(){
        return currentTime;
    }

    public void scheduleEvent(
            long executionTime,
            Net net,
            Voltage voltage){

        if(executionTime < currentTime){
            throw new IllegalArgumentException(
                    "Cannot schedule an event in the past."
            );
        }

        eventQueue.add(
                new SimulationEvent(
                        executionTime,
                        net,
                        voltage
                )
        );
    }

    public void scheduleEvent(
            long delay,
            Net net,
            Voltage voltage,
            boolean relativeToCurrentTime){

        if(!relativeToCurrentTime){
            scheduleEvent(delay, net, voltage);
            return;
        }

        if(delay < 0){
            throw new IllegalArgumentException(
                    "Event delay cannot be negative."
            );
        }

        scheduleEvent(
                currentTime + delay,
                net,
                voltage
        );
    }

    public int getPendingEventCount(){
        return eventQueue.size();
    }

    public void step(long timeStep, Circuit circuit){

        if(timeStep < 0){
            throw new IllegalArgumentException(
                    "Time step cannot be negative."
            );
        }

        currentTime += timeStep;

        processEvents();

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

    public void reset(){
        currentTime = 0;
        eventQueue.clear();
    }

    private void processEvents(){

        while(!eventQueue.isEmpty()
                && eventQueue.peek().getExecutionTime() <= currentTime){

            SimulationEvent event = eventQueue.poll();

            event.apply();
        }
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
                changed |= drivePin(
                        circuit,
                        component.getPins().getFirst(),
                        Voltage.HIGH
                );
            }
            else if(component instanceof GroundNode){
                changed |= drivePin(
                        circuit,
                        component.getPins().getFirst(),
                        Voltage.LOW
                );
            }
            else if(component instanceof InputNode inputNode){
                changed |= drivePin(
                        circuit,
                        inputNode.getPins().getFirst(),
                        inputNode.getVoltage()
                );
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

                if(gate == null
                        || source == null
                        || drain == null){
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

                if(gate == null
                        || source == null
                        || drain == null){
                    continue;
                }

                if(gate.getVoltage() == Voltage.LOW){
                    changed |= connect(source, drain);
                }
            }
        }

        return changed;
    }

    private boolean drivePin(
            Circuit circuit,
            Pin pin,
            Voltage voltage){

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

    private boolean connect(
            Net first,
            Net second){

        Voltage firstVoltage = first.getVoltage();
        Voltage secondVoltage = second.getVoltage();

        if(firstVoltage == secondVoltage){
            return false;
        }

        if(firstVoltage == Voltage.CONFLICT
                || secondVoltage == Voltage.CONFLICT){

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
