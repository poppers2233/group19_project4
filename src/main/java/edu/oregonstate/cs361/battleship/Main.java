package edu.oregonstate.cs361.battleship;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

public class Main {

    public static void main(String[] args) {


        //This will allow us to server the static pages such as index.html, app.js, etc.
        staticFiles.location("/public");
        //This will listen to GET requests to /model and return a clean new model
        get("/model", (req, res) -> newModel());
        //This will listen to POST requests and expects to receive a game model, as well as location to fire to
        post("/fire/:row/:col", (req, res) -> prepFire(req));
        //This will listen to POST requests and expects to receive a game model, as well as location to scan
        post("/scan/:row/:col", (req, res) -> scan(req));
        //This will listen to POST requests and expects to receive a game model, as well as location to place the ship
        post("/placeShip/:id/:row/:col/:orientation/:difficulty", (req, res) -> placeShip(req));


    }


    private static String scan(Request req) {

        BattleshipModel currModel = getModelFromReq(req);
        String row = req.params("row");
        String col = req.params("col");
        int rowInt = Integer.parseInt(row);
        int colInt = Integer.parseInt(col);
        currModel.scan(rowInt, colInt);
        //currModel.shootAtPlayer();
        Gson gson = new Gson();
        return gson.toJson(currModel);
    }


    //This function should return a new model
    static String newModel() {

        BattleshipModel model = new BattleshipModel();
        Ship ship = new Ship("thefkinship", 1, 0, 0, 0, 0);
        Gson gson = new Gson();
        //System.out.println(gson.toJson(ship));
        //System.out.println(gson.toJson(model));
        return gson.toJson(model);
    }

    //This function should accept an HTTP request and deseralize it into an actual Java object.
    private static BattleshipModel getModelFromReq(Request req) {
        String boardState = req.body(); //should be a JSON stored as a string
        Gson gson = new Gson();
        BattleshipModel x = gson.fromJson(boardState, BattleshipModel.class); //should create a battleshipModel object from the board state json
        return x; //returns the battleship model
    }

