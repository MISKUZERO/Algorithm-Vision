package algo;

import java.util.Arrays;

/**
 * @author MiskuZero
 */
public class QuickSort {

    public static void main(String[] args) {
        int len = 20;
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * len);
        }
        QuickSort.randomPivot(arr, 0, len - 1);
        System.out.println(Arrays.toString(arr));
    }

    public static void firstPivot(int[] arr, int begin, int end) {
        if (begin < end) {
            int pivot = arr[begin];
            pivot = divide(arr, begin, end, pivot);
            firstPivot(arr, begin, pivot - 1);
            firstPivot(arr, pivot + 1, end);
        }
    }

    public static void randomPivot(int[] arr, int begin, int end) {
        if (begin < end) {
            int pivot = arr[(int) (Math.random() * (end - begin + 1)) + begin];
            pivot = divide(arr, begin, end, pivot);
            randomPivot(arr, begin, pivot - 1);
            randomPivot(arr, pivot + 1, end);
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
