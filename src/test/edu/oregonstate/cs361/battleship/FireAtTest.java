/**
 * Created by Zaengle on 2/4/2017.
 */

/**
 * These tests check to see that all the helper
 * fucntions for fireAt() in Main work properly.
 * */

package edu.oregonstate.cs361.battleship;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class FireAtTest {
    
    //This function tests to make sure the game over function returnes the correct value when the number of hits
    // by the player OR the AI reaches the maximum number of hits for their respective board. IE: all ships for
    // that board have been sunk.
    //This covers the fire user story, the AI user story, the place ships user story, and the win condition user story
    @Test
    public void gameOverTest(){
        BattleshipModel model = new BattleshipModel();
        BattleshipModel model2 = new BattleshipModel();

        assertEquals(false, Main.game_over(model));

        for(int i = 0; i <= 15; i = i + 1){
            model.add_player_hit(new Coord(1, i));
        }

        assertEquals(true, Main.game_over(model));

        for(int i = 0; i <= 15; i = i + 1){
            model2.add_computer_hit(new Coord(1, i));
        }

        assertEquals(true, Main.game_over(model2));
    }
    
    //This function is used to make sure there is no colision with the passed ship at the given position
    //This makes sure the user's hits/ misses register as they should
    //This makes sure the AI's hits/missses register as they should
    //This makes sure the ships can NOT be overlaping at any point
    //This covers The fire user story, the AI user story, and the place ships user story
    @Test
    public void posHelperTest(){
        Ship model = new Ship("Tester", 5, 1, 2, 1, 6);

        Coord starthit = new Coord(1, 2);
        Coord endmiss = new Coord(1, 7);

        assertEquals(true, Main.posHelper(model, starthit));
        assertEquals(false, Main.posHelper(model, endmiss));
    }
    
    //This test makes sure the user can only fire at each location once and is used to make sure the AI doesn't fire more than the player
    @Test
    public void checkPlayerShotTest(){
        BattleshipModel model = new BattleshipModel();
        model.add_computer_hit(new Coord(1, 2));
        model.add_computer_miss(new Coord(1, 1));

        assertEquals(false, Main.checkPlayerShot(model, new Coord(1, 2)));
        assertEquals(false, Main.checkPlayerShot(model, new Coord(1, 1)));
        assertEquals(true, Main.checkPlayerShot(model, new Coord(4, 4)));
    }

    //This is a test of the function that makes sure the AI doenst shoot the same place twice and doesn't shoot out of bounds
    //This covers part of the fire user story AND the AI user story
    //This ALSO covers part of the AI user story as the function prevents the AI from firing if the user tries to fire at the same location
    @Test
    public void checkValidShotTest(){
        BattleshipModel model = new BattleshipModel();
        model.add_player_hit(new Coord(1, 2));
        model.add_player_miss(new Coord(1, 1));

        assertEquals(false, Main.checkValidShot(model, new Coord(1, 2)));
        assertEquals(false, Main.checkValidShot(model, new Coord(1, 1)));
        assertEquals(true, Main.checkValidShot(model, new Coord(4, 4)));
        assertEquals(false, Main.checkValidShot(model, new Coord(11, 4)));
    }
    
    //This tests the fire user story and the AI user story
    @Test
    public void testDoFire(){
        BattleshipModel model = new BattleshipModel();
        Coord shot = new Coord(1, 1);
        Gson gson = new Gson();
        Ship AC = new Ship("AircraftCarrier", 5, 1, 2, 1, 7);

        model.setAircraftCarrier(AC);

        model = Main.doMyFire(model, shot, gson);
        model = Main.doMyFire(model, new Coord(0, 0), gson);
        model = Main.doMyFire(model, new Coord(1, 1), gson);
        model = Main.doMyFire(model, new Coord(1, 2), gson);
        model = Main.doMyFire(model, new Coord(1, 3), gson);
        model = Main.doMyFire(model, new Coord(1, 4), gson);
    }


    //This just makes sure the change is made, we know it displays "W" "L" on the boards
    @Test
    public void testGameCompleteDraw(){
        BattleshipModel test = new BattleshipModel();
        BattleshipModel model = new BattleshipModel();
        Main.game_complete(model, true);
        Main.game_complete(model, false);
    }

}
