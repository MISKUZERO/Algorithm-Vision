package view.data;

/**
 * @author MiskuZero
 */
public interface AlgoList<E> extends AlgoData {

    /**
     * 获取索引位置元素
     *
     * @param index 索引
     * @return 索引位置元素
     */
    E get(int index);

    /**
     * 把元素写入索引位置
     *
     * @param index 索引
     * @param e     元素
     * @return 写入之前的元素
     */
    E set(int index, E e);

    /**
     * 添加元素值末尾，继承者可以决定是否允许调用该方法
     *
     * @param e 元素
     * @return 是否添加成功
     * @throws IllegalAccessException 不允许调用异常
     */
    boolean add(E e) throws IllegalAccessException;

    /**
     * 把元素添加到索引位置，继承者可以决定是否允许调用该方法
     *
     * @param index 索引
     * @param e     元素
     * @throws IllegalAccessException 不允许调用异常
     */
    void add(int index, E e) throws IllegalAccessException;

    /**
     * 移除指定位置元素，继承者可以决定是否允许调用该方法
     *
     * @param index 索引
     * @return 移除之前的元素
     * @throws IllegalAccessException 不允许调用异常
     */
    E remove(int index) throws IllegalAccessException;

}
