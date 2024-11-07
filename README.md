# Graph Traversal Visualization Application

A Java-based educational tool that provides an interactive visualization of Depth-First Search (DFS) and Breadth-First Search (BFS) graph traversal algorithms. This application helps students and developers understand how these fundamental graph algorithms work through real-time visualization and step-by-step pseudocode execution.

## Features

- **Interactive Graph Visualization**
  - Visual representation of a graph with nodes and edges
  - Color-coded nodes showing traversal progress
  - Animated traversal sequence
  - Real-time node highlighting during algorithm execution

- **Algorithm Visualization**
  - Support for both DFS and BFS algorithms
  - Side-by-side pseudocode display
  - Step-by-step highlighting of current execution line
  - Visual explanation of algorithm progression

- **Educational Components**
  - Algorithm complexity information
  - Real-time explanation of each step
  - Adjustable visualization speed
  - Clear visualization of visited nodes and traversal path

## System Requirements

- Java Development Kit (JDK) 8 or higher
- Java Runtime Environment (JRE)
- Any operating system that supports Java (Windows, macOS, Linux)

## Installation

1. Clone or download the repository
2. Navigate to the project directory
3. Compile the Java files:
```bash
javac VisualGraphTraversal.java PseudocodePanel.java
```
4. Run the application:
```bash
java VisualGraphTraversal
```

## Usage

1. Launch the application
2. The main window shows:
   - Graph visualization in the center
   - Pseudocode panel on the right
   - Control panel at the bottom

3. Controls:
   - Use the "DFS" button to start Depth-First Search
   - Use the "BFS" button to start Breadth-First Search
   - Adjust the speed slider to control visualization speed
   - Watch the pseudocode highlighting to follow algorithm execution
   - View explanations in the text area below the pseudocode

## Algorithm Details

### Depth-First Search (DFS)
- Explores as far as possible along each branch before backtracking
- Time Complexity: O(V + E)
- Space Complexity: O(V)
- Visualization Color: Green

### Breadth-First Search (BFS)
- Explores all vertices at the present depth before moving to vertices at the next depth level
- Time Complexity: O(V + E)
- Space Complexity: O(V)
- Visualization Color: Blue

## Project Structure

```
├── VisualGraphTraversal.java    # Main application and graph panel implementation
├── PseudocodePanel.java         # Pseudocode visualization component
└── README.md                    # This file
```

## Implementation Details

- Built using Java Swing for the graphical user interface
- Uses multi-threading for smooth animation
- Custom graph implementation using adjacency matrix
- Synchronized visualization of algorithm execution and pseudocode
- Real-time node coloring to show traversal progress

## Contributing

Feel free to submit issues and enhancement requests!

## Educational Use

This application is designed for:
- Computer Science students learning graph algorithms
- Teachers demonstrating graph traversal concepts
- Self-learners studying algorithm visualization
- Anyone interested in understanding graph traversal algorithms

## Future Enhancements

Potential areas for improvement:
1. Add more graph algorithms (Dijkstra's, A*, etc.)
2. Allow custom graph creation
3. Add save/load functionality for different graph configurations
4. Implement step-by-step manual traversal mode
5. Add animation for edge traversal
6. Include more detailed algorithm analysis

## License

This project is licensed under the MIT License - see the LICENSE file for details