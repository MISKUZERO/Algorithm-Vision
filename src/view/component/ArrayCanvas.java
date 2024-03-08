package view.component;

import view.data.AlgoArray;
import view.data.AlgoData;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * @author MiskuZero
 */
public class ArrayCanvas extends JPanel implements AlgoCanvas {

    private final int width;
    private final int height;
    private AlgoArray list;

    public ArrayCanvas(int width, int height) {
        this.width = width;
        this.height = height;
        setBorder(new LineBorder(Color.BLACK));//边框
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // 抗锯齿
        RenderingHints hints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.addRenderingHints(hints);
        // 具体绘制
        paintDataList(g2d, list, width, height);
    }

    protected static void paintDataList(Graphics2D g2d, AlgoArray list, int width, int height) {
        int capacity = list.capacity();
        if (capacity == 0) return;
        int w = width / capacity;
        g2d.setColor(Color.GRAY);
        for (int i = 0; i < capacity; i++)
            g2d.fillRect(i * w, height - list.get(i), w - 1, list.get(i));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    @Override
    public void updateData(AlgoData data, Object... args) {
        this.list = (AlgoArray) data;
        repaint();
    }
}

