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
public class RanAndSortCanvas extends AlgoCanvas {

    private final int width;
    private final int height;
    private final BufferedImage bufferedImage;
    private final JLabel title;
    private final JLabel length;
    private final JLabel value;
    private final JLabel sample;
    private final JLabel read;
    private final JLabel write;
    private final String titleText;
    private final String lenText;
    private String valText;
    private String samText;
    private String rText;
    private String wText;
    private AlgoArray list;
    private boolean showText;
    private boolean render;
    //随机点坐标
    private int x;
    private int y;
    //统计值
    private int count;
    private int interCount;
    private int readCount;
    private int writeCount;
    //排序标记
    private int low;
    private int mid;
    private int high;
    private int pivot;
    private int extra;

    public RanAndSortCanvas(String name, int width, int height) {
        this.width = width;
        this.height = height;
        this.titleText = name;
        this.lenText = " 数据量：" + AlgoController.DATA_LENGTH;
        this.showText = true;
        this.render = true;
        this.low = -1;
        this.mid = -1;
        this.high = -1;
        this.pivot = -100;
        this.extra = -1;
        setLayout(new GridLayout(height / (AlgoController.TEXT_SIZE * 5 / 4), 1));//布局
        //标签
        title = new JLabel(name);
        length = new JLabel();
        value = new JLabel();
        sample = new JLabel();
        read = new JLabel();
        write = new JLabel();
        title.setForeground(Color.CYAN);
        length.setForeground(Color.WHITE);
        value.setForeground(Color.WHITE);
        sample.setForeground(Color.WHITE);
        read.setForeground(Color.WHITE);
        write.setForeground(Color.WHITE);
        title.setFont(new Font("楷体", Font.BOLD, AlgoController.TEXT_SIZE));
        length.setFont(new Font("楷体", Font.PLAIN, AlgoController.TEXT_SIZE));
        value.setFont(new Font("楷体", Font.PLAIN, AlgoController.TEXT_SIZE));
        sample.setFont(new Font("楷体", Font.PLAIN, AlgoController.TEXT_SIZE));
        read.setFont(new Font("楷体", Font.PLAIN, AlgoController.TEXT_SIZE));
        write.setFont(new Font("楷体", Font.PLAIN, AlgoController.TEXT_SIZE));
        length.setText(lenText);
        add(title);
        add(length);
        add(sample);
        add(value);
        add(read);
        add(write);
        //背景
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.setColor(Color.GREEN);
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
        // 绘制背景
        Graphics graphics = bufferedImage.getGraphics();
        graphics.setColor(Color.YELLOW);
        graphics.fillRect(x, y, 1, 1);
        int wd = width, h = height;
        g2d.drawImage(bufferedImage, 0, 0, wd, h, null);
        //绘制数据（数组元素数值）
        if (render) {
            int capacity = list.capacity();
            if (capacity == 0) return;
            int w = wd / capacity;
            for (int i = 0; i < capacity; i++) {
                if (i == low)
                    g2d.setColor(Color.RED);
                else if (i == high)
                    g2d.setColor(Color.CYAN);
                else if (i == mid)
                    g2d.setColor(Color.YELLOW);
                else if (i == extra)
                    g2d.setColor(Color.GREEN);
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
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    @Override
    public void updateData(AlgoData data, Object... args) {
        this.list = (AlgoArray) data;
        if (args.length == 2) {
            int c = ++count;
            int x = (int) args[0], y = (int) args[1];
            this.x = x;
            this.y = y;
            if (x * x + y * y < AlgoController.CANVAS_EDGE * AlgoController.CANVAS_EDGE)
                interCount++;
            String vt = " π ≈ " + ((double) (interCount << 2) / c);
            String st = " 样本总数：" + c;
            valText = vt;
            samText = st;
            if (showText) {
                value.setText(vt);
                sample.setText(st);
            }
        } else {
            low = (int) args[0];
            mid = (int) args[1];
            high = (int) args[2];
            pivot = (int) args[3];
            int rw = (int) args[4];
            if (args.length > 5)
                extra = (int) args[5];
            else
                extra = -1;
            if ((rw & 0b01) == 0b01)
                readCount++;
            if ((rw & 0b10) == 0b10)
                writeCount++;
            if (rw == -1) {
                readCount = 0;
                writeCount = 0;
            }
            String rt = " 读次数：" + readCount;
            String wt = " 写次数：" + writeCount;
            rText = rt;
            wText = wt;
            if (showText) {
                read.setText(rt);
                write.setText(wt);
            }
        }
        repaint();
    }

    @Override
    public void adjustTextSize(int textSize) {
        setLayout(new GridLayout(height / (textSize * 5 / 4), 1));
        title.setFont(new Font("楷体", Font.BOLD, textSize));
        length.setFont(new Font("楷体", Font.PLAIN, textSize));
        value.setFont(new Font("楷体", Font.PLAIN, textSize));
        sample.setFont(new Font("楷体", Font.PLAIN, textSize));
        read.setFont(new Font("楷体", Font.PLAIN, textSize));
        write.setFont(new Font("楷体", Font.PLAIN, textSize));
    }

    @Override
    public void showText() {
        showText = true;
        title.setText(titleText);
        length.setText(lenText);
        value.setText(valText);
        sample.setText(samText);
        read.setText(rText);
        write.setText(wText);
    }

    @Override
    public void closeText() {
        showText = false;
        title.setText("");
        length.setText("");
        value.setText("");
        sample.setText("");
        read.setText("");
        write.setText("");
    }

    @Override
    public void renderData(boolean isRender) {
        this.render = isRender;
        repaint();
    }

}

