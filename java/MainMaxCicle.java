import java.io.IOException;
import java.util.Scanner;

/**
 * Created by alde1 on 4/4/14.
 */
public class MainMaxCicle {

    public MainMaxCicle() {
    }

    public long maximumCicleNaive(long n) {
        if (n == 1) {
            return 1;
        }
        if (n % 2 == 0) {
            long result = 1 + maximumCicleNaive(n / 2);
            return result;
        }
        long result = 1 + maximumCicleNaive(3 * n + 1);
        return result;
    }

    public static void main(String a[]) throws IOException {
        Scanner in = new Scanner(System.in);
        int ini, fin;
        while (true) {
            MainMaxCicle maximumCicle = new MainMaxCicle();
            int i = in.nextInt();
            int j = in.nextInt();
            if (i < j) {
                ini = i;
                fin = j;
            } else {
                ini = j;
                fin = i;
            }
            long max = -1;
            long curr;
            for (int k = ini; k <= fin; k++) {
                curr = maximumCicle.maximumCicleNaive(k);
                if (curr > max) {
                    max = curr;
                }
            }
            System.out.println(i + " " + j + " " + max);
            if (!in.hasNext()) {
                break;
            }
            Math.ceil(2.3);
        }
        System.exit(0);
    }
}
