package view;

import algo.SquMidRandom;
import view.component.AlgoFrame;
import view.data.AlgoArray;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Random;

/**
 * @author MiskuZero
 */
public class AlgoSupplier {

    /**
     * 两数相除，结果向上取整
     *
     * @param x 被除数
     * @param y 除数
     * @return 向上取整后的商
     */
    public static int ceilDiv(int x, int y) {
        int r = x / y;
        if ((x ^ y) > 0 && r * y != x)
            return r + 1;
        else
            return r;
    }

    /**
     * 计算卡方值，用于卡方检验
     *
     * @param obs   频次
     * @param exps  期望频次
     * @param scale 计算结果保留的小数位数（四舍五入）
     * @return 卡方值
     */
    public static BigDecimal chiSquVal(int[] obs, int[] exps, int scale) {
        BigDecimal res = new BigDecimal(0);
        for (int i = 0; i < exps.length; i++)
            res = res.add(new BigDecimal(obs[i]).add(new BigDecimal(exps[i]).negate()).pow(2).divide(new BigDecimal(exps[i]), scale, RoundingMode.HALF_UP));
        return res;
    }

    public static void jdkRan(int tid, AlgoArray arr, int testCount, int scale) {
        Random random = new Random();
        int capacity = arr.capacity();
        for (int i = 0; i < testCount; i++) {
            int r = random.nextInt(Integer.MAX_VALUE);
            int index = r % capacity;
            arr.set(index, arr.get(index) + scale);
            AlgoFrame.updateData(tid, arr, r % AlgoController.CANVAS_EDGE, (r >>> 16) % AlgoController.CANVAS_EDGE);
        }
    }

    public static void squMidRan(int tid, AlgoArray arr, int testCount, int scale) {
        int capacity = arr.capacity();
        for (int i = 0; i < testCount; i++) {
            int r = SquMidRandom.nextInt(Integer.MAX_VALUE);
            int index = r % capacity;
            arr.set(index, arr.get(index) + scale);
            AlgoFrame.updateData(tid, arr, r % AlgoController.CANVAS_EDGE, (r >>> 16) % AlgoController.CANVAS_EDGE);
        }
    }

    /**
     * 将数组按照数值排列成“山形”，即数组中心值最大，离数组中心越远的数值越小<p>
     * 主要的用来模拟一些排序算法的极端情况，例如固定轴和取三数中间值为轴的快速排序算法
     *
     * @param tid 渲染的线程ID
     * @param arr 待排列的数组
     */
    public static void mountain(int tid, AlgoArray arr) {
        int capacity = arr.capacity();
        int[] ints = new int[capacity];
        for (int i = 0; i < capacity; i++)
            ints[i] = arr.get(i);
        Arrays.sort(ints);
        for (int i = 0, j = 0, k = capacity - 1; i < capacity; ) {
            arr.set(j++, ints[i++]);
            AlgoFrame.updateData(tid, arr, -1, -1, -1, -100, 0);
            if (i < capacity) {
                arr.set(k--, ints[i++]);
                AlgoFrame.updateData(tid, arr, -1, -1, -1, -100, 0);
            }
        }
    }

    public static void mergeSort(int tid, AlgoArray arr, int begin, int end, AlgoArray tmp) {
        if (begin < end) {
            int mid = (end + begin) >> 1;
            mergeSort(tid, arr, begin, mid, tmp);
            mergeSort(tid, arr, mid + 1, end, tmp);
            int m = mid + 1, len = end + 1;
            for (int i = begin; i < len; i++) {
                tmp.set(i, arr.get(i));
                AlgoFrame.updateData(tid, arr, i, -1, -1, -100, 3);
            }
            int i = begin, j = m, k = begin;
            while (i != m && j != len) {
                AlgoFrame.updateData(tid, arr, i, k, j, -100, 1);
                AlgoFrame.updateData(tid, arr, i, k, j, -100, 1);
                if (tmp.get(i) < tmp.get(j))
                    arr.set(k++, tmp.get(i++));
                else
                    arr.set(k++, tmp.get(j++));
                AlgoFrame.updateData(tid, arr, i, k, j, -100, 3);
            }
            while (i != m) {
                arr.set(k++, tmp.get(i++));
                AlgoFrame.updateData(tid, arr, i, k, j, -100, 3);
            }
            while (j != len) {
                arr.set(k++, tmp.get(j++));
                AlgoFrame.updateData(tid, arr, i, k, j, -100, 3);
            }
        }
    }

