package view;

import algo.SquareMidRandom;
import view.component.AlgoCanvas;
import view.component.AlgoFrame;
import view.component.RanArrCanvas;
import view.data.AlgoArray;
import view.data.AlgoData;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.LockSupport;

/**
 * @author MiskuZero
 */
public class AlgoController {
    //窗口设置
    public static final String TITLE = "**仰望星空**";
    public static final String[] CANVAS_NAMES = {
            " 线性同余（JDK） + 固定轴",
            " 平方取中 + 随机轴",
            " 平方取中 + 随机轴&聚集相等元素"};
    public static final int FRAME_WIDTH = 2000;
    public static final int FRAME_HEIGHT = 1000;
    public static final int CANVAS_TEXT_SIZE = 32;
    public static final int CANVAS_COUNT = 3;
    public static final int CANVAS_ROWS = 1;
    public static final Class<? extends AlgoCanvas> CANVAS_TYPE = RanArrCanvas.class;//画布类型
    //画布中最大的正方形棱长（保证是画布是正方形）
    public static final int CANVAS_EDGE = Math.min(FRAME_WIDTH / CANVAS_COUNT, FRAME_HEIGHT / CANVAS_ROWS);
    //数据参数
    public static final int N = 333;//数组长度
    public static final int SCALE = 10;//增量
    public static final int DELAY = 160;//延迟（正常播放速度）
    public static final int FAST_WARD = 10;//快进延迟（快进播放速度）
    public static final int TEST_COUNT = 6666;//重复次数
    //线程相关
    private static final Thread[] THREADS = new Thread[CANVAS_COUNT];
    private static final CountDownLatch LATCH = new CountDownLatch(CANVAS_COUNT);//同步锁
    private static final CountDownLatch LATCH_1 = new CountDownLatch(CANVAS_COUNT);//同步锁

    public static void launch() {
        try {
            new AlgoFrame(TITLE, CANVAS_TYPE, CANVAS_EDGE, CANVAS_EDGE, CANVAS_COUNT, CANVAS_ROWS, CANVAS_NAMES);
        } catch (Exception e) {
            e.printStackTrace();
        }
        THREADS[0] = new Thread(() -> {
            try {
                run(new AlgoArray(N));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        THREADS[1] = new Thread(() -> {
            try {
                run1(new AlgoArray(N));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        THREADS[2] = new Thread(() -> {
            try {
                run2(new AlgoArray(N));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        for (Thread thread : THREADS)
            thread.start();
    }

    private static void run(AlgoData data) throws InterruptedException {
        int tid = 0;
        Random random = new Random();
        AlgoArray arr = (AlgoArray) data;
        for (int i = 0; i < TEST_COUNT; i++) {
            int r = random.nextInt(Integer.MAX_VALUE);
            int index = r % N;
            arr.set(index, arr.get(index) + SCALE);
            AlgoFrame.updateData(tid, data, r % CANVAS_EDGE, (r >>> 16) % CANVAS_EDGE);
        }
        LATCH.countDown();
        LATCH.await();//等待就绪
        Thread.sleep(3000);
        AlgoImpl.fixedPivQSort(tid, false, (AlgoArray) data, 0, N - 1);
        AlgoFrame.updateData(tid, arr, -1, -1, -1, -100, 0);
        LATCH_1.countDown();
        LATCH_1.await();//等待就绪
        Thread.sleep(1500);
        AlgoFrame.updateData(tid, arr, -1, -1, -1, -100, -1);//重置读写
        Thread.sleep(1500);
        AlgoImpl.fixedPivQSort(tid, true, (AlgoArray) data, 0, N - 1);
        AlgoFrame.updateData(tid, arr, -1, -1, -1, -100, 0);
    }

    private static void run1(AlgoData data) throws InterruptedException {
        int tid = 1;
        AlgoArray arr = (AlgoArray) data;
        for (int i = 0; i < TEST_COUNT; i++) {
            int r = SquareMidRandom.nextInt(Integer.MAX_VALUE);
            int index = r % N;
            arr.set(index, arr.get(index) + SCALE);
            AlgoFrame.updateData(tid, data, r % CANVAS_EDGE, (r >>> 16) % CANVAS_EDGE);
        }
        LATCH.countDown();
        LATCH.await();//等待就绪
        Thread.sleep(3000);
        AlgoImpl.ranPivQSort(tid, false, (AlgoArray) data, 0, N - 1);
        AlgoFrame.updateData(tid, arr, -1, -1, -1, -100, 0);
        LATCH_1.countDown();
        LATCH_1.await();//等待就绪
        Thread.sleep(1500);
        AlgoFrame.updateData(tid, arr, -1, -1, -1, -100, -1);//重置读写
        Thread.sleep(1500);
        AlgoImpl.ranPivQSort(tid, true, (AlgoArray) data, 0, N - 1);
        AlgoFrame.updateData(tid, arr, -1, -1, -1, -100, 0);
    }

    private static void run2(AlgoData data) throws InterruptedException {
        int tid = 2;
        AlgoArray arr = (AlgoArray) data;
        for (int i = 0; i < TEST_COUNT; i++) {
            int r = SquareMidRandom.nextInt(Integer.MAX_VALUE);
            int index = r % N;
            arr.set(index, arr.get(index) + SCALE);
            AlgoFrame.updateData(tid, data, r % CANVAS_EDGE, (r >>> 16) % CANVAS_EDGE);
        }
        LATCH.countDown();
        LATCH.await();//等待就绪
        Thread.sleep(3000);
        AlgoImpl.antiGaEquQSort(tid, (AlgoArray) data, 0, N - 1);
        AlgoFrame.updateData(tid, arr, -1, -1, -1, -100, 0);
        LATCH_1.countDown();
        LATCH_1.await();//等待就绪
        Thread.sleep(1500);
        AlgoFrame.updateData(tid, arr, -1, -1, -1, -100, -1);//重置读写
        Thread.sleep(1500);
        AlgoImpl.gaEquQSort(tid, (AlgoArray) data, 0, N - 1);
        AlgoFrame.updateData(tid, arr, -1, -1, -1, -100, 0);
    }

    public static void resume() {
        for (Thread thread : THREADS)
            LockSupport.unpark(thread);
    }

}