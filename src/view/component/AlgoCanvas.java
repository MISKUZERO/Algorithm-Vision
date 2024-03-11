package view.component;

import view.data.AlgoData;

import javax.swing.*;

/**
 * @author MiskuZero
 */
public abstract class AlgoCanvas extends JPanel {
    /**
     * 更新数据，并重写绘制组件
     *
     * @param data 数据
     * @param args 附加参数
     */
    public abstract void updateData(AlgoData data, Object... args);
}
