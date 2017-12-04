import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by alde1 on 7/1/14.
 */
public class MainCheckPath {

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
        int color = -1;

        public String path() {
            if (parent != null) {
                return parent.path() + " " + id;
            }
            return id + "";
        }
    }

    public void checkPath(Scanner in) {
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
        }
        boolean exists = findPaths(s, t, nodes);
        System.out.println("Exists: " + exists);
    }

    private boolean findPaths(int s, int t, List<Node> nodes) {
        for (Node v : nodes) {
            v.reached = false;
            v.parent = null;
            v.color = -1;
        }
        Node sn = nodes.get(s - 1);
        Node tn = nodes.get(t - 1);
        sn.color = 11;
        existPath(s, t, nodes);
        return sn.color == tn.color;
    }

    private void existPath(int current, int t, List<Node> nodes) {
        Node startNode = nodes.get(current - 1);
        startNode.color = 11;
        for (Edge e : startNode.adj) {
            Node toNode = nodes.get(e.toNode - 1);
            if (toNode.color != -1) {
                continue;
            }
            existPath(e.toNode, t, nodes);
        }
    }

    public static void main(String a[]) {
        Scanner in = new Scanner(System.in);
        while (true) {
            MainCheckPath mainTravel = new MainCheckPath();
            mainTravel.checkPath(in);
            if (!in.hasNext()) {
                break;
            }
        }
        System.exit(0);
    }
}
