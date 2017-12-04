
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by alde on 8/9/17.
 * kattis countdown
 */
public class Countdown {

    Result best;
    long currDiff = Long.MAX_VALUE;
    Map<String, Boolean> explored;

    class Node {
        Node operand1;
        Node operand2;
        char operator;
        int value;
        Node parent;
        int originalIndex = -1;
        //
        List<Node> missingNodes;

        Node() {

        }

        Node(int value, int originalIndex) {
            this.value = value;
            this.operator = '=';
            //
            this.originalIndex = originalIndex;
        }

        //
        List<Node> expand(boolean normalExpand) {
            if (missingNodes == null || missingNodes.size() == 0) {
                return new ArrayList<Node>();
            }
            //System.out.println("Missing nodes :: "+ missingNodes.size());
            List<Node> nodes = new ArrayList<Node>();
            for (int i = 0; i < missingNodes.size(); i++) {
                List<Node> newMissingNodes = new ArrayList<Node>();
                for (int j = 0; j < missingNodes.size(); j++) {
                    if (i == j) {
                        continue;
                    }
                    newMissingNodes.add(missingNodes.get(j));
                    if (normalExpand) {
                        newMissingNodes.addAll(missingNodes.get(j).expand(false));
                    }
                }
                addNode(nodes, missingNodes.get(i), '*', newMissingNodes);
                addNode(nodes, missingNodes.get(i), '+', newMissingNodes);
                addNode(nodes, missingNodes.get(i), '-', newMissingNodes);
                addNode(nodes, missingNodes.get(i), '/', newMissingNodes);
            }

            //
            return nodes;
        }

        boolean isValid(Node other) {
            int idx1 = -1;
            int idx2 = -1;
            int idxThis = this.getOriginalIndex();
            if (this.operand2 != null) {
                idx2 = this.operand2.getOriginalIndex();
            }
            if (this.operand1 != null) {
                idx1 = this.operand1.getOriginalIndex();
            }


            int idxOther = other.getOriginalIndex();
            if (idxOther == idx1 || idxOther == idx2 || idxOther == idxThis) {
                //System.out.println("Invalid operation repeated...");
                return false;
            }
            if (other.operand1 != null) {
                int idxOtherO1 = other.operand1.getOriginalIndex();
                if (idxOtherO1 == idx1 || idxOtherO1 == idx2 || idxOtherO1 == idxThis) {
                    //System.out.println("Invalid operation repeated...");
                    return false;
                }
            }
            if (other.operand2 != null) {

                int idxOtherO2 = other.operand2.getOriginalIndex();
                if (idxOtherO2 == idx1 || idxOtherO2 == idx2 || idxOtherO2 == idxThis) {
                    //System.out.println("Invalid operation repeated...");
                    return false;
                }
            }


            return true;
        }


        private void addNode(List<Node> nodes, Node other, char c, List<Node> missingNodes) {

            if (!isValid(other)) {
                return;
            }
            if ('/' == c && other.value == 0) {
                return;
            }

            Node n = new Node();
            n.operator = c;
            n.operand2 = other;
            n.operand1 = this;
            n.parent = this;
            n.value = n.eval();
            n.missingNodes = missingNodes;
            nodes.add(n);

        }

        int getOriginalIndex() {
            if (parent != null) {
                return parent.getOriginalIndex();
            }
            return originalIndex;
        }

        int eval() {

            if ('*' == operator) {
                return operand1.value * operand2.value;
            } else if ('/' == operator && operand2.value != 0) {
                return operand1.value / operand2.value;
            } else if ('+' == operator) {
                return operand1.value + operand2.value;
            } else if ('-' == operator) {
                return operand1.value - operand2.value;
            } else {
                return value;
            }
        }

        public String print() {
            if (operator == '=') {
                return "";
            }

            String p = "";
            if (parent != null) {
                parent.print();
            }

            String op1 = "";
            String op2 = "";
            if (operand1 != null) {
                op1 = operand1.print();
            }
            if (operand2 != null) {
                op2 = operand2.print();
            }

            return p + op1 + op2 + String.format(" %s %s %s = %s\n", operand1.value, operator, operand2.value, value);
        }
    }

