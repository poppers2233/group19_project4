package edu.oregonstate.cs361.battleship;
import spark.Request;

/**
 * Created by MarkSprouse on 3/1/2017.
 */
public class CivShip extends Ship {


    public CivShip(String id, int length, int x1, int y1, int x2, int y2) {
        super(id,length,x1,y1,x2,y2);
    }

    public void hit(BattleshipModel model, boolean isAI)//Occurs when a civilian ship is shot
    {

        model.add_computer_hit(start);
        //Need to find which orientation the ship is in
        if(start.get_x() == end.get_x())//If the X's are equal, then it is vertical
        {
            if(isAI){
                //if its a Clipper need to add a hit at start, end, and in the middle
                if(this.name.equals("Clipper")){
                    model.add_computer_hit(this.start);
                    model.add_computer_hit(new Coord(this.start.get_y()+1, this.start.get_x()));
                    model.add_computer_hit(this.end);
                }
                //if its a dinghy its only one space so just need to add one at start
                else{
                    model.add_computer_hit(this.start);
                }
            }
            else{
                //if its a Clipper need to add a hit at start, end, and in the middle
                if(this.name.equals("Clipper")){
                    model.add_player_hit(this.start);
                    model.add_player_hit(new Coord(this.start.get_y()+1, this.start.get_x()));
                    model.add_player_hit(this.end);
                }
                //if its a dinghy its only one space so just need to add one at start
                else{
                    model.add_player_hit(this.start);
                }
            }

            //Iterate through the ships, places and create a hit at that location
            //This might have to change locations/scopes

        }
        else//Not vertical means Horizontal
        {
            if(isAI){
                //if its a Clipper need to add a hit at start, end, and in the middle
                if(this.name.equals("Clipper")){
                    model.add_computer_hit(this.start);
                    model.add_computer_hit(new Coord(this.start.get_y(), this.start.get_x()+1));
                    model.add_computer_hit(this.end);
                }
                //if its a dinghy its only one space so just need to add one at start
                else{
                    model.add_computer_hit(this.start);
                }
            }
            else{
                //if its a Clipper need to add a hit at start, end, and in the middle
                if(this.name.equals("Clipper")){
                    model.add_player_hit(this.start);
                    model.add_player_hit(new Coord(this.start.get_y(), this.start.get_x()+1));
                    model.add_player_hit(this.end);
                }
                //if its a dinghy its only one space so just need to add one at start
                else{
                    model.add_player_hit(this.start);
                }
            }

        }

    }
}
