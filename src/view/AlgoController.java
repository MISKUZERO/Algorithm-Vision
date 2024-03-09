package view;

import algo.SquareMidRandom;
import view.component.AlgoFrame;
import view.data.AlgoArray;
import view.data.AlgoData;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
    private static final int CANVAS_COUNT = 2;
    private static final int CANVAS_ROWS = 1;
    //画布中最大的正方形棱长
    public static final int CANVAS_EDGE = Math.min(SCENE_WIDTH / CANVAS_COUNT, SCENE_HEIGHT / CANVAS_ROWS);
    //参数
    private static final int N = 50;//数组长度
    private static final int TEST_COUNT = 2000;//重复次数
    public static final int SCALE = 20;//增量
    private static final int DELAY = 200;//延迟（播放速度）
    private static int delay = DELAY;//控制延迟
//    private static final CountDownLatch latch = new CountDownLatch(CANVAS_COUNT);

    public static void launch() {
        FRAME = new AlgoFrame(TITLE, CANVAS_EDGE, CANVAS_EDGE, CANVAS_COUNT, CANVAS_ROWS);
        FRAME.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_RIGHT)
                    delay = DELAY >> 2;
                if (keyCode == KeyEvent.VK_SUBTRACT)
                    delay = 1;

            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_SUBTRACT)
                    delay = DELAY;
            }

        });
        new Thread(() -> run(new AlgoArray(N))).start();
        new Thread(() -> run1(new AlgoArray(N))).start();
    }

    private static void run(AlgoData data) {
        int tid = 0;
        Random random = new Random();
        AlgoArray arr = (AlgoArray) data;
        for (int i = 0; i < TEST_COUNT; i++) {
            int r = random.nextInt(Integer.MAX_VALUE);
            int index = r % N;
            arr.set(index, arr.get(index) + SCALE);
            update(tid, data, r % CANVAS_EDGE, (r >>> 16) % CANVAS_EDGE);
        }
        firstPivotQuickSort(tid, false, (AlgoArray) data, 0, N - 1);
        firstPivotQuickSort(tid, true, (AlgoArray) data, 0, N - 1);
    }

    private static void run1(AlgoData data) {
        int tid = 1;
        AlgoArray arr = (AlgoArray) data;
        for (int i = 0; i < TEST_COUNT; i++) {
            int r = SquareMidRandom.nextInt(Integer.MAX_VALUE);
            int index = r % N;
            arr.set(index, arr.get(index) + SCALE);
            update(tid, data, r % CANVAS_EDGE, (r >>> 16) % CANVAS_EDGE);
        }
        randomPivotQuickSort(tid, false, (AlgoArray) data, 0, N - 1);
        randomPivotQuickSort(tid, true, (AlgoArray) data, 0, N - 1);
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
            update(tid, arr, -1, -1, pivot, 3);
            arr.set(pivotIndex, t);
            update(tid, arr, -1, -1, pivot, 3);
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
            while (low != high && arr.get(high) >= pivot) {
                update(tid, arr, -1, high, pivot, 1);
                high--;
            }
            arr.set(low, arr.get(high));
            update(tid, arr, -1, -1, pivot, 3);
            while (low != high && arr.get(low) <= pivot) {
                update(tid, arr, low, -1, pivot, 1);
                low++;
            }
            arr.set(high, arr.get(low));
            update(tid, arr, -1, -1, pivot, 3);
        }
        arr.set(low, pivot);
        update(tid, arr, -1, -1, pivot, 2);
        return low;
    }

    private static int reverseDivide(int tid, AlgoArray arr, int begin, int end, int pivot) {
        int low = begin, high = end;
        while (low != high) {
            while (low != high && arr.get(high) <= pivot) {
                update(tid, arr, -1, high, pivot, 1);
                high--;
            }
            arr.set(low, arr.get(high));
            update(tid, arr, -1, -1, pivot, 3);
            while (low != high && arr.get(low) >= pivot) {
                update(tid, arr, low, -1, pivot, 1);
                low++;
            }
            arr.set(high, arr.get(low));
            update(tid, arr, -1, -1, pivot, 3);
        }
        arr.set(low, pivot);
        update(tid, arr, -1, -1, pivot, 2);
        return low;
    }

    private static void update(int tid, AlgoData data, Object... args) {
        FRAME.render(tid, data, args);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}