    public void process() throws IOException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int cases = Integer.parseInt(st.nextToken());
        String line = null;
        int count = 0;
        while (count < cases) {
            best = null;
            currDiff = Long.MAX_VALUE;
            //
            line = br.readLine();
            st = new StringTokenizer(line);
            if (!st.hasMoreTokens()) {
                break;
            }
            int i = 0;
            int[] numbers = new int[6];
            while (i < 6) {
                numbers[i] = Integer.parseInt(st.nextToken());
                i++;
            }
            long target = Long.parseLong(st.nextToken());
            //
            //solve(target, numbers);
            List<Result> results = new LinkedList<Result>();
            List<Result> allResults = new LinkedList<Result>();
            for (i = 0; i < numbers.length; i++) {
                Result res = new Result(numbers[i]);
                results.add(res);
                allResults.add(res);
            }
            explored = new HashMap<String, Boolean>();
            solve3(results, target);
            //
            System.out.println("Target: " + target);
            System.out.print(best);
            System.out.println("Best approx: " + best.value + "\n");
            count++;

            //System.out.println("Explored::" + explored.size());
        }
    }

    private String solve2(int target, int[] numbers) {
        List<Node> nodes = new ArrayList<Node>();
        for (int i = 0; i < numbers.length; i++) {
            Node n = new Node(numbers[i], i);
            nodes.add(n);
        }
        List<Node> nodes2 = new ArrayList<Node>();
        for (Node n : nodes) {
            for (Node nj : nodes) {
                if (n.isValid(nj)) {
                    n.addNode(nodes2, nj, '*', null);
                    n.addNode(nodes2, nj, '/', null);
                    n.addNode(nodes2, nj, '+', null);
                    n.addNode(nodes2, nj, '-', null);
                }
            }
        }
        //01020304051012131415202123242530313234354041424340515253546
        List<Node> nodes3 = new ArrayList<Node>();
        for (Node n : nodes2) {
            for (Node nj : nodes2) {
                if (n.isValid(nj)) {
                    n.addNode(nodes3, nj, '*', null);
                    n.addNode(nodes3, nj, '/', null);
                    n.addNode(nodes3, nj, '+', null);
                    n.addNode(nodes3, nj, '-', null);
                }
            }
        }
        //

        //
        List<Node> allNodes = new ArrayList<Node>();
        allNodes.addAll(nodes);
        allNodes.addAll(nodes2);
        //allNodes.addAll(nodes3);
        //
        List<Node> allNodes2 = new ArrayList<Node>();
        for (Node n : allNodes) {
            for (Node nj : allNodes) {
                if (n.isValid(nj)) {
                    n.addNode(allNodes2, nj, '*', null);
                    n.addNode(allNodes2, nj, '/', null);
                    n.addNode(allNodes2, nj, '+', null);
                    n.addNode(allNodes2, nj, '-', null);
                }
            }
        }//*/
        allNodes = new ArrayList<Node>();
        allNodes.addAll(nodes);
        allNodes.addAll(nodes2);
        allNodes.addAll(allNodes2);
        allNodes.addAll(nodes3);
        //
        Node result = null;
        int minDiff = Integer.MAX_VALUE;
        while (!allNodes.isEmpty()) {
            Node node = allNodes.remove(0);
            //
            if (node.value == target) {
                result = node;
                break;
            }
            int diff = Math.abs(node.value - target);
            if (diff < minDiff) {
                result = node;
                minDiff = diff;
            }
            //
            nodes.addAll(node.expand(true));
            System.out.println(result.value + " => " + allNodes.size());
        }
        System.out.print(result.print());
        return "";
    }


    private String solve(int target, int[] numbers) {
        List<Node> nodes = new LinkedList<Node>();
        for (int i = 0; i < numbers.length; i++) {
            Node n = new Node(numbers[i], i);
            nodes.add(n);
        }
        for (int i = 0; i < nodes.size(); i++) {
            List<Node> missing = new ArrayList<Node>();
            for (int j = 0; j < nodes.size(); j++) {
                if (i == j) {
                    continue;
                }
                missing.add(nodes.get(j));
            }
            nodes.get(i).missingNodes = missing;
        }
        Node result = null;
        int minDiff = Integer.MAX_VALUE;
        while (!nodes.isEmpty()) {
            Node node = nodes.remove(0);
            //
            if (node.value == target) {
                result = node;
                break;
            }
            int diff = Math.abs(node.value - target);
            if (diff < minDiff) {
                result = node;
                minDiff = diff;
            }
            //
            nodes.addAll(node.expand(true));
            System.out.println(result.value + " " + nodes.size());
        }

        return "";
    }

    class Result {
        long value;
        char operator;
        //
        Result r1;
        Result r2;

        //
        Result(long value_) {
            this.value = value_;
        }

        Result(Result r1, Result r2, char operator) {
            this.r1 = r1;
            this.r2 = r2;
            this.operator = operator;
            //
            if ('*' == operator) {
                this.value = r1.value * r2.value;
            }
            if ('+' == operator) {
                this.value = r1.value + r2.value;
            }
            if ('-' == operator) {
                this.value = r1.value - r2.value;
            }
            if ('/' == operator) {
                this.value = r1.value / r2.value;
            }
        }

        public String toString() {
//            return value+"";

            if (r1 != null && r2 != null) {
                return r1.toString() + r2.toString() + String.format(r1.value + " " + operator + " " + r2.value + " = " + String.valueOf(value)) + "\n";
            }
            return "";//*/
        }

    }

    public void solve3(List<Result> results, long target) {
        if(currDiff == 0){
            return;
        }
        String key = "";
        Collections.sort(results, new Comparator<Result>() {
            @Override
            public int compare(Result o1, Result o2) {
                return new Long(o1.value).compareTo(new Long(o2.value));
            }
        });//*/
        for (Result res : results) {
            key += res.value + ";";
        }//*/


        for (Result r : results) {
            if (r.value == target) {
                best = r;
                return;
            }
        }
        if (results.size() == 1) {
            return;
        }

        if (explored.get(key) != null) {
            //System.err.println("Exists!! KEY ==> [" + key+"]");
            return;
        }

        explored.put(key, true);
        for (int i = 0; i < results.size(); i++) {
            Result current = results.get(i);
            List<Result> others = new LinkedList<Result>();
            for (int j = 0; j < results.size(); j++) {
                if (i != j) {
                    others.add(results.get(j));
                }
            }

            for (int oindex = 0; oindex < others.size(); oindex++) {
                Result o = others.get(oindex);
                Result r1 = new Result(o, current, '*');
                Result r2 = new Result(o, current, '+');
                Result r3 = null;

                if (current.value != 0 && ((o.value % current.value) == 0)) {
                    r3 = new Result(o, current, '/');
                }

                Result r4 = null;
                if (o.value > current.value) {
                    r4 = new Result(o, current, '-');
                }
                //
                List<Result> newOthers = new LinkedList<Result>();
                List<Result> newResults1 = new LinkedList<Result>();
                List<Result> newResults2 = new LinkedList<Result>();
                List<Result> newResults3 = new LinkedList<Result>();
                List<Result> newResults4 = new LinkedList<Result>();
                for (int j = 0; j < others.size(); j++) {
                    Result oj = others.get(j);
                    if (oindex != j) {
                        newOthers.add(oj);
                        newResults1.add(oj);
                        newResults2.add(oj);
                        newResults3.add(oj);
                        newResults4.add(oj);
                    }
                }
                newResults1.add(0, r1);
                newResults2.add(0, r2);
                if (r3 != null) {
                    newResults3.add(r3);
                }

                if (r4 != null && r4.value > 0) {
                    newResults4.add(r4);
                }

                if (r1.value == target) {
                    currDiff = 0;
                    best = r1;
                    return;
                }
                if (r2.value == target) {
                    currDiff = 0;
                    best = r2;
                    return;
                }
                if (r3 != null && r3.value == target) {
                    currDiff = 0;
                    best = r3;
                    return;
                }
                if (r4 != null && r4.value == target) {
                    currDiff = 0;
                    best = r4;
                    return;
                }

                //
                if (Math.abs(r1.value - target) < currDiff) {
                    best = r1;
                    currDiff = Math.abs(r1.value - target);
                }
                if (Math.abs(r2.value - target) < currDiff) {
                    best = r2;
                    currDiff = Math.abs(r2.value - target);
                }
                if (r3 != null && Math.abs(r3.value - target) < currDiff) {
                    best = r3;
                    currDiff = Math.abs(r3.value - target);
                }
                if (r4 != null && r4.value >= 0 && Math.abs(r4.value - target) < currDiff) {
                    best = r4;
                    currDiff = Math.abs(r4.value - target);
                }//*/

                solve3(newResults1, target);
                solve3(newResults2, target);
                if (r3 != null) {
                    solve3(newResults3, target);
                }
                if (r4 != null && r4.value > 0) {
                    solve3(newResults4, target);
                }

            }
            //
        }
//        System.out.println("currDiff :: " + currDiff);
    }


    public static void main(String a[]) {
        try {
            Countdown c = new Countdown();
            c.process();
            // 1
            // 1 2 2 2 3 11 275
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

}
