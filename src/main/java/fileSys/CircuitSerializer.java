package fileSys;

import circuit.*;
import components.Component;
import util.Vector2;

import java.io.FileWriter;
import java.io.IOException;

public class CircuitSerializer {
    public static void save(Circuit circuit, String path) throws IOException {
        FileWriter writer = new FileWriter(path);

        writer.write("{\n");

        writeComponents(writer, circuit);

        writer.write(",\n");

        writeNets(writer, circuit);

        writer.write(",\n");

        writeWires(writer, circuit);

        writer.write("\n}");

        writer.close();
    }

    private static void writeComponents(FileWriter writer, Circuit circuit) throws IOException {
        writer.write("\"components\": [\n");

        for(int i = 0; i < circuit.getComponents().size(); i++){
            Component component = circuit.getComponents().get(i);
            Vector2 pos = component.getPosition();

            writer.write("{\n");

            writer.write("\"type\":\"" + component.getID() + "\",\n");
            writer.write("\"x\":" + pos.getX() + ",\n");
            writer.write("\"y\":" + pos.getY() + ",\n");
            writer.write("\"rotation\":" + component.getRotation());

            writer.write("\n}");

            if(i < circuit.getComponents().size()-1){
                writer.write(",");
            }
            writer.write("\n");
        }
        writer.write("]");
    }

    private static void writeNets(FileWriter writer, Circuit circuit) throws IOException {
        writer.write("\"nets\": [\n");

        for(int i = 0; i < circuit.getNets().size(); i++){
            Net net = circuit.getNets().get(i);

            writer.write("{\n");
            writer.write("\"pins\": [\n");

            for(int j = 0; j < net.getPins().size(); j++){

                writePin(writer, circuit, net.getPins().get(j));

                if(j < net.getPins().size()-1){
                    writer.write(",");
                }

                writer.write("\n");
            }
            writer.write("]\n}");

            if(i < circuit.getNets().size()-1){
                writer.write(",");
            }
            writer.write("\n");
        }
        writer.write("]");
    }

    private static void writeWires(FileWriter writer, Circuit circuit) throws IOException {
        writer.write("\"wires\": [\n");

        for(int i = 0; i < circuit.getWires().size(); i++){
            Wire wire = circuit.getWires().get(i);

            writer.write("{\n");

            writer.write("\"start\":");
            writePin(writer, circuit, wire.getStart());

            writer.write(",\n");

            writer.write("\"end\":");
            writePin(writer, circuit, wire.getEnd());

            writer.write("\n}");

            if(i < circuit.getWires().size()-1){
                writer.write(",");
            }
            writer.write("\n");
        }
        writer.write("]");
    }

    private static void writePin(FileWriter writer, Circuit circuit, Pin pin) throws IOException {
        int componentIndex = circuit.getComponents().indexOf(pin.getOwner());
        int pinIndex = pin.getOwner().getPins().indexOf(pin);
        writer.write("{");
        writer.write("\"component\":" + componentIndex + ",");
        writer.write("\"pin\":" + pinIndex);
        writer.write("}");
    }
}