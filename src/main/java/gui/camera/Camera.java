package gui.camera;

import util.Vector2;

public class Camera {

    private Vector2 center;
    private double zoom;

    public static final double DEFAULT_ZOOM = 1.0;

    private static final double MIN_ZOOM = 0.1;
    private static final double MAX_ZOOM = 10.0;

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
            throw new IllegalArgumentException("Zoom must be positive.");
        }

        this.zoom = Math.max(MIN_ZOOM, Math.min(MAX_ZOOM, zoom));
    }

    public Vector2 worldToScreen(
            Vector2 world,
            double viewportWidth,
            double viewportHeight){

        Vector2 relative = world.subtract(center);
        relative = relative.multiply(zoom);

        return relative.add(new Vector2(
                viewportWidth / 2,
                viewportHeight / 2
        ));
    }

    public Vector2 screenToWorld(
            Vector2 screen,
            double viewportWidth,
            double viewportHeight){

        Vector2 relative = screen.subtract(
                new Vector2(
                        viewportWidth / 2,
                        viewportHeight / 2
                )
        );

        relative = relative.divide(zoom);

        return relative.add(center);
    }

    public void zoom(double factor){
        setZoom(zoom * factor);
    }

    public void zoomAt(
            double factor,
            Vector2 screenPosition,
            double viewportWidth,
            double viewportHeight){

        Vector2 worldPosition = screenToWorld(
                screenPosition,
                viewportWidth,
                viewportHeight
        );

        zoom(factor);

        Vector2 viewportCenter = new Vector2(
                viewportWidth / 2,
                viewportHeight / 2
        );

        Vector2 screenOffset = screenPosition.subtract(viewportCenter);

        center = worldPosition.subtract(
                screenOffset.divide(zoom)
        );
    }
}