    //This controller should take a json object from the front end, and place the ship as requested, and then return the object.
    private static String placeShip(Request req) {

        Random rand = new Random(System.currentTimeMillis());
        int AICol, AIRow;
        AICol = 0;
        AIRow = 0;
        String AIOrientation;
        AIOrientation = "throw me an error";
        String difficulty = req.params(":difficulty");
        Coord[] easyPlace = new Coord[5];
        String[] easyOrient = new String[5];
        System.out.println("difficulty set at " + difficulty);

        //intialize coordinate and orientation arrays for ez mode
        //non casual mode is handled within the actual placement
        for (int i = 0; i < 5; i++) {
            if (i < 3) {
                easyPlace[i] = new Coord((i + 1) * 2, 1);
                easyOrient[i] = "vertical";
            } else {
                easyPlace[i] = new Coord(1, i * 2);
                easyOrient[i] = "horizontal";
            }
        }

        //------------------------------Parsing and execution of the player's turn
        BattleshipModel model = getModelFromReq(req); //calls above function to create an object from board state
        if (model == null) {
            model = new BattleshipModel();
        }
        Gson gson = new Gson();
        //declares variables for the details specified for the ship
        String id = req.params(":id");
        System.out.println("ship type" + id);
        //System.out.println(req);
        int row = Integer.parseInt(req.params(":row"));
        int col = Integer.parseInt(req.params(":col"));
        String orientation = req.params(":orientation");

        //System.out.println("row: " + row + " col: " + col + " id: " + id + " orientation: " + orientation);
        if (id.equals("aircraftCarrier")) {
            System.out.println("dropping aircraft carrier");
            if (isValidLocation(model, row, col, orientation, 5, true)) {
                model.getAircraftCarrier().set_location(row, col, orientation);
                Ship temp = model.getAIaircraftCarrier();
                if (difficulty.equals("hard")) {
                    System.out.println("hard place");
                    do {
                        AICol = rand.nextInt(boardWidth + 1) + 1;
                        AIRow = rand.nextInt(boardHeight + 1) + 1;
                        if (rand.nextInt(2) == 0)
                            AIOrientation = "horizontal";
                        else
                            AIOrientation = "vertical";


                    } while (!isValidLocation(model, AIRow, AICol, AIOrientation, temp.get_length(), false));
                }
                if (difficulty.equals("easy")) {
                    //coords are now valid (in theory)
                    System.out.println("place battleship easy at" + easyPlace[0].get_x() + "," + easyPlace[0].get_y());
                    AICol = easyPlace[0].get_x();
                    AIRow = easyPlace[0].get_y();
                    AIOrientation = easyOrient[0];

                }
                System.out.println("x: " + AICol + "y: " + AIRow + "ori" + AIOrientation);
                model.getAIaircraftCarrier().set_location(AIRow, AICol, AIOrientation);//Place a ship into
            }
        } else if (id.equals("battleship")) {
            if (isValidLocation(model, row, col, orientation, 5, true)) {
                model.getBattleship().set_location(row, col, orientation);
                Ship temp = model.getAIbattleship();
                if (difficulty.equals("hard")) {
                    do {
                        AICol = rand.nextInt(boardWidth + 1) + 1;
                        AIRow = rand.nextInt(boardHeight + 1) + 1;
                        if (rand.nextInt(2) == 0)
                            AIOrientation = "horizontal";
                        else
                            AIOrientation = "vertical";


                    } while (!isValidLocation(model, AIRow, AICol, AIOrientation, temp.get_length(), false));
                }
                if (difficulty.equals("easy")) {
                    //coords are now valid (in theory)
                    AICol = easyPlace[1].get_x();
                    AIRow = easyPlace[1].get_y();
                    AIOrientation = easyOrient[1];

                }

                model.getAIbattleship().set_location(AIRow, AICol, AIOrientation);//Place a ship into
            }
        } else if (id.equals("clipper")) {
            if (isValidLocation(model, row, col, orientation, 5, true)) {
                model.getClipper().set_location(row, col, orientation);
                Ship temp = model.getComputer_clipper();
                if (difficulty.equals("hard")) {
                    do {
                        AICol = rand.nextInt(boardWidth + 1) + 1;
                        AIRow = rand.nextInt(boardHeight + 1) + 1;
                        if (rand.nextInt(2) == 0)
                            AIOrientation = "horizontal";
                        else
                            AIOrientation = "vertical";


                    } while (!isValidLocation(model, AIRow, AICol, AIOrientation, temp.get_length(), false));
                }
                if (difficulty.equals("easy")) {
                    //coords are now valid (in theory)
                    AICol = easyPlace[2].get_x();
                    AIRow = easyPlace[2].get_y();
                    AIOrientation = easyOrient[2];

                }
                model.getComputer_clipper().set_location(AIRow, AICol, AIOrientation);//Place a ship into
            }
        } else if (id.equals("dinghy")) {
            if (isValidLocation(model, row, col, orientation, 5, true)) {
                model.getDinghy().set_location(row, col, orientation);
                Ship temp = model.getComputer_dinghy();
                if (difficulty.equals("hard")) {
                    do {
                        AICol = rand.nextInt(boardWidth + 1) + 1;
                        AIRow = rand.nextInt(boardHeight + 1) + 1;
                        if (rand.nextInt(2) == 0)
                            AIOrientation = "horizontal";
                        else
                            AIOrientation = "vertical";


                    } while (!isValidLocation(model, AIRow, AICol, AIOrientation, temp.get_length(), false));
                }
                if (difficulty.equals("easy")) {
                    //coords are now valid (in theory)
                    AICol = easyPlace[3].get_x();
                    AIRow = easyPlace[3].get_y();
                    AIOrientation = easyOrient[3];

                }
                model.getComputer_dinghy().set_location(AIRow, AICol, AIOrientation);//Place a ship into
            }
        } else if (id.equals("submarine")) {
            if (isValidLocation(model, row, col, orientation, 5, true)) {
                model.getSubmarine().set_location(row, col, orientation);
                Ship temp = model.getAIsubmarine();
                if (difficulty.equals("hard")) {
                    do {
                        AICol = rand.nextInt(boardWidth + 1) + 1;
                        AIRow = rand.nextInt(boardHeight + 1) + 1;
                        if (rand.nextInt(2) == 0)
                            AIOrientation = "horizontal";
                        else
                            AIOrientation = "vertical";


                    } while (!isValidLocation(model, AIRow, AICol, AIOrientation, temp.get_length(), false));
                }
                if (difficulty.equals("easy")) {
                    //coords are now valid (in theory)
                    AICol = easyPlace[4].get_x();
                    AIRow = easyPlace[4].get_y();
                    AIOrientation = easyOrient[4];

                }
                model.getAIsubmarine().set_location(AIRow, AICol, AIOrientation);//Place a ship into
            }

        }

        return gson.toJson(model);
    }

    private static boolean isValidLocation(BattleshipModel model, int row, int col, String orientation, int length, boolean isPlayer)//Needs to check to see if a given coordiante is valid for the ship to be placed at.  OTHER PARAMS MAY BE NEEDED
    {
        if (isPlayer) {
            // aircraft carrier check
            if ((row == model.getAircraftCarrier().get_start().get_x()) &&
                    (col == model.getAircraftCarrier().get_start().get_y())) {
                return false;
            } else {


            }
        }
    }
}