    public static void antiMergeSort(int tid, AlgoArray arr, int begin, int end, AlgoArray tmp) {
        if (begin < end) {
            int mid = (end + begin) >> 1;
            antiMergeSort(tid, arr, begin, mid, tmp);
            antiMergeSort(tid, arr, mid + 1, end, tmp);
            int m = mid + 1, len = end + 1;
            for (int i = begin; i < len; i++) {
                tmp.set(i, arr.get(i));
                AlgoFrame.updateData(tid, arr, i, -1, -1, -100, 3);
            }
            int i = begin, j = m, k = begin;
            while (i != m && j != len) {
                AlgoFrame.updateData(tid, arr, i, k, j, -100, 1);
                AlgoFrame.updateData(tid, arr, i, k, j, -100, 1);
                if (tmp.get(i) > tmp.get(j))
                    arr.set(k++, tmp.get(i++));
                else
                    arr.set(k++, tmp.get(j++));
                AlgoFrame.updateData(tid, arr, i, k, j, -100, 3);
            }
            while (i != m) {
                arr.set(k++, tmp.get(i++));
                AlgoFrame.updateData(tid, arr, i, k, j, -100, 3);
            }
            while (j != len) {
                arr.set(k++, tmp.get(j++));
                AlgoFrame.updateData(tid, arr, i, k, j, -100, 3);
            }
        }
    }


    public static void fixedPivQSort(int tid, boolean asc, AlgoArray arr, int begin, int end) {
        if (begin < end) {
            int pivot = arr.get(begin);
            if (asc)
                pivot = divide(tid, arr, begin, end, pivot);
            else
                pivot = antiDivide(tid, arr, begin, end, pivot);
            fixedPivQSort(tid, asc, arr, begin, pivot - 1);
            fixedPivQSort(tid, asc, arr, pivot + 1, end);
        }
    }

    public static void ranPivQSort(int tid, boolean asc, AlgoArray arr, int begin, int end) {
        if (begin < end) {
            int pivotIndex = (int) (Math.random() * (end - begin + 1)) + begin;
            int pivot = arr.get(pivotIndex);
            AlgoFrame.updateData(tid, arr, -1, -1, pivotIndex, pivot, 1);
            int t = arr.get(begin);
            AlgoFrame.updateData(tid, arr, begin, -1, -1, pivot, 1);
            arr.set(begin, pivot);
            AlgoFrame.updateData(tid, arr, begin, -1, -1, pivot, 2);
            arr.set(pivotIndex, t);
            AlgoFrame.updateData(tid, arr, -1, -1, pivotIndex, pivot, 2);
            if (asc)
                pivotIndex = divide(tid, arr, begin, end, pivot);
            else
                pivotIndex = antiDivide(tid, arr, begin, end, pivot);
            ranPivQSort(tid, asc, arr, begin, pivotIndex - 1);
            ranPivQSort(tid, asc, arr, pivotIndex + 1, end);
        }
    }

    public static int divide(int tid, AlgoArray arr, int begin, int end, int pivot) {
        int low = begin, high = end;
        while (low != high) {
            AlgoFrame.updateData(tid, arr, -1, -1, high, pivot, 1);
            while (low != high && arr.get(high) >= pivot) {
                high--;
                AlgoFrame.updateData(tid, arr, -1, -1, high, pivot, 1);
            }
            arr.set(low, arr.get(high));
            AlgoFrame.updateData(tid, arr, low, -1, high, pivot, 3);
            AlgoFrame.updateData(tid, arr, low, -1, -1, pivot, 1);
            while (low != high && arr.get(low) <= pivot) {
                low++;
                AlgoFrame.updateData(tid, arr, low, -1, -1, pivot, 1);
            }
            arr.set(high, arr.get(low));
            AlgoFrame.updateData(tid, arr, low, -1, high, pivot, 3);
        }
        arr.set(low, pivot);
        AlgoFrame.updateData(tid, arr, low, -1, -1, pivot, 2);
        return low;
    }

