package edu.oregonstate.cs361.battleship;

import com.google.gson.Gson;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import spark.Spark;
import spark.utils.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static spark.Spark.awaitInitialization;


/**
 * Created by michaelhilton on 1/26/17.
 */
class MainTest {

    @BeforeAll
    public static void beforeClass() {
        Main.main(null);
        awaitInitialization();
    }

    @AfterAll
    public static void afterClass() {
        Spark.stop();
    }

    @Test
    public void testGetModel() {
        TestResponse res = request("GET", "/model");
        assertEquals(200, res.status);
        assertEquals("{\"aircraftCarrier\":{\"name\":\"AircraftCarrier\",\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"length\":5},\"battleship\":{\"name\":\"BattleShip\",\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"length\":4},\"cruiser\":{\"name\":\"Cruiser\",\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"length\":3},\"destroyer\":{\"name\":\"Destroyer\",\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"length\":2},\"submarine\":{\"name\":\"Submarine\",\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"length\":2},\"computer_aircraftCarrier\":{\"name\":\"AircraftCarrier\",\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"length\":5},\"computer_battleship\":{\"name\":\"BattleShip\",\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"length\":4},\"computer_cruiser\":{\"name\":\"Cruiser\",\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"length\":3},\"computer_destroyer\":{\"name\":\"Destroyer\",\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"length\":2},\"computer_submarine\":{\"name\":\"Submarine\",\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"length\":2},\"computerHits\":[],\"computerMisses\":[],\"playerHits\":[],\"playerMisses\":[]}",res.body);
    }

    @Test
    public void testPlaceShip() {
            BattleshipModel model = new BattleshipModel();
            Gson gson = new Gson();
            TestResponse res = request("POST", "/placeShip/aircraftCarrier/1/1/horizontal");
            assertEquals(200, res.status);
            model = gson.fromJson(res.body, BattleshipModel.class);
            assertEquals(1, model.getAircraftCarrier().get_start().get_x());
            assertEquals(1, model.getAircraftCarrier().get_start().get_y());
            assertEquals(1, model.getAircraftCarrier().get_end().get_x());
            assertEquals(6, model.getAircraftCarrier().get_end().get_y());
            //name
            assertEquals("AircraftCarrier", model.getAircraftCarrier().get_name());

            //model = gson.fromJson(, BattleshipModel.class);

            res = request("POST", "/placeShip/submarine/1/1/horizontal");
            model = gson.fromJson(res.body, BattleshipModel.class);
            assertEquals(1, model.getSubmarine().get_start().get_x());
            res = request("POST", "/placeShip/battleship/1/1/horizontal");
            model = gson.fromJson(res.body, BattleshipModel.class);
            assertEquals(1, model.getBattleship().get_start().get_x());
            res = request("POST", "/placeShip/cruiser/1/1/horizontal");
            model = gson.fromJson(res.body, BattleshipModel.class);
            assertEquals(1, model.getCruiser().get_start().get_x());
            res = request("POST", "/placeShip/destroyer/1/1/horizontal");
            model = gson.fromJson(res.body, BattleshipModel.class);
            assertEquals(1, model.getDestroyer().get_start().get_x());

            //out of bounds
            res = request("POST", "/placeShip/aircraftCarrier/12/12/horizontal");
            model = gson.fromJson(res.body, BattleshipModel.class);
            assertEquals(0, model.getAircraftCarrier().get_end().get_x());

            //vertical
            res = request("POST", "/placeShip/aircraftCarrier/1/1/vertical");
            model = gson.fromJson(res.body, BattleshipModel.class);
            assertEquals(1, model.getAircraftCarrier().get_start().get_x());
            res = request("POST", "/placeShip/submarine/1/1/vertical");
            model = gson.fromJson(res.body, BattleshipModel.class);
            assertEquals(1, model.getSubmarine().get_start().get_x());
            res = request("POST", "/placeShip/battleship/1/1/vertical");
            model = gson.fromJson(res.body, BattleshipModel.class);
            assertEquals(1, model.getBattleship().get_start().get_x());
            res = request("POST", "/placeShip/cruiser/1/1/vertical");
            model = gson.fromJson(res.body, BattleshipModel.class);
            assertEquals(1, model.getCruiser().get_start().get_x());
            res = request("POST", "/placeShip/destroyer/1/1/vertical");
            model = gson.fromJson(res.body, BattleshipModel.class);
            assertEquals(1, model.getDestroyer().get_start().get_x());
    }

    private TestResponse request(String method, String path) {
        try {
            URL url = new URL("http://localhost:4567" + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            connection.connect();
            String body = IOUtils.toString(connection.getInputStream());
            return new TestResponse(connection.getResponseCode(), body);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Sending request failed: " + e.getMessage());
            return null;
        }
    }

    private static class TestResponse {

        public final String body;
        public final int status;

        public TestResponse(int status, String body) {
            this.status = status;
            this.body = body;
        }

        public Map<String,String> json() {
            return new Gson().fromJson(body, HashMap.class);
        }
    }


}
