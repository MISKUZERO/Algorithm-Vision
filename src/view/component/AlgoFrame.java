package view.component;

import view.AlgoController;
import view.data.AlgoData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.Constructor;
import java.util.concurrent.locks.LockSupport;

public class AlgoFrame extends JFrame {

    private static final AlgoCanvas[] CANVAS = new AlgoCanvas[AlgoController.CANVAS_COUNT];
    private static final JLabel LABEL = new JLabel();
    private static int textSize = AlgoController.TEXT_SIZE;
    private static int delay = AlgoController.DELAY;
    private static int mask;

    private static boolean pause;

    public AlgoFrame(String title, Class<? extends AlgoCanvas> canvasClass, int canvasWidth, int canvasHeight, int canvasCount, int canvasRows, String[] names) throws Exception {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(canvasRows, 0));
        addCanvas(canvasClass, canvasWidth, canvasHeight, canvasCount, names);
        LABEL.setForeground(Color.RED);
        LABEL.setFont(new Font("楷体", Font.ITALIC, AlgoController.TEXT_SIZE));
        CANVAS[(canvasCount - 1) >> 1].add(LABEL);//显示在中间的画布上
        pack();
        registerListener();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void addCanvas(Class<? extends AlgoCanvas> canvasClass, int canvasWidth, int canvasHeight, int canvasCount, String[] names) throws Exception {
        Constructor<? extends AlgoCanvas> constructor = canvasClass.getConstructor(String.class, int.class, int.class);
        for (int i = 0; i < canvasCount; i++) {
            CANVAS[i] = constructor.newInstance(names[i], canvasWidth, canvasHeight);
            add(CANVAS[i]);
        }
    }

    private void registerListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_RIGHT) {//右箭头16倍速快进，暂停模式下长按加快步骤
                    if (pause) {
                        mask++;
                        if (mask > 1) {//暂停下触发长按，短按则是一步调试
                            delay = AlgoController.FAST_WARD;
                            AlgoController.resume();
                        }
                    } else {
                        delay = AlgoController.FAST_WARD;
                        LABEL.setForeground(Color.GREEN);
                        LABEL.setText(" 16×");
                    }
                    return;
                }
                if (keyCode == KeyEvent.VK_ADD) {
                    if (textSize != AlgoController.TEXT_MAX_SIZE)
                        textSize++;
                    adjustTextSize(textSize);
                    return;
                }
                if (keyCode == KeyEvent.VK_SUBTRACT) {
                    if (textSize != AlgoController.TEXT_MIN_SIZE)
                        textSize--;
                    adjustTextSize(textSize);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_SPACE) {//空格暂停
                    delay = AlgoController.DELAY;//重置播放速度
                    pause = !pause;
                    if (pause) {
                        LABEL.setForeground(Color.RED);
                        LABEL.setText(" pause");
                    } else {
                        LABEL.setText("");
                        AlgoController.resume();
                    }
                    return;
                }
                if (pause) {
                    if (keyCode == KeyEvent.VK_RIGHT) {//右箭头暂停状态下调试功能
                        mask = 0;
                        AlgoController.resume();
                    }
                } else {
                    if (keyCode == KeyEvent.VK_RIGHT) {
                        delay = AlgoController.DELAY;
                        LABEL.setText("");
                        return;
                    }
                    if (keyCode == KeyEvent.VK_MULTIPLY)//小键盘上的右上角”乘号“满速运行（160倍速）
                        if (delay == 1) {
                            delay = AlgoController.DELAY;
                            LABEL.setText("");
                        } else {
                            delay = 1;
                            LABEL.setForeground(Color.GREEN);
                            LABEL.setText(" 160×");
                        }
                }
            }
        });
    }

    private void adjustTextSize(int textSize) {
        LABEL.setFont(new Font("楷体", Font.ITALIC, textSize));
        for (AlgoCanvas canvas : CANVAS)
            canvas.adjustTextSize(textSize);
    }

    public static void updateData(int tid, AlgoData data, Object... args) {
        if (pause)
            LockSupport.park();
        CANVAS[tid].updateData(data, args);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