    public static int antiDivide(int tid, AlgoArray arr, int begin, int end, int pivot) {
        int low = begin, high = end;
        while (low != high) {
            AlgoFrame.updateData(tid, arr, -1, -1, high, pivot, 1);
            while (low != high && arr.get(high) <= pivot) {
                high--;
                AlgoFrame.updateData(tid, arr, -1, -1, high, pivot, 1);
            }
            arr.set(low, arr.get(high));
            AlgoFrame.updateData(tid, arr, low, -1, high, pivot, 3);
            AlgoFrame.updateData(tid, arr, low, -1, -1, pivot, 1);
            while (low != high && arr.get(low) >= pivot) {
                low++;
                AlgoFrame.updateData(tid, arr, low, -1, -1, pivot, 1);
            }
            arr.set(high, arr.get(low));
            AlgoFrame.updateData(tid, arr, low, -1, high, pivot, 3);
        }
        arr.set(low, pivot);
        AlgoFrame.updateData(tid, arr, low, -1, -1, pivot, 2);
        return low;
    }

    public static void gaEquQSort(int tid, AlgoArray arr, int begin, int end) {
        if (end - begin < 20) {
            iSortInQSort(tid, arr, begin, end);
            return;
        }
        int pivotIndex = (int) (Math.random() * (end - begin)) + begin + 1;//长度为N的数组相对范围[1, N]
        int pivot = arr.get(pivotIndex);
        int i = begin, j = end, k = pivotIndex;
        AlgoFrame.updateData(tid, arr, i, k, j, pivot, 1);
        while (k != j) {
            AlgoFrame.updateData(tid, arr, i, k, j, pivot, 1);
            while (arr.get(j) > pivot) {
                j--;
                AlgoFrame.updateData(tid, arr, i, k, j, pivot, 1);
            }
            AlgoFrame.updateData(tid, arr, i, k, j, pivot, 1);
            if (arr.get(j) == pivot) {
                AlgoFrame.updateData(tid, arr, i, k, j, pivot, 1);
                while (k != j && arr.get(k) == pivot) {
                    k++;
                    AlgoFrame.updateData(tid, arr, i, k, j, pivot, 1);
                }
                arr.set(j, arr.get(k));
                AlgoFrame.updateData(tid, arr, i, k, j, pivot, 3);
                arr.set(k, pivot);
                AlgoFrame.updateData(tid, arr, i, k, j, pivot, 2);
                continue;
            }
            AlgoFrame.updateData(tid, arr, i, k, j, pivot, 1);
            while (arr.get(i) < pivot) {
                i++;
                AlgoFrame.updateData(tid, arr, i, k, j, pivot, 1);
            }
            if (i == pivotIndex) {
                if (++k == j) {
                    arr.set(i, arr.get(j));
                    AlgoFrame.updateData(tid, arr, i, k, j, pivot, 3);
                    arr.set(j, pivot);
                    AlgoFrame.updateData(tid, arr, i, k, j, pivot, 2);
                    pivotIndex++;//保证：arr[pivotIndex, k]之间值都是pivot
                    break;
                }
                arr.set(i, arr.get(k));
                AlgoFrame.updateData(tid, arr, i, k, j, pivot, 3);
                arr.set(k, pivot);
                AlgoFrame.updateData(tid, arr, i, k, j, pivot, 2);
                pivotIndex++;//保证：arr[pivotIndex, k]之间值都是pivot
            }
            int t = arr.get(i);
            AlgoFrame.updateData(tid, arr, i, k, j, pivot, 1);
            arr.set(i, arr.get(j));
            AlgoFrame.updateData(tid, arr, i, k, j, pivot, 3);
            arr.set(j, t);
            AlgoFrame.updateData(tid, arr, i, k, j, pivot, 2);

        }
        //arr[j] = pivot，且arr[pivotIndex, k]之间值都是pivot
        while (i < pivotIndex) {
            AlgoFrame.updateData(tid, arr, i, k, j, pivot, 1);
            while (arr.get(i) < pivot) {
                i++;
                AlgoFrame.updateData(tid, arr, i, k, j, pivot, 1);
            }
            AlgoFrame.updateData(tid, arr, i, k, j, pivot, 1);
            if (arr.get(i) != pivot) {
                arr.set(j--, arr.get(i));
                AlgoFrame.updateData(tid, arr, i, k, j, pivot, 2);
            }
            arr.set(i, arr.get(--pivotIndex));
            AlgoFrame.updateData(tid, arr, i, k, j, pivot, 3);
            arr.set(pivotIndex, pivot);
            AlgoFrame.updateData(tid, arr, i, k, j, pivot, 2);
        }
        gaEquQSort(tid, arr, begin, i);
        gaEquQSort(tid, arr, j + 1, end);
    }

