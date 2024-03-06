package view.canvas;

import view.data.AlgoData;

/**
 * @author MiskuZero
 */
public interface AlgoCanvas {

    /**
     * 更新数据，并重写绘制组件
     *
     * @param data  要更新的数据集
     * @param index 数据在其数据集的索引
     */
    void updateData(AlgoData data, int index);
}
