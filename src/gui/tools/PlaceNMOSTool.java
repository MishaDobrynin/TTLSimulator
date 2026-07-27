package gui.tools;

public class PlaceNMOSTool {
    @Override
    public void mousePressed(
            MouseEvent event,
            CircuitCanvas canvas){

        Vector2 worldPosition =
                canvas.getCamera().screenToWorld(
                        new Vector2(
                                event.getX(),
                                event.getY()
                        ),
                        canvas.getWidth(),
                        canvas.getHeight()
                );

        canvas.getCircuit().addComponent(
                new NMOS(worldPosition)
        );

        canvas.redraw();
    }
}
