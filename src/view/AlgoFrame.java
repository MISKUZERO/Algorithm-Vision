package view;

import view.canvas.ArrayCanvas;
import view.data.AlgoArray;
import view.data.AlgoData;

import javax.swing.*;

/**
 * @author MiskuZero
 */
public class AlgoFrame extends JFrame {

    private final ArrayCanvas canvas;

    public AlgoFrame(String title, int canvasWidth, int canvasHeight) {

        super(title);

        canvas = new ArrayCanvas(canvasWidth, canvasHeight, new AlgoArray());
        setContentPane(canvas);
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        setVisible(true);
    }

    public void render(AlgoData<Integer> data) {
        canvas.updateData(data);
        repaint();
    }

}