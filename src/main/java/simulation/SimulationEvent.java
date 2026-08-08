package simulation;

import circuit.Net;

public class SimulationEvent implements Comparable<SimulationEvent> {

    private final long executionTime;
    private final Net net;
    private final Voltage voltage;

    public SimulationEvent(
            long executionTime,
            Net net,
            Voltage voltage){

        if(executionTime < 0){
            throw new IllegalArgumentException(
                    "Execution time cannot be negative."
            );
        }

        if(net == null){
            throw new IllegalArgumentException(
                    "Event net cannot be null."
            );
        }

        if(voltage == null){
            throw new IllegalArgumentException(
                    "Event voltage cannot be null."
            );
        }

        this.executionTime = executionTime;
        this.net = net;
        this.voltage = voltage;
    }

    public long getExecutionTime(){
        return executionTime;
    }

    public Net getNet(){
        return net;
    }

    public Voltage getVoltage(){
        return voltage;
    }

    public void apply(){
        net.setVoltage(voltage);
    }

    @Override
    public int compareTo(SimulationEvent other){

        if(other == null){
            throw new NullPointerException(
                    "Cannot compare a simulation event to null."
            );
        }

        return Long.compare(
                executionTime,
                other.executionTime
        );
    }
}
