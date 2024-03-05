package view;

import random.SquareMid;
import view.data.AlgoArray;
import view.data.AlgoData;

/**
 * @author MiskuZero
 */
public class AlgoLauncher {

    private static final int N = 200;
    private static final int DELAY = 2;
    private static final int SCENE_WIDTH = 1920;
    private static final int SCENE_HEIGHT = 1080;
    private static final String TITLE = "Algo Visualization";
    private static final AlgoData[] DATA = new AlgoData[2];
    private static AlgoFrame FRAME;

    public static void run() {
        FRAME = new AlgoFrame(TITLE, SCENE_WIDTH / DATA.length, SCENE_HEIGHT, 2);
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
        for (int i = 0; i < N; i++) {
            update();
            data.add(i, SquareMid.nextInt(SCENE_HEIGHT) + 1);
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
            update();
            data.remove(0);
        }
        update();
    }

    private static void threadWork1(AlgoArray data) {
        for (int i = 0; i < N; i++) {
            update();
            data.add(i, SquareMid.nextInt(SCENE_HEIGHT) + 1);
        }
    }

    private static void update() {
        FRAME.render(DATA);
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}