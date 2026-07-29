package gui.tools;

import components.Component;
import util.Vector2;

public interface ComponentFactory {
    Component create(Vector2 position);
}