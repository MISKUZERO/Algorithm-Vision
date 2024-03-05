package view;

import java.awt.*;

/**
 * @author Administrator
 */
public class AlgoVisualizer {

    private static final int DELAY = 10;

    private final InsertionSortData data;
    private AlgoFrame frame;

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int n) {

        // 初始化数据
        data = new InsertionSortData(n, sceneHeight);

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Array Visualization", sceneWidth, sceneHeight);
            new Thread(this::run).start();
        });
    }

    public void run() {

        setData(0, -1);

        for (int i = 0; i < data.n(); i++) {

            setData(i, i);
            for (int j = i; j > 0 && data.get(j) < data.get(j - 1); j--) {
                data.swap(j, j - 1);
                setData(i + 1, j - 1);
            }
        }
        setData(data.n(), -1);

    }

    private void setData(int orderedIndex, int currentIndex) {
        data.orderedIndex = orderedIndex;
        data.currentIndex = currentIndex;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    public static void main(String[] args) {

        int sceneWidth = 1000;
        int sceneHeight = 800;
        int n = 100;

        new AlgoVisualizer(sceneWidth, sceneHeight, n);
    }
}