    public static void antiGaEquQSort(int tid, AlgoArray arr, int begin, int end) {
        if (end - begin < 20) {
            antiISortInQSort(tid, arr, begin, end);
            return;
        }
        int pivotIndex = (int) (Math.random() * (end - begin)) + begin + 1;//长度为N的数组相对范围[1, N]
        int pivot = arr.get(pivotIndex);
        int i = begin, j = end, k = pivotIndex;
        AlgoFrame.updateData(tid, arr, i, k, j, pivot, 1);
        while (k != j) {
            AlgoFrame.updateData(tid, arr, i, k, j, pivot, 1);
            while (arr.get(j) < pivot) {
                j--;
                AlgoFrame.updateData(tid, arr, i, k, j, pivot, 1);
            }
            AlgoFrame.updateData(tid, arr, i, k, j, pivot, 1);
            if (arr.get(j) == pivot) {
                AlgoFrame.updateData(tid, arr, i, k, j, pivot, 1);
                while (k != j && arr.get(k) == pivot) {
                    k++;
                    AlgoFrame.updateData(tid, arr, i, k, j, pivot, 1);
                }
                arr.set(j, arr.get(k));
                AlgoFrame.updateData(tid, arr, i, k, j, pivot, 3);
                arr.set(k, pivot);
                AlgoFrame.updateData(tid, arr, i, k, j, pivot, 2);
                continue;
            }
            AlgoFrame.updateData(tid, arr, i, k, j, pivot, 1);
            while (arr.get(i) > pivot) {
                i++;
                AlgoFrame.updateData(tid, arr, i, k, j, pivot, 1);
            }
            if (i == pivotIndex) {
                if (++k == j) {
                    arr.set(i, arr.get(j));
                    AlgoFrame.updateData(tid, arr, i, k, j, pivot, 3);
                    arr.set(j, pivot);
                    AlgoFrame.updateData(tid, arr, i, k, j, pivot, 2);
                    pivotIndex++;//保证：arr[pivotIndex, k]之间值都是pivot
                    break;
                }
                arr.set(i, arr.get(k));
                AlgoFrame.updateData(tid, arr, i, k, j, pivot, 3);
                arr.set(k, pivot);
                AlgoFrame.updateData(tid, arr, i, k, j, pivot, 2);
                pivotIndex++;//保证：arr[pivotIndex, k]之间值都是pivot
            }
            int t = arr.get(i);
            AlgoFrame.updateData(tid, arr, i, k, j, pivot, 1);
            arr.set(i, arr.get(j));
            AlgoFrame.updateData(tid, arr, i, k, j, pivot, 3);
            arr.set(j, t);
            AlgoFrame.updateData(tid, arr, i, k, j, pivot, 2);

        }
        //arr[j] = pivot，且arr[pivotIndex, k]之间值都是pivot
        while (i < pivotIndex) {
            AlgoFrame.updateData(tid, arr, i, k, j, pivot, 1);
            while (arr.get(i) > pivot) {
                i++;
                AlgoFrame.updateData(tid, arr, i, k, j, pivot, 1);
            }
            AlgoFrame.updateData(tid, arr, i, k, j, pivot, 1);
            if (arr.get(i) != pivot) {
                arr.set(j--, arr.get(i));
                AlgoFrame.updateData(tid, arr, i, k, j, pivot, 2);
            }
            arr.set(i, arr.get(--pivotIndex));
            AlgoFrame.updateData(tid, arr, i, k, j, pivot, 3);
            arr.set(pivotIndex, pivot);
            AlgoFrame.updateData(tid, arr, i, k, j, pivot, 2);
        }
        antiGaEquQSort(tid, arr, begin, i);
        antiGaEquQSort(tid, arr, j + 1, end);
    }

    public static void iSortInQSort(int tid, AlgoArray arr, int begin, int end) {
        int len = end + 1;
        for (int i = begin + 1; i < len; i++) {
            AlgoFrame.updateData(tid, arr, i, -1, -1, -100, 1);
            AlgoFrame.updateData(tid, arr, i, -1, -1, -100, 1);
            if (arr.get(i) < arr.get(i - 1)) {
                int j = i;
                AlgoFrame.updateData(tid, arr, i, -1, j, -100, 1);
                int t = arr.get(j);
                AlgoFrame.updateData(tid, arr, i, -1, j, -100, 1);
                while (j > 0 && arr.get(j - 1) > t) {
                    arr.set(j, arr.get(--j));
                    AlgoFrame.updateData(tid, arr, i, -1, j, -100, 3);
                    AlgoFrame.updateData(tid, arr, i, -1, j, -100, 1);
                }
                arr.set(j, t);
                AlgoFrame.updateData(tid, arr, i, -1, j, -100, 2);
            }
        }
    }

