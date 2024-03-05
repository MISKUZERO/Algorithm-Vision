package view;

import view.canvas.ArrayCanvas;
import view.data.AlgoArray;
import view.data.AlgoData;

import javax.swing.*;
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
        setLayout(new GridLayout());
        canvas = new ArrayCanvas[canvasCount];
        this.canvasCount = canvasCount;
        for (int i = 0; i < canvasCount; i++) {
            canvas[i] = new ArrayCanvas(panelWidth, panelHeight, new AlgoArray());
            add(canvas[i]);
        }
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public void render(AlgoData[] data) {
        for (int i = 0; i < canvasCount; i++) {
            canvas[i].updateData(data[i]);
        }
        repaint();
    }

}