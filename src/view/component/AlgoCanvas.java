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

    /**
     * 调整字体尺寸
     *
     * @param textSize 新的字体大小
     */
    public abstract void adjustTextSize(int textSize);

    /**
     * 展示文本
     */
    public abstract void showText();

    /**
     * 关闭文本
     */
    public abstract void closeText();

    /**
     * 控制是否渲染数据
     *
     * @param isRender 是否渲染
     */
    public abstract void renderData(boolean isRender);
}
