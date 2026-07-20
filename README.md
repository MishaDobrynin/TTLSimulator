# TTLSimulator

A Java-based circuit simulator designed to explore digital logic, transistor-level circuits, and computer architecture.

The project is structured to separate:

- circuit logic,
- graphical representation,
- simulation behavior,
- reusable utility systems.

This allows each system to be developed and tested independently.

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
    - InputNode.java
    - PowerNode.java
    - GroundNode.java
    - Transistor.java
    - NMOS.java
    - PMOS.java

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

# simulator

## Main

**Package:** `simulator`

**Purpose:**

Application entry point.

**Implemented Features:**

- Starts JavaFX
- Creates the main window
- Initializes the application

---

# gui

Contains all graphical interface systems.

---

## MainWindow

**Package:** `gui`

**Purpose:**

Creates and manages the main application window.

**Implemented Features:**

- Creates JavaFX stage and scene
- Sets window size and title
- Creates the Camera
- Creates the CircuitCanvas
- Connects GUI systems

**Design:**

MainWindow creates shared objects and passes them into other systems.
This ensures systems such as rendering and camera control use the same state.

---

## CircuitCanvas

**Package:** `gui`

**Purpose:**

Main workspace for displaying the circuit.

**Implemented Features:**

- Creates the JavaFX canvas
- Resizes with the window
- Stores the Camera
- Connects rendering systems

**Design:**

CircuitCanvas does not directly draw objects.

Rendering is handled by separate classes:

- GridRenderer
- ComponentRenderer
- WireRenderer

---

# gui.camera

## Camera

**Package:** `gui.camera`

**Purpose:**

Controls the relationship between world coordinates and screen coordinates.

**Implemented Features:**

- Store camera position
- Store zoom level
- Move camera
- Convert world coordinates to screen coordinates
- Convert screen coordinates to world coordinates

**Design:**

Objects exist in world space and are independent from the display.

The Camera handles:
- zoom,
- future panning,
- coordinate conversion.

---

# gui.render

## GridRenderer

**Package:** `gui.render`

**Purpose:**

Draws the circuit workspace grid.

**Implemented Features:**

- Creates world-space grid points
- Converts points through the Camera
- Draws grid dots
- Enables/disables grid rendering

**Design:**

The grid exists in world coordinates.

This allows:
- correct zoom behavior,
- consistent grid placement,
- future snapping.

---

## ComponentRenderer

**Package:** `gui.render`

**Purpose:**

Placeholder for drawing circuit components.

**Planned Features:**

- Draw components
- Display component states
- Handle component graphics

---

## WireRenderer

**Package:** `gui.render`

**Purpose:**

Placeholder for drawing electrical connections.

**Planned Features:**

- Draw wires between pins
- Display circuit connections

---

# components

Contains all physical circuit components.

Components store structure and connections.
Simulation behavior is handled separately.

Hierarchy:
Component
|
+-- InputNode
+-- PowerNode
+-- GroundNode
+-- Transistor
|
+-- NMOS
+-- PMOS

---

## Component

**Package:** `components`

**Purpose:**

Abstract base class for all circuit components.

**Implemented Features:**

- Store position
- Store rotation
- Store component pins
- Add pins

**Design:**

Component defines shared physical properties.

Rendering and simulation are handled separately.

---

## InputNode

**Package:** `components`

**Purpose:**

Represents a user-controlled input.

**Implemented Features:**

- Stores an output pin
- Stores input state

**Future Use:**

Will provide HIGH/LOW signals to circuits.

---

## PowerNode

**Package:** `components`

**Purpose:**

Represents a positive voltage source.

**Implemented Features:**

- Stores a connection pin

**Future Use:**

Will provide constant HIGH voltage during simulation.

---

## GroundNode

**Package:** `components`

**Purpose:**

Represents circuit ground.

**Implemented Features:**

- Stores a connection pin

**Future Use:**

Will provide constant LOW voltage during simulation.

---

## Transistor

**Package:** `components`

**Purpose:**

Abstract base class for MOS transistors.

**Implemented Features:**

- Creates gate pin
- Creates drain pin
- Creates source pin

**Design:**

All MOS transistors share the same physical structure.

Specific transistor behavior is handled by subclasses and the simulation system.

---

## NMOS

**Package:** `components`

**Purpose:**

Represents an N-channel MOS transistor.

**Implemented Features:**

- Inherits transistor structure
- Provides gate, drain, and source pins

**Future Behavior:**

- HIGH gate signal allows conduction
- LOW gate signal prevents conduction

---

## PMOS

**Package:** `components`

**Purpose:**

Represents a P-channel MOS transistor.

**Implemented Features:**

- Inherits transistor structure
- Provides gate, drain, and source pins

**Future Behavior:**

- LOW gate signal allows conduction
- HIGH gate signal prevents conduction

---

# circuit

Contains the logical representation of circuits.

---

## Pin

**Package:** `circuit`

**Purpose:**

Represents a connection point on a component.

**Implemented Features:**

- Stores owning component
- Stores local position
- Calculates world position

**Design:**

Pins use local coordinates.

When a component moves, its pins automatically move with it.

---

## Wire

**Package:** `circuit`

**Purpose:**

Represents a physical connection between two pins.

**Implemented Features:**

- Store start pin
- Store end pin
- Retrieve pin positions

---

## Net

**Package:** `circuit`

**Purpose:**

Represents an electrical connection between multiple pins.

**Implemented Features:**

- Store connected pins
- Store connected wires
- Add pins
- Add wires

---

## Circuit

**Package:** `circuit`

**Purpose:**

Top-level container for circuit data.

**Implemented Features:**

- Store components
- Store nets

---

# simulation

Contains systems responsible for circuit behavior.

---

## SimulationEngine

**Package:** `simulation`

**Purpose:**

Placeholder for running circuit simulations.

---

## VoltageSolver

**Package:** `simulation`

**Purpose:**

Placeholder for calculating voltage states.

---

# util

Contains reusable utility classes.

---

## Vector2

**Package:** `util`

**Purpose:**

Immutable two-dimensional vector class.

**Implemented Features:**

- Store x and y coordinates
- Addition
- Subtraction
- Scalar multiplication
- Scalar division
- Dot product
- Length calculations
- Distance calculations
- Normalization
- Negation
- Coordinate updates
- Equality comparison

**Design:**

Vector2 objects cannot be modified after creation.

Operations create new Vector2 objects, preventing accidental changes between systems.

Used for:

- positions,
- movement,
- camera calculations,
- rendering.

---

# Development Philosophy

The simulator follows a modular architecture.

Principles:

- Each class has one responsibility
- Circuit logic is separate from graphics
- Rendering is separate from simulation
- Components are reusable
- Systems communicate through clear interfaces

The goal is to create a scalable foundation for exploring digital logic, transistor circuits, and computer architecture.