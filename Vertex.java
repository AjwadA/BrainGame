package braingame;


public class Vertex{
    
    int neighbour=0;
    int ID;
    boolean visit;
    public Edge[] edge;
    int x,y;
    int level;
    
    public void top(int d, int t,int endID){
        int j = findTop();
        edge[j] = new Edge(d,t,endID);
    }
    
    public Vertex(){
    }
    
    public Vertex(int ID,int n){
        edge = new Edge[n-1];
        this.ID=ID;
        this.visit = false;
        this.level=0;
    }
    
    private int findTop(){
        int k=0;
        while(edge[k]!=null){
            k++;
        }
        return k;
    }
    
    
}
