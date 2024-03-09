package algo;

import java.util.Arrays;

/**
 * @author MiskuZero
 */
public class QuickSort {

    public static void main(String[] args) {
        int len = 10000000;
        int[] arr = new int[len];
        for (int j = 0; j < len; j++) {
            arr[j] = (int) (Math.random() * len / 1000);
        }
        int[] arr1 = Arrays.copyOf(arr, len);

        long l = System.currentTimeMillis();
        QuickSort.gatherEqu(arr1, 0, len - 1);
        long l1 = System.currentTimeMillis();
        System.out.println(l1 - l + "ms");
        l = System.currentTimeMillis();
        QuickSort.firstPivot(arr, 0, len - 1);
        l1 = System.currentTimeMillis();
        System.out.println(l1 - l + "ms");

        System.out.println(Arrays.equals(arr, arr1));

    }

    public static void firstPivot(int[] arr, int begin, int end) {
        if (begin < end) {
            int pivot = arr[begin];
            pivot = divide(arr, begin, end, pivot);
            firstPivot(arr, begin, pivot - 1);
            firstPivot(arr, pivot + 1, end);
        }
    }

    public static void gatherEqu(int[] arr, int begin, int end) {
        if (end - begin < 47) {
            InsertSort.doSort(arr, begin, end);
            return;
        }
        int pivotIndex = (int) (Math.random() * (end - begin)) + begin + 1;//长度为N的数组相对范围[1, N]
        int pivot = arr[pivotIndex];
        int i = begin, j = end, k = pivotIndex;
        while (k != j) {
            while (arr[j] > pivot) j--;
            if (arr[j] == pivot) {
                while (k != j && arr[k] == pivot) k++;
                arr[j] = arr[k];
                arr[k] = pivot;
                continue;
            }
            while (arr[i] < pivot) i++;
            if (i == pivotIndex) {
                if (++k == j) {
                    arr[i] = arr[j];
                    arr[j] = pivot;
                    pivotIndex++;//保证：arr[pivotIndex, k]之间值都是pivot
                    break;
                }
                arr[i] = arr[k];
                arr[k] = pivot;
                pivotIndex++;//保证：arr[pivotIndex, k]之间值都是pivot
            }
            int t = arr[i];
            arr[i] = arr[j];
            arr[j] = t;
        }
        //arr[j] = pivot，且arr[pivotIndex, k]之间值都是pivot
        while (i < pivotIndex) {
            while (arr[i] < pivot) i++;
            if (arr[i] != pivot)
                arr[j--] = arr[i];
            arr[i] = arr[--pivotIndex];
            arr[pivotIndex] = pivot;
        }
        gatherEqu(arr, begin, i);
        gatherEqu(arr, j + 1, end);
    }

    public static void randomPivot(int[] arr, int begin, int end) {
        if (begin < end) {
            int pivotIndex = (int) (Math.random() * (end - begin + 1)) + begin;
            int pivot = arr[pivotIndex];
            int t = arr[begin];
            arr[begin] = pivot;
            arr[pivotIndex] = t;
            pivotIndex = divide(arr, begin, end, pivot);
            randomPivot(arr, begin, pivotIndex - 1);
            randomPivot(arr, pivotIndex + 1, end);
        }
    }

    private static int divide(int[] arr, int begin, int end, int pivot) {
        int low = begin, high = end;
        while (low != high) {
            while (low != high && arr[high] >= pivot) high--;
            arr[low] = arr[high];
            while (low != high && arr[low] <= pivot) low++;
            arr[high] = arr[low];
        }
        arr[low] = pivot;
        return low;
    }

}
