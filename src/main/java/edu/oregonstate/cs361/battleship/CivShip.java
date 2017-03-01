package edu.oregonstate.cs361.battleship;

/**
 * Created by MarkSprouse on 3/1/2017.
 */
public class CivShip extends Ship {
    private String name;
    private Coord start;
    private Coord end;
    private int length;

    public CivShip(String id, int length, int x1, int y1, int x2, int y2) {
        super(id,length,x1,y1,x2,y2);

    }

    public void hit()//Occures when a civilian ship is shot
    {

    }
}
