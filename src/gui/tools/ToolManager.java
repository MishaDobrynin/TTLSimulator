package gui.tools;

import gui.CircuitCanvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * Manages the currently active editor tool.
 *
 * All user input is forwarded to the active Tool.
 */
public class ToolManager {
    private Tool currentTool;

    /**
     * Creates a ToolManager with no active tool.
     */
    public ToolManager() {
        currentTool = null;
    }

    /**
     * Returns the currently active tool.
     */
    public Tool getCurrentTool() {
        return currentTool;
    }

    /**
     * Changes the active tool.
     */
    public void setCurrentTool(Tool tool, CircuitCanvas canvas) {

        if (currentTool != null) {
            currentTool.onDeselected(canvas);
        }

        currentTool = tool;

        if (currentTool != null) {
            currentTool.onSelected(canvas);
        }
    }

    /**
     * Forwards a mouse press.
     */
    public void mousePressed(MouseEvent event, CircuitCanvas canvas) {
        if (currentTool != null) {
            currentTool.mousePressed(event, canvas);
        }
    }

    /**
     * Forwards a mouse release.
     */
    public void mouseReleased(MouseEvent event, CircuitCanvas canvas) {
        if (currentTool != null) {
            currentTool.mouseReleased(event, canvas);
        }
    }

    /**
     * Forwards a mouse drag.
     */
    public void mouseDragged(MouseEvent event, CircuitCanvas canvas) {
        if (currentTool != null) {
            currentTool.mouseDragged(event, canvas);
        }
    }

    /**
     * Forwards mouse movement.
     */
    public void mouseMoved(MouseEvent event, CircuitCanvas canvas) {
        if (currentTool != null) {
            currentTool.mouseMoved(event, canvas);
        }
    }

    /**
     * Forwards a key press.
     */
    public void keyPressed(KeyEvent event, CircuitCanvas canvas) {
        if (currentTool != null) {
            currentTool.keyPressed(event, canvas);
        }
    }

    /**
     * Forwards a key release.
     */
    public void keyReleased(KeyEvent event, CircuitCanvas canvas) {
        if (currentTool != null) {
            currentTool.keyReleased(event, canvas);
        }
    }
}
