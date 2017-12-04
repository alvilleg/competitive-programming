import java.io.IOException;
import java.util.*;

/**
 * Created by alde1 on 4/5/14.
 */
public class MainTravel {

    Solution bestResult;
    PriorityQueue<Node> queue;
    boolean DEBUG = false;
    Comparator<Node> comparatorDistance;
    Comparator<Node> comparatorTemperature;
    List<Integer> temperatures = new ArrayList<Integer>();

    MainTravel() {
        queue = new PriorityQueue<Node>();
        //
        comparatorDistance = new Comparator<Node>() {

            @Override
            public int compare(Node node, Node node2) {
                return node.d - node2.d;
            }
        };
        //
        comparatorTemperature = new Comparator<Node>() {

            @Override
            public int compare(Node node, Node node2) {
                return node.r - node2.r;
            }
        };
        //
        bestResult = new Solution();
        bestResult.r = Integer.MAX_VALUE;
        bestResult.d = Integer.MAX_VALUE;
        //
    }

    interface Filter {

        public boolean filter(Edge e);
    }
    Filter filterNoFilter = new Filter() {

        @Override
        public boolean filter(Edge e) {
            return false;
        }
    };

    //
    interface Relaxer {

        public void relax(Edge e, Node current, Node parent,
                PriorityQueue<Node> queue_);
    }
    //
    private Relaxer relax_D = new Relaxer() {

        @Override
        public void relax(MainTravel.Edge edge, Node current, Node parent,
                PriorityQueue<Node> queue_) {
            if (DEBUG) {
                System.out.println("relax_D: [" + current.id + "] parent: ["
                        + parent.id + "]");
                System.out.println("AFT relax_D: [" + current.d + "] ");
            }
            if (current.d > (edge.d + parent.d)) {
                current.parent = parent;
                current.r = Math.max(edge.r, parent.r);
                current.d = (edge.d + parent.d);
                queue_.add(current);
            }
            if (DEBUG) {
                System.out.println("END relax_D: [" + current.d + "] ");
            }
        }
    };
    private Relaxer relax_T = new Relaxer() {

        @Override
        public void relax(MainTravel.Edge edge, Node current, Node parent,
                PriorityQueue<Node> queue_) {
            if (DEBUG) {
                System.out.println("relax_T: [" + current.id + "] parent: ["
                        + parent.id + "] rp:[" + parent.r + "] er: [" + edge.r
                        + "]");
                System.out.println("AFT relax_T: [" + current.r + "] ");
            }
            if (current.r > (Math.max(edge.r, parent.r))) {
                current.parent = parent;
                current.r = Math.max(edge.r, parent.r);
                current.d = edge.d + parent.d;
                //
                queue.add(current);
            }
            if (DEBUG) {
                System.out.println("END relax_T: [" + current.r + "] ");
            }
        }
    };

    class Solution {

        String path;
        int r;
        int d;

        public void print() {
            System.out.println(path);
            System.out.printf("%.1f %.1f\n", ((double) d / 10),
                    ((double) r / 10));
        }
    }

    class Edge {

        Edge() {
        }
        int toNode;
        int r, d;
    }

    class Node {

        public boolean reached = false;

        Node() {
            adj = new ArrayList<Edge>();
        }
        List<Edge> adj;
        int id;
        int d = Integer.MAX_VALUE;
        int r = Integer.MAX_VALUE;
        Node parent = null;

        public String path() {
            if (parent != null) {
                return parent.path() + " " + id;
            }
            return id + "";
        }
    }

    public static void main(String a[]) throws IOException {
        Scanner in = new Scanner(System.in);
        while (true) {
            MainTravel mainTravel = new MainTravel();
            mainTravel.readAndProcess(in);
            if (!in.hasNext()) {
                break;
            }
        }
        System.exit(0);
    }

