package view.data;

/**
 * @author MiskuZero
 */
public class AlgoArray implements AlgoList<Integer> {

    private final int[] eleData;

    public int[] getEleData() {
        return eleData;
    }

    public AlgoArray() {
        this.eleData = new int[16];
    }

    public AlgoArray(int capacity) {
        this.eleData = new int[capacity];
    }

    public int capacity() {
        return eleData.length;
    }

    @Override
    public Integer get(int index) {
        return eleData[index];
    }

    @Override
    public Integer set(int index, Integer integer) {
        int[] array = eleData;
        int res = array[index];
        array[index] = integer;
        return res;
    }

    @Override
    public boolean add(Integer integer) throws IllegalAccessException {
        throw new IllegalAccessException("数组不支持添加！");
    }

    @Override
    public void add(int index, Integer integer) throws IllegalAccessException {
        throw new IllegalAccessException("数组不支持添加！");
    }

    @Override
    public Integer remove(int index) throws IllegalAccessException {
        throw new IllegalAccessException("数组不支持移除！");
    }

}
