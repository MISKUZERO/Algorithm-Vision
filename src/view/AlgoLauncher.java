package view;

import random.SquareMid;
import view.data.AlgoArray;
import view.data.AlgoData;

/**
 * @author MiskuZero
 */
public class AlgoLauncher {

    private static final int N = 20;
    private static final int DELAY = 20;
    private static final int SCENE_WIDTH = 1600;
    private static final int SCENE_HEIGHT = 800;
    private static final String TITLE = "Algo Visualization";
    private static AlgoData<Integer> DATA;
    private static AlgoFrame FRAME;

    public static void run() {
        FRAME = new AlgoFrame(TITLE, SCENE_WIDTH, SCENE_HEIGHT);
        AlgoArray data = new AlgoArray(N);
        DATA = data;
        for (int i = 0; i < N; i++) {
            update();
            data.add(i, SquareMid.nextInt(SCENE_HEIGHT));
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
            data.remove(0);
            update();
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