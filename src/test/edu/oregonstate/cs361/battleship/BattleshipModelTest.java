/**
 * Created by Zaengle on 2/3/2017.
 */

package edu.oregonstate.cs361.battleship;

import com.google.gson.Gson;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import spark.Spark;
import spark.utils.IOUtils;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static spark.Spark.awaitInitialization;

public class BattleshipModelTest {

    @Test
    public void ModelTest(){
        BattleshipModel model = new BattleshipModel();

        Ship AC = new Ship("AircraftCarrier", 5, 0, 0, 0, 0);
        Ship BS = new Ship("BattleShip", 4, 0, 0, 0, 0);
        CivShip CL = new CivShip("Clipper", 3, 0, 0, 0, 0);
        CivShip DI = new CivShip("Dinghy", 1, 0, 0, 0, 0);
        Ship SB = new Ship("Submarine", 2, 0, 0, 0, 0);
        assertEquals(AC.get_name(), model.getAircraftCarrier().get_name());
        assertEquals(AC.get_name(), model.getAIaircraftCarrier().get_name());

        assertEquals(BS.get_name(), model.getBattleship().get_name());
        assertEquals(BS.get_name(), model.getAIbattleship().get_name());

        assertEquals(SB.get_name(), model.getSubmarine().get_name());
        assertEquals(SB.get_name(), model.getAIsubmarine().get_name());

        assertEquals(CL.get_name(), model.getClipper().get_name());
        assertEquals(CL.get_name(), model.getComputer_clipper().get_name());
        //CivShip Ding;
        assertEquals(DI.get_name(), model.getDinghy().get_name());
        assertEquals(DI.get_name(), model.getComputer_dinghy().get_name());

        model.setAircraftCarrier(AC);
        model.setBattleship(BS);
        model.setSubmarine(SB);
        model.setClipper(CL);
        model.setDinghy(DI);

        model.setAIaircraftCarrier(AC);
        model.setAIbattleship(BS);
        model.setComputer_battleship(BS);
        model.setAIsubmarine(SB);

        assertEquals(BS, model.getComputer_battleship());

        ArrayList<Coord> testHits = new ArrayList<Coord>();
        ArrayList<Coord> testMisses = new ArrayList<Coord>();

        assertEquals(model.get_player_hits(), testHits);
        assertEquals(model.get_computer_misses(), testHits);

        assertEquals(model.get_player_misses(), testMisses);
        assertEquals(model.get_computer_misses(), testMisses);

        Coord shot = new Coord(2, 3);
        Coord miss = new Coord(5, 7);

        testHits.add(shot);
        testMisses.add(miss);

        model.add_computer_hit(shot);
        model.add_computer_miss(miss);
        model.add_player_hit(shot);
        model.add_player_miss(miss);

        assertEquals(model.get_player_hits(), testHits);
        assertEquals(model.get_computer_hits(), testHits);

        assertEquals(model.get_player_misses(), testMisses);
        assertEquals(model.get_computer_misses(), testMisses);

        assertEquals(2, shot.get_x());
        assertEquals(3, shot.get_y());

        ArrayList<Coord> newtest = new ArrayList<Coord>();
        Coord new1 = new Coord(9, 7);
        Coord new2 = new Coord(6, 3);
        Coord new3 = new Coord(0, 1);
        newtest.add(new1);
        newtest.add(new2);
        newtest.add(new3);

        model.setComputerHits(newtest);
        model.setPlayerHits(newtest);

        model.getBattleship().set_location(2, 4, "vertical");
        assertEquals(5, model.getBattleship().get_end().get_x());
        model.getSubmarine().set_location(10, 10, "horizontal");

        //Scan test.
        model.setAIaircraftCarrier(new Ship("AircraftCarrier", 5, 1, 1, 1, 5));
        model.setAIbattleship(new Ship("BattleShip", 4, 4, 1, 4, 4));
        model.setAIsubmarine(new Ship("Submarine", 2, 7, 1, 7, 2));
        model.setAIclipper(new CivShip("Clipper", 3, 10,1,10,3));
        model.setAIdinghy(new CivShip("Dinghy",1,1,10,1,10));

        model.scan(1,1); //Should return true for an aircraft carrier
        assertEquals(model.getScanResult(), true);

        model.scan(4,1);
        //should return false because the battleship has stealth
        assertEquals(model.getScanResult(), false);

        model.scan(7,1);
        //should return false beccause the submarine has stealth
        assertEquals(model.getScanResult(), false);

        model.scan(10,1);
        //should return true for the Clipper
        assertEquals(model.getScanResult(), true);

        model.scan(1,10);
        //should return true for the Dinghy
        assertEquals(model.getScanResult(), true);

        model.scan(10, 10);
        //should return false for not hitting any ship
        assertEquals(model.getScanResult(), false);

        model.setDifficulty(true);
        boolean diff = model.isHard();
        assertEquals(diff, true);

        Coord AIshot = new Coord(1,1);
        model.setAIShot(AIshot);
        Coord Testshot = model.getAIShot();
        assertEquals(Testshot, AIshot);
    }

}
