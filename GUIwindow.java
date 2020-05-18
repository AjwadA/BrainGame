package braingame;

import javax.swing.JFrame;

public class GUIwindow extends JFrame{
    
    
    public GUIwindow(){
        super.setTitle("Brain Game");
        super.setSize(1000, 700);
        super.setResizable(false);
        super.add(new Content());
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setVisible(true);
    }
    
    public static void main(String[] args){
        new GUIwindow(); 
    }
}
