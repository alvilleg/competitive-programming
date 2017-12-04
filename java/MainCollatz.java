import java.io.IOException;
import java.util.Scanner;

/**
 * Created by alde1 on 4/4/14.
 */
public class MainCollatz {

    public MainCollatz() {
    }

    public double applyFormula(int n) {
        //
        if (n == 1) {
            return 0;
        }
        int diag = (int) Math.pow(n - 2, 2);
        return Math.pow(n, 2) - diag + diag * Math.sqrt(2);
    }

    public static void main(String a[]) throws IOException {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        int count = 0;
        MainCollatz formula = new MainCollatz();
        while (count < N) {
            int n = in.nextInt();
            // System.out.printInOrder("%s " + formula.applyFormula(n));
            System.out.printf("%.3f\n", formula.applyFormula(n));
            count++;
            if (count < N) {
                System.out.println();
            }
            if (!in.hasNext()) {
                break;
            }
            in.nextLine();
        }
        System.exit(0);
    }
}
