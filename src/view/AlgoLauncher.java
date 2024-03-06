package view;

import random.SquareMid;
import view.data.AlgoArray;
import view.data.AlgoData;

import java.util.Random;

/**
 * @author MiskuZero
 */
public class AlgoLauncher {

    private static AlgoFrame FRAME;
    //设置
    private static final String TITLE = "仰望星空";
    private static final int SCENE_WIDTH = 2000;
    private static final int SCENE_HEIGHT = 1000;
    private static final int DELAY = 1;
    //参数
    private static final int N = 500;//边界
    private static final int TEST_COUNT = 100000;//重复次数
    private static final int SCALE = 2;//增量
    private static final int CANVAS_COUNT = 2;//画布数

    public static void launch() {
        FRAME = new AlgoFrame(TITLE, SCENE_WIDTH / CANVAS_COUNT, SCENE_HEIGHT, CANVAS_COUNT);
        new Thread(() -> run(new AlgoArray(N))).start();
        run1(new AlgoArray(N));
    }

    private static void run(AlgoArray data) {
        Random random = new Random();
        for (int i = 0; i < TEST_COUNT; i++) {
            int r = random.nextInt(TEST_COUNT) % N;
            data.set(r, data.get(r) + SCALE);
            update(0, data, r);
        }
    }

    private static void run1(AlgoArray data) {
        for (int i = 0; i < TEST_COUNT; i++) {
            int r = SquareMid.nextInt(TEST_COUNT) % N;
            data.set(r, data.get(r) + SCALE);
            update(1, data, r);
        }
    }

    private static void update(int tid, AlgoData data, int index) {
        FRAME.render(tid, data, index);
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}