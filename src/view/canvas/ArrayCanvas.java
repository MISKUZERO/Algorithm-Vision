package view.canvas;

import view.data.AlgoArray;
import view.data.AlgoData;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author MiskuZero
 */
public class ArrayCanvas extends JPanel {

    private AlgoArray list;
    private final int width;
    private final int height;
    private final BufferedImage bufferedImage;

    public ArrayCanvas(int width, int height, AlgoArray data) {
        super(true);
        if (data == null)
            throw new IllegalArgumentException("数据不能为空！");
        this.width = width;
        this.height = height;
        this.list = data;
        bufferedImage = new BufferedImage(width, height, 1);
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
        Graphics graphics = bufferedImage.getGraphics();
        graphics.setColor(Color.YELLOW);
        graphics.fillRect((int) (Math.random() * width), (int) (Math.random() * height), 1, 1);
        g.drawImage(bufferedImage, 0, 0, width, height, null);
        AlgoArray algoList = list;
        int capacity = algoList.capacity();
        if (capacity == 0) return;
        int w = width / capacity;
        g2d.setColor(Color.GRAY);
        for (int i = 0; i < capacity; i++) {
            g2d.fillRect(i * w, height - algoList.get(i), w - 1, algoList.get(i));
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    public void updateData(AlgoData data, int index) {
        this.list = (AlgoArray) data;
    }
}

