package edu.oregonstate.cs361.battleship;

import com.google.gson.Gson;
import spark.Request;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import java.util.Random;


public class Main {
	
	
	public static int boardWidth = 10;
	public static int boardHeight = 10;
	public static int vertical = 1;
	public static int horizontal = 0;

    public static void main(String[] args) {
    	
        //This will allow us to server the static pages such as index.html, app.js, etc.
        staticFiles.location("/public");

        //This will listen to GET requests to /model and return a clean new model
        get("/model", (req, res) -> newModel());
        //This will listen to POST requests and expects to receive a game model, as well as location to fire to
        post("/fire/:row/:col", (req, res) -> fireAt(req));
        //This will listen to POST requests and expects to receive a game model, as well as location to place the ship
        post("/placeShip/:id/:row/:col/:orientation", (req, res) -> placeShip(req));
        

    }
    //This function should return a new model
    static String newModel() {
    	BattleshipModel model = new BattleshipModel();
    	Gson gson = new Gson();
    	System.out.println(gson.toJson(model));
        return gson.toJson(model);
    }

    //This function should accept an HTTP request and deseralize it into an actual Java object.
    private static BattleshipModel getModelFromReq(Request req){
    	String boardState = req.body(); //should be a JSON stored as a string
        Gson gson = new Gson();
        BattleshipModel x = gson.fromJson(boardState, BattleshipModel.class); //should create a battleshipModel object from the board state json
        return x; //returns the battleship model
    }

    //This controller should take a json object from the front end, and place the ship as requested, and then return the object.
    private static String placeShip(Request req) {
    	
    	
   //------------------------------Parsing and execution of the player's turn
    	
        BattleshipModel model = getModelFromReq(req); //calls above function to create an object from board state
        Gson gson = new Gson();
        //declares variables for the details specified for the ship
        String id = req.params(":id");
        int row = Integer.parseInt(req.params(":row"));
        int col = Integer.parseInt(req.params(":col"));
        String orientation = req.params(":orientation");

        if(id.equals("AircraftCarrier")){
            model.getAircraftCarrier().set_location(row, col, orientation);
        }
        else if(id.equals("BattleShip")){
            model.getBattleship().set_location(row, col, orientation);
        }
        else if(id.equals("Cruiser")){
            model.getCruiser().set_location(row, col, orientation);
        }
        else if(id.equals("Destroyer")){
            model.getDestroyer().set_location(row, col, orientation);
        }
        else if(id.equals("Submarine")){
            model.getSubmarine().set_location(row, col, orientation);
        }
        
        
      //------------------------------Execution of the AI's turn
        
        Random rand = new Random(System.currentTimeMillis());
        int AICol, AIRow, AIOrientation;
        do
        {
        	AICol = rand.nextInt(boardWidth);
        	AIRow = rand.nextInt(boardHeight);
        	AIOrientation = rand.nextInt(1);
        	
        }while(!checkValidLocation(AICol,AIRow, AIOrientation));
        
        //coords are now valid (in theory)
        
        model.getUnplacedShip().set_location(AIRow, AICol, getOrientation(AIOrientation));
        
        //Possibly need below code for the AI since they are different objects in BattleshipModel, but they have same ID 
        //so I can't distinguish them right now unless I change the ID names of the AI ships in BattleshipModel
        /*
        else if(id.equals("AIaircraftCarrier")){
            model.getBattleship().set_location(row, col, orientation);
        }
        else if(id.equals("BattleShip")){
            model.getBattleship().set_location(row, col, orientation);
        }
        else if(id.equals("Cruiser")){
            model.getCruiser().set_location(row, col, orientation);
        }
        else if(id.equals("Destroyer")){
            model.getDestroyer().set_location(row, col, orientation);
        }
        else if(id.equals("Submarine")){
            model.getSubmarine().set_location(row, col, orientation);
        }
        */
        
       // Ship currentShip = BattleshipModel.get
        return gson.toJson(model);
    }
    
    private static String getOrientation(int orientation)
    {
    	if(orientation == 0)
    		return "horizontal";
    	return "vertical";
    }
    
    private static boolean checkValidLocation(int x, int y, int orientation)//Needs to check to see if a given coordiante is valid for the ship to be placed at.  OTHER PARAMS MAY BE NEEDED
    {
    	
    	return true;
    }

    //Similar to placeShip, but with firing.
    private static String fireAt(Request req) {
        return null;
    }

}
