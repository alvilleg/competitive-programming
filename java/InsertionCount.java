import java.util.Scanner;

public class InsertionCount {

    long totalInversions = 0;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for (int i = 0; i < t; i++) {
            InsertionCount insertionCount = new InsertionCount();
            int n = in.nextInt();
            int[] ar = new int[n];
            for (int j = 0; j < n; j++) {
                ar[j] = in.nextInt();
                // System.err.println(ar[j]);
            }
            long c = insertionCount.insertSort(ar);
            System.out.println(c);
        }
    }

    public long insertSort(int[] ar) {
        mergeSort(ar, 0, ar.length - 1);
        return totalInversions;
    }

    private void printArray(int[] ar) {
        for (int i = 0; i < ar.length; i++) {
            System.out.print("\t" + ar[i]);
        }
    }

    private int mergeSort(int a[], int p, int r) {
        if (p < r) {
            int q = (p + r) / 2;
            mergeSort(a, p, q);
            mergeSort(a, q + 1, r);
            merge(a, p, q, r);
        }
        return 0;
    }

    private long merge(int a[], int p, int q, int r) {
        int n1 = q - p + 1;
        int n2 = r - q;
        int left[] = new int[n1 + 1];
        int right[] = new int[n2 + 1];
        for (int i = 0; i < n1; i++) {
            left[i] = a[p + i];
        }
        for (int j = 0; j < n2; j++) {
            right[j] = a[q + j + 1];
        }
        //
        left[n1] = Integer.MAX_VALUE;
        right[n2] = Integer.MAX_VALUE;
        //
        int i = 0;
        int j = 0;
        for (int k = p; k <= r; k++) {
            if (left[i] <= right[j]) {
                a[k] = left[i];
                i++;
            } else {
                // there is an inversion
                a[k] = right[j];
                if (left[i] < Integer.MAX_VALUE) {
                    // the right[j] element is smaller than (n1-i) elements in
                    // the left array
                    totalInversions += n1 - i;
                }
                j++;
            }
        }
        return totalInversions;
    }
}
