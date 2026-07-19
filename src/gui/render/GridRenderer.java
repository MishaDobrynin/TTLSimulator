package gui.render;

import gui.camera.Camera;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import util.Vector2;

public class GridRenderer {
    private final Camera camera; //shared
    private static final double GRID_SPACING = 20;
    private static final double DOT_RADIUS = 2;
    private boolean enabled; //enabled /disabled grid dots

    public GridRenderer(Camera camera){
        this.camera = camera;
        this.enabled = true;
    }

    public void render(GraphicsContext gc, double width, double height){
        if(!enabled){
            return;
        }
        
        gc.setFill(Color.LIGHTGRAY);
        Vector2 worldTopLeft = camera.screenToWorld(
                new Vector2(0, 0), //pixels 0 0
                width,
                height
        );
        Vector2 worldBottomRight = camera.screenToWorld(
                new Vector2(width, height),
                width,
                height
        );

        double startX = Math.floor(worldTopLeft.getX() / GRID_SPACING) * GRID_SPACING;
        double startY = Math.floor(worldTopLeft.getY() / GRID_SPACING) * GRID_SPACING;
        for(double x = startX; x <= worldBottomRight.getX(); x += GRID_SPACING){
            for(double y = startY; y <= worldBottomRight.getY(); y += GRID_SPACING){
                Vector2 screenPosition = camera.worldToScreen(
                        new Vector2(x, y),
                        width,
                        height
                );
                gc.fillOval(
                        screenPosition.getX() - DOT_RADIUS,
                        screenPosition.getY() - DOT_RADIUS,
                        DOT_RADIUS * 2,
                        DOT_RADIUS * 2
                );
            }
        }
    }

    public boolean isEnabled(){
        return enabled;
    }

    public void setEnabled(boolean enabled){
        this.enabled = enabled;
    }
}
