package braingame;

import java.util.Scanner;

public class Test_graph {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        System.out.print("Number of node: ");
        int n = scan.nextInt();
        int a,m,A,d,t;
        
        Graph g = new Graph(n);
        for(int i=0;i<n;i++){
            System.out.print("Node's ID: ");
            a = scan.nextInt();
            g.add_Vertex(a);
            System.out.print("Number of connected node: ");
            m = scan.nextInt();
            for(int j=0; j<m; j++){
                System.out.print("Connected node's ID: ");
                A = scan.nextInt();
                System.out.println("Distance and Time: ");
                d = scan.nextInt();
                t = scan.nextInt();
                g.add_Path_both(a, A, d, t);
            }
            System.out.println("");
        }
        g.print_All_ID();
        System.out.println("\n\n");
        
        int x;
        System.out.print("Number of Message: ");
        x = scan.nextInt();
        int[][] sms = new int[x][2];
        for(int i=0; i<x; i++){
            System.out.println("Start and end: ");
            sms[i][0] = scan.nextInt();
            sms[i][1] = scan.nextInt();
        }
        
        System.out.println("\n\n\n----- outcome -----");
        
        for(int i=0; i<x; i++){
            System.out.println("+== ["+sms[i][0]+"] >> ["+sms[i][1]+"] ==+"); 
            g._DepthFS(sms[i][0], sms[i][1]);
            g._BreadthFS(sms[i][0], sms[i][1]);
            g._GreedyFS(sms[i][0], sms[i][1], true);
            g._GreedyFS(sms[i][0], sms[i][1], false);
            g._ShortestDT(sms[i][0], sms[i][1]);
            System.out.println("\n\n");
        }
        
        
        
        
        
        
        
        
        
        
        
        
//        
//        Graph graph = new Graph(5);
//        
////        int a=1;
//        graph.add_Vertex(a);
//        graph.add_Path_both(a, 2, 4, 7);
//        graph.add_Path_both(a, 3, 2, 1);
//        graph.add_Path_both(a, 4, 6, 2);
//        
//        graph.add_Vertex(2);
//        
//        graph.add_Vertex(3);
//        graph.add_Path_both(3, 4, 3, 3);
//        graph.add_Path_both(3, 5, 7, 2);
//        graph.add_Path_both(3, 6, 7, 4);
//        graph.add_Vertex(4);    
//        graph.print_All_ID();
//        
//        System.out.println("\nDFS");
//        graph.Dijsktra(2, true);
//        
//        
//         test dijsktra
//        
//        graph.add_Vertex(1);
//        graph.add_Vertex(2);
//        graph.add_Vertex(3);
//        graph.add_Vertex(4);
//        graph.add_Vertex(5);
//        graph.add_Path_both(1, 2, 6, 2);
//        graph.add_Path_both(1, 5, 1, 2);
//        graph.add_Path_both(2, 4, 2, 2);
//        graph.add_Path_both(2, 5, 2, 2);
//        graph.add_Path_both(2, 3, 5, 2);
//        graph.add_Path_both(3, 4, 5, 2);
//        graph.add_Path_both(4, 5, 1, 2);
//        graph.print_All_ID();
//        graph._GreedyFS(1, 3, true);
//        graph._GreedyFS(3, 1, true);
//        graph._DepthFS(1, 3);
//        graph._DepthFS(3, 1);
//        System.out.println("\n\n");
//        graph.ShortestDT(3, 1);
//     
//        graph.add_Vertex(1);
//        graph.add_Path_both(1, 2, 1, 1);
//        graph.add_Path_both(2, 3, 1, 1);
//        graph.add_Path_both(3, 4, 1, 1);
//        graph.add_Path_both(2, 5, 2, 2);
//        graph.add_Path_both(5, 6, 2, 2);
//        graph.add_Path_both(6, 8, 2, 2);
//        graph.add_Path_both(5, 7, 3, 3);
//        graph.print_All_ID();
//        graph.Dijsktra_shortest_path(1, 7);
        
    }
}
