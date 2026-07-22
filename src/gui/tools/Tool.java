package gui.tools;

import gui.CircuitCanvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * Base class for all editor tools.
 *
 * A Tool receives user input from the CircuitCanvas and performs
 * actions such as selecting, wiring, moving components, or placing
 * new objects.
 *
 * The currently active Tool is managed by ToolManager.
 */
public abstract class Tool {
    /**
     * Called when the tool becomes active.
     */
    public void onSelected(CircuitCanvas canvas) {

    }

    /**
     * Called when another tool replaces this one.
     */
    public void onDeselected(CircuitCanvas canvas) {

    }

    /**
     * Mouse pressed.
     */
    public void mousePressed(MouseEvent event, CircuitCanvas canvas) {

    }

    /**
     * Mouse released.
     */
    public void mouseReleased(MouseEvent event, CircuitCanvas canvas) {

    }

    /**
     * Mouse dragged.
     */
    public void mouseDragged(MouseEvent event, CircuitCanvas canvas) {

    }

    /**
     * Mouse moved.
     */
    public void mouseMoved(MouseEvent event, CircuitCanvas canvas) {

    }

    /**
     * Key pressed.
     */
    public void keyPressed(KeyEvent event, CircuitCanvas canvas) {

    }

    /**
     * Key released.
     */
    public void keyReleased(KeyEvent event, CircuitCanvas canvas) {

    }
}
