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

    public void hit()//Occurs when a civilian ship is shot
    {
        //Need to find which orientation the ship is in
        if(start.get_x() == end.get_x())//If the X's are equal, then it is vertical
        {

            //Iterate through the ships, places and create a hit at that location
            //This might have to change locations/scopes

        }
        else//Not vertical means Horizontal
        {

        }
    }
}
