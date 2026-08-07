package simulation;

import circuit.Net;

public class SimulationEvent implements Comparable<SimulationEvent> {

    private final long executionTime;
    private final Net net;
    private final Voltage voltage;

    public SimulationEvent(long executionTime, Net net, Voltage voltage){
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
        return Long.compare(executionTime, other.executionTime);
    }
}1
