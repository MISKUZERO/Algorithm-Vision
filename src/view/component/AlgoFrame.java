package view.component;

import view.AlgoController;
import view.data.AlgoData;

import javax.swing.*;
import java.awt.*;

public class AlgoFrame extends JFrame {

    private final AlgoCanvas[] canvas;
    private final JLabel speed;

    public AlgoFrame(String title, int canvasWidth, int canvasHeight, int canvasCount, int canvasRows, String[] names) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(canvasRows, 0));
        speed = new JLabel();
        speed.setForeground(Color.WHITE);
        speed.setFont(new Font("楷体", Font.PLAIN, AlgoController.TEXT_SIZE));
        canvas = new AlgoCanvas[canvasCount];
        for (int i = 0; i < canvasCount; i++) {
            canvas[i] = new RandomCanvas(names[i], canvasWidth, canvasHeight);
            add((Component) canvas[i]);
            if (i + 1 == canvasCount)
                ((JPanel) canvas[i]).add(speed);
        }
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public void renderCanvas(int tid, AlgoData data, Object... args) {
        canvas[tid].updateData(data, args);
    }

    public void renderText(String text) {
        speed.setText(text);
    }

}

