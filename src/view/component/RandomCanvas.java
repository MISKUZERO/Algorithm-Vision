package view.component;

import view.AlgoController;
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
    private final JLabel read = new JLabel();
    private final JLabel write = new JLabel();
    private final BufferedImage bufferedImage;
    //随机点坐标
    private int x;
    private int y;
    //统计值
    private int count;
    private int interCount;
    private int readCount;
    private int writeCount;
    //排序标记
    private int low = -1;
    private int high = -1;
    private int pivot = -100;

    public RandomCanvas(int id, int width, int height) {
        this.id = id;
        this.width = width;
        this.height = height;
        setLayout(new GridLayout(height / (TEXT_SIZE * 5 / 4), 1));//布局
        //标签
        value.setForeground(Color.WHITE);
        sample.setForeground(Color.WHITE);
        read.setForeground(Color.WHITE);
        write.setForeground(Color.WHITE);
        value.setFont(new Font("楷体", Font.PLAIN, TEXT_SIZE));
        sample.setFont(new Font("楷体", Font.PLAIN, TEXT_SIZE));
        read.setFont(new Font("楷体", Font.PLAIN, TEXT_SIZE));
        write.setFont(new Font("楷体", Font.PLAIN, TEXT_SIZE));
        add(value);
        add(sample);
        add(read);
        add(write);
        //背景
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.setColor(Color.CYAN);
        graphics.drawOval(0, 0, AlgoController.CANVAS_EDGE << 1, AlgoController.CANVAS_EDGE << 1);
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
        int wd = width, h = height;
        g2d.drawImage(bufferedImage, 0, 0, wd, h, null);
        int capacity = list.capacity();
        if (capacity == 0) return;
        int w = wd / capacity;
        for (int i = 0; i < capacity; i++) {
            if (i == low)
                g2d.setColor(Color.RED);
            else if (i == high)
                g2d.setColor(Color.CYAN);
            else
                g2d.setColor(Color.GRAY);
            g2d.fillRect(i * w, h - list.get(i), w - 1, list.get(i));
        }
        int p = pivot;
        int ph = h - p;
        g2d.setColor(Color.WHITE);
        g2d.drawString("Pivot", 5, ph - 5);
        g2d.drawLine(0, ph, wd, ph);
    }

    @Override
    public void updateData(AlgoData data, Object... args) {
        this.list = (AlgoArray) data;
        if (args.length == 2) {
            int c = ++count;
            x = (int) args[0];
            y = (int) args[1];
            if (x * x + y * y < AlgoController.CANVAS_EDGE * AlgoController.CANVAS_EDGE)
                interCount++;
            value.setText(" 算法" + id + "：π ≈ " + ((double) (interCount << 2) / c));
            sample.setText(" 样本总数：" + c);
        } else {
            low = (int) args[0];
            high = (int) args[1];
            pivot = (int) args[2];
            int rw = (int) args[3];
            if ((rw & 0b01) == 0b01)
                readCount++;
            if ((rw & 0b10) == 0b10)
                writeCount++;
            read.setText(" 读次数：" + readCount);
            write.setText(" 写次数：" + writeCount);
        }
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

}

