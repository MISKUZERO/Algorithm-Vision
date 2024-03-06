package view.component;

import view.data.AlgoData;

/**
 * @author MiskuZero
 */
public interface AlgoCanvas {

    /**
     * 更新数据，并重写绘制组件
     *
     * @param data 数据
     * @param args 附加参数
     */
    void updateData(AlgoData data, Object... args);
}
