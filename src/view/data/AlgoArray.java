package view.data;

/**
 * 只能存储正整数
 *
 * @author MiskuZero
 */
public class AlgoArray implements AlgoList<Integer> {

    private final int[] eleData;
    private int size = 0;

    public AlgoArray() {
        this.eleData = new int[100];
    }

    public AlgoArray(int capacity) {
        this.eleData = new int[capacity];
    }

    public int capacity() {
        return eleData.length;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Integer get(int index) {
        return eleData[index];
    }

    /**
     * 写入数值到索引位置，若值小于1则删除该值
     *
     * @param index   索引
     * @param integer 数值
     * @return 写入之前的元素
     */
    @Override
    public Integer set(int index, Integer integer) {
        int[] array = eleData;
        int res = array[index];
        if (integer < 1) {
            remove(index);
            return res;
        }
        array[index] = integer;
        return res;
    }

    @Override
    public boolean add(Integer integer) throws IllegalAccessException {
        throw new IllegalAccessException("这个方法在数组中不允许！");
    }

    /**
     * 添加数值至索引位置，若值小于1则添加失败
     *
     * @param index   索引
     * @param integer 元素
     */
    @Override
    public void add(int index, Integer integer) {
        if (integer < 1)
            throw new IllegalArgumentException("数值不能小于1！");
        eleData[index] = integer;
        size++;
    }

    @Override
    public Integer remove(int index) {
        int[] array = eleData;
        int res = array[index];
        array[index] = 0;
        size--;
        return res;
    }
}
