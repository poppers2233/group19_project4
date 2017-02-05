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

    @Test
    public void posHelperTest(){
        Ship model = new Ship("Tester", 5, 1, 2, 1, 6);

        Coord starthit = new Coord(1, 2);
        Coord endmiss = new Coord(1, 7);

        assertEquals(true, Main.posHelper(model, starthit));
        assertEquals(false, Main.posHelper(model, endmiss));
    }

    @Test
    public void checkPlayerShotTest(){
        BattleshipModel model = new BattleshipModel();
        model.add_computer_hit(new Coord(1, 2));
        model.add_computer_miss(new Coord(1, 1));

        assertEquals(false, Main.checkPlayerShot(model, new Coord(1, 2)));
        assertEquals(false, Main.checkPlayerShot(model, new Coord(1, 1)));
        assertEquals(true, Main.checkPlayerShot(model, new Coord(4, 4)));
    }

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

}
