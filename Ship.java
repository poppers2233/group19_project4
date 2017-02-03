package edu.oregonstate.cs361.battleship;

/**
 * Created by Juanma on 2/2/2017.
 */
public class Ship {

    private String id;
    private int x1,x2,y1,y2,length;

    public Ship(String id, int length, int x1,int y1,int x2,int y2){
        this.length = length;
        this.id = id;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;

    }

    public int get_length(){ return length; }
    /////////////////////////////////////
    public int get_x1(){ return x1; }
    public int get_y1(){ return y1; }
    public int get_x2(){ return x2; }
    public int get_y2(){ return y2; }
    public String get_id(){ return id; }

    /////////////////////////////////////
    public void set_location(int row, int col, String orientation){
        x1 = col;
        y1 = row;

        if(orientation == "vertical"){
            x2 = x1;
            y2 = y1+length;
        }
        else{
            x2 = x1+length;
            y2 = y1;
        }
    }
}