    private void readAndProcess(Scanner in) {
        int N = in.nextInt();
        int E = in.nextInt();
        List<Node> nodes = new ArrayList<Node>();
        for (int i = 1; i <= N; i++) {
            Node n = new Node();
            n.id = i;
            nodes.add(n);
        }
        int s = in.nextInt();
        int t = in.nextInt();
        for (int e = 0; e < E; e++) {
            int u = in.nextInt();
            int v = in.nextInt();
            double r = in.nextDouble();
            double d = in.nextDouble();
            //
            Node nu = nodes.get(u - 1);
            Node nv = nodes.get(v - 1);
            // u,v
            Edge edgeU_V = new Edge();
            edgeU_V.d = (int) (d * 10);
            edgeU_V.r = (int) (r * 10);
            edgeU_V.toNode = v;
            nu.adj.add(edgeU_V);
            // v,u
            Edge edgeV_U = new Edge();
            edgeV_U.d = (int) (d * 10);
            edgeV_U.r = (int) (r * 10);
            edgeV_U.toNode = u;
            nv.adj.add(edgeV_U);
            //
            temperatures.add(edgeU_V.r);
        }
        Collections.sort(temperatures);
        findPathsAux(s, t, nodes);
    }

    private void findPathsAux(int s, int t, List<Node> nodes) {
        Node startNode = nodes.get(s - 1);
        //
        for (final Integer temperature : temperatures) {
            reset(nodes, comparatorDistance);
            startNode.d = 0;
            startNode.r = 0;
            startNode.reached = false;
            queue.add(startNode);
            Filter filterTemperature = new Filter() {

                @Override
                public boolean filter(Edge e) {
                    return e.r > temperature;
                }
            };
            dijkstra(startNode, t, nodes, filterTemperature, relax_D);
            Node tNode = nodes.get(t - 1);
            // Found a best solution with shortest path
            if (tNode.d < bestResult.d) {
                bestResult.r = tNode.r;
                bestResult.d = tNode.d;
                bestResult.path = tNode.path();
                break;
            }
        }
        bestResult.print();
    }

    private void findPaths(int s, int t, List<Node> nodes) {
        Node startNode = nodes.get(s - 1);
        reset(nodes, comparatorTemperature);
        startNode.d = 0;
        startNode.r = 0;
        startNode.reached = false;
        queue.add(startNode);
        //
        dijkstra(startNode, t, nodes, filterNoFilter, relax_T);
        // Set the best solution
        bestResult.r = nodes.get(t - 1).r;
        bestResult.d = nodes.get(t - 1).d;
        bestResult.path = nodes.get(t - 1).path();
        // /
        if (DEBUG) {
            System.out.println("Best result: ");
            bestResult.print();
        }
        //
        reset(nodes, comparatorDistance);
        startNode = nodes.get(s - 1);
        startNode.d = 0;
        startNode.r = 0;
        startNode.reached = false;
        queue.add(startNode);
        Filter filterTemperature = new Filter() {

            @Override
            public boolean filter(Edge e) {
                return e.r > bestResult.r;
            }
        };
        dijkstra(startNode, t, nodes, filterTemperature, relax_D);
        Node tNode = nodes.get(t - 1);
        // Found a best solution with shortest path
        if (tNode.d < bestResult.d) {
            bestResult.r = tNode.r;
            bestResult.d = tNode.d;
            bestResult.path = tNode.path();
        }
        bestResult.print();
    }

    private void reset(List<Node> nodes, Comparator<Node> comparator) {
        queue = new PriorityQueue<Node>(100, comparator);
        for (Node n : nodes) {
            n.parent = null;
            n.r = Integer.MAX_VALUE;
            n.d = Integer.MAX_VALUE;
            n.reached = false;
        }
    }

    private void dijkstra(Node startNode, int t, List<Node> nodes,
            Filter filter, Relaxer relaxer) {
        while (!queue.isEmpty()) {
            Node nextNode = queue.poll();
            if (nextNode.reached) {
                continue;
            }
            if (DEBUG) {
                System.out.println("****** TEMPERATURE Next node: ["
                        + nextNode.id + "] [" + nextNode.r + "]");
            }
            nextNode.reached = true;
            if (startNode.id == t) {
                return;
            }
            for (Edge edge : nextNode.adj) {
                if (filter.filter(edge)) {
                    continue;
                }
                Node target = nodes.get(edge.toNode - 1);
                relaxer.relax(edge, target, nextNode, queue);
            }
        }
    }
}
