import javax.swing.*;
import java.awt.*;

public class PseudocodePanel extends JPanel {
    private final String[] dfsCode = {
        "DFS(node):",
        "    mark node as visited",
        "    process current node",
        "    for each neighbor of node:",
        "        if neighbor is not visited:",
        "            DFS(neighbor)"
    };

    private final String[] bfsCode = {
        "BFS(startNode):",
        "    create empty queue",
        "    add startNode to queue",
        "    mark startNode as visited",
        "    while queue is not empty:",
        "        currentNode = queue.poll()",
        "        process currentNode",
        "        for each neighbor of currentNode:",
        "            if neighbor is not visited:",
        "                mark neighbor as visited",
        "                add neighbor to queue"
    };

    private int highlightedLine = -1;
    private boolean isDFS = true;

    public PseudocodePanel() {
        setPreferredSize(new Dimension(300, 400));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createTitledBorder("Pseudocode"));
    }

    public void setAlgorithm(boolean isDFS) {
        this.isDFS = isDFS;
        highlightedLine = -1;
        repaint();
    }

    public void highlightLine(int step) {
        highlightedLine = step;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        String[] currentCode = isDFS ? dfsCode : bfsCode;
        int y = 40;
        int lineHeight = 25;
        Font codeFont = new Font("Monospace", Font.PLAIN, 14);
        g2d.setFont(codeFont);

        for (int i = 0; i < currentCode.length; i++) {
            if (i == highlightedLine) {
                g2d.setColor(new Color(255, 255, 200));
                g2d.fillRect(10, y - 20, getWidth() - 20, lineHeight);
                g2d.setColor(Color.BLUE);
            } else {
                g2d.setColor(Color.BLACK);
            }
            
            // Calculate indentation based on spaces at start of line
            int indent = currentCode[i].length() - currentCode[i].trim().length();
            g2d.drawString(currentCode[i], 20 + (indent * 8), y);
            y += lineHeight;
        }
    }
}