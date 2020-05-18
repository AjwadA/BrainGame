package braingame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Content extends JPanel implements ActionListener, KeyListener{
    
    public int n;
    public int SizeOfNode = 40;
    public int frameSize = 1000-20;
    public Graph gr;
    private Timer t;
    public int[][] nodeTable;
    public int[] stackedNode;
    public String search="";
    
    public Content(){
        super.setDoubleBuffered(true);
        t = new Timer(100,this);
        t.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        System.out.println("------INPUT 1------");
        INPUT_1();
        System.out.println("------INPUT 2------");
        INPUT_2();
    }
   
    
    public void paintComponent(Graphics g){
        
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.LIGHT_GRAY);//background
        g2d.fillRect(0, 0, 1000, 700);
        int d=0;
        int e=0;
        
        //PRINT TYPE of search
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, 190, 14);//35 for dfs
        g2d.setColor(Color.BLACK);
        g2d.drawString(""+search, 3, 12);
        
        
        //print lines
        g2d.setColor(Color.BLACK);
        for(int i=0;i<n;i++){
            for(int j=0; j<gr.vertex[i].neighbour; j++)
            g2d.drawLine(gr.vertex[i].x+(this.SizeOfNode/2),gr.vertex[i].y+(this.SizeOfNode/2), gr.vertex[gr.end_ID_index(i, j)].x+(this.SizeOfNode/2), gr.vertex[gr.end_ID_index(i, j)].y+(this.SizeOfNode/2));
        }
        
        //========= stacked line and box
        if(stackedNode[1]!=0){
            g2d.setColor(Color.LIGHT_GRAY);
            for(int i=0;i<n;i++){
                for(int j=0; j<gr.vertex[i].neighbour; j++)
                g2d.drawLine(gr.vertex[i].x+(this.SizeOfNode/2),gr.vertex[i].y+(this.SizeOfNode/2), gr.vertex[gr.end_ID_index(i, j)].x+(this.SizeOfNode/2), gr.vertex[gr.end_ID_index(i, j)].y+(this.SizeOfNode/2));
            }
            g2d.setColor(Color.BLACK);
            for(int i=0;i<n;i++){
                if((i+1)==n)
                    break;
                if(stackedNode[i+1]==0)
                    break;
                else
                    g2d.drawLine(gr.vertex[gr.find_Index(stackedNode[i])].x+(this.SizeOfNode/2), gr.vertex[gr.find_Index(stackedNode[i])].y+(this.SizeOfNode/2), gr.vertex[gr.find_Index(stackedNode[i+1])].x+(this.SizeOfNode/2), gr.vertex[gr.find_Index(stackedNode[i+1])].y+(this.SizeOfNode/2));         
            }
            for(int i=0;i<n;i++){
                if((i+1)==n)
                    break;
                if(stackedNode[i+1]==0)
                    break;
                else{
                    d=(gr.vertex[gr.find_Index(stackedNode[i])].x+(this.SizeOfNode/2)+gr.vertex[gr.find_Index(stackedNode[i+1])].x+(this.SizeOfNode/2))/2;
                    e=(gr.vertex[gr.find_Index(stackedNode[i])].y+(this.SizeOfNode/2)+gr.vertex[gr.find_Index(stackedNode[i+1])].y+(this.SizeOfNode/2))/2;
                    g2d.setColor(Color.ORANGE);
                    g2d.fillRect(d-1, e-10, 25, 14);
                }  
            }
        }
        
        //print node fill
        g2d.setColor(Color.darkGray);
        for(int i=0;i<n;i++){
            g2d.fillOval(gr.vertex[i].x, gr.vertex[i].y, this.SizeOfNode, this.SizeOfNode);
        }
        
        //========= PRINT node after search selected
        g2d.setColor(Color.ORANGE);
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(gr.vertex[i].ID==stackedNode[j]){
                    g2d.fillOval(gr.vertex[i].x, gr.vertex[i].y, this.SizeOfNode, this.SizeOfNode);
                }
            }
        }
        
        //print node"from", node "to" fill
        for(int i=0;i<n;i++){
            if(gr.vertex[i].ID==from){
                g2d.setColor(Color.GREEN);
                g2d.fillOval(gr.vertex[i].x, gr.vertex[i].y, this.SizeOfNode, this.SizeOfNode);
            }
            if(gr.vertex[i].ID==to){
                g2d.setColor(Color.RED);
                g2d.fillOval(gr.vertex[i].x, gr.vertex[i].y, this.SizeOfNode, this.SizeOfNode);
            } 
        }
        
        //print node outline
        g2d.setColor(Color.black);
        for(int i=0;i<n;i++){
            g2d.drawOval(gr.vertex[i].x, gr.vertex[i].y, this.SizeOfNode, this.SizeOfNode);
        }
        
        
        //print info
        for(int i=0;i<n;i++){
            for(int k=0; k<gr.vertex[i].neighbour; k++){
                d=(gr.vertex[i].x+(this.SizeOfNode/2)+gr.vertex[gr.end_ID_index(i, k)].x+(this.SizeOfNode/2))/2;
                e=(gr.vertex[i].y+(this.SizeOfNode/2)+gr.vertex[gr.end_ID_index(i, k)].y+(this.SizeOfNode/2))/2;
                g2d.setColor(Color.BLACK);
                g2d.drawString("["+gr.vertex[i].edge[k].get_Distance()+"/"+gr.vertex[i].edge[k].get_Time()+"]", d, e);
            }
        }
        
        //print node ID
        g2d.setColor(Color.WHITE);
        for(int i=0;i<n;i++){
            g2d.drawString(""+gr.vertex[i].ID, gr.vertex[i].x+(this.SizeOfNode/2)-3, gr.vertex[i].y+(this.SizeOfNode/2)+2);
        }
        
    }
    
    public void INPUT_1(){
        Scanner scan = new Scanner(System.in);
        n = scan.nextInt();
        int a,m,A,d,t;
        
        gr = new Graph(n);
        nodeTable = new int[n][2];
        stackedNode = new int[n];
        for(int i=0;i<n;i++){
            a = scan.nextInt();
            gr.add_Vertex(a);
            m = scan.nextInt();
            for(int j=0; j<m; j++){
                A = scan.nextInt();
                d = scan.nextInt();
                t = scan.nextInt();
                if(d<0&&t<0)
                    gr.add_Path_single(a, A, -d, -t);
                else
                    gr.add_Path_both(a, A, d, t);
            }
        }
        gr.print_All_ID();
    }
    
    int[][] sms;
    public void INPUT_2(){
        int x;
        Scanner scan = new Scanner(System.in);
//        System.out.print("Number of Message: ");
        x = scan.nextInt();
        sms = new int[x][2];
        for(int i=0; i<x; i++){
//            System.out.println("Start and end: ");
            do{
                sms[i][0] = scan.nextInt();
                sms[i][1] = scan.nextInt();
            if(sms[i][0]==sms[i][1])
                System.out.println("Sorry, Start and End ID is the same["+sms[i][0]+"]... try again");
            if(gr.find_Index(sms[i][0])==-1)
                    System.out.println("sorry, Start ID["+sms[i][0]+"] not found... try again");
            if(gr.find_Index(sms[i][1])==-1)
                    System.out.println("sorry, End ID["+sms[i][1]+"] not found... try again");
            }while(sms[i][0]==sms[i][1]||gr.find_Index(sms[i][0])==-1||gr.find_Index(sms[i][1])==-1);
            
        }
        
        System.out.println("\n\n\n----- outcome -----");
        
        for(int i=0; i<x; i++){
            System.out.println("+== ["+sms[i][0]+"] >> ["+sms[i][1]+"] ==+"); 
            gr._DepthFS(sms[i][0], sms[i][1]);
            gr._BreadthFS(sms[i][0], sms[i][1]);
            gr._GreedyFS(sms[i][0], sms[i][1], false);
            gr._GreedyFS(sms[i][0], sms[i][1], true);
            gr._ShortestDT(sms[i][0], sms[i][1]);
            System.out.println("\n\n");
        }
    }

    boolean Repaint;
    @Override
    public void actionPerformed(ActionEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    int j=0;
    int from=0, to=0;
    
    
    @Override
    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();
        Random rgen = new Random();
         
        if(c==KeyEvent.VK_A){
            search = "";
            for(int i=0; i<n; i++){
                gr.vertex[i].x =rgen.nextInt(this.frameSize-this.SizeOfNode);
                gr.vertex[i].y =rgen.nextInt(700-20-this.SizeOfNode);
            }
            for(int i=0;i<n;i++){
                stackedNode[i]=0;
            }
            from=to=0;
            repaint();
        }
        else if(c==KeyEvent.VK_S){//show to and from
            search = "";
            from = sms[j][0];
            to = sms[j][1];
            j++;
            if(j>=sms.length){
                j=0;
            }
            for(int i=0;i<n;i++){
                stackedNode[i]=0;
            }
            repaint();            
        }else if(c==KeyEvent.VK_D&&from!=0&&to!=0){//show all
            search = "";
            for(int i=0;i<n;i++){
                stackedNode[i]=0;
            }
            from=to=0;
            repaint();
        }else if(c==KeyEvent.VK_F&&from!=0&&to!=0){//show all but highlight to and from
            search = "";
            for(int i=0;i<n;i++){
                stackedNode[i]=0;
            }
            repaint();
        }else if(c==KeyEvent.VK_1&&from!=0&&to!=0){//depth first search
            search = "Depth First Search";
            for(int i=0;i<n;i++){
                stackedNode[i]=0;
            }
            gr.DFS(from, to);
            int k=0;
            while(!gr.ID.empty()){
                stackedNode[k]=gr.ID.pop();
                k++;
            }
            gr.reset();
            repaint();
        }else if(c==KeyEvent.VK_2&&from!=0&&to!=0){//depth first search
            search = "Breadth First Search";
            for(int i=0;i<n;i++){
                stackedNode[i]=0;
            }
            gr.reset();
            gr.BFS(from, to);
            while(!gr.ID.isEmpty()){
                gr.ID_bfs.push(gr.ID.pop());
            }
            gr.isConnected();
            int k=0;
            while(!gr.ID.empty()){
                stackedNode[k]=gr.ID.pop();
                k++;
            }
            gr.reset();
            repaint();
        }else if(c==KeyEvent.VK_3&&from!=0&&to!=0){
            search = "Greedy First Search [distance]";
            for(int i=0;i<n;i++){
                stackedNode[i]=0;
            }
            gr.GFS(from, to,true);
            int k=0;
            while(!gr.ID.empty()){
                stackedNode[k]=gr.ID.pop();
                k++;
            }
            gr.reset();
            repaint();
        }else if(c==KeyEvent.VK_4&&from!=0&&to!=0){
            search = "Greedy First Search [time]";
            for(int i=0;i<n;i++){
                stackedNode[i]=0;
            }
            gr.GFS(from, to,false);
            int k=0;
            while(!gr.ID.empty()){
                stackedNode[k]=gr.ID.pop();
                k++;
            }
            gr.reset();
            repaint();
        }else if(c==KeyEvent.VK_5&&from!=0&&to!=0){
            search = "Dijkstra's Shortest Path [distance]";
            for(int i=0;i<n;i++){
                stackedNode[i]=0;
            }
            gr.DijsktraGUI(from, to,true);
            int k=0;
            while(!gr.ID.empty()){
                stackedNode[k]=gr.ID.pop();
                k++;
            }
            gr.reset();
            repaint();
        }else if(c==KeyEvent.VK_6&&from!=0&&to!=0){
            search = "Dijkstra's Shortest Path [time]";
            for(int i=0;i<n;i++){
                stackedNode[i]=0;
            }
            gr.DijsktraGUI(from, to,false);
            int k=0;
            while(!gr.ID.empty()){
                stackedNode[k]=gr.ID.pop();
                k++;
            }
            gr.reset();
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}