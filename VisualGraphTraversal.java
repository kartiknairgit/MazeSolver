import javax.swing.*;
import java.awt.*;
import java.util.*;

public class VisualGraphTraversal extends JFrame {
    private final GraphPanel graphPanel;
    private final PseudocodePanel pseudocodePanel;

    public VisualGraphTraversal() {
        setTitle("Graph Traversal Educational App");
        setSize(1700, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create panels
        graphPanel = new GraphPanel();
        pseudocodePanel = new PseudocodePanel();
        graphPanel.setPseudocodePanel(pseudocodePanel);

        // Create right panel to hold both pseudocode and explanation
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(pseudocodePanel, BorderLayout.CENTER);
        
        // Text area for explanations
        JTextArea explanationArea = new JTextArea(10, 25);
        explanationArea.setEditable(false);
        rightPanel.add(new JScrollPane(explanationArea), BorderLayout.SOUTH);

        // Add panels to frame
        add(graphPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        // Button panel
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

        // Action listeners
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

class GraphPanel extends JPanel {
    private final int[][] graph;
    private final Point[] nodePositions;
    private final boolean[] visited;
    private Color traversalColor = Color.GREEN;
    private int nodeCount = 0;
    private PseudocodePanel pseudocodePanel;

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

        nodePositions = new Point[]{
            new Point(700, 100), new Point(500, 200), new Point(900, 200),
            new Point(400, 350), new Point(600, 350), new Point(800, 350),
            new Point(1000, 350), new Point(350, 500), new Point(650, 500),
            new Point(750, 500), new Point(1050, 500)
        };

        visited = new boolean[graph.length];
    }

    public void setPseudocodePanel(PseudocodePanel panel) {
        this.pseudocodePanel = panel;
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
        pseudocodePanel.setAlgorithm(true);
        new Thread(() -> dfs(start, explanationArea, speed)).start();
    }

    private void dfs(int node, JTextArea explanationArea, int speed) {
        pseudocodePanel.highlightLine(0); // Highlight "DFS(node)"
        sleep(speed);
        
        pseudocodePanel.highlightLine(1); // Highlight "mark node as visited"
        visited[node] = true;
        sleep(speed);
        
        pseudocodePanel.highlightLine(2); // Highlight "process current node"
        nodeCount++;
        repaint();
        explanationArea.append("\nVisited Node: " + node);
        sleep(speed);

        pseudocodePanel.highlightLine(3); // Highlight "for each neighbor"
        sleep(speed);
        
        for (int i = 0; i < graph[node].length; i++) {
            if (graph[node][i] == 1) {
                pseudocodePanel.highlightLine(4); // Highlight "if neighbor is not visited"
                sleep(speed);
                
                if (!visited[i]) {
                    pseudocodePanel.highlightLine(5); // Highlight "DFS(neighbor)"
                    sleep(speed);
                    dfs(i, explanationArea, speed);
                }
            }
        }
    }

    public void startBFS(int start, JTextArea explanationArea, int speed) {
        resetVisited(Color.BLUE);
        explanationArea.setText("Starting BFS...\nTime Complexity: O(V + E)\nSpace Complexity: O(V)");
        pseudocodePanel.setAlgorithm(false);
        new Thread(() -> bfs(start, explanationArea, speed)).start();
    }

    private void bfs(int start, JTextArea explanationArea, int speed) {
        Queue<Integer> queue = new LinkedList<>();
        
        pseudocodePanel.highlightLine(1); // Highlight "create empty queue"
        sleep(speed);
        
        pseudocodePanel.highlightLine(2); // Highlight "add startNode to queue"
        queue.add(start);
        sleep(speed);
        
        pseudocodePanel.highlightLine(3); // Highlight "mark startNode as visited"
        visited[start] = true;
        repaint();
        explanationArea.append("\nVisited Node: " + start);
        sleep(speed);

        while (!queue.isEmpty()) {
            pseudocodePanel.highlightLine(4); // Highlight "while queue is not empty"
            sleep(speed);
            
            pseudocodePanel.highlightLine(5); // Highlight "currentNode = queue.poll()"
            int node = queue.poll();
            sleep(speed);
            
            pseudocodePanel.highlightLine(6); // Highlight "process currentNode"
            sleep(speed);

            pseudocodePanel.highlightLine(7); // Highlight "for each neighbor"
            for (int i = 0; i < graph[node].length; i++) {
                if (graph[node][i] == 1) {
                    pseudocodePanel.highlightLine(8); // Highlight "if neighbor is not visited"
                    sleep(speed);
                    
                    if (!visited[i]) {
                        pseudocodePanel.highlightLine(9); // Highlight "mark neighbor as visited"
                        visited[i] = true;
                        repaint();
                        sleep(speed);
                        
                        pseudocodePanel.highlightLine(10); // Highlight "add neighbor to queue"
                        queue.add(i);
                        explanationArea.append("\nVisited Node: " + i);
                        sleep(speed);
                    }
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