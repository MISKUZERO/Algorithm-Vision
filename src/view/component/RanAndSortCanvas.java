package view.component;

import view.AlgoController;
import view.AlgoSupplier;
import view.ChiSquareTable;
import view.data.AlgoArray;
import view.data.AlgoData;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author MiskuZero
 */
public class RanAndSortCanvas extends AlgoCanvas {

    private final int width;
    private final int height;
    private final BufferedImage bufferedImage;
    private final JLabel title = new JLabel();
    private final JLabel length = new JLabel();
    private final JLabel piVal = new JLabel();
    private final JLabel chiSquVal = new JLabel();
    private final JLabel sample = new JLabel();
    private final JLabel read = new JLabel();
    private final JLabel write = new JLabel();
    private final String titleText;
    private String lenText;
    private String piValText;
    private String chiSquValText;
    private String samText;
    private String rText;
    private String wText;
    private AlgoArray list;
    //随机点坐标
    private int x = -100;
    private int y = -100;
    //统计值
    private int count;
    private int interCount;
    private int readCount;
    private int writeCount;
    //排序标记
    private int low = -1;
    private int mid = -1;
    private int high = -1;
    private int pivot = -100;
    private int extra = -1;
    //控制标记
    private boolean showText = true;
    private boolean render = true;

    public RanAndSortCanvas(String name, int width, int height) {
        this.width = width;
        this.height = height;
        this.titleText = name;
        setLayout(new GridLayout(height / (AlgoController.TEXT_SIZE * 5 / 4), 1));//布局
        initText();
        //渲染背景
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.setColor(Color.GREEN);
        graphics.drawOval(0, 0, AlgoController.CANVAS_EDGE << 1, AlgoController.CANVAS_EDGE << 1);
        setBorder(new LineBorder(Color.BLACK));//边框
    }

