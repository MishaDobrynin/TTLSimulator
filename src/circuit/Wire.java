package circuit;
import util.Vector2;

public class Wire {
    private final Pin start;
    private final Pin end;

    public Wire(Pin start, Pin end){
        this.start = start;
        this.end = end;
    }

    public Pin getStart(){
        return this.start;
    }

    public Pin getEnd(){
        return this.end;
    }

    public Vector2 getStartPosition(){
        return start.getWorldPosition();
    }
    public Vector2 getEndPosition(){
        return end.getWorldPosition();
    }
}