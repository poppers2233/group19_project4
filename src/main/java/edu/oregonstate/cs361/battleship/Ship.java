package edu.oregonstate.cs361.battleship;


/**
 * Created by Juanma on 2/2/2017.
 */
public class Ship {

    private String name;
    private Coord start;
    private Coord end;
    private int length;

    public Ship(String id, int length, int x1,int y1,int x2,int y2){
        this.length = length;
        this.name = id;
        this.start = new Coord(x1,y1);
        this.end = new Coord(x2,y2);

    }

    public int get_length(){ return length; }
    public Coord get_start(){ return start; }
    public Coord get_end(){ return end; }
    public String get_name(){ return name; }

    public void set_location(int col, int row, String orientation){
        int x1 = col;
        int y1 = row;
        int x2 = 0;
        int y2 = 0;

        if(orientation.equals("horizontal")){
             x2 = x1;
             y2 = y1+length;
             if(x2 > 10 || y2 > 10){
                 x2 = 0;
                 y2 = 0;
             }
        }
        else{
             x2 = x1+length;
             y2 = y1;
        }
        if(x2 > 10 || y2 > 10){
            x2 = 0;
            y2 = 0;
        }
        start = new Coord(x1,y1);
        end   = new Coord(x2,y2);
    }

//////////////////////////////////////////////////////////////////////////////
    //Pulled directly from their github and altered to compile
    //NOTE: get_x and get_y may need to be switched.  Context switch
    //May have jumbled them
//////////////////////////////////////////////////////////////////////////////
    public boolean covers(Coord test) {
        //horizontal
        if(start.get_x() == end.get_x()){
            if(test.get_x() == start.get_x()){
                if((test.get_y() >= start.get_y()) &&
                        (test.get_y() <= end.get_y()))
                    return true;
            } else {
                return false;
            }
        }
        //vertical
        else{
            if(test.get_y() == start.get_y()){
                if((test.get_x() >= start.get_x()) &&
                        (test.get_x() <= end.get_x()))
                    return true;
            } else {
                return false;
            }

        }
        return false;
    }

    public boolean scan(Coord coor) {
        if(covers(coor)){
            return true;
        }
        if(covers(new Coord(coor.get_x()-1,coor.get_y()))){
            return true;
        }
        if(covers(new Coord(coor.get_x()+1,coor.get_y()))){
            return true;
        }
        if(covers(new Coord(coor.get_x(),coor.get_y()-1))){
            return true;
        }
        if(covers(new Coord(coor.get_x(),coor.get_y()+1))){
            return true;
        }
        return false;
    }
}