    private void initText() {
        int capacity = AlgoController.DATA_LENGTH;
        lenText = "N：" + capacity;
        switch (capacity) {
            case 1:
                lenText += " χ2：" + ChiSquareTable.ALPHA_1[0] +
                        "(.1) " + ChiSquareTable.ALPHA_05[0] +
                        "(.05) " + ChiSquareTable.ALPHA_001[0] + "(.005)";
                break;
            case 10:
                lenText += " χ2：" + ChiSquareTable.ALPHA_1[1] +
                        "(.1) " + ChiSquareTable.ALPHA_05[1] +
                        "(.05) " + ChiSquareTable.ALPHA_001[1] + "(.005)";
                break;
            case 20:
                lenText += " χ2：" + ChiSquareTable.ALPHA_1[2] +
                        "(.1) " + ChiSquareTable.ALPHA_05[2] +
                        "(.05) " + ChiSquareTable.ALPHA_001[2] + "(.005)";
                break;
            case 30:
                lenText += " χ2：" + ChiSquareTable.ALPHA_1[3] +
                        "(.1) " + ChiSquareTable.ALPHA_05[3] +
                        "(.05) " + ChiSquareTable.ALPHA_001[3] + "(.005)";
                break;
            case 40:
                lenText += " χ2：" + ChiSquareTable.ALPHA_1[4] +
                        "(.1) " + ChiSquareTable.ALPHA_05[4] +
                        "(.05) " + ChiSquareTable.ALPHA_001[4] + "(.005)";
                break;
            case 50:
                lenText += " χ2：" + ChiSquareTable.ALPHA_1[5] +
                        "(.1) " + ChiSquareTable.ALPHA_05[5] +
                        "(.05) " + ChiSquareTable.ALPHA_001[5] + "(.005)";
                break;
            case 60:
                lenText += " χ2：" + ChiSquareTable.ALPHA_1[6] +
                        "(.1) " + ChiSquareTable.ALPHA_05[6] +
                        "(.05) " + ChiSquareTable.ALPHA_001[6] + "(.005)";
                break;
            case 70:
                lenText += " χ2：" + ChiSquareTable.ALPHA_1[7] +
                        "(.1) " + ChiSquareTable.ALPHA_05[7] +
                        "(.05) " + ChiSquareTable.ALPHA_001[7] + "(.005)";
                break;
            case 80:
                lenText += " χ2：" + ChiSquareTable.ALPHA_1[8] +
                        "(.1) " + ChiSquareTable.ALPHA_05[8] +
                        "(.05) " + ChiSquareTable.ALPHA_001[8] + "(.005)";
                break;
            case 90:
                lenText += " χ2：" + ChiSquareTable.ALPHA_1[9] +
                        "(.1) " + ChiSquareTable.ALPHA_05[9] +
                        "(.05) " + ChiSquareTable.ALPHA_001[9] + "(.005)";
                break;
            case 100:
                lenText += " χ2：" + ChiSquareTable.ALPHA_1[10] +
                        "(.1) " + ChiSquareTable.ALPHA_05[10] +
                        "(.05) " + ChiSquareTable.ALPHA_001[10] + "(.005)";
                break;
        }
        title.setForeground(Color.CYAN);
        length.setForeground(Color.WHITE);
        piVal.setForeground(Color.WHITE);
        chiSquVal.setForeground(Color.WHITE);
        sample.setForeground(Color.WHITE);
        read.setForeground(Color.WHITE);
        write.setForeground(Color.WHITE);
        title.setFont(new Font("楷体", Font.BOLD, AlgoController.TEXT_SIZE));
        length.setFont(new Font("楷体", Font.PLAIN, AlgoController.TEXT_SIZE * 3 / 4));
        piVal.setFont(new Font("楷体", Font.PLAIN, AlgoController.TEXT_SIZE));
        chiSquVal.setFont(new Font("楷体", Font.PLAIN, AlgoController.TEXT_SIZE));
        sample.setFont(new Font("楷体", Font.PLAIN, AlgoController.TEXT_SIZE));
        read.setFont(new Font("楷体", Font.PLAIN, AlgoController.TEXT_SIZE));
        write.setFont(new Font("楷体", Font.PLAIN, AlgoController.TEXT_SIZE));
        title.setText(titleText);
        length.setText(lenText);
        add(title);
        add(length);
        add(chiSquVal);
        add(piVal);
        add(sample);
        add(read);
        add(write);
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
        int x = this.x, y = this.y;
        BufferedImage bi = bufferedImage;
        Graphics graphics = bi.getGraphics();
        if (x >= 0 && y >= 0) {
            int rgb = bi.getRGB(x, y);//获取坐标位置的颜色值
            //排除背景色的干扰
            if (rgb == Color.BLACK.getRGB() || rgb == Color.GREEN.getRGB())
                graphics.setColor(Color.YELLOW);//黄色值十六进制数：0xffffff00
            else {
                int newRgb = rgb + 50;
            /*
            色阶变化6档：（纯黄色）0xffffff00 ~ （接近白色）0xfffffffa
            每次加50后比较最后一位字节是否溢出
            若溢出，则颜色值停留在0xfffffffa
            */
                if ((newRgb & 0x000000ff) < (rgb & 0x000000ff))
                    graphics.setColor(new Color(rgb));
                else
                    graphics.setColor(new Color(newRgb));
            }
            graphics.fillRect(x, y, 1, 1);
        }
        int width = this.width, height = this.height;
        g2d.drawImage(bi, 0, 0, width, height, null);
        //绘制数据（数组元素数值）
        if (render) {
            int capacity = list.capacity();
            if (capacity == 0) return;
            int w = width / capacity;
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
                g2d.fillRect(i * w, height - list.get(i), w - 1, list.get(i));
            }
            int p = pivot;
            int ph = height - p;
            g2d.setColor(Color.WHITE);
            g2d.drawString("Pivot", 5, ph - 5);
            g2d.drawLine(0, ph, width, ph);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    @Override
    public void updateData(AlgoData data, Object... args) {
        AlgoArray arr = (AlgoArray) data;
        this.list = arr;
        if (args.length == 2) {
            int c = ++count;
            int x = (int) args[0], y = (int) args[1];
            this.x = x;
            this.y = y;
            if (x * x + y * y < AlgoController.CANVAS_EDGE * AlgoController.CANVAS_EDGE)
                interCount++;
            String vt = "π ≈ " + new BigDecimal(interCount << 2).divide(new BigDecimal(c), AlgoController.SCALE, RoundingMode.HALF_UP);
            String st = "抽样次数：" + c;
            String ct = "卡方值 ≈ " + AlgoSupplier.uDistributeChiSquTest(arr, c);
            piValText = vt;
            chiSquValText = ct;
            samText = st;
            if (showText) {
                piVal.setText(vt);
                chiSquVal.setText(ct);
                sample.setText(st);
            }
        } else {//第一阶段结束，开始排序
            this.x = -100;
            this.y = -100;
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
            String rt = "读次数：" + readCount;
            String wt = "写次数：" + writeCount;
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
        length.setFont(new Font("楷体", Font.PLAIN, textSize * 3 / 4));
        piVal.setFont(new Font("楷体", Font.PLAIN, textSize));
        chiSquVal.setFont(new Font("楷体", Font.PLAIN, textSize));
        sample.setFont(new Font("楷体", Font.PLAIN, textSize));
        read.setFont(new Font("楷体", Font.PLAIN, textSize));
        write.setFont(new Font("楷体", Font.PLAIN, textSize));
    }

    @Override
    public void showText() {
        showText = true;
        title.setText(titleText);
        length.setText(lenText);
        piVal.setText(piValText);
        chiSquVal.setText(chiSquValText);
        sample.setText(samText);
        read.setText(rText);
        write.setText(wText);
    }

    @Override
    public void closeText() {
        showText = false;
        title.setText("");
        length.setText("");
        piVal.setText("");
        chiSquVal.setText("");
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

