import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class VisualGraphTraversal extends JFrame {
    private final GraphPanel graphPanel;

    public VisualGraphTraversal() {
        setTitle("Graph Traversal Visualizer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the graph panel
        graphPanel = new GraphPanel();
        add(graphPanel, BorderLayout.CENTER);

        // Create buttons for BFS and DFS
        JPanel buttonPanel = new JPanel();
        JButton dfsButton = new JButton("DFS");
        JButton bfsButton = new JButton("BFS");

        buttonPanel.add(dfsButton);
        buttonPanel.add(bfsButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners for buttons
        dfsButton.addActionListener(e -> graphPanel.startDFS(0));
        bfsButton.addActionListener(e -> graphPanel.startBFS(0));
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

    public GraphPanel() {
        graph = new int[][]{
            {0, 1, 1, 0, 0},
            {1, 0, 0, 1, 1},
            {1, 0, 0, 0, 0},
            {0, 1, 0, 0, 1},
            {0, 1, 0, 1, 0}
        };
        nodePositions = new Point[]{
            new Point(100, 100),
            new Point(300, 100),
            new Point(500, 100),
            new Point(200, 300),
            new Point(400, 300)
        };
        visited = new boolean[graph.length];
    }

    // Draw the graph nodes and edges
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
            g.setColor(visited[i] ? Color.GREEN : Color.RED);
            g.fillOval(nodePositions[i].x - 15, nodePositions[i].y - 15, 30, 30);
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(i), nodePositions[i].x - 5, nodePositions[i].y + 5);
        }
    }

    // Reset visited nodes for fresh traversal
    private void resetVisited() {
        Arrays.fill(visited, false);
        repaint();
    }

    // DFS traversal with visualization
    public void startDFS(int start) {
        resetVisited();
        new Thread(() -> dfs(start)).start();
    }

    private void dfs(int node) {
        visited[node] = true;
        repaint();
        sleep(500);

        for (int i = 0; i < graph[node].length; i++) {
            if (graph[node][i] == 1 && !visited[i]) {
                dfs(i);
            }
        }
    }

    // BFS traversal with visualization
    public void startBFS(int start) {
        resetVisited();
        new Thread(() -> bfs(start)).start();
    }

    private void bfs(int start) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited[start] = true;
        repaint();
        sleep(500);

        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int i = 0; i < graph[node].length; i++) {
                if (graph[node][i] == 1 && !visited[i]) {
                    queue.add(i);
                    visited[i] = true;
                    repaint();
                    sleep(500);
                }
            }
        }
    }

    // Helper method to pause for visualization effect
    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
