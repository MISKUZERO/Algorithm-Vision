package view;

import view.canvas.AlgoCanvas;
import view.canvas.RandomCanvas;
import view.data.AlgoArray;
import view.data.AlgoData;

import javax.swing.*;
import java.awt.*;

/**
 * @author MiskuZero
 */
public class AlgoFrame extends JFrame {

    private final AlgoCanvas[] canvas;

    public AlgoFrame(String title, int panelWidth, int panelHeight, int canvasCount) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2));
        canvas = new AlgoCanvas[canvasCount];
        for (int i = 0; i < canvasCount; i++) {
            canvas[i] = new RandomCanvas(i, panelWidth, panelHeight, new AlgoArray());
            add((Component) canvas[i]);
        }
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public void render(int tid, AlgoData data, int index) {
        canvas[tid].updateData(data, index);
        repaint();
    }

}