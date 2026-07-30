package util;

public class GridSnap {

    private static final double GRID_SIZE = 20;

    public static Vector2 snap(Vector2 position){

        double x =
                Math.round(position.getX() / GRID_SIZE)
                        * GRID_SIZE;

        double y =
                Math.round(position.getY() / GRID_SIZE)
                        * GRID_SIZE;

        return new Vector2(x, y);
    }
}