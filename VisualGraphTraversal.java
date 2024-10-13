import javax.swing.*;
import java.awt.*;
import java.util.*;

public class VisualGraphTraversal extends JFrame {
    private final GraphPanel graphPanel;

    public VisualGraphTraversal() {
        setTitle("Graph Traversal Educational App");
        setSize(1400, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Graph panel to visualize the graph
        graphPanel = new GraphPanel();
        add(graphPanel, BorderLayout.CENTER);

        // Button panel with DFS, BFS buttons and speed control
        JPanel buttonPanel = new JPanel();
        JButton dfsButton = new JButton("DFS");
        JButton bfsButton = new JButton("BFS");
        JSlider speedSlider = new JSlider(100, 1000, 500);

        speedSlider.setMajorTickSpacing(300);
        speedSlider.setMinorTickSpacing(100);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);

        buttonPanel.add(new JLabel("Speed:"));
        buttonPanel.add(speedSlider);
        buttonPanel.add(dfsButton);
        buttonPanel.add(bfsButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Text area to display explanations and complexity
        JTextArea explanationArea = new JTextArea(10, 25);
        explanationArea.setEditable(false);
        add(new JScrollPane(explanationArea), BorderLayout.EAST);

        // Action listeners for buttons
        dfsButton.addActionListener(e -> graphPanel.startDFS(0, explanationArea, speedSlider.getValue()));
        bfsButton.addActionListener(e -> graphPanel.startBFS(0, explanationArea, speedSlider.getValue()));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VisualGraphTraversal frame = new VisualGraphTraversal();
            frame.setVisible(true);
        });
    }
}

// Panel to visualize the graph
class GraphPanel extends JPanel {
    private final int[][] graph; // Adjacency matrix
    private final Point[] nodePositions; // Coordinates of nodes
    private final boolean[] visited; // Track visited nodes
    private Color traversalColor = Color.GREEN; // Color for current traversal
    private int nodeCount = 0; // Track number of visited nodes

    public GraphPanel() {
        graph = new int[][]{
            {0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0}
        };

        // Adjusted node positions to fit the screen better
        nodePositions = new Point[]{
            new Point(700, 100), new Point(500, 200), new Point(900, 200),
            new Point(400, 350), new Point(600, 350), new Point(800, 350),
            new Point(1000, 350), new Point(350, 500), new Point(650, 500),
            new Point(750, 500), new Point(1050, 500)
        };

        visited = new boolean[graph.length];
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);

        // Draw edges
        for (int i = 0; i < graph.length; i++) {
            for (int j = i + 1; j < graph[i].length; j++) {
                if (graph[i][j] == 1) {
                    g.drawLine(nodePositions[i].x, nodePositions[i].y,
                               nodePositions[j].x, nodePositions[j].y);
                }
            }
        }

        // Draw nodes
        for (int i = 0; i < nodePositions.length; i++) {
            g.setColor(visited[i] ? traversalColor : Color.RED);
            g.fillOval(nodePositions[i].x - 20, nodePositions[i].y - 20, 40, 40);
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(i), nodePositions[i].x - 5, nodePositions[i].y + 5);
        }
    }

    private void resetVisited(Color color) {
        Arrays.fill(visited, false);
        traversalColor = color;
        nodeCount = 0;
        repaint();
    }

    public void startDFS(int start, JTextArea explanationArea, int speed) {
        resetVisited(Color.GREEN);
        explanationArea.setText("Starting DFS...\nTime Complexity: O(V + E)\nSpace Complexity: O(V)");
        new Thread(() -> dfs(start, explanationArea, speed)).start();
    }

    private void dfs(int node, JTextArea explanationArea, int speed) {
        visited[node] = true;
        nodeCount++;
        repaint();
        sleep(speed);
        explanationArea.append("\nVisited Node: " + node);

        for (int i = 0; i < graph[node].length; i++) {
            if (graph[node][i] == 1 && !visited[i]) {
                dfs(i, explanationArea, speed);
            }
        }
    }

    public void startBFS(int start, JTextArea explanationArea, int speed) {
        resetVisited(Color.BLUE);
        explanationArea.setText("Starting BFS...\nTime Complexity: O(V + E)\nSpace Complexity: O(V)");
        new Thread(() -> bfs(start, explanationArea, speed)).start();
    }

    private void bfs(int start, JTextArea explanationArea, int speed) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited[start] = true;
        repaint();
        sleep(speed);
        explanationArea.append("\nVisited Node: " + start);

        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int i = 0; i < graph[node].length; i++) {
                if (graph[node][i] == 1 && !visited[i]) {
                    queue.add(i);
                    visited[i] = true;
                    repaint();
                    sleep(speed);
                    explanationArea.append("\nVisited Node: " + i);
                }
            }
        }
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