    public static void antiISortInQSort(int tid, AlgoArray arr, int begin, int end) {
        int len = end + 1;
        for (int i = begin + 1; i < len; i++) {
            AlgoFrame.updateData(tid, arr, i, -1, -1, -100, 1);
            AlgoFrame.updateData(tid, arr, i, -1, -1, -100, 1);
            if (arr.get(i) > arr.get(i - 1)) {
                int j = i;
                AlgoFrame.updateData(tid, arr, i, -1, j, -100, 1);
                int t = arr.get(j);
                AlgoFrame.updateData(tid, arr, i, -1, j, -100, 1);
                while (j > 0 && arr.get(j - 1) < t) {
                    arr.set(j, arr.get(--j));
                    AlgoFrame.updateData(tid, arr, i, -1, j, -100, 3);
                    AlgoFrame.updateData(tid, arr, i, -1, j, -100, 1);
                }
                arr.set(j, t);
                AlgoFrame.updateData(tid, arr, i, -1, j, -100, 2);
            }
        }
    }

    public static void tInsGaQSort(int tid, AlgoArray arr, int begin, int end) {
        if (end - begin < 20) {//插排，递归出口
            iSortInQSort(tid, arr, begin, end);
            return;
        }
        //三数取中
        int mid = begin + (end + -begin) / 2;
        AlgoFrame.updateData(tid, arr, begin, mid, end, -100, 1);
        AlgoFrame.updateData(tid, arr, begin, mid, end, -100, 1);
        if (arr.get(mid) > arr.get(end)) {
            int t = arr.get(mid);
            AlgoFrame.updateData(tid, arr, begin, mid, end, -100, 1);
            arr.set(mid, arr.get(end));
            AlgoFrame.updateData(tid, arr, begin, mid, end, -100, 3);
            arr.set(end, t);
            AlgoFrame.updateData(tid, arr, begin, mid, end, -100, 2);
        }
        AlgoFrame.updateData(tid, arr, begin, mid, end, -100, 1);
        AlgoFrame.updateData(tid, arr, begin, mid, end, -100, 1);
        if (arr.get(begin) > arr.get(end)) {
            int t = arr.get(begin);
            AlgoFrame.updateData(tid, arr, begin, mid, end, -100, 1);
            arr.set(begin, arr.get(end));
            AlgoFrame.updateData(tid, arr, begin, mid, end, -100, 3);
            arr.set(end, t);
            AlgoFrame.updateData(tid, arr, begin, mid, end, -100, 2);
        }
        AlgoFrame.updateData(tid, arr, begin, mid, end, -100, 1);
        AlgoFrame.updateData(tid, arr, begin, mid, end, -100, 1);
        if (arr.get(mid) > arr.get(begin)) {
            int t = arr.get(begin);
            AlgoFrame.updateData(tid, arr, begin, mid, end, -100, 1);
            arr.set(begin, arr.get(mid));
            AlgoFrame.updateData(tid, arr, begin, mid, end, -100, 3);
            arr.set(mid, t);
            AlgoFrame.updateData(tid, arr, begin, mid, end, -100, 2);
        }
        //进行左右分组（处理相等元素）
        int l = begin, r = end, lBound = begin, rBound = end, lLen = 0, rLen = 0;
        int pivot = arr.get(l);
        AlgoFrame.updateData(tid, arr, l, lBound, r, pivot, 1, rBound);
        while (l != r) {
            AlgoFrame.updateData(tid, arr, l, lBound, r, pivot, 1, rBound);
            while (l != r && arr.get(r) >= pivot) {
                AlgoFrame.updateData(tid, arr, l, lBound, r, pivot, 1, rBound);
                if (arr.get(r) == pivot) { //处理相等元素
                    arr.set(r, arr.get(rBound));
                    AlgoFrame.updateData(tid, arr, l, lBound, r, pivot, 3, rBound);
                    arr.set(rBound--, pivot);
                    AlgoFrame.updateData(tid, arr, l, lBound, r, pivot, 2, rBound);
                    rLen++;
                }
                r--;
                AlgoFrame.updateData(tid, arr, l, lBound, r, pivot, 1, rBound);
            }
            arr.set(l, arr.get(r));
            AlgoFrame.updateData(tid, arr, l, lBound, r, pivot, 3, rBound);
            AlgoFrame.updateData(tid, arr, l, lBound, r, pivot, 1, rBound);
            while (l != r && arr.get(l) <= pivot) {
                AlgoFrame.updateData(tid, arr, l, lBound, r, pivot, 1, rBound);
                if (arr.get(l) == pivot) {
                    arr.set(l, arr.get(lBound));
                    AlgoFrame.updateData(tid, arr, l, lBound, r, pivot, 3, rBound);
                    arr.set(lBound++, pivot);
                    AlgoFrame.updateData(tid, arr, l, lBound, r, pivot, 2, rBound);
                    lLen++;
                }
                l++;
                AlgoFrame.updateData(tid, arr, l, lBound, r, pivot, 1, rBound);
            }
            arr.set(r, arr.get(l));
            AlgoFrame.updateData(tid, arr, l, lBound, r, pivot, 3, rBound);
        }
        arr.set(l, pivot);
        AlgoFrame.updateData(tid, arr, l, lBound, r, pivot, 2, rBound);
        //一次快排结束
        //把与基准元pivot相同的元素移到最终位置周围
        int i = l - 1;
        int j = begin;
        AlgoFrame.updateData(tid, arr, j, lBound, i, pivot, 1, rBound);
        while (j < lBound && arr.get(i) != pivot) {
            int t = arr.get(i);
            AlgoFrame.updateData(tid, arr, j, lBound, i, pivot, 1, rBound);
            arr.set(i--, arr.get(j));
            AlgoFrame.updateData(tid, arr, j, lBound, i, pivot, 3, rBound);
            arr.set(j++, t);
            AlgoFrame.updateData(tid, arr, j, lBound, i, pivot, 2, rBound);
            AlgoFrame.updateData(tid, arr, j, lBound, i, pivot, 1, rBound);
        }
        i = r + 1;
        j = end;
        AlgoFrame.updateData(tid, arr, i, lBound, j, pivot, 1, rBound);
        while (j > rBound && arr.get(i) != pivot) {
            int t = arr.get(i);
            AlgoFrame.updateData(tid, arr, i, lBound, j, pivot, 1, rBound);
            arr.set(i++, arr.get(j));
            AlgoFrame.updateData(tid, arr, i, lBound, j, pivot, 3, rBound);
            arr.set(j--, t);
            AlgoFrame.updateData(tid, arr, i, lBound, j, pivot, 2, rBound);
            AlgoFrame.updateData(tid, arr, i, lBound, j, pivot, 1, rBound);
        }
        tInsGaQSort(tid, arr, begin, l - lLen - 1);
        tInsGaQSort(tid, arr, l + rLen + 1, end);
    }

