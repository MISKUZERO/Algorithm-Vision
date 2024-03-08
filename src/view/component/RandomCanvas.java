package view.component;

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

    private static final int TEXT_SIZE = 32;//文本尺寸

    private AlgoArray list;
    private final JLabel value = new JLabel();
    private final JLabel sample = new JLabel();
    private final BufferedImage bufferedImage;
    private int x;
    private int y;
    private int count;
    private int inCycle;

    public RandomCanvas(int id, int width, int height) {
        this.id = id;
        this.width = width;
        this.height = height;
        setLayout(new GridLayout(height / (TEXT_SIZE * 5 / 4), 1));//布局
        //标签
        value.setForeground(Color.WHITE);
        value.setFont(new Font("楷体", Font.PLAIN, TEXT_SIZE));
        add(value);
        sample.setForeground(Color.WHITE);
        sample.setFont(new Font("楷体", Font.PLAIN, TEXT_SIZE));
        add(sample);
        //背景
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.setColor(Color.CYAN);
        graphics.drawOval(1, 1, width << 1, height << 1);
        setBorder(new LineBorder(Color.BLACK));//边框
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // 抗锯齿
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.addRenderingHints(hints);
        // 具体绘制
        Graphics graphics = bufferedImage.getGraphics();
        graphics.setColor(Color.YELLOW);
        graphics.fillRect(x, y, 1, 1);
        g.drawImage(bufferedImage, 0, 0, width, height, null);
        ArrayCanvas.paint(g2d, list, width, height);
    }

    @Override
    public void updateData(AlgoData data, Object... args) {
        this.list = (AlgoArray) data;
        int c = ++count;
        int r = height;
        x = (Integer) args[0];
        y = (Integer) args[1];
        if (x * x + y * y < r * r)
            inCycle++;
        value.setText(" 算法" + id + "：π ≈ " + ((double) (inCycle << 2) / c));
        sample.setText(" 样本总数：" + c);
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

}

