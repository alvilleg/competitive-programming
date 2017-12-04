import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by alde on 9/21/17.
 * katis subseqhard: https://open.kattis.com/problems/subseqhard
 */
public class Subseqhard {
    public static void main(String arg[]) {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in));
        String line ;
        try {
            line = br.readLine();
            int T = Integer.parseInt(line);
            int count=0;
            
            while (count<T) {
                br.readLine();
                line = br.readLine();
                int N = Integer.parseInt(line);
                //
                line = br.readLine();
                StringTokenizer stringTokenizer = new StringTokenizer(line);
                int a[] = new int[N];
                Map<Integer,Integer> countOccur = new HashMap<Integer,Integer>();
                int accumulated=0;
                int countSubSeq=0;
                countOccur.put(0,1);
                for(int i=0;i<N;i++){
                    a[i] = Integer.parseInt(stringTokenizer.nextToken());
                    accumulated+=a[i];
                    int key = accumulated - 47;
                    if(countOccur.get(key)!=null){
                        countSubSeq += countOccur.get(key);
                    }
                    if(countOccur.get(accumulated) == null){
                        countOccur.put(accumulated,0);
                    }
                    countOccur.put(accumulated, countOccur.get(accumulated)+1);
                }
                System.out.println(countSubSeq);
                count++;

            }
        }catch (Exception exc){
            exc.printStackTrace();
        }
    }
}
