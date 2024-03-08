package view.component;

import view.data.AlgoData;

import javax.swing.*;
import java.awt.*;

public class AlgoFrame extends JFrame {

    private final AlgoCanvas[] canvas;

    public AlgoFrame(String title, int panelWidth, int panelHeight, int canvasCount, int canvasRows) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(canvasRows, 0));
        canvas = new AlgoCanvas[canvasCount];
        for (int i = 0; i < canvasCount; i++) {
            canvas[i] = new RandomCanvas(i, panelWidth, panelHeight);
            add((Component) canvas[i]);
        }
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public void render(int tid, AlgoData data, Object... args) {
        canvas[tid].updateData(data, args);
    }

}