    public static void antiTInsGaQSort(int tid, AlgoArray arr, int begin, int end) {
        if (end - begin < 20) {//插排，递归出口
            antiISortInQSort(tid, arr, begin, end);
            return;
        }
        //三数取中
        int mid = begin + (end + -begin) / 2;
        AlgoFrame.updateData(tid, arr, begin, mid, end, -100, 1);
        AlgoFrame.updateData(tid, arr, begin, mid, end, -100, 1);
        if (arr.get(mid) > arr.get(end)) {
            int t = arr.get(mid);
            AlgoFrame.updateData(tid, arr, begin, mid, end, -100, 1);
            arr.set(mid, arr.get(end));
            AlgoFrame.updateData(tid, arr, begin, mid, end, -100, 3);
            arr.set(end, t);
            AlgoFrame.updateData(tid, arr, begin, mid, end, -100, 2);
        }
        AlgoFrame.updateData(tid, arr, begin, mid, end, -100, 1);
        AlgoFrame.updateData(tid, arr, begin, mid, end, -100, 1);
        if (arr.get(begin) > arr.get(end)) {
            int t = arr.get(begin);
            AlgoFrame.updateData(tid, arr, begin, mid, end, -100, 1);
            arr.set(begin, arr.get(end));
            AlgoFrame.updateData(tid, arr, begin, mid, end, -100, 3);
            arr.set(end, t);
            AlgoFrame.updateData(tid, arr, begin, mid, end, -100, 2);
        }
        AlgoFrame.updateData(tid, arr, begin, mid, end, -100, 1);
        AlgoFrame.updateData(tid, arr, begin, mid, end, -100, 1);
        if (arr.get(mid) > arr.get(begin)) {
            int t = arr.get(begin);
            AlgoFrame.updateData(tid, arr, begin, mid, end, -100, 1);
            arr.set(begin, arr.get(mid));
            AlgoFrame.updateData(tid, arr, begin, mid, end, -100, 3);
            arr.set(mid, t);
            AlgoFrame.updateData(tid, arr, begin, mid, end, -100, 2);
        }
        //进行左右分组（处理相等元素）
        int l = begin, r = end, lBound = begin, rBound = end, lLen = 0, rLen = 0;
        int pivot = arr.get(l);
        AlgoFrame.updateData(tid, arr, l, lBound, r, pivot, 1, rBound);
        while (l != r) {
            AlgoFrame.updateData(tid, arr, l, lBound, r, pivot, 1, rBound);
            while (l != r && arr.get(r) <= pivot) {
                AlgoFrame.updateData(tid, arr, l, lBound, r, pivot, 1, rBound);
                if (arr.get(r) == pivot) { //处理相等元素
                    arr.set(r, arr.get(rBound));
                    AlgoFrame.updateData(tid, arr, l, lBound, r, pivot, 3, rBound);
                    arr.set(rBound--, pivot);
                    AlgoFrame.updateData(tid, arr, l, lBound, r, pivot, 2, rBound);
                    rLen++;
                }
                r--;
                AlgoFrame.updateData(tid, arr, l, lBound, r, pivot, 1, rBound);
            }
            arr.set(l, arr.get(r));
            AlgoFrame.updateData(tid, arr, l, lBound, r, pivot, 3, rBound);
            AlgoFrame.updateData(tid, arr, l, lBound, r, pivot, 1, rBound);
            while (l != r && arr.get(l) >= pivot) {
                AlgoFrame.updateData(tid, arr, l, lBound, r, pivot, 1, rBound);
                if (arr.get(l) == pivot) {
                    arr.set(l, arr.get(lBound));
                    AlgoFrame.updateData(tid, arr, l, lBound, r, pivot, 3, rBound);
                    arr.set(lBound++, pivot);
                    AlgoFrame.updateData(tid, arr, l, lBound, r, pivot, 2, rBound);
                    lLen++;
                }
                l++;
                AlgoFrame.updateData(tid, arr, l, lBound, r, pivot, 1, rBound);
            }
            arr.set(r, arr.get(l));
            AlgoFrame.updateData(tid, arr, l, lBound, r, pivot, 3, rBound);
        }
        arr.set(l, pivot);
        AlgoFrame.updateData(tid, arr, l, lBound, r, pivot, 2, rBound);
        //一次快排结束
        //把与基准元pivot相同的元素移到最终位置周围
        int i = l - 1;
        int j = begin;
        AlgoFrame.updateData(tid, arr, j, lBound, i, pivot, 1, rBound);
        while (j < lBound && arr.get(i) != pivot) {
            int t = arr.get(i);
            AlgoFrame.updateData(tid, arr, j, lBound, i, pivot, 1, rBound);
            arr.set(i--, arr.get(j));
            AlgoFrame.updateData(tid, arr, j, lBound, i, pivot, 3, rBound);
            arr.set(j++, t);
            AlgoFrame.updateData(tid, arr, j, lBound, i, pivot, 2, rBound);
            AlgoFrame.updateData(tid, arr, j, lBound, i, pivot, 1, rBound);
        }
        i = r + 1;
        j = end;
        AlgoFrame.updateData(tid, arr, i, lBound, j, pivot, 1, rBound);
        while (j > rBound && arr.get(i) != pivot) {
            int t = arr.get(i);
            AlgoFrame.updateData(tid, arr, i, lBound, j, pivot, 1, rBound);
            arr.set(i++, arr.get(j));
            AlgoFrame.updateData(tid, arr, i, lBound, j, pivot, 3, rBound);
            arr.set(j--, t);
            AlgoFrame.updateData(tid, arr, i, lBound, j, pivot, 2, rBound);
            AlgoFrame.updateData(tid, arr, i, lBound, j, pivot, 1, rBound);
        }
        antiTInsGaQSort(tid, arr, begin, l - lLen - 1);
        antiTInsGaQSort(tid, arr, l + rLen + 1, end);
    }


}
