package view;

import random.SquareMid;
import view.data.AlgoArrayList;
import view.data.AlgoList;

import java.awt.*;

/**
 * @author MiskuZero
 */
public class AlgoVisualizer {

    private static final int N = 200;
    private static final int DELAY = 20;
    private final int SCENE_WIDTH = 1600;
    private final int SCENE_HEIGHT = 800;
    private final AlgoList<Integer> data = new AlgoArrayList();
    private AlgoFrame frame;

    public AlgoVisualizer() {
        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Algo Visualization", SCENE_WIDTH, SCENE_HEIGHT);
            new Thread(this::run).start();
        });
    }

    public void run() {
        for (int i = 0; i < N; i++) {
            update();
            try {
                data.add(SquareMid.nextInt(SCENE_HEIGHT));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < data.size(); i++) {
            update();
            for (int j = i; j > 0 && data.get(j) < data.get(j - 1); j--) {
                Integer t = data.get(j);
                data.set(j, data.get(j - 1));
                data.set(j - 1, t);
                update();
            }
        }
        for (int i = 0; i < N; i++) {
            data.remove(0);
            update();
        }
    }

    private void update() {
        frame.render(data);
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AlgoVisualizer();
    }
}