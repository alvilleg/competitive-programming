import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by alde on 9/27/17.
 */
public class MainXOR {

        public static void main(String arg[]) {
            try {

                BufferedReader br = new BufferedReader(
                        new InputStreamReader(System.in));
                StringTokenizer st;
                String line;
                line = br.readLine();
                while(line != null) {
                    int aa = 0;
                    st = new StringTokenizer(line);
                    int i=4;
                    while (st.hasMoreElements()){
                        aa =  (Integer.parseInt(st.nextToken())<<i) | aa;
                        i--;
                    }
                    //
                    line = br.readLine();
                    int bb =0;
                    st = new StringTokenizer(line);
                    i=4;
                    while (st.hasMoreElements()){
                        bb =  (Integer.parseInt(st.nextToken())<<i) | bb;
                        i--;
                    }
                    //
                    if(31 == (aa^bb)){
                        System.out.println("Y");
                    } else{
                        System.out.println("N");
                    }
                    line = br.readLine();
                }

            }catch (Exception ex){

            }

    }
}
