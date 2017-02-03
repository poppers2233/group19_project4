package edu.oregonstate.cs361.battleship;

/**
 * Created by Juanma on 2/2/2017.
 */
public class Coord {
    private int Across;
    private int Down;

    public Coord(int x, int y){
        Across = x;
        Down = y;
    }

    public int get_x(){ return Across; }
    public int get_y() {return Down; }
}
