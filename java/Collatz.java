import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by alde on 8/26/17.
 */
public class Collatz {
    class CollatzResult {
        CollatzResult(){

        }
        long number;
        long position;
        long start;

        //*
        public String toString(){
            return number +" " + position;
        }
    }

    private HashMap<Long, CollatzResult> collatz(long start, HashMap<Long, CollatzResult> prevResults){
        long value = start;
        int counter = 0;
        //
        HashMap<Long,CollatzResult> result = new HashMap<Long, CollatzResult>();
        //

        while (value > 1) {
            //
            CollatzResult cr = new  CollatzResult();
            cr.number = value;
            cr.position = counter;
            cr.start = start;
            //

            if(prevResults == null) {
                result.put(value, cr);
            }
            else {
                CollatzResult cr1 = prevResults.get(value);
                if (cr1 != null) {
                    System.out.printf("%d needs %d steps, %d needs %d steps, they meet at %d %n",cr1.start,cr1.position,cr.start,cr.position,cr.number);
                    return null;
                }
            }
            //
            if(value%2 == 0){
                value = value/2;
            } else{
                value= 3*value+1;
            }
            counter++;
            //
        }
        //
        CollatzResult cr = new  CollatzResult();
        cr.number = value;
        cr.position = counter;
        cr.start = start;
        //

        if(prevResults == null) {
            result.put(value, cr);
        }
        else {
            CollatzResult cr1 = prevResults.get(value);
            if (cr1 != null) {
                System.out.printf("%d needs %d steps, %d needs %d steps, they meet at %d %n",cr1.start,cr1.position,cr.start,cr.position,cr.number);
            }
        }
        //
        return result;
    }

    public static void main(String arg[]) {
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(System.in));
            StringTokenizer st ;
            Collatz collatz = new Collatz();
            String line ;
            while (true) {

                line = br.readLine();
                if(line == null){
                    break;
                }
                st = new StringTokenizer(line);
                if (!st.hasMoreTokens()) {
                    break;
                }
                long a = Long.parseLong(st.nextToken());
                long b = Long.parseLong(st.nextToken());
                if(a==b && a==0) {
                    break;
                }
                HashMap<Long,CollatzResult> resultA = collatz.collatz(a, null);
                //System.err.println("Size A : " + resultA);
                HashMap<Long,CollatzResult> resultB = collatz.collatz(b, resultA);
                //
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
