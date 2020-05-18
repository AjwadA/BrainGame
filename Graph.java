package braingame;

import java.util.*;

public class Graph{
    
    private java.util.LinkedList queue = new java.util.LinkedList(); 
    private int mainIndex=0;//use for vertex
    private int n;
    Vertex[] vertex;

    public Graph(){
    }
    
    public Graph(int n){
        vertex = new Vertex[n];
        this.n=n;
    }
    
    public void add_Vertex(int ID){
        if(find_Index(ID)==-1){
            vertex[mainIndex]= new Vertex(ID,n);
            //System.out.println("Vertex with id["+ID+"] created");
            mainIndex++;}
        else{
            //System.out.println("ID ["+ID+"] is already created");
            System.out.print("");
        }
    }
    
    public int find_Index(int ID){
        for(int k=0;k<this.mainIndex;k++){
            if(vertex[k].ID==ID)
                return k;
        }
        return -1;
    }
    
    private int find_ID(int index){
        return vertex[index].ID;
    }
    
    public void add_Path_single(int inID, int outID, int d, int t){
        int j = find_Index(inID); // ret -1 if no id
        
        if(j==-1){
            add_Vertex(inID);
            j=find_Index(inID);
        }
        
        vertex[j].top(d, t, outID);
        vertex[j].neighbour++;
    }
    
    public void add_Path_both(int inID, int outID, int d, int t){
        int j = find_Index(inID); // ret -1 if no id
        int k = find_Index(outID);
        
        if(k==-1){
            add_Vertex(outID);
            
            k=find_Index(outID);
        }
        if(j==-1){
            add_Vertex(inID);
            j=find_Index(inID);
        }
        
        vertex[j].top(d, t, outID);
        vertex[j].neighbour++;
        vertex[k].top(d, t, inID);
        vertex[k].neighbour++;
    }
    
    //public void add_Path_single(int inID, int outID, int d, int t) //-----------------------------------------[public]
    
    
    private void print_ID_Content(int ID){
        int q = find_Index(ID);
        System.out.print(vertex[q].ID+" : ");
        for(int p=0; p<vertex[q].neighbour; p++){
            System.out.print("["+vertex[q].edge[p].endID+","+vertex[q].edge[p].get_Distance()+","+vertex[q].edge[p].get_Time()+"] ");
        }
    }
    
    public void print_All_ID(){
        for(int q=0;q<mainIndex;q++){
            print_ID_Content(find_ID(q));
            System.out.println("");
        }
    }
    
    private boolean allVisited(int index) {
        int count=0;
        for(int i = vertex[index].neighbour-1;i>=0;i--){
            if(vertex[end_ID_index(index,i)].visit==true)
                count++;
        }
        if(count==vertex[index].neighbour)
            return true;
        else
            return false;
    }
    
    public int end_ID_index(int Index,int i){
        return find_Index(vertex[Index].edge[i].endID);
    }
    
    public void reset(){
        //all visit: true --> false
        for(int i=0;i<n;i++){
            vertex[i].visit=false;
            vertex[i].level=0;
        }
        
        //clear stack
        ID.clear();
        holdID.clear();
        ID_bfs.clear();
        queue.clear();
        
        //FOUND: true --> false
        FOUND=false;
        
        countLVL=0;
        
        //tableSetup: true -->false
        tableSetup=false;
    }
    
    private int find_EdgeIndex(int fromID, int toID){
        int i;
        for(i=0;i<vertex[find_Index(fromID)].neighbour; i++){
            if(vertex[find_Index(fromID)].edge[i].endID==toID)
                return i;
        }
        //System.out.println("Error: edge index not found...");
        //System.out.println(fromID + " : " + toID);
        //System.out.println("i - "+i);
        return -1;
    }
    
    
    
    private void travelled(){
        int distance=0,time=0;
        int fromID;
        while(!ID.empty()){
//            System.out.println("ID    : "+ID.toString());
            holdID.push(ID.pop());
//            System.out.println("holdID: "+holdID.toString());
        }
        while(!holdID.empty()){
            try{
                fromID=holdID.pop();
                distance+=vertex[find_Index(fromID)].edge[find_EdgeIndex(fromID,holdID.peek())].get_Distance();
                time+=vertex[find_Index(fromID)].edge[find_EdgeIndex(fromID,holdID.peek())].get_Time();
            }catch(EmptyStackException e){
                System.out.println("# Distance : "+distance);
                System.out.println("# Time : "+time);
            }
        }
    }
    
    
    
    //======================== S E A R C H E S ========================//
    
    private boolean FOUND = false;
    public Stack<Integer> ID = new Stack();
    private Stack<Integer> holdID = new Stack();
    
    //---------------------- Depth First search------------------------//
    
    public void _DepthFS(int fromID, int toID){
        reset();
        DFS(fromID,toID);
        if(FOUND==false){
            System.out.println("[Depth_FS]");
            System.out.println("path not found...");
        }else{
        System.out.println("[Depth_FS]\n# "+ID.toString());
        travelled();
        }
        System.out.println("");
        reset();
    }
    
