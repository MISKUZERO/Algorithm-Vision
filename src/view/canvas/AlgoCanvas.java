package view.canvas;

import view.data.AlgoData;

/**
 * @author MiskuZero
 */
public interface AlgoCanvas<E> {
    /**
     * 更新数据
     *
     * @param data 数据
     */
    void updateData(AlgoData<E> data);
}
