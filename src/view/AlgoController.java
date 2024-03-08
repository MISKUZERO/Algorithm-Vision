package view;

import random.SquareMid;
import view.component.AlgoFrame;
import view.data.AlgoArray;
import view.data.AlgoData;

import java.util.Random;

/**
 * @author MiskuZero
 */
public class AlgoController {

    private static AlgoFrame FRAME;
    //设置
    private static final String TITLE = "**仰望星空**";
    private static final int SCENE_WIDTH = 2000;
    private static final int SCENE_HEIGHT = 1000;
    private static final int CANVAS_COUNT = 3;
    private static final int CANVAS_ROWS = 1;
    //画布中最大的正方形棱长
    public static final int CANVAS_EDGE = Math.min(SCENE_WIDTH / CANVAS_COUNT, SCENE_HEIGHT / CANVAS_ROWS);
    //参数
    private static final int N = 160;//数组长度
    private static final int TEST_COUNT = 10000;//重复次数
    private static final int SCALE = 1;//增量
    private static final int DELAY = 1;//延迟（播放速度）

    public static void launch() {
        FRAME = new AlgoFrame(TITLE, CANVAS_EDGE, CANVAS_EDGE, CANVAS_COUNT, CANVAS_ROWS);
        new Thread(() -> run(new AlgoArray(N))).start();
        new Thread(() -> run1(new AlgoArray(N))).start();
        new Thread(() -> run2(new AlgoArray(N))).start();
    }

    private static void run(AlgoData data) {
        Random random = new Random();
        AlgoArray arr = (AlgoArray) data;
        for (int i = 0; i < TEST_COUNT; i++) {
            int r = random.nextInt(Integer.MAX_VALUE);
            int index = r % N;
            arr.set(index, arr.get(index) + SCALE);
            update(0, data, r % CANVAS_EDGE, (r >>> 16) % CANVAS_EDGE);
        }
    }

    private static void run1(AlgoData data) {
        AlgoArray arr = (AlgoArray) data;
        for (int i = 0; i < TEST_COUNT; i++) {
            int r = SquareMid.nextInt(Integer.MAX_VALUE);
            int index = r % N;
            arr.set(index, arr.get(index) + SCALE);
            update(1, data, r % CANVAS_EDGE, (r >>> 16) % CANVAS_EDGE);
        }
    }

    private static void run2(AlgoData data) {
        AlgoArray arr = (AlgoArray) data;
        for (int i = 0; i < TEST_COUNT; i++) {
            int r = SquareMid.nextInt(Integer.MAX_VALUE);
            int index = r % N;
            arr.set(index, arr.get(index) + SCALE);
            update(2, data, r % CANVAS_EDGE, (r >>> 16) % CANVAS_EDGE);
        }
    }

    private static void update(int tid, AlgoData data, Object... args) {
        FRAME.render(tid, data, args);
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}