    public void DFS(int fromID, int toID){
        
        int index = find_Index(fromID);
        for(int i=0; i<vertex[index].neighbour; i++){
            
            if(FOUND==true)
                break;

            if(vertex[index].visit==false){
                vertex[index].visit = true;
                ID.push(vertex[index].ID);
            }
            
            if(allVisited(index)==true){
                break;
            }

            if(vertex[end_ID_index(index,i)].visit==true){
                while(vertex[end_ID_index(index,i)].visit==true){
                    if(i==vertex[index].neighbour-1)
                        break;
                    i++;
                }
            }

            if(vertex[end_ID_index(index,i)].ID==toID){
                ID.push(vertex[end_ID_index(index,i)].ID);
                FOUND = true;
                break;
            }else{
                DFS(vertex[end_ID_index(index,i)].ID,toID);
            }
        }
        if(FOUND==false){
            ID.pop();
        }
    }
    
    //--------------------- Breadth First search -----------------------//
    
    public Stack<Integer> ID_bfs=new Stack();
    private int countLVL=0;
    
    public void _BreadthFS(int fromID, int toID){
        reset();
        BFS(fromID,toID);
        if(FOUND==false){
            System.out.println("[Breadth_FS]");
            System.out.println("path not found...");
        }else{
        
        while(!ID.isEmpty()){
        ID_bfs.push(ID.pop());
        }
            //System.out.println("id_bfs: "+ID_bfs.toString());
        System.out.println("[Breadth_FS]");
        isConnected();
            //System.out.println("id_bfs: "+ID_bfs.toString());
            System.out.println("# "+ID.toString());
        
        travelled();
        }
        System.out.println("");
        reset();
    }
     
  
      public void BFS(int fromID, int toID){

        int index = find_Index(fromID);  

        countLVL=vertex[index].level;
        countLVL++;

        for(int i=0; i<vertex[index].neighbour; i++){

            if(FOUND==true)
                break;

            if(vertex[index].ID==toID){
                ID.push(vertex[index].ID);
                FOUND=true;
                break;
            }
            if(vertex[index].visit==false || !ID.contains(vertex[index].ID)){
                vertex[index].visit = true;
                ID.push(vertex[index].ID);
            }

            if(allVisited(index)==true){
                break;
            }

            if(vertex[end_ID_index(index,i)].visit==false){
                vertex[end_ID_index(index,i)].visit=true;
                queue.addLast(vertex[end_ID_index(index,i)].ID);
                vertex[end_ID_index(index,i)].level=countLVL;///--------------------------------------------------
               // System.out.println(vertex[end_ID_index(index,i)].ID+" :-->> "+vertex[end_ID_index(index,i)].level);//-------------
            }

        }
            //System.out.println("-----------"+queue.toString()+"---------");
            if(FOUND==false&&queue.isEmpty()==false){
                //System.out.println("in: "+queue.toString());
                BFS((int) queue.pop(),toID);
            }

        if(FOUND==false){
            ID.pop();
        }
      }


      public void isConnected(){

        int k=0,l=1;

        int fromID=ID_bfs.elementAt(k);

        holdID.push(fromID);

        while(true){

            if(l==ID_bfs.size())
                    break;
            int toID=ID_bfs.elementAt(l);


            //System.out.println("--------------------------- fromID: "+fromID+" ---- fromID: "+toID);
            if(find_EdgeIndex(fromID,toID)==-1||vertex[find_Index(fromID)].level<=vertex[find_Index(toID)].level){
               // System.out.println("skip");
                l++;
            }

            else{
                holdID.push(toID);
                fromID=toID;
                if(l==ID_bfs.size()-1)
                    break;
                l++;
            }

        }
        //System.out.println("==================="+holdID.toString());

          while(!holdID.empty()){
            ID.push(holdID.pop());
        }
      }

    //--------------------- Greedy First search -----------------------//
    
    public void _GreedyFS(int fromID,int toID,boolean distance){
        GFS(fromID,toID,distance);
        if(FOUND==false){
            if(distance==true)
                System.out.println("[Greedy_FS - Distance]");
            else
                System.out.println("[Greedy_FS - Time]");
            System.out.println("path not found...");
        }else{
        if(distance==true)
            System.out.println("[Greedy_FS - Distance]\n# "+ID.toString());
        else
            System.out.println("[Greedy_FS - Time]\n# "+ID.toString());
        travelled();
        }
        System.out.println("");
        reset();
    }
    
    public void GFS(int fromID,int toID,boolean distance){
        
        int index = find_Index(fromID);
        int i;
        
        vertex[index].visit = true;        
        ID.push(vertex[index].ID);
        if(vertex[index].ID==toID)
            FOUND=true;
        
        while(allVisited(index)==false&&FOUND==false){
            i=find_smallest_DT_edgeIndex(index,distance);
            GFS(vertex[end_ID_index(index,i)].ID,toID,distance);
            if(FOUND==true){
                break;
            }
            ID.pop();
        }
    }
    
