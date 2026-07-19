package gui.camera;
import util.Vector2;
public class Camera {
    private Vector2 center;
    private double zoom;
    public static final double DEFAULT_ZOOM = 1.0; //zoom default
    
    public Camera(){
        center = new Vector2(0, 0);
        zoom = DEFAULT_ZOOM;
    }
    
    public Vector2 getCenter(){
        return this.center;
    }
    
    public double getZoom(){
        return this.zoom;
    }
    
    public void setCenter(Vector2 center){
        this.center = center;
    }
    
    public void move(double dx, double dy){
        this.center = new Vector2(
                center.getX() + dx,
                center.getY() + dy
        );
    }
    
    public void setZoom(double zoom){
        if(zoom <= 0){
            throw new IllegalArgumentException ("Zoom must be positive.");
        }
        this.zoom = zoom;
    }
    
    public Vector2 worldToScreen(Vector2 world, double viewportWidth, double viewportHeight){
        Vector2 relative = world.subtract(center); //where the item is relative to the center
        relative = relative.multiply(zoom); //account for zooming
        return relative.add(new Vector2(
                viewportWidth/2, //because the center is width/2, height/2
                viewportHeight/2
                )
        );
    }
    
    public Vector2 screenToWorld(Vector2 screen, double viewportWidth, double viewportHeight){ //process mouse click data
        Vector2 relative = screen.subtract(
                new Vector2(
                        viewportWidth/2,
                        viewportHeight/2
                )
        );
        relative = relative.divide(zoom);
        return relative.add(center);
    }
}
