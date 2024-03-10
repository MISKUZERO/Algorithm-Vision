package view;

import algo.SquareMidRandom;
import view.component.AlgoFrame;
import view.data.AlgoArray;
import view.data.AlgoData;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.LockSupport;

/**
 * @author MiskuZero
 */
public class AlgoController {

    private static AlgoFrame frame;
    //设置
    private static final String TITLE = "**仰望星空**";
    private static final int SCENE_WIDTH = 2000;
    private static final int SCENE_HEIGHT = 1000;
    private static final int CANVAS_COUNT = 2;
    private static final int CANVAS_ROWS = 1;
    //画布中最大的正方形棱长
    public static final int CANVAS_EDGE = Math.min(SCENE_WIDTH / CANVAS_COUNT, SCENE_HEIGHT / CANVAS_ROWS);
    //参数
    private static final int N = 100;//数组长度
    private static final int TEST_COUNT = 5000;//重复次数
    private static final int SCALE = 10;//增量
    private static final int DELAY = 160;//延迟（正常播放速度）
    private static final int FAST_WARD = 10;//快进延迟（快进播放速度）

    private static final CountDownLatch latch = new CountDownLatch(CANVAS_COUNT);//同步锁
    private static final CountDownLatch latch1 = new CountDownLatch(CANVAS_COUNT);//同步锁
    private static int delay = DELAY;
    private static boolean pause;
    private static Thread[] threads;
    private static int mask;

