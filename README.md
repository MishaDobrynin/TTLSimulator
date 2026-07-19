# Project Structure

The project is organized into several packages, each responsible for a distinct part of the simulator. 
Architecture is designed to separate all components (graphical representation, circuit logic, simulation behavior, and utility functions)
so that each system can be developed and tested independently.
src
- circuit pkg
  - Circuit.java
  - Net.java
  - Pin.java
  - Wire.java
- components pkg
  - Component.java
  - GroundNode.java
  - InputNode.java
  - NMOS.java
  - PMOS.java
  - PowerNode.java
- gui pkg
  - CircuitCanvas.java
  - MainWindow.java
  - camera pkg
    - Camera.java
  - render pkg
    - ComponentRenderer.java
    - GridRenderer.java
    - WireRenderer.java
  - selection pkg
    - SelectionManager.java
  - tools pkg
    - SelectTool.java
    - Tool.java
    - ToolManager.java
    - WireTool.java
- simulation pkg
  - SimulationEngine.java
  - VoltageSolver.java
- simulator pkg
  - Main.java
- util
  - Vector2.java

## circuit

Logical representation of electrical circuits.
This package manages the relationships between components, including connections, wires, and circuit structure. 
Separate from the graphical structure so that circuits exist independently from their appearance.

## components

Individual electrical components that can exist within a circuit.
Includes transistors, logic gates, and other circuit elements. 
Each component is created to have its own behavior while interacting with the larger circuit system.

## gui

Contains all JavaFX-based graphical interface classes.
This package manages:
- application windows,
- circuit visualization,
- user interaction,
- graphical rendering.
GUI is kept separate from the simulation logic to allow the core circuit system to operate independently.

## simulation

Systems responsible for simulating circuit behavior.
This package will handle (on full implementation):
- voltage propagation,
- component state changes,
- logic evaluation,
- execution of simulated circuits.

## simulator

Application initialization.
Launches the simulator and creates the application environment.

## util

Reusable utility classes used throughout the project.
Independent of the simulator itself and are designed to be reusable across multiple systems.
Examples include:
- mathematical structures,
  - Vector2
- coordinate systems,
- helper functions.
  - Also Vector2


------
# Class Specifications



## Camera

**Package:** `gui.camera`

**Purpose:**

`Camera` is an abstraction of the user's view of the circuit workspace.
Manages the relationship between the simulator's world coordinates and the graphical screen coordinates.
Camera does not directly control rendering or user input. 
- Stores view information and performs coordinate transformations required by the graphical systems.

---

**Design:**

`Camera` separates the circuit's coordinate system from the screen's coordinate system.
Objects in the simulator exist in world space, independent of the window size or zoom level. 
Before being displayed, their positions are transformed into screen coordinates through Camera.

This allows:
- circuits to exist independently of the GUI,
- zooming without modifying object positions,
- future panning and navigation features.

---

**Implemented Features:**

### Camera Information
- Store camera center position
- Store zoom level
- Retrieve camera position
- Retrieve zoom level

### Camera Movement
- Move the camera by specified x and y offset
- Update camera center position

### Zoom Control
- Set zoom level
- Validate that zoom values remain positive

### Coordinate Transformations
- Convert world coordinates into screen coordinates
- Convert screen coordinates back into world coordinates

### Coordinate Processing
- Account for:
  - camera position,
  - zoom scaling,
  - viewport dimensions

---

## GridRenderer

**Package:** `gui.render`

**Purpose:**

`GridRenderer` renders the visual grid of the circuit workspace.
The grid consists of fixed world-space points that are transformed into screen-space coordinates through the `Camera`. 
This ensures that grid positions remain constant while allowing the user view to change through zooming and future camera movement.

---

**Design:**

`GridRenderer` only handles visualization of the grid.
Movement, user interaction, and snapping are not its responsibilities.
Grid points are generated in world coordinates based on the currently visible area.
Before rendering, these points are converted into screen coordinates using the shared `Camera` instance.
Creates consistent grid behavior during zooming.

---

**Implemented Features:**

### Grid Rendering
- Generate visible grid points based on world coordinates
- Render grid points onto a JavaFX `GraphicsContext`
- Maintain constant world-space grid spacing

### Camera Integration
- Convert screen boundaries into world coordinates
- Convert grid positions from world space into screen space
- Account for camera zoom and position

### Grid Control
- Enable or disable grid rendering
- Configure grid spacing and dot size

---

## MainWindow

**Package:** `gui`

**Purpose:**

`MainWindow` is responsible for creating and managing the primary application window.
Initializes the JavaFX environment, creates the main layout, and connects major GUI systems. 

---

**Design:**

`MainWindow` is the top-level organizer of the GUI.
`MainWindow` creates shared objects instead of allowing subordinate components to create their own dependencies
This ensures that major GUI components share consistent state and remain independently testable.

---

**Implemented Features:**

### Window Management
- Create JavaFX stage and scene
- Set application title
- Define default window dimensions
- Set minimum window dimensions
- Display the application window

### GUI Initialization
- Create the main layout container
- Initialize the `Camera`
- Initialize the `CircuitCanvas`
- Connect the circuit workspace to the application window
- Provides the canvas with access to the camera system.


### Layout Management
- Use `BorderPane` as the primary layout system
- Place the circuit workspace in the center of the window

---

## CircuitCanvas

**Package:** `gui`

**Purpose:**

`CircuitCanvas` is the primary workspace container for displaying and interacting with the circuit simulation.
Orovides the JavaFX canvas environment with circuit elements, grids, wires, and other visual component positions to be rendered.

---

**Design:**

`CircuitCanvas` separates the graphical workspace from individual rendering systems.
`CircuitCanvas` itself does not directly draw circuit elements. 
Rendering responsibilities are delegated to specialized renderer classes, allowing different visual systems to be developed independently.
i.e. `GridRenderer` handles grid visualization exclusively
`CircuitCanvas` receives a shared `Camera` instance to ensure all rendering systems use consistent coordinate transforms.

---

**Implemented Features:**

### Canvas Management
- Create JavaFX canvas for rendering
- Bind canvas dimensions to the workspace size
- Automatically resize with the application window

### Rendering System Integration
- Store reference to the shared camera
- Initialize rendering systems
- Connect renderer classes to the workspace

### GUI Structure
- Extends JavaFX `Pane`
- Serve as the central container for visual circuit elements

---

## Vector2

**Package:** `util`

**Purpose:**

`Vector2` is an immutable two-dimensional vector class for representing positions, directions, and other geometric quantities.
The class provides a common mathematical foundation for systems that require coordinate manipulation.
Includes camera transformations, rendering, and future circuit placement.

---

**Design:**

`Vector2` instances are immutable for consistency purposes and to avoid confusion between variables. 
Instead of modifying existing vectors, operations create and return new `Vector2` objects.

---

**Implemented Features:**

### Vector Constants
- `ZERO` — for `(0,0)`
- `UNIT_X` — for `(1,0)`
- `UNIT_Y` — for `(0,1)`

### Accessors
- Retrieve x-coordinate
- Retrieve y-coordinate

### Arithmetic Operations
- Vector addition
- Vector subtraction
- Scalar multiplication
- Scalar division

### Mathematical Operations
- Dot product
- Vector length
- Squared vector length
- Distance between vectors
- Squared distance between vectors
- Vector normalization
- Vector negation

### Utility Operations
- Create modified copies with updated x or y values
- Compare vectors for equality
- Generate hash values
- Convert vectors to readable string representations

---
