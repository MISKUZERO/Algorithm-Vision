package view;

import view.canvas.ArrayCanvas;
import view.data.AlgoArray;
import view.data.AlgoData;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * @author MiskuZero
 */
public class AlgoFrame extends JFrame {

    private final ArrayCanvas[] canvas;
    private final int canvasCount;

    public AlgoFrame(String title, int panelWidth, int panelHeight, int canvasCount) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2));
        canvas = new ArrayCanvas[canvasCount];
        this.canvasCount = canvasCount;
        for (int i = 0; i < canvasCount; i++) {
            int finalI = i;
            canvas[i] = new ArrayCanvas(panelWidth, panelHeight, new AlgoArray()) {
                private final JLabel value = new JLabel();
                private final JLabel sample = new JLabel();
                private int inCycle = 0;
                private int count = 1;

                {
                    setLayout(new GridLayout(24, 1));
                    setBorder(new LineBorder(Color.BLACK));
                    value.setForeground(Color.WHITE);
                    value.setFont(new Font("楷体", Font.PLAIN, 32));
                    add(value);
                    sample.setForeground(Color.WHITE);
                    sample.setFont(new Font("楷体", Font.PLAIN, 32));
                    add(sample);
                    pack();
                }

                @Override
                public void updateData(AlgoData data, int index) {
                    super.updateData(data, index);
                    int c = count++;
                    AlgoArray array = (AlgoArray) data;
                    int num = array.get(index);
                    if (num * num + num * num < 500 * 500)
                        inCycle++;
                    value.setText(" 算法" + finalI + "：π ≈ " + (((double) inCycle) / c));
                    sample.setText(" 样本数量：" + c);
                }
            };
            add(canvas[i]);
        }
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public void render(AlgoData[] data, int index) {
        for (int i = 0; i < canvasCount; i++) {
            canvas[i].updateData(data[i], index);
        }
        repaint();
    }

}