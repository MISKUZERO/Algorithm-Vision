package algo;

/**
 * @author MiskuZero
 */
public class InsertSort {

    public static void doSort(int[] arr, int begin, int end) {
        int len = end + 1;
        for (int i = begin + 1; i < len; i++)
            if (arr[i] < arr[i - 1]) {
                int j = i;
                int t = arr[j];
                while (j > 0 && arr[j - 1] > t) arr[j] = arr[--j];
                arr[j] = t;
            }
    }
}
