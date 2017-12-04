
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by alde on 11/28/17.
 *
 * MainKing CCPL2017 15
 *
 * 11352 - Crazy King
 *
 */
public class MainKing {


    public static void main(String a[]) {
        try {

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(System.in));

            String line = br.readLine();
            int T = Integer.parseInt(line.trim());
            MainKing king = new MainKing();
            for (int i = 0; i < T; i++) {
                line = br.readLine();
                if (line == null) {
                    break;
                }
                StringTokenizer st = new StringTokenizer(line);
                int M = Integer.parseInt(st.nextToken());
                int N = Integer.parseInt(st.nextToken());

                char mat[][] = new char[M][N];
                for (int j = 0; j < M; j++) {
                    line = br.readLine();
                    line = line.trim();
                    for (int k = 0; k < N; k++) {
                        mat[j][k] = line.charAt(k);

                    }
                }
                king.solve(mat);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void print(char mat[][]){
        if(true ){
            return;
        }
        for(int i=0;i<mat.length;i++){
            for(int j=0;j<mat[0].length;j++){
                System.out.print(mat[i][j]);
            }
            System.out.println();
        }
    }


    private  void solve(char[][] mat) {
        print(mat);
        for(int i=0;i<mat.length;i++) {
            for (int j = 0; j < mat[0].length; j++) {
                if(mat[i][j] == 'Z'){
                    updateThreat(mat, i, j);
                }
            }
        }
        print(mat);
        bfs('B', mat);
    }

    private void updateThreat(char[][] mat, int i, int j) {
        updatePosition(mat, i+2,j+1);
        updatePosition(mat, i+2,j-1);
        updatePosition(mat, i-2,j+1);
        updatePosition(mat, i-2,j-1);
        //
        updatePosition(mat, i+1,j+2);
        updatePosition(mat, i+1,j-2);
        updatePosition(mat, i-1,j+2);
        updatePosition(mat, i-1,j-2);

    }

    private void updatePosition(char[][] mat, int i, int j) {

        if(i< mat.length && i>=0 && j<mat[0].length && j>=0){
            if(mat[i][j] == '.' ) {
                mat[i][j] = 'X';
            }
        }
    }


    class Node{
        int i;
        int j;
        int steps;
        Node(){

        }
    }

    private void bfs(char target, char mat[][]){
        List<Node> queue = new LinkedList<Node>();
        for(int i=0;i<mat.length;i++) {
            for (int j = 0; j < mat[0].length; j++) {
                if (mat[i][j] == 'A') {
                    Node n = new Node();
                    n.i =i;
                    n.j=j;
                    n.steps =0;
                    queue.add(n);
                    break;
                }
            }

        }
        boolean found = false;
        while (!queue.isEmpty()){
            Node node = queue.remove(0);
            if(mat[node.i][node.j] == 'B') {
                found = true;
                System.out.println("Minimal possible length of a trip is " + node.steps);
                break;
            }
            if(mat[node.i][node.j] == '.') {
                mat[node.i][node.j] = 'P';
            }
            addNodeValidMove(node.i+1,node.j, node.steps ,queue, mat);
            addNodeValidMove(node.i+1,node.j-1, node.steps ,queue, mat);
            addNodeValidMove(node.i+1,node.j+1, node.steps ,queue, mat);
            addNodeValidMove(node.i-1,node.j, node.steps ,queue, mat);
            addNodeValidMove(node.i-1,node.j-1, node.steps ,queue, mat);
            addNodeValidMove(node.i-1,node.j+1, node.steps ,queue, mat);
            addNodeValidMove(node.i,node.j-1, node.steps ,queue, mat);
            addNodeValidMove(node.i,node.j+1, node.steps ,queue, mat);
        }
        if(!found) {
            System.out.println("King Peter, you can't go now!");
        }
    }

    private void addNodeValidMove(int i, int j, int steps, List<Node> queue, char[][] mat) {
        if (i < mat.length && i >= 0 && j < mat[0].length && j >= 0) {
            if (mat[i][j] == '.' || mat[i][j] == 'B') {
                Node node = new Node();
                node.steps = steps + 1;
                node.i = i;
                node.j = j;
                if(mat[i][j] == '.') {
                    mat[node.i][node.j] = 'P';
                }
                queue.add(node);
            }
        }

    }

}
