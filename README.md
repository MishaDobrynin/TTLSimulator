# TTLSimulator

A Java-based circuit simulator designed to explore digital logic, transistor-level circuits, and computer architecture.

The project is structured to separate:
- circuit logic,
- graphical representation,
- simulation behavior,
- and reusable utility systems.

This separation allows each system to be developed, tested, and expanded independently.

---

# Project Structure

src

- circuit
    - Circuit.java
    - Net.java
    - Pin.java
    - Wire.java

- components
    - Component.java
    - GroundNode.java
    - InputNode.java
    - NMOS.java
    - PMOS.java
    - PowerNode.java

- gui
    - CircuitCanvas.java
    - MainWindow.java
    - camera
        - Camera.java
    - render
        - ComponentRenderer.java
        - GridRenderer.java
        - WireRenderer.java
    - selection
        - SelectionManager.java
    - tools
        - SelectTool.java
        - Tool.java
        - ToolManager.java
        - WireTool.java

- simulation
    - SimulationEngine.java
    - VoltageSolver.java

- simulator
    - Main.java

- util
    - Vector2.java


---

# Classes

# simulator

## Main

**Package:** `simulator`

**Purpose:**

Entry point of the application.

**Implemented Features:**

- Starts the JavaFX application
- Creates the main application window
- Initializes the program environment

---

# gui

## MainWindow

**Package:** `gui`

**Purpose:**

Manages the primary application window and connects major GUI systems.

**Implemented Features:**

- Creates JavaFX stage and scene
- Sets window title and dimensions
- Initializes the Camera
- Creates the CircuitCanvas
- Connects the workspace to the application window

**Design:**

MainWindow creates shared objects and passes them into other systems instead of allowing each class to create its own dependencies.

This ensures that systems such as rendering and coordinate transformation share consistent state.

---

## CircuitCanvas

**Package:** `gui`

**Purpose:**

Provides the main workspace where circuits and graphical elements will be displayed.

**Implemented Features:**

- Creates the JavaFX drawing canvas
- Automatically resizes with the window
- Stores the shared Camera
- Connects rendering systems
- Manages redraw operations

**Design:**

CircuitCanvas does not directly draw circuit objects.

Rendering is delegated to specialized renderer classes:
- GridRenderer
- ComponentRenderer
- WireRenderer

This keeps the graphical workspace separate from individual rendering systems.

---

# gui.camera

## Camera

**Package:** `gui.camera`

**Purpose:**

Manages the relationship between world coordinates and screen coordinates.

The Camera represents the user's view of the circuit workspace.

**Implemented Features:**

### Camera Information
- Store camera center position
- Store zoom level
- Retrieve camera information

### Movement
- Move camera position

### Zoom
- Modify zoom level
- Validate zoom values

### Coordinate Conversion
- Convert world coordinates to screen coordinates
- Convert screen coordinates to world coordinates

**Design:**

Objects in the simulator exist in world space and are independent from the display window.

The Camera transforms these positions for rendering, allowing:
- zooming,
- future panning,
- consistent coordinate systems.

---

# gui.render

## GridRenderer

**Package:** `gui.render`

**Purpose:**

Renders the workspace grid.

**Implemented Features:**

- Generates grid points in world coordinates
- Converts grid positions into screen coordinates
- Draws grid points using JavaFX GraphicsContext
- Supports enabling and disabling the grid

**Design:**

The grid exists in world space rather than screen space.

This allows:
- consistent grid positions,
- correct zoom behavior,
- future snapping functionality.

---

## ComponentRenderer

**Package:** `gui.render`

**Purpose:**

Placeholder for rendering circuit components.

**Planned Features:**

- Render component graphics
- Display component states
- Handle component visualization

---

## WireRenderer

**Package:** `gui.render`

**Purpose:**

Placeholder for rendering electrical connections.

**Planned Features:**

- Draw wires between pins
- Display connections between components

---

# components

## Component

**Package:** `components`

**Purpose:**

Abstract base class for all electrical components.

**Implemented Features:**

- Store component position
- Store component rotation
- Retrieve and modify spatial information

**Design:**

Component only stores information shared by all circuit elements.

Rendering and simulation behavior are handled by separate systems.

---

## PowerNode

**Package:** `components`

**Purpose:**

Placeholder for a positive voltage source.

---

## GroundNode

**Package:** `components`

**Purpose:**

Placeholder for circuit ground reference.

---

## InputNode

**Package:** `components`

**Purpose:**

Placeholder for user-controlled circuit inputs.

---

## NMOS

**Package:** `components`

**Purpose:**

Placeholder for N-channel MOSFET implementation.

---

## PMOS

**Package:** `components`

**Purpose:**

Placeholder for P-channel MOSFET implementation.

---

# circuit

## Pin

**Package:** `circuit`

**Purpose:**

Represents an electrical connection point belonging to a component.

**Implemented Features:**

- Stores owning component
- Stores position relative to component
- Calculates world position

**Design:**

Pins store local coordinates instead of absolute coordinates.

This allows pins to automatically move with their component while maintaining their relative location.

---

## Circuit

**Package:** `circuit`

**Purpose:**

Placeholder for the main circuit container.

**Planned Features:**

- Store components
- Store wires
- Manage circuit structure

---

## Net

**Package:** `circuit`

**Purpose:**

Placeholder for electrical networks connecting multiple pins.

---

## Wire

**Package:** `circuit`

**Purpose:**

Placeholder for physical connections between pins.

---

# simulation

## SimulationEngine

**Package:** `simulation`

**Purpose:**

Placeholder for circuit simulation execution.

---

## VoltageSolver

**Package:** `simulation`

**Purpose:**

Placeholder for solving voltage states throughout circuits.

---

# util

## Vector2

**Package:** `util`

**Purpose:**

Immutable two-dimensional vector class used throughout the simulator.

**Implemented Features:**

- Store x and y coordinates
- Vector addition
- Vector subtraction
- Scalar multiplication
- Scalar division
- Dot product
- Length calculations
- Distance calculations
- Normalization
- Negation
- Coordinate updates
- Equality comparison
- String representation

**Design:**

Vector2 objects are immutable.

Operations return new Vector2 objects rather than modifying existing ones, preventing unexpected changes between systems.

Vector2 is used for:
- positions,
- movement,
- camera transformations,
- rendering calculations.

---

# Development Philosophy

The simulator follows a modular architecture.

Major principles:

- Each class has a single responsibility
- Circuit logic remains separate from graphics
- Rendering remains separate from simulation
- Components remain reusable
- Systems communicate through clear interfaces

The goal is to create a scalable foundation for exploring digital logic, transistor circuits, and computer architecture.