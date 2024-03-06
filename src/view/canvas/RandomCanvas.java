package view.canvas;

import view.data.AlgoArray;
import view.data.AlgoData;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author MiskuZero
 */
public class RandomCanvas extends JPanel implements AlgoCanvas {

    private final int id;

    private final int width;
    private final int height;

    private AlgoArray list;
    private final JLabel value = new JLabel();
    private final JLabel sample = new JLabel();
    private final BufferedImage bufferedImage;
    private int inCycle;
    private int count;

    public RandomCanvas(int id, int width, int height, AlgoArray array) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.list = array;
        setLayout(new GridLayout(24, 1));
        setBorder(new LineBorder(Color.BLACK));
        value.setForeground(Color.WHITE);
        value.setFont(new Font("楷体", Font.PLAIN, 32));
        add(value);
        sample.setForeground(Color.WHITE);
        sample.setFont(new Font("楷体", Font.PLAIN, 32));
        add(sample);
        bufferedImage = new BufferedImage(width, height, 1);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.setColor(Color.CYAN);
        graphics.drawOval(1, 1, width << 1, height << 1);
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
        int x = (int) (Math.random() * width);
        int y = (int) (Math.random() * height);
        graphics.setColor(Color.YELLOW);
        graphics.fillRect(x, y, 1, 1);
        g.drawImage(bufferedImage, 0, 0, width, height, null);
        ArrayCanvas.paint(g2d, list, width, height);
    }

    @Override
    public void updateData(AlgoData data, int index) {
        this.list = (AlgoArray) data;
        int c = ++count;
        if ((index - 1000) * (index - 1000) + (index - 1000) * (index - 1000) < 1000 * 1000)
            inCycle++;
        value.setText(" 算法" + id + "：π ≈ " + (((double) inCycle) / c));
        sample.setText(" 样本数量：" + c);
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

}