    public static void launch() {
        frame = new AlgoFrame(TITLE, CANVAS_EDGE, CANVAS_EDGE, CANVAS_COUNT, CANVAS_ROWS);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {//右箭头16倍速快进，暂停模式下长按加快步骤
                    if (pause) {
                        mask++;
                        if (mask > 1) {//暂停下触发长按，短按则是一步调试
                            delay = FAST_WARD;
                            for (Thread thread : threads)
                                LockSupport.unpark(thread);
                        }
                    } else {
                        delay = FAST_WARD;
                        frame.renderText(" 16×");
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_SPACE) {//空格暂停
                    delay = DELAY;//重置播放速度
                    pause = !pause;
                    if (pause) {
                        frame.renderText(" pause");
                    } else {
                        frame.renderText("");
                        for (Thread thread : threads)
                            LockSupport.unpark(thread);
                    }
                }
                if (pause) {
                    if (keyCode == KeyEvent.VK_RIGHT) {//右箭头暂停状态下调试功能
                        mask = 0;
                        for (Thread thread : threads)
                            LockSupport.unpark(thread);
                    }
                } else {
                    if (keyCode == KeyEvent.VK_RIGHT) {
                        delay = DELAY;
                        frame.renderText("");
                    }
                    if (keyCode == KeyEvent.VK_SUBTRACT)//小键盘上的右上角”减号“满速运行（160倍速）
                        if (delay == 1) {
                            delay = DELAY;
                            frame.renderText("");
                        } else {
                            delay = 1;
                            frame.renderText(" 160×");
                        }
                }
            }
        });
        threads = new Thread[CANVAS_COUNT];
        threads[0] = new Thread(() -> {
            try {
                run(new AlgoArray(N));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threads[1] = new Thread(() -> {
            try {
                run1(new AlgoArray(N));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        for (Thread thread : threads)
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
            update(tid, data, r % CANVAS_EDGE, (r >>> 16) % CANVAS_EDGE);
        }
        latch.countDown();
        latch.await();//等待就绪
        Thread.sleep(3000);
        firstPivotQuickSort(tid, false, (AlgoArray) data, 0, N - 1);
        update(tid, arr, -1, -1, -1, -100, 0);
        latch1.countDown();
        latch1.await();//等待就绪
        Thread.sleep(3000);
        firstPivotQuickSort(tid, true, (AlgoArray) data, 0, N - 1);
        update(tid, arr, -1, -1, -1, -100, 0);
    }

    private static void run1(AlgoData data) throws InterruptedException {
        int tid = 1;
        AlgoArray arr = (AlgoArray) data;
        for (int i = 0; i < TEST_COUNT; i++) {
            int r = SquareMidRandom.nextInt(Integer.MAX_VALUE);
            int index = r % N;
            arr.set(index, arr.get(index) + SCALE);
            update(tid, data, r % CANVAS_EDGE, (r >>> 16) % CANVAS_EDGE);
        }
        latch.countDown();
        latch.await();//等待就绪
        Thread.sleep(3000);
        randomPivotQuickSort(tid, false, (AlgoArray) data, 0, N - 1);
        update(tid, arr, -1, -1, -1, -100, 0);
        latch1.countDown();
        latch1.await();//等待就绪
        Thread.sleep(3000);
        randomPivotQuickSort(tid, true, (AlgoArray) data, 0, N - 1);
        update(tid, arr, -1, -1, -1, -100, 0);
    }

    private static void firstPivotQuickSort(int tid, boolean asc, AlgoArray arr, int begin, int end) {
        if (begin < end) {
            int pivot = arr.get(begin);
            if (asc)
                pivot = divide(tid, arr, begin, end, pivot);
            else
                pivot = reverseDivide(tid, arr, begin, end, pivot);
            firstPivotQuickSort(tid, asc, arr, begin, pivot - 1);
            firstPivotQuickSort(tid, asc, arr, pivot + 1, end);
        }
    }

    private static void randomPivotQuickSort(int tid, boolean asc, AlgoArray arr, int begin, int end) {
        if (begin < end) {
            int pivotIndex = (int) (Math.random() * (end - begin + 1)) + begin;
            int pivot = arr.get(pivotIndex);
            int t = arr.get(begin);
            arr.set(begin, pivot);
            update(tid, arr, begin, -1, -1, pivot, 3);
            arr.set(pivotIndex, t);
            update(tid, arr, -1, -1, pivotIndex, pivot, 3);
            if (asc)
                pivotIndex = divide(tid, arr, begin, end, pivot);
            else
                pivotIndex = reverseDivide(tid, arr, begin, end, pivot);
            randomPivotQuickSort(tid, asc, arr, begin, pivotIndex - 1);
            randomPivotQuickSort(tid, asc, arr, pivotIndex + 1, end);
        }
    }

    private static int divide(int tid, AlgoArray arr, int begin, int end, int pivot) {
        int low = begin, high = end;
        while (low != high) {
            update(tid, arr, -1, -1, high, pivot, 1);
            while (low != high && arr.get(high) >= pivot) {
                high--;
                update(tid, arr, -1, -1, high, pivot, 1);
            }
            arr.set(low, arr.get(high));
            update(tid, arr, low, -1, high, pivot, 3);
            update(tid, arr, low, -1, -1, pivot, 1);
            while (low != high && arr.get(low) <= pivot) {
                low++;
                update(tid, arr, low, -1, -1, pivot, 1);
            }
            arr.set(high, arr.get(low));
            update(tid, arr, low, -1, high, pivot, 3);
        }
        arr.set(low, pivot);
        update(tid, arr, low, -1, -1, pivot, 2);
        return low;
    }

    private static int reverseDivide(int tid, AlgoArray arr, int begin, int end, int pivot) {
        int low = begin, high = end;
        while (low != high) {
            update(tid, arr, -1, -1, high, pivot, 1);
            while (low != high && arr.get(high) <= pivot) {
                high--;
                update(tid, arr, -1, -1, high, pivot, 1);
            }
            arr.set(low, arr.get(high));
            update(tid, arr, low, -1, high, pivot, 3);
            update(tid, arr, low, -1, -1, pivot, 1);
            while (low != high && arr.get(low) >= pivot) {
                low++;
                update(tid, arr, low, -1, -1, pivot, 1);
            }
            arr.set(high, arr.get(low));
            update(tid, arr, low, -1, high, pivot, 3);
        }
        arr.set(low, pivot);
        update(tid, arr, low, -1, -1, pivot, 2);
        return low;
    }

    public static void gatherEqu(int[] arr, int begin, int end) {
        if (end - begin < 40) {
            insertSort(arr, begin, end);
            return;
        }
        int pivotIndex = (int) (Math.random() * (end - begin)) + begin + 1;//长度为N的数组相对范围[1, N]
        int pivot = arr[pivotIndex];
        int i = begin, j = end, k = pivotIndex;
        while (k != j) {
            while (arr[j] > pivot) j--;
            if (arr[j] == pivot) {
                while (k != j && arr[k] == pivot) k++;
                arr[j] = arr[k];
                arr[k] = pivot;
                continue;
            }
            while (arr[i] < pivot) i++;
            if (i == pivotIndex) {
                if (++k == j) {
                    arr[i] = arr[j];
                    arr[j] = pivot;
                    pivotIndex++;//保证：arr[pivotIndex, k]之间值都是pivot
                    break;
                }
                arr[i] = arr[k];
                arr[k] = pivot;
                pivotIndex++;//保证：arr[pivotIndex, k]之间值都是pivot
            }
            int t = arr[i];
            arr[i] = arr[j];
            arr[j] = t;
        }
        //arr[j] = pivot，且arr[pivotIndex, k]之间值都是pivot
        while (i < pivotIndex) {
            while (arr[i] < pivot) i++;
            if (arr[i] != pivot)
                arr[j--] = arr[i];
            arr[i] = arr[--pivotIndex];
            arr[pivotIndex] = pivot;
        }
        gatherEqu(arr, begin, i);
        gatherEqu(arr, j + 1, end);
    }

    public static void insertSort(int[] arr, int begin, int end) {
        int len = end + 1;
        for (int i = begin + 1; i < len; i++)
            if (arr[i] < arr[i - 1]) {
                int j = i;
                int t = arr[j];
                while (j > 0 && arr[j - 1] > t) arr[j] = arr[--j];
                arr[j] = t;
            }
    }


    private static void update(int tid, AlgoData data, Object... args) {
        if (pause)
            LockSupport.park();
        frame.renderCanvas(tid, data, args);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}