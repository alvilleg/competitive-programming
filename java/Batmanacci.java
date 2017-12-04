import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by alde on 8/26/17.
 */
public class Batmanacci {



    public static void main(String arg[]) {
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(System.in));
            StringTokenizer st;
            String line;

            line = br.readLine();

            st = new StringTokenizer(line);

            int n = Integer.parseInt(st.nextToken());
            long k = Long.parseLong(st.nextToken());
            //
            Map<Integer, BigInteger> fibonaccis = new HashMap<Integer, BigInteger>();
            fibonaccis.put(1, BigInteger.valueOf(1));
            fibonaccis.put(2, BigInteger.valueOf(1));
            for (int i = 3; i <= n; i++) {
                fibonaccis.put(i, BigInteger.valueOf(0));
                fibonaccis.put(i, fibonaccis.get(i).add(fibonaccis.get(i - 1)));
                fibonaccis.put(i, fibonaccis.get(i).add(fibonaccis.get(i - 2)));
            }

            //
            BigInteger currentIndexSearch = BigInteger.valueOf(k);
            int currentFib = n;

            while (currentFib > 2) {
                //System.out.printf("fib:%d  fib_val:%d index:%d%n",currentFib, fibonaccis.get(currentFib), currentIndexSearch);
                //
                BigInteger value1 = fibonaccis.get(currentFib - 1);
                BigInteger value2 = fibonaccis.get(currentFib - 2);
                //
                if (currentIndexSearch.compareTo(value2) > 0) {
                    currentFib = currentFib - 1;
                    currentIndexSearch = currentIndexSearch.subtract(value2);
                } else {
                    currentFib = currentFib - 2;
                }

            }
            //System.out.println("END: " + currentFib);
            if (currentFib == 1) {
                System.out.println("N");
            } else if(currentFib == 2) {
                System.out.println("A");
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
