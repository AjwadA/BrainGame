package braingame;
public class Edge {
    
    private int distance;
    private int time;
    int endID;
    //boolean visited = false;
    
    public Edge(){
    }
    
    public Edge(int d, int t,int endID){
        this.distance = d;
        this.time = t;
        this.endID = endID;
    }
    
    public int get_Distance(){
        return this.distance;
    }
    
    public int get_Time(){
        return this.time;
    }
}
