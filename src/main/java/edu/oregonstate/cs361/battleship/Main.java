package edu.oregonstate.cs361.battleship;

import com.google.gson.Gson;
import spark.Request;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import java.util.ArrayList;
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

    	Random rand = new Random(System.currentTimeMillis());
        int AICol, AIRow;
        String AIOrientation;
    	
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
            if(isValidLocation(model, row, col, orientation, 5, true)) {
                model.getAircraftCarrier().set_location(row, col, orientation);
                Ship temp = model.getAIaircraftCarrier();
                do
                {
                	AICol = rand.nextInt(boardWidth);
                	AIRow = rand.nextInt(boardHeight);
                	if(rand.nextInt(1) == 0)
                		AIOrientation = "horizontal";
                	else
                		AIOrientation = "vertical";
                	
                	
                }while(!isValidLocation(model, AIRow,AICol, AIOrientation, temp.get_length(), false));
                
                //coords are now valid (in theory)
                
                temp.set_location(AIRow, AICol, AIOrientation);//Place a ship into
            }
        }
        else if(id.equals("battleship")){
            if(isValidLocation(model, row, col, orientation, 5, true)) {
                model.getBattleship().set_location(row, col, orientation);
                Ship temp = model.getAIbattleship();
                do
                {
                	AICol = rand.nextInt(boardWidth);
                	AIRow = rand.nextInt(boardHeight);
                	if(rand.nextInt(1) == 0)
                		AIOrientation = "horizontal";
                	else
                		AIOrientation = "vertical";
                	
                	
                }while(!isValidLocation(model, AIRow,AICol, AIOrientation, temp.get_length(), false));
                
                //coords are now valid (in theory)
                
                temp.set_location(AIRow, AICol, AIOrientation);//Place a ship into
            }
        }
        else if(id.equals("cruiser")){
            if(isValidLocation(model, row, col, orientation, 5, true)) {
                model.getCruiser().set_location(row, col, orientation);
                Ship temp = model.getAIcruiser();
                do
                {
                	AICol = rand.nextInt(boardWidth);
                	AIRow = rand.nextInt(boardHeight);
                	if(rand.nextInt(1) == 0)
                		AIOrientation = "horizontal";
                	else
                		AIOrientation = "vertical";
                	
                	
                }while(!isValidLocation(model, AIRow,AICol, AIOrientation, temp.get_length(), false));
                
                //coords are now valid (in theory)
                
                temp.set_location(AIRow, AICol, AIOrientation);//Place a ship into
            }
        }
        else if(id.equals("destroyer")){
            if(isValidLocation(model, row, col, orientation, 5, true)) {
                model.getDestroyer().set_location(row, col, orientation);
                Ship temp = model.getAIdestroyer();
                do
                {
                	AICol = rand.nextInt(boardWidth);
                	AIRow = rand.nextInt(boardHeight);
                	if(rand.nextInt(1) == 0)
                		AIOrientation = "horizontal";
                	else
                		AIOrientation = "vertical";
                	
                	
                }while(!isValidLocation(model, AIRow,AICol, AIOrientation, temp.get_length(), false));
                
                //coords are now valid (in theory)
                
                temp.set_location(AIRow, AICol, AIOrientation);//Place a ship into
            }
        }
        else if(id.equals("submarine")){
            if(isValidLocation(model, row, col, orientation, 5, true)) {
                model.getSubmarine().set_location(row, col, orientation);
                Ship temp = model.getAIsubmarine();
                do
                {
                	AICol = rand.nextInt(boardWidth);
                	AIRow = rand.nextInt(boardHeight);
                	if(rand.nextInt(1) == 0)
                		AIOrientation = "horizontal";
                	else
                		AIOrientation = "vertical";
                	
                	
                }while(!isValidLocation(model, AIRow,AICol, AIOrientation, temp.get_length(), false));
                
                //coords are now valid (in theory)
                
                temp.set_location(AIRow, AICol, AIOrientation);//Place a ship into
            }

        }
        
        
        return gson.toJson(model);
    }
    
    private static boolean isValidLocation(BattleshipModel model, int row, int col, String orientation, int length, boolean isPlayer)//Needs to check to see if a given coordiante is valid for the ship to be placed at.  OTHER PARAMS MAY BE NEEDED
    {
        if(isPlayer)
        {
        	// aircraft carrier check
            if(     (row != model.getAircraftCarrier().get_start().get_x() ) &&
                    (col != model.getAircraftCarrier().get_start().get_y() )   ) {

            	Ship s = model.getAircraftCarrier();
            	
                if (orientation.equals("horizontal")) {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row, col + i);
                        if (posHelper(s, c))
                            return false;
                    }
                } else {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row + i, col);
                        if (posHelper(s, c))
                            return false;
                    }
                }
            }
            // battle ship check
            if(     (row != model.getBattleship().get_start().get_x() ) &&
                    (col != model.getBattleship().get_start().get_y() )   ) {

            	Ship s = model.getBattleship();
            	
                if (orientation.equals("horizontal")) {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row, col + i);
                        if (posHelper(s, c))
                            return false;
                    }
                } else {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row + i, col);
                        if (posHelper(s, c))
                            return false;
                    }
                }
            }
            // cruiser check
            if(     (row != model.getCruiser().get_start().get_x() ) &&
                    (col != model.getCruiser().get_start().get_y() )   ) {

            	Ship s = model.getCruiser();
            	
                if (orientation.equals("horizontal")) {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row, col + i);
                        if (posHelper(s, c))
                            return false;
                    }
                } else {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row + i, col);
                        if (posHelper(s, c))
                            return false;
                    }
                }
            }
            // destroyer check
            if(     (row != model.getDestroyer().get_start().get_x() ) &&
                    (col != model.getDestroyer().get_start().get_y() )   ) {

            	Ship s = model.getDestroyer();
            	
                if (orientation.equals("horizontal")) {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row, col + i);
                        if (posHelper(s, c))
                            return false;
                    }
                } else {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row + i, col);
                        if (posHelper(s, c))
                            return false;
                    }
                }
            }
            //submarine check
            if(     (row != model.getSubmarine().get_start().get_x() ) &&
                    (col != model.getSubmarine().get_start().get_y() )   ) {

            	Ship s = model.getSubmarine();
            	
                if (orientation.equals("horizontal")) {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row, col + i);
                        if (posHelper(s, c))
                            return false;
                    }
                } else {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row + i, col);
                        if (posHelper(s, c))
                            return false;
                    }
                }
            }
        	return true;
        }
        else
        {
        	// aircraft carrier check
            if(     (row != model.getAIaircraftCarrier().get_start().get_x() ) &&
                    (col != model.getAIaircraftCarrier().get_start().get_y() )   ) {

            	Ship s = model.getAIaircraftCarrier();
            	
                if (orientation.equals("horizontal")) {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row, col + i);
                        if (posHelper(s, c))
                            return false;
                    }
                } else {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row + i, col);
                        if (posHelper(s, c))
                            return false;
                    }
                }
            }
            // battle ship check
            if(     (row != model.getAIbattleship().get_start().get_x() ) &&
                    (col != model.getAIbattleship().get_start().get_y() )   ) {

            	Ship s = model.getAIbattleship();
            	
                if (orientation.equals("horizontal")) {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row, col + i);
                        if (posHelper(s, c))
                            return false;
                    }
                } else {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row + i, col);
                        if (posHelper(s, c))
                            return false;
                    }
                }
            }
            // cruiser check
            if(     (row != model.getAIcruiser().get_start().get_x() ) &&
                    (col != model.getAIcruiser().get_start().get_y() )   ) {

            	Ship s = model.getAIcruiser();
            	
                if (orientation.equals("horizontal")) {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row, col + i);
                        if (posHelper(s, c))
                            return false;
                    }
                } else {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row + i, col);
                        if (posHelper(s, c))
                            return false;
                    }
                }
            }
            // destroyer check
            if(     (row != model.getAIdestroyer().get_start().get_x() ) &&
                    (col != model.getAIdestroyer().get_start().get_y() )   ) {

            	Ship s = model.getAIdestroyer();
            	
                if (orientation.equals("horizontal")) {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row, col + i);
                        if (posHelper(s, c))
                            return false;
                    }
                } else {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row + i, col);
                        if (posHelper(s, c))
                            return false;
                    }
                }
            }
            //submarine check
            if(     (row != model.getAIsubmarine().get_start().get_x() ) &&
                    (col != model.getAIsubmarine().get_start().get_y() )   ) {

            	Ship s = model.getAIsubmarine();
            	
                if (orientation.equals("horizontal")) {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row, col + i);
                        if (posHelper(s, c))
                            return false;
                    }
                } else {
                    for (int i = 0; i < length; i++) {
                        Coord c = new Coord(row + i, col);
                        if (posHelper(s, c))
                            return false;
                    }
                }
            }
        	return true;
        }
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
        
    	
    	//Need to check to see if the game is now complete (and who won)
    	
        
        if(game_over(model))
        	game_complete(model, true);
    	
        //------------------------------Execution of the AI's turn
        
        
    	//If game isn't over, AI does his fire
    	
    	
    	
   		do
   		{
   			mycoord = new Coord(rand.nextInt(boardHeight), rand.nextInt(boardWidth));
   			
     	}while(!checkValidShot(model,mycoord));//while the shot has already been done

    	//check to see if the shot hits or misses
    	
    	 //if we register any hits
        if(posHelper(model.getAIaircraftCarrier(), mycoord) || posHelper(model.getAIbattleship(), mycoord) || posHelper(model.getAIcruiser(), mycoord) || posHelper(model.getAIdestroyer(), mycoord) || posHelper(model.getAIsubmarine(), mycoord)){
            //mark as a hit for the player
            model.add_computer_hit(mycoord);
            System.out.println("hit!");
        } else {
            //mark as a miss for the player
            model.add_computer_miss(mycoord);
            System.out.println("miss!");

        }
    	
    	//Check to see if the game is over now
        
        if(game_over(model))
        	game_complete(model, false);
        
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
     
     private static void game_complete(BattleshipModel model, boolean isPlayer)
     {
    	 ArrayList<Coord> temp = new ArrayList<Coord>();
    	 model.get_player_hits().clear();
		 model.get_computer_hits().clear();
		 model.get_computer_misses().clear();
		 model.get_player_misses().clear();
    	 if(isPlayer)
    	 {
    		 
    		 //Add in the coords for the W and L for the winner and loser
    		 //MAKES THE W
    		 temp.add(new Coord(2,1));temp.add(new Coord(2,2));temp.add(new Coord(3,1));
    		 temp.add(new Coord(3,2));temp.add(new Coord(4,1));temp.add(new Coord(4,2));
    		 temp.add(new Coord(4,3));temp.add(new Coord(5,2));temp.add(new Coord(5,3));
    		 temp.add(new Coord(6,2));temp.add(new Coord(6,3));temp.add(new Coord(6,4));
    		 temp.add(new Coord(7,3));temp.add(new Coord(7,4));temp.add(new Coord(7,5));
    		 temp.add(new Coord(8,4));temp.add(new Coord(8,5));temp.add(new Coord(7,6));
    		 temp.add(new Coord(6,6));temp.add(new Coord(7,7));temp.add(new Coord(8,7));
    		 temp.add(new Coord(8,7));temp.add(new Coord(7,8));temp.add(new Coord(6,8));
    		 temp.add(new Coord(6,9));temp.add(new Coord(5,8));temp.add(new Coord(5,9));
    		 temp.add(new Coord(4,8));temp.add(new Coord(4,9));temp.add(new Coord(4,10));
    		 temp.add(new Coord(3,9));temp.add(new Coord(3,10));temp.add(new Coord(2,9));
    		 temp.add(new Coord(2,10));
    		 
    		 model.setPlayerHits(temp);
    		 
    		 
    		 //MAKES THE L
    		 temp = new ArrayList<Coord>();
    		 
    		 temp.add(new Coord(2,4));temp.add(new Coord(2,5));temp.add(new Coord(3,4));
    		 temp.add(new Coord(3,5));temp.add(new Coord(4,4));temp.add(new Coord(4,5));
    		 temp.add(new Coord(5,4));temp.add(new Coord(5,4));temp.add(new Coord(6,4));
    		 temp.add(new Coord(6,5));temp.add(new Coord(7,4));temp.add(new Coord(7,5));
    		 temp.add(new Coord(8,4));temp.add(new Coord(8,5));temp.add(new Coord(8,6));
    		 temp.add(new Coord(8,7));temp.add(new Coord(8,8));temp.add(new Coord(9,4));
    		 temp.add(new Coord(9,5));temp.add(new Coord(9,6));temp.add(new Coord(9,7));
    		 temp.add(new Coord(9,8));
    		 
    		 model.setComputerHits(temp);
    		 
    	 }
    	 else
    	 {
    		//Add in the coords for the W and L for the winner and loser
    		 //MAKES THE W
    		 temp.add(new Coord(2,1));temp.add(new Coord(2,2));temp.add(new Coord(3,1));
    		 temp.add(new Coord(3,2));temp.add(new Coord(4,1));temp.add(new Coord(4,2));
    		 temp.add(new Coord(4,3));temp.add(new Coord(5,2));temp.add(new Coord(5,3));
    		 temp.add(new Coord(6,2));temp.add(new Coord(6,3));temp.add(new Coord(6,4));
    		 temp.add(new Coord(7,3));temp.add(new Coord(7,4));temp.add(new Coord(7,5));
    		 temp.add(new Coord(8,4));temp.add(new Coord(8,5));temp.add(new Coord(7,6));
    		 temp.add(new Coord(6,6));temp.add(new Coord(7,7));temp.add(new Coord(8,7));
    		 temp.add(new Coord(8,7));temp.add(new Coord(7,8));temp.add(new Coord(6,8));
    		 temp.add(new Coord(6,9));temp.add(new Coord(5,8));temp.add(new Coord(5,9));
    		 temp.add(new Coord(4,8));temp.add(new Coord(4,9));temp.add(new Coord(4,10));
    		 temp.add(new Coord(3,9));temp.add(new Coord(3,10));temp.add(new Coord(2,9));
    		 temp.add(new Coord(2,10));
    		 
    		 model.setComputerHits(temp);
    		 
    		 
    		 //MAKES THE L
    		 temp = new ArrayList<Coord>();
    		 
    		 temp.add(new Coord(2,4));temp.add(new Coord(2,5));temp.add(new Coord(3,4));
    		 temp.add(new Coord(3,5));temp.add(new Coord(4,4));temp.add(new Coord(4,5));
    		 temp.add(new Coord(5,4));temp.add(new Coord(5,4));temp.add(new Coord(6,4));
    		 temp.add(new Coord(6,5));temp.add(new Coord(7,4));temp.add(new Coord(7,5));
    		 temp.add(new Coord(8,4));temp.add(new Coord(8,5));temp.add(new Coord(8,6));
    		 temp.add(new Coord(8,7));temp.add(new Coord(8,8));temp.add(new Coord(9,4));
    		 temp.add(new Coord(9,5));temp.add(new Coord(9,6));temp.add(new Coord(9,7));
    		 temp.add(new Coord(9,8));
    		 
    		 model.setPlayerHits(temp);
    	 }
     }
}
