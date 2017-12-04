import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA. User: alde1 Date: 1/29/14 Time: 7:03 AM To change
 * this template use File | Settings | File Templates.
 */
public class Change {

    public Map<Integer, String> solutions;
    public Map<Integer, Integer> problems;

    public Change() {
        solutions = new HashMap<Integer, String>();
        problems = new HashMap<Integer, Integer>();
    }

    public static void main(String arg[]) {
        Change c = new Change();
        Scanner scan = new Scanner(System.in);
        List<Integer> allData = new ArrayList<Integer>();
        while (true) {
            String str = scan.next();
            if (str.endsWith(",")) {
                int i = Integer.parseInt(str.substring(0, str.length() - 1));
                allData.add(i);
            } else {
                int i = Integer.parseInt(str);
                allData.add(i);
            }
            if (!scan.hasNext()) {
                break;
            }
            //
        }
        int N = allData.get(allData.size() - 1);
        int d[] = new int[allData.size() - 1];
        for (int i = 0; i < allData.size() - 1; i++) {
            d[i] = allData.get(i);
        }
        long t1 = System.currentTimeMillis();
        // System.out.println(c.change(N, d, d.length));
        // System.out.println("Solutions: " + c.solutions);
        //
        long t2 = System.currentTimeMillis();
        System.out.println(c.dynamicChange(N, d, d.length));
        /*
         * 
         * long t3 = System.currentTimeMillis(); System.out.println("t1: " + (t2
         * - t1)); System.out.println("t2: " + (t3 - t2));
         * System.out.println("Problems: " + c.problems);
         */
    }

    private int dynamicChange(int n, int[] d, int last) {
        int c[][] = new int[n + 1][last + 1];
        //
        for (int i = 0; i <= n; i++) {
            c[i][0] = 0;
        }
        for (int j = 0; j <= last; j++) {
            c[0][j] = 0;
        }
        for (int j = 1; j <= last; j++) {
            int index = d[j - 1];
            if (index <= n) {
                c[index][j] = 1;
            }
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= last; j++) {
                int c1 = 0;
                int coinVal = d[j - 1];
                if ((i - coinVal) >= 0) {
                    c1 = c[i - coinVal][j];
                }
                int c2 = c[i][j - 1];
                c[i][j] = c[i][j] + c1 + c2;
            }
        }
        // printMatrix(c);
        return c[n][last];
    }

    private void printMatrix(int[][] c) {
        for (int[] row : c) {
            for (int val : row) {
                System.out.print(val + "\t");
            }
            System.out.print("\n");
        }
    }

    private int change(int n, int[] d, int last) {
        //
        if (n <= 0) {
            return 0;
        }
        if (last <= 0) {
            return 0;
        }
        Integer valP = problems.get(n);
        if (valP == null) {
            valP = 0;
        }
        problems.put(n, valP + 1);
        String val = solutions.get(n);
        if (val == null) {
            solutions.put(n, "");
        } else {
            solutions.put(n, val);
        }
        if (n == d[last - 1]) {
            String currSol = solutions.get(n);
            solutions.put(n, "*" + d[last - 1]);
            return 1 + change(n, d, last - 1);
        }
        int c1 = change(n - d[last - 1], d, last);
        int c2 = change(n, d, last - 1);
        if (c1 > 0) {
            String currSol = solutions.get(n);
            String usedSol = solutions.get(n - d[last - 1]);
            solutions.put(n, usedSol + "|" + d[last - 1]);
        }
        return c1 + c2;
    }
}
