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
	public static int max_hits = 16;
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
        System.out.println("hello");
        BattleshipModel model = getModelFromReq(req); //calls above function to create an object from board state
        Gson gson = new Gson();
        //declares variables for the details specified for the ship
        String id = req.params(":id");
        int row = Integer.parseInt(req.params(":row"));
        int col = Integer.parseInt(req.params(":col"));
        String orientation = req.params(":orientation");

        System.out.println("row: " + row + " col: " + col + " id: " + id + " orientation: " + orientation);
        if(id.equals("aircraftCarrier")){
            if(isValidLocation(model, row, col, orientation, 5)) {
                model.getAircraftCarrier().set_location(row, col, orientation);
            }
        }
        else if(id.equals("battleship")){
            if(isValidLocation(model, row, col, orientation, 5)) {
                model.getBattleship().set_location(row, col, orientation);
            }
        }
        else if(id.equals("cruiser")){
            if(isValidLocation(model, row, col, orientation, 5)) {
                model.getCruiser().set_location(row, col, orientation);
            }
        }
        else if(id.equals("destroyer")){
            if(isValidLocation(model, row, col, orientation, 5)) {
                model.getDestroyer().set_location(row, col, orientation);
            }
        }
        else if(id.equals("submarine")){
            if(isValidLocation(model, row, col, orientation, 5)) {
                model.getSubmarine().set_location(row, col, orientation);
            }

        }
        
      //------------------------------Execution of the AI's turn
        
        Random rand = new Random(System.currentTimeMillis());
        int AICol, AIRow, AIOrientation;
        Ship temp = model.getUnplacedShip();
        do
        {
        	AICol = rand.nextInt(boardWidth);
        	AIRow = rand.nextInt(boardHeight);
        	AIOrientation = rand.nextInt(1);
        	
        }while(!checkValidLocation(AICol,AIRow, AIOrientation, temp.get_length()));
        
        //coords are now valid (in theory)
        
        temp.set_location(AIRow, AICol, getOrientation(AIOrientation));//Place a ship into
        
        
        return gson.toJson(model);
    }
    
    private static String getOrientation(int orientation)
    {
    	if(orientation == 0)
    		return "horizontal";
    	return "vertical";
    }
    
    private static boolean isValidLocation(BattleshipModel model, int row, int col, String orientation, int length)//Needs to check to see if a given coordiante is valid for the ship to be placed at.  OTHER PARAMS MAY BE NEEDED
    {
        // aircraft carrier check
        if(     (row != model.getAIaircraftCarrier().get_start().get_x() ) &&
                (col != model.getAIaircraftCarrier().get_start().get_y() )   ) {

            if (orientation.equals("horizontal")) {
                for (int i = 0; i < length; i++) {
                    Coord c = new Coord(row, col + i);
                    if (poshelper(s, c))
                        return false;
                }
            } else {
                for (int i = 0; i < length; i++) {
                    Coord c = new Coord(row + i, col);
                    if (poshelper(s, c))
                        return false;
                }
            }
        }
        // battle ship check
        if(     (row != model.getBattleship().get_start().get_x() ) &&
                (col != model.getBattleship().get_start().get_y() )   ) {

            if (orientation.equals("horizontal")) {
                for (int i = 0; i < length; i++) {
                    Coord c = new Coord(row, col + i);
                    if (poshelper(s, c))
                        return false;
                }
            } else {
                for (int i = 0; i < length; i++) {
                    Coord c = new Coord(row + i, col);
                    if (poshelper(s, c))
                        return false;
                }
            }
        }
        // cruiser check
        if(     (row != model.getCruiser().get_start().get_x() ) &&
                (col != model.getCruiser().get_start().get_y() )   ) {

            if (orientation.equals("horizontal")) {
                for (int i = 0; i < length; i++) {
                    Coord c = new Coord(row, col + i);
                    if (poshelper(s, c))
                        return false;
                }
            } else {
                for (int i = 0; i < length; i++) {
                    Coord c = new Coord(row + i, col);
                    if (poshelper(s, c))
                        return false;
                }
            }
        }
        // destroyer check
        if(     (row != model.getDestroyer().get_start().get_x() ) &&
                (col != model.getDestroyer().get_start().get_y() )   ) {

            if (orientation.equals("horizontal")) {
                for (int i = 0; i < length; i++) {
                    Coord c = new Coord(row, col + i);
                    if (poshelper(s, c))
                        return false;
                }
            } else {
                for (int i = 0; i < length; i++) {
                    Coord c = new Coord(row + i, col);
                    if (poshelper(s, c))
                        return false;
                }
            }
        }
        //submarine check
        if(     (row != model.getSubmarine().get_start().get_x() ) &&
                (col != model.getSubmarine().get_start().get_y() )   ) {

            if (orientation.equals("horizontal")) {
                for (int i = 0; i < length; i++) {
                    Coord c = new Coord(row, col + i);
                    if (poshelper(s, c))
                        return false;
                }
            } else {
                for (int i = 0; i < length; i++) {
                    Coord c = new Coord(row + i, col);
                    if (poshelper(s, c))
                        return false;
                }
            }
        }
    	return true;
    }

    private static boolean checkValidShot(BattleshipModel model, Coord coord)//Checks to see if a shot being done by the AI has already been done
    {
    	//Check to see if it is off the map
    	if(coord.get_x() < 0 || coord.get_x() > boardWidth || coord.get_y() < 0 || coord.get_y() > boardHeight)
    	{
    		return false;
    	}
    	//Check to see if thats been fired before
    	for (int i = 0; i < model.get_computer_hits().size(); i++) {
			if(coord.get_x() == model.get_computer_hits().get(i).get_x())//check if they have matching X coords
			{
				if(coord.get_y() == model.get_computer_hits().get(i).get_y())//check if they have matching Y coords as well
				{
					return false;
				}
			}	
		}
    	
    	for (int i = 0; i < model.get_computer_misses().size(); i++) {
			if(coord.get_x() == model.get_computer_misses().get(i).get_x())//check if they have matching X coords
			{
				if(coord.get_y() == model.get_computer_misses().get(i).get_y())//check if they have matching Y coords as well
				{
					return false;
				}
			}	
		}
    	
    	return true;
    }
    
    //Similar to placeShip, but with firing.
    private static String fireAt(Request req) {
    	
    	Random rand = new Random(System.currentTimeMillis());
      
    	Coord mycoord;
    	
    	
    	//------------------------------Parsing and execution of the player's turn
    	
    	
        System.out.println("fireAt called.");
        BattleshipModel model = getModelFromReq(req);
        Gson gson = new Gson();
        int[] pos = new int[]{0, 0};                                //to be passed to the position helper function
        pos[0] = Integer.parseInt(req.params(":col"));
        pos[1] = Integer.parseInt(req.params(":row"));
        Coord shot = new Coord(pos[0], pos[1]);

        System.out.println(pos[0]);
        System.out.println(pos[1]);

        //if we register any hits
        if(posHelper(model.getAIaircraftCarrier(), shot) || posHelper(model.getAIbattleship(), shot) || posHelper(model.getAIcruiser(), shot) || posHelper(model.getAIdestroyer(), shot) || posHelper(model.getAIsubmarine(), shot)){
            //mark as a hit for the player
            model.add_player_hit(shot);
            System.out.println("hit!");
        } else {
            //mark as a miss for the player
            model.add_player_miss(shot);
            System.out.println("miss!");

        }


        //add to hit/miss array in the gamestate
        //possibly have Computer fire back in this function for ease of programming?
        System.out.println(gson.toJson(model));
      

    	//int[] mycoord = new int[2];//row,col

    	//Player does his fire things
        
    	
    	//Need to check to see if the game is now complete (and who won)
    	
    	
        //------------------------------Execution of the AI's turn
        
        
    	//If game isn't over, AI does his fire
    	
    	
    	if(model.getAIShot() == null)//If the last show wasn't a hit
    	{
    		do
    		{
    			mycoord = new Coord(rand.nextInt(boardHeight), rand.nextInt(boardWidth));
 
    		}while(!checkValidShot(model,mycoord));//while the shot has already been done
    	}
    	else
    	{
    		mycoord = new Coord(model.getAIShot().get_x() + 1, model.getAIShot().get_y());
    		if(!checkValidShot(model, mycoord))//If thats wrong
    			mycoord = new Coord(model.getAIShot().get_x() - 1, model.getAIShot().get_y());
    	}
    	
    	//check to see if the shot hits or misses
    	
    	 //if we register any hits
        if(posHelper(model.getAIaircraftCarrier(), shot) || posHelper(model.getAIbattleship(), shot) || posHelper(model.getAIcruiser(), shot) || posHelper(model.getAIdestroyer(), shot) || posHelper(model.getAIsubmarine(), shot)){
            //mark as a hit for the player
            model.add_computer_hit(shot);
            model.setAIShot(shot);
            System.out.println("hit!");
        } else {
            //mark as a miss for the player
            model.add_computer_miss(shot);
            model.setAIShot(null);
            System.out.println("miss!");

        }
    	
    	//Check to see if the game is over now
    	 return gson.toJson(model);
    
    }

    private static boolean posHelper(Ship model, Coord pos){
        Coord start = model.get_start();
        Coord end = model.get_end();

        if(pos.get_x() >= start.get_x() && pos.get_x() <= end.get_x()) {           //if the x of the shot is within x bounds of ship
            if (pos.get_y() >= start.get_y() && pos.get_y() <= end.get_y()) {      //if the y of the shot is within y bounds of ship
                System.out.println("True");
                return true;
            }
        }
        System.out.println("False");
        return false;
    }
     private static boolean game_over(BattleshipModel model){

        if(model.get_player_hits().size() == max_hits)
        {   return true; }

        if(model.get_computer_hits().size() == max_hits)
        {   return true; }

        else
            return false;
     }
}
