package algo;

public class QSort {

    public static void threeInsertGather(int[] arr, int begin, int end) {
        if (end - begin < 40) {//插排，递归出口
            InsertSort.doSort(arr, begin, end);
            return;
        }
        //三数取中
        int mid = begin + (end + -begin) / 2;
        if (arr[mid] > arr[end]) {
            int t = arr[mid];
            arr[mid] = arr[end];
            arr[end] = t;
        }
        if (arr[begin] > arr[end]) {
            int t = arr[begin];
            arr[begin] = arr[end];
            arr[end] = t;
        }
        if (arr[mid] > arr[begin]) {
            int t = arr[begin];
            arr[begin] = arr[mid];
            arr[mid] = t;
        }
        //进行左右分组（处理相等元素）
        int l = begin, r = end, lBound = begin, rBound = end, lLen = 0, rLen = 0;
        int pivot = arr[l];
        while (l != r) {
            while (l != r && arr[r] >= pivot) {
                if (arr[r] == pivot) { //处理相等元素
                    arr[r] = arr[rBound];
                    arr[rBound--] = pivot;
                    rLen++;
                }
                r--;
            }
            arr[l] = arr[r];
            while (l != r && arr[l] <= pivot) {
                if (arr[l] == pivot) {
                    arr[l] = arr[lBound];
                    arr[lBound++] = pivot;
                    lLen++;
                }
                l++;
            }
            arr[r] = arr[l];
        }
        arr[l] = pivot;
        //一次快排结束
        //把与基准元pivot相同的元素移到最终位置周围
        int i = l - 1;
        int j = begin;
        while (j < lBound && arr[i] != pivot) {
            int t = arr[i];
            arr[i--] = arr[j];
            arr[j++] = t;
        }
        i = r + 1;
        j = end;
        while (j > rBound && arr[i] != pivot) {
            int t = arr[i];
            arr[i++] = arr[j];
            arr[j--] = t;
        }
        threeInsertGather(arr, begin, l - lLen - 1);
        threeInsertGather(arr, l + rLen + 1, end);
    }


}
