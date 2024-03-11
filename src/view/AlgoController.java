package view;

import view.component.AlgoCanvas;
import view.component.AlgoFrame;
import view.component.RanAndSortCanvas;
import view.data.AlgoArray;
import view.data.AlgoData;

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
            " 线性同余（JDK） + 随机轴",
            " 平方取中 + 随机轴&聚集相等（三指针）",
            " 平方取中 + 中值轴&聚集相等（四指针）",
            " 平方取中 + 归并排序"
    };
    public static final int FRAME_WIDTH = 2000;
    public static final int FRAME_HEIGHT = 1000;
    public static final int CANVAS_TEXT_SIZE = 20;
    public static final int CANVAS_COUNT = 5;
    public static final int CANVAS_ROWS = 2;
    public static final Class<? extends AlgoCanvas> CANVAS_TYPE = RanAndSortCanvas.class;//画布类型
    //画布中最大的正方形棱长（保证是画布是正方形）
    public static final int CANVAS_EDGE = Math.min(FRAME_WIDTH / AlgoSupplier.upFloorDiv(CANVAS_COUNT, CANVAS_ROWS), FRAME_HEIGHT / CANVAS_ROWS);
    //数据参数
    public static final int DATA_LENGTH = 250;//数据长度
    public static final int SCALE = 5;//增量
    public static final int DELAY = 160;//延迟（正常播放速度）
    public static final int FAST_WARD = 10;//快进延迟（快进播放速度）
    public static final int TEST_COUNT = 5000;//重复次数
    //线程相关
    private static final Thread[] THREADS = new Thread[CANVAS_COUNT];
    private static final CountDownLatch LATCH = new CountDownLatch(CANVAS_COUNT);//同步锁
    private static final CountDownLatch LATCH_1 = new CountDownLatch(CANVAS_COUNT);//同步锁
    private static final CountDownLatch LATCH_2 = new CountDownLatch(CANVAS_COUNT);//同步锁

    public static void launch() {
        try {
            new AlgoFrame(TITLE, CANVAS_TYPE, CANVAS_EDGE, CANVAS_EDGE, CANVAS_COUNT, CANVAS_ROWS, CANVAS_NAMES);
        } catch (Exception e) {
            e.printStackTrace();
        }
        THREADS[0] = new Thread(() -> {
            try {
                run(new AlgoArray(DATA_LENGTH));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        THREADS[1] = new Thread(() -> {
            try {
                run1(new AlgoArray(DATA_LENGTH));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        THREADS[2] = new Thread(() -> {
            try {
                run2(new AlgoArray(DATA_LENGTH));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        THREADS[3] = new Thread(() -> {
            try {
                run3(new AlgoArray(DATA_LENGTH));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        THREADS[4] = new Thread(() -> {
            try {
                run4(new AlgoArray(DATA_LENGTH));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        for (Thread thread : THREADS)
            thread.start();
    }

    private static void run(AlgoData data) throws InterruptedException {
        int tid = 0;
        AlgoArray arr = (AlgoArray) data;
        AlgoSupplier.jdkRan(tid, arr, TEST_COUNT, SCALE);
        LATCH.countDown();
        LATCH.await();//等待就绪
        Thread.sleep(3000);
        AlgoSupplier.fixedPivQSort(tid, false, (AlgoArray) data, 0, DATA_LENGTH - 1);
        AlgoFrame.updateData(tid, arr, -1, -1, -1, -100, 0);
        LATCH_1.countDown();
        LATCH_1.await();//等待就绪
        Thread.sleep(1500);
        AlgoFrame.updateData(tid, arr, -1, -1, -1, -100, -1);//重置读写
        Thread.sleep(1500);
        AlgoSupplier.fixedPivQSort(tid, true, (AlgoArray) data, 0, DATA_LENGTH - 1);
        AlgoFrame.updateData(tid, arr, -1, -1, -1, -100, 0);
        LATCH_2.countDown();
        LATCH_2.await();//等待就绪
        Thread.sleep(3000);
        AlgoSupplier.mountain(tid, arr);
        Thread.sleep(1500);
        AlgoFrame.updateData(tid, arr, -1, -1, -1, -100, -1);//重置读写
        Thread.sleep(1500);
        AlgoSupplier.fixedPivQSort(tid, true, (AlgoArray) data, 0, DATA_LENGTH - 1);
        AlgoFrame.updateData(tid, arr, -1, -1, -1, -100, 0);

    }

    private static void run1(AlgoData data) throws InterruptedException {
        int tid = 1;
        AlgoArray arr = (AlgoArray) data;
        AlgoSupplier.jdkRan(tid, arr, TEST_COUNT, SCALE);
        LATCH.countDown();
        LATCH.await();//等待就绪
        Thread.sleep(3000);
        AlgoSupplier.ranPivQSort(tid, false, (AlgoArray) data, 0, DATA_LENGTH - 1);
        AlgoFrame.updateData(tid, arr, -1, -1, -1, -100, 0);
        LATCH_1.countDown();
        LATCH_1.await();//等待就绪
        Thread.sleep(1500);
        AlgoFrame.updateData(tid, arr, -1, -1, -1, -100, -1);//重置读写
        Thread.sleep(1500);
        AlgoSupplier.ranPivQSort(tid, true, (AlgoArray) data, 0, DATA_LENGTH - 1);
        AlgoFrame.updateData(tid, arr, -1, -1, -1, -100, 0);
        LATCH_2.countDown();
        LATCH_2.await();//等待就绪
        Thread.sleep(3000);
        AlgoSupplier.mountain(tid, arr);
        Thread.sleep(1500);
        AlgoFrame.updateData(tid, arr, -1, -1, -1, -100, -1);//重置读写
        Thread.sleep(1500);
        AlgoSupplier.ranPivQSort(tid, true, (AlgoArray) data, 0, DATA_LENGTH - 1);
        AlgoFrame.updateData(tid, arr, -1, -1, -1, -100, 0);
    }

    private static void run2(AlgoData data) throws InterruptedException {
        int tid = 2;
        AlgoArray arr = (AlgoArray) data;
        AlgoSupplier.squMidRan(tid, arr, TEST_COUNT, SCALE);
        LATCH.countDown();
        LATCH.await();//等待就绪
        Thread.sleep(3000);
        AlgoSupplier.antiGaEquQSort(tid, (AlgoArray) data, 0, DATA_LENGTH - 1);
        AlgoFrame.updateData(tid, arr, -1, -1, -1, -100, 0);
        LATCH_1.countDown();
        LATCH_1.await();//等待就绪
        Thread.sleep(1500);
        AlgoFrame.updateData(tid, arr, -1, -1, -1, -100, -1);//重置读写
        Thread.sleep(1500);
        AlgoSupplier.gaEquQSort(tid, (AlgoArray) data, 0, DATA_LENGTH - 1);
        AlgoFrame.updateData(tid, arr, -1, -1, -1, -100, 0);
        LATCH_2.countDown();
        LATCH_2.await();//等待就绪
        Thread.sleep(3000);
        AlgoSupplier.mountain(tid, arr);
        Thread.sleep(1500);
        AlgoFrame.updateData(tid, arr, -1, -1, -1, -100, -1);//重置读写
        Thread.sleep(1500);
        AlgoSupplier.gaEquQSort(tid, (AlgoArray) data, 0, DATA_LENGTH - 1);
        AlgoFrame.updateData(tid, arr, -1, -1, -1, -100, 0);
    }

    private static void run3(AlgoData data) throws InterruptedException {
        int tid = 3;
        AlgoArray arr = (AlgoArray) data;
        AlgoSupplier.squMidRan(tid, arr, TEST_COUNT, SCALE);
        LATCH.countDown();
        LATCH.await();//等待就绪
        Thread.sleep(3000);
        AlgoSupplier.antiTInsGaQSort(tid, (AlgoArray) data, 0, DATA_LENGTH - 1);
        AlgoFrame.updateData(tid, arr, -1, -1, -1, -100, 0);
        LATCH_1.countDown();
        LATCH_1.await();//等待就绪
        Thread.sleep(1500);
        AlgoFrame.updateData(tid, arr, -1, -1, -1, -100, -1);//重置读写
        Thread.sleep(1500);
        AlgoSupplier.tInsGaQSort(tid, (AlgoArray) data, 0, DATA_LENGTH - 1);
        AlgoFrame.updateData(tid, arr, -1, -1, -1, -100, 0);
        LATCH_2.countDown();
        LATCH_2.await();//等待就绪
        Thread.sleep(3000);
        AlgoSupplier.mountain(tid, arr);
        Thread.sleep(1500);
        AlgoFrame.updateData(tid, arr, -1, -1, -1, -100, -1);//重置读写
        Thread.sleep(1500);
        AlgoSupplier.tInsGaQSort(tid, (AlgoArray) data, 0, DATA_LENGTH - 1);
        AlgoFrame.updateData(tid, arr, -1, -1, -1, -100, 0);
    }

    private static void run4(AlgoData data) throws InterruptedException {
        int tid = 4;
        AlgoArray arr = (AlgoArray) data;
        AlgoArray tmp = new AlgoArray(DATA_LENGTH);
        AlgoSupplier.squMidRan(tid, arr, TEST_COUNT, SCALE);
        LATCH.countDown();
        LATCH.await();//等待就绪
        Thread.sleep(3000);
        AlgoSupplier.antiMergeSort(tid, (AlgoArray) data, 0, DATA_LENGTH - 1, tmp);
        AlgoFrame.updateData(tid, arr, -1, -1, -1, -100, 0);
        LATCH_1.countDown();
        LATCH_1.await();//等待就绪
        Thread.sleep(1500);
        AlgoFrame.updateData(tid, arr, -1, -1, -1, -100, -1);//重置读写
        Thread.sleep(1500);
        AlgoSupplier.mergeSort(tid, (AlgoArray) data, 0, DATA_LENGTH - 1, tmp);
        AlgoFrame.updateData(tid, arr, -1, -1, -1, -100, 0);
        LATCH_2.countDown();
        LATCH_2.await();//等待就绪
        Thread.sleep(3000);
        AlgoSupplier.mountain(tid, arr);
        Thread.sleep(1500);
        AlgoFrame.updateData(tid, arr, -1, -1, -1, -100, -1);//重置读写
        Thread.sleep(1500);
        AlgoSupplier.mergeSort(tid, (AlgoArray) data, 0, DATA_LENGTH - 1, tmp);
        AlgoFrame.updateData(tid, arr, -1, -1, -1, -100, 0);
    }


    public static void resume() {
        for (Thread thread : THREADS)
            LockSupport.unpark(thread);
    }

}