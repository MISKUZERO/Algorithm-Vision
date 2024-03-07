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
    private static final int DELAY = 1;//延迟（播放速度）
    //参数
    private static final int N = 500;//边界
    private static final int TEST_COUNT = 200000;//重复次数
    private static final int SCALE = 2;//增量
    private static final int CANVAS_COUNT = 2;//画布数（大于2会丢失直方图）
    private static final int CANVAS_ROWS = 1;//画布行数

    public static void launch() {
        FRAME = new AlgoFrame(TITLE, SCENE_WIDTH / CANVAS_COUNT, SCENE_HEIGHT, CANVAS_COUNT, CANVAS_ROWS);
        new Thread(() -> run(new AlgoArray(N))).start();
        new Thread(() -> run1(new AlgoArray(N))).start();
    }

    private static void run(AlgoData data) {
        Random random = new Random();
        AlgoArray arr = (AlgoArray) data;
        for (int i = 0; i < TEST_COUNT; i++) {
            int r = random.nextInt(Integer.MAX_VALUE);
            int index = r % N;
            arr.set(index, arr.get(index) + SCALE);
            update(0, data, r % SCENE_HEIGHT, (r >>> 16) % SCENE_HEIGHT);
        }
    }

    private static void run1(AlgoData data) {
        AlgoArray arr = (AlgoArray) data;
        for (int i = 0; i < TEST_COUNT; i++) {
            int r = SquareMid.nextInt(Integer.MAX_VALUE);
            int index = r % N;
            arr.set(index, arr.get(index) + SCALE);
            update(1, data, r % SCENE_HEIGHT, (r >>> 16) % SCENE_HEIGHT);
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