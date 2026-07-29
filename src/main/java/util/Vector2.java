package util;
public class Vector2 {
    public static final Vector2 ZERO = new Vector2(0, 0);
    public static final Vector2 UNIT_X = new Vector2(1, 0);
    public static final Vector2 UNIT_Y = new Vector2(0, 1);
    private final double x;
    private final double y;
    public Vector2(double x, double y){
        this.x = x;
        this.y = y;
    }
    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }
    public Vector2 add(Vector2 inp){
        return new Vector2(this.x+inp.x, this.y+inp.y);

    }
    public Vector2 subtract(Vector2 inp){
        return new Vector2(this.x-inp.x, this.y-inp.y);
    }
    public Vector2 multiply(double inp){
        return new Vector2(this.x*inp, this.y*inp);
    }
    public Vector2 divide(double inp){
        //why would you even call this
        //except to normalize a vector
        if(inp == 0){
            throw new IllegalArgumentException("Cannot divide by zero.");
        }
        return new Vector2(this.x/inp, this.y/inp);
    }
    public double length(){
        return Math.sqrt(lengthSquared());
    }
    public double lengthSquared(){
        return dot(this);
    }
    public double dot(Vector2 inp){
        return this.x*inp.x + this.y*inp.y;
    }
    public double distance(Vector2 inp){
        return subtract(inp).length();
    }
    public double distanceSquared(Vector2 inp){
        return subtract(inp).lengthSquared();
    }
    public Vector2 normalize(){
        double len = this.length(); //optimization yay
        if(len==0){
            throw new IllegalStateException("Cannot normalize zero vector.");
        }
        return divide(len);
    }
    public Vector2 negate(){
        return new Vector2(-x, -y);
    }
    public Vector2 updateX(double x){
        return new Vector2(x, this.y);
    }
    public Vector2 updateY(double y){
        return new Vector2(this.x, y);
    }
    @Override //don't overload
    public boolean equals(Object ob){
        if(this==ob)return true;
        if(!(ob instanceof Vector2)){
            return false;
        }
        Vector2 other = (Vector2) ob;
        return Double.compare(this.x, other.x) ==0 && Double.compare(this.y, other.y)==0;
    }
    @Override
    public int hashCode(){
        return java.util.Objects.hash(this.x, this.y);
    }
    @Override
    public String toString(){
        return "Vector2("+x+", "+y+")";
    }
}
