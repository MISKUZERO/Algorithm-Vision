package view;

import random.SquareMid;
import view.data.AlgoArray;
import view.data.AlgoData;

import java.util.Random;

/**
 * @author MiskuZero
 */
public class AlgoLauncher {

    private static final int DELAY = 1;
    private static final int TEST_COUNT = 100000;
    private static final int N = 500;
    private static final int SCALE = 2;
    private static final int SCENE_WIDTH = 2000;
    private static final int SCENE_HEIGHT = 1000;
    private static final int CANVAS_COUNT = 2;
    private static final AlgoData[] DATA = new AlgoData[CANVAS_COUNT];
    private static final String TITLE = "Algo Visualization";
    private static AlgoFrame FRAME;

    public static void run() {
        FRAME = new AlgoFrame(TITLE, SCENE_WIDTH / CANVAS_COUNT, SCENE_HEIGHT, CANVAS_COUNT);
        new Thread(() -> {
            AlgoArray data = new AlgoArray(N);
            DATA[0] = data;
            threadWork(data);
        }).start();
        AlgoArray data = new AlgoArray(N);
        DATA[1] = data;
        threadWork1(data);
    }

    private static void threadWork(AlgoArray data) {
        Random random = new Random();
        for (int i = 0; i < TEST_COUNT; i++) {
            int r = random.nextInt(TEST_COUNT) % N;
            data.set(r, data.get(r) + SCALE);
            update(r);
        }
    }

    private static void threadWork1(AlgoArray data) {
        for (int i = 0; i < TEST_COUNT; i++) {
            int r = SquareMid.nextInt(TEST_COUNT) % N;
            data.set(r, data.get(r) + SCALE);
            update(r);
        }
    }

    private static void update(int index) {
        FRAME.render(DATA, index);
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}