    //Finding smallest distance/time 
    //true-->distance
    //false-->time
    private int find_smallest_DT_edgeIndex(int index,boolean distance){
        int minimum=9999999;
        int indexSmall=0;
        for(int i=0;i<vertex[index].neighbour;i++){
            //System.out.println("-----------------------distance: find smallest dt dijstra: "+distance);
            if(distance==true){
                if(vertex[index].edge[i].get_Distance()<minimum&&vertex[end_ID_index(index,i)].visit==false){
                    minimum=vertex[index].edge[i].get_Distance();
                    indexSmall=i;
                    //System.out.println("-----------------------distance: find smallest dt dijstra: in");
                }
            }else{
                if(vertex[index].edge[i].get_Time()<minimum&&vertex[end_ID_index(index,i)].visit==false){
                    minimum=vertex[index].edge[i].get_Time();
                    indexSmall=i;
                }
            }
        }
        //return index of edge with the smallest distance/time
        return indexSmall;
    }
    
    //=======================================+========================//
    
    
    
    
    //==================== SHORTEST DISTANCE & TIME ====================//    
            
    // -->Dijkstra's shortest path algorithm
    // -->USES greedy first search

    private boolean tableSetup = false;
    int[][] table;
    
    public void DijsktraGUI(int fromID,int toID,boolean distance){
        Dijsktra(fromID,distance);
        int id = find_Index(toID);
        int shortDT = table[id][0];
        if(shortDT==999999){
           System.out.print("");
           reset();
        }
        else{        
            reset();
            table2stack(id);
            while(!holdID.empty()){
                ID.push(holdID.pop());
            }
        }
    }
    
    private void Dijsktra(int fromID,boolean distance){
        
        int index = find_Index(fromID);
        vertex[index].visit = true;
        //update table
        if(tableSetup==false){
            setupTable(fromID);
            tableSetup=true;
          //  printTable();
        }
        updateTable(index,distance);
       // printTable();
        
        ID.push(vertex[index].ID);
        int i;
        while(allVisited(index)==false){
            i=find_smallest_DT_edgeIndex(index,distance);
            Dijsktra(vertex[end_ID_index(index,i)].ID,distance);
        }
    }
        
    private void setupTable(int fromID){
        table = new int[n][2];
        int index = find_Index(fromID);
        for(int i=0;i<n;i++){
            if(i==index){
                table[i][0]=0;
                table[i][1]=-1;
            }else
                table[i][0]=999999;
        }
    }
    
    private void updateTable(int index, boolean distance){
        //System.out.println("current : "+vertex[index].ID);
        int endID_index;
        for(int i=0; i< vertex[index].neighbour; i++){
            endID_index = end_ID_index(index,i);
            if(vertex[endID_index].visit==true){
                continue;
            }
            if(distance==true){
                if((table[index][0]+vertex[index].edge[i].get_Distance())<table[endID_index][0]){
                    table[endID_index][0]=(table[index][0]+vertex[index].edge[i].get_Distance());
                    table[endID_index][1]=index;
                }
            }else{
                if((table[index][0]+vertex[index].edge[i].get_Time())<table[endID_index][0]){
                    table[endID_index][0]=(table[index][0]+vertex[index].edge[i].get_Time());
                    table[endID_index][1]=index;
                }
            }
        }
        //printTable();
    }  
    
    private void printTable(){
        System.out.println("Node ID | shortest | prev ID");
        for(int i=0;i<n;i++){
            System.out.print(vertex[i].ID+"\t| ");
            System.out.print(table[i][0]+"\t| ");
            if(table[i][1]==-1)
                System.out.println("start");
            else
                System.out.println(vertex[table[i][1]].ID);
        }
        System.out.println("");
    }
    
    public void _ShortestDT(int fromID,int toID){
        
        int distance,time;
        int id = find_Index(toID);
        int id2 = find_Index(fromID);
        
        if(id==-1||id2==-1){
            System.out.println("Error id not found");
        }else{
            System.out.println("[Shortest Distance & Time]");
            //System.out.println(fromID+" >> "+toID);

            Dijsktra(fromID,true);
            distance = table[id][0];
            if(distance==999999)
                    System.out.print("");
            else{
                System.out.println("Distance : " + distance);
                reset();
                table2stack(id);
                restack();
                reset();
            }


            Dijsktra(fromID,false);
            time = table[id][0];
            if(time==999999)
                    System.out.print("");
            else{
                System.out.println("Time : "+time);
                reset();
                table2stack(id);
                restack();
                reset();
            }
            
            if(distance==999999||time==999999)
                System.out.println("path not found...");
        
        }
    }
    
    private void table2stack(int index){ //now is the goal index
        
        if(index==-1){
        }else{
            holdID.push(find_ID(index));
            table2stack(table[index][1]);
        }
    }
    
    private void restack(){
        while(!holdID.empty()){
            ID.push(holdID.pop());
        }
        System.out.println("# "+ID.toString());
    }
    //--------------------------------------------------------------------//
    
    
    
    
    
}
