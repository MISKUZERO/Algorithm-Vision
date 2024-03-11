package algo;

/**
 * @author MiskuZero
 */
public class MergeSort {

    public static void doSort(int[] arr, int begin, int end, int[] tmp) {
        if (begin < end) {
            int mid = (end + begin) >> 1;
            doSort(arr, begin, mid, tmp);
            doSort(arr, mid + 1, end, tmp);
            int m = mid + 1, len = end + 1;
            System.arraycopy(arr, begin, tmp, begin, len - begin);
            int i = begin, j = m, k = begin;
            while (i != m && j != len)
                if (tmp[i] < tmp[j])
                    arr[k++] = tmp[i++];
                else
                    arr[k++] = tmp[j++];
            while (i != m) arr[k++] = tmp[i++];
            while (j != len) arr[k++] = tmp[j++];
        }
    }
}
