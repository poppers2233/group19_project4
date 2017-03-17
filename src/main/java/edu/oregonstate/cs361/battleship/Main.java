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
	public static int max_hits = 15;
	public static int vertical = 1;
	public static int horizontal = 0;

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
		currModel.scan(rowInt,colInt);
		//currModel.shootAtPlayer();
		Gson gson = new Gson();
		return gson.toJson(currModel);
    }


    //This function should return a new model
    static String newModel() {

    	BattleshipModel model = new BattleshipModel();
    	Gson gson = new Gson();
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
        AICol=0;
        AIRow =0;
        String AIOrientation;
        AIOrientation = "throw me an error";
        String difficulty = req.params(":difficulty");
        Coord[] easyPlace = new Coord[5];
        String[] easyOrient = new String[5];
        System.out.println("difficulty set at "+difficulty);

        //intialize coordinate and orientation arrays for ez mode
        //non casual mode is handled within the actual placement
        for(int i=0; i<5; i++){
            if(i<3){
                easyPlace[i] = new Coord((i+1)*2,1);
                easyOrient[i] = "vertical";
            }
            else{
                easyPlace[i] = new Coord(1,i*2);
                easyOrient[i] = "horizontal";
            }
        }
    	
   //------------------------------Parsing and execution of the player's turn
        BattleshipModel model = getModelFromReq(req); //calls above function to create an object from board state
        if(model == null){
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
        if(id.equals("aircraftCarrier")){
            System.out.println("dropping aircraft carrier");
            if(isValidLocation(model, row, col, orientation, 5, true)) {
                model.getAircraftCarrier().set_location(row, col, orientation);
                Ship temp = model.getAIaircraftCarrier();
                if (difficulty.equals("hard")) {
                    System.out.println("hard place");
                    do {
                        AICol = rand.nextInt(boardWidth + 1) +1;
                        AIRow = rand.nextInt(boardHeight + 1) +1;
                        if (rand.nextInt(2) == 0)
                            AIOrientation = "horizontal";
                        else
                            AIOrientation = "vertical";


                    } while (!isValidLocation(model, AIRow, AICol, AIOrientation, temp.get_length(), false));
                }
                if(difficulty.equals("easy")){
                    //coords are now valid (in theory)
                    System.out.println("place battleship easy at"+ easyPlace[0].get_x()+","+easyPlace[0].get_y());
                    AICol = easyPlace[0].get_x();
                    AIRow = easyPlace[0].get_y();
                    AIOrientation = easyOrient[0];

                }
                System.out.println("x: "+AICol + "y: "+AIRow +"ori"+ AIOrientation);
                model.getAIaircraftCarrier().set_location(AIRow, AICol, AIOrientation);//Place a ship into
            }
        }
        else if(id.equals("battleship")){
            if(isValidLocation(model, row, col, orientation, 5, true)) {
                model.getBattleship().set_location(row, col, orientation);
                Ship temp = model.getAIbattleship();
                if (difficulty.equals("hard")) {
                    do {
                        AICol = rand.nextInt(boardWidth + 1) +1;
                        AIRow = rand.nextInt(boardHeight + 1) +1;
                        if (rand.nextInt(2) == 0)
                            AIOrientation = "horizontal";
                        else
                            AIOrientation = "vertical";


                    } while (!isValidLocation(model, AIRow, AICol, AIOrientation, temp.get_length(), false));
                }
                if(difficulty.equals("easy")){
                    //coords are now valid (in theory)
                    AICol = easyPlace[1].get_x();
                    AIRow = easyPlace[1].get_y();
                    AIOrientation = easyOrient[1];

                }

                model.getAIbattleship().set_location(AIRow, AICol, AIOrientation);//Place a ship into
            }
        }
        else if(id.equals("clipper")) {
            if (isValidLocation(model, row, col, orientation, 5, true)) {
                model.getClipper().set_location(row, col, orientation);
                Ship temp = model.getComputer_clipper();
                if (difficulty.equals("hard")) {
                    do {
                        AICol = rand.nextInt(boardWidth + 1)+1;
                        AIRow = rand.nextInt(boardHeight + 1)+1;
                        if (rand.nextInt(2) == 0)
                            AIOrientation = "horizontal";
                        else
                            AIOrientation = "vertical";


                    } while (!isValidLocation(model, AIRow, AICol, AIOrientation, temp.get_length(), false));
                }
                if(difficulty.equals("easy")){
                    //coords are now valid (in theory)
                    AICol = easyPlace[2].get_x();
                    AIRow = easyPlace[2].get_y();
                    AIOrientation = easyOrient[2];

                }
                model.getComputer_clipper().set_location(AIRow, AICol, AIOrientation);//Place a ship into
            }
        }
        else if (id.equals("dinghy")) {
                if (isValidLocation(model, row, col, orientation, 5, true)) {
                    model.getDinghy().set_location(row, col, orientation);
                    Ship temp = model.getComputer_dinghy();
                    if (difficulty.equals("hard")) {
                        do {
                            AICol = rand.nextInt(boardWidth + 1) +1;
                            AIRow = rand.nextInt(boardHeight + 1) +1;
                            if (rand.nextInt(2) == 0)
                                AIOrientation = "horizontal";
                            else
                                AIOrientation = "vertical";


                        } while (!isValidLocation(model, AIRow, AICol, AIOrientation, temp.get_length(), false));
                    }
                    if(difficulty.equals("easy")){
                        //coords are now valid (in theory)
                        AICol = easyPlace[3].get_x();
                        AIRow = easyPlace[3].get_y();
                        AIOrientation = easyOrient[3];

                    }
                    model.getComputer_dinghy().set_location(AIRow, AICol, AIOrientation);//Place a ship into
                }
            }
            else if (id.equals("submarine")) {
                if (isValidLocation(model, row, col, orientation, 5, true)) {
                    model.getSubmarine().set_location(row, col, orientation);
                    Ship temp = model.getAIsubmarine();
                    if (difficulty.equals("hard")) {
                        do {
                            AICol = rand.nextInt(boardWidth + 1) +1;
                            AIRow = rand.nextInt(boardHeight + 1) +1;
                            if (rand.nextInt(2) == 0)
                                AIOrientation = "horizontal";
                            else
                                AIOrientation = "vertical";


                        } while (!isValidLocation(model, AIRow, AICol, AIOrientation, temp.get_length(), false));
                    }
                    if(difficulty.equals("easy")){
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
        if(isPlayer)
        {
            // aircraft carrier check
            if(     (row == model.getAircraftCarrier().get_start().get_x() ) &&
                    (col == model.getAircraftCarrier().get_start().get_y() )   ) {
                return false;
            }
            else{

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
            if(     (row == model.getBattleship().get_start().get_x() ) &&
                    (col == model.getBattleship().get_start().get_y() )   ) {
                return false;
            }
            else {
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
            // dinghy check
            if(     (row == model.getDinghy().get_start().get_x() ) &&
                    (col == model.getDinghy().get_start().get_y() )   ) {

                return false;
            }
            else{
            	Ship s = model.getDinghy();
            	
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
            // clipper check
            if(     (row == model.getClipper().get_start().get_x() ) &&
                    (col == model.getClipper().get_start().get_y() )   ) {
                return false;
            }
            else{
            	Ship s = model.getClipper();
            	
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
            if(     (row == model.getSubmarine().get_start().get_x() ) &&
                    (col == model.getSubmarine().get_start().get_y() )   ) {
                return false;
            }
            else{
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
            if(     (row == model.getAIaircraftCarrier().get_start().get_x() ) &&
                    (col == model.getAIaircraftCarrier().get_start().get_y() )   ) {
                return false;
            }
            else{
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
            if(     (row == model.getAIbattleship().get_start().get_x() ) &&
                    (col == model.getAIbattleship().get_start().get_y() )   ) {
                return false;
            }
            else{
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
            // dinghy check
            if(     (row == model.getComputer_dinghy().get_start().get_x() ) &&
                    (col == model.getComputer_dinghy().get_start().get_y() )   ) {
                return false;
            }
            else{
            	Ship s = model.getComputer_dinghy();
            	
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
            // clipper check
            if(     (row == model.getComputer_clipper().get_start().get_x() ) &&
                    (col == model.getComputer_clipper().get_start().get_y() )   ) {
                return false;
            }
            else{
            	Ship s = model.getComputer_clipper();
            	
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
            if(     (row == model.getAIsubmarine().get_start().get_x() ) &&
                    (col == model.getAIsubmarine().get_start().get_y() )   ) {
                return false;
            }
            else{
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

    public static boolean checkValidShot(BattleshipModel model, Coord coord)//Checks to see if a shot being done by the AI has already been done
    {
    	//Check to see if it is off the map
    	if(coord.get_x() < 1 || coord.get_x() > boardWidth + 1 || coord.get_y() < 1 || coord.get_y() > boardHeight + 1)
    	{
    		return false;
    	}
    	//Check to see if thats been fired before
    	for (int i = 0; i < model.get_player_hits().size(); i++) {
			if(coord.get_x() == model.get_player_hits().get(i).get_x())//check if they have matching X coords
			{
				if(coord.get_y() == model.get_player_hits().get(i).get_y())//check if they have matching Y coords as well
				{
					return false;
				}
			}	
		}
    	
    	for (int i = 0; i < model.get_player_misses().size(); i++) {
			if(coord.get_x() == model.get_player_misses().get(i).get_x())//check if they have matching X coords
			{
				if(coord.get_y() == model.get_player_misses().get(i).get_y())//check if they have matching Y coords as well
				{
					return false;
				}
			}	
		}
    	
    	return true;
    }

    //Used to make sure the player does not fire at the same spot more than once.
    public static boolean checkPlayerShot(BattleshipModel model, Coord coord)//Checks to see if a shot being done by the AI has already been done
    {
        //Check to see if thats been fired before
        for (int i = 0; i < model.get_computer_hits().size(); i++) {
            if(coord.get_x() == model.get_computer_hits().get(i).get_x())//check if they have matching X coords
            {
                if(coord.get_y() == model.get_computer_hits().get(i).get_y())//check if they have matching Y coords as well
                {
                    //System.out.println("False");
                    return false;
                }
            }
        }

        for (int i = 0; i < model.get_computer_misses().size(); i++) {
            if(coord.get_x() == model.get_computer_misses().get(i).get_x())//check if they have matching X coords
            {
                if(coord.get_y() == model.get_computer_misses().get(i).get_y())//check if they have matching Y coords as well
                {
                    //System.out.println("False");
                    return false;
                }
            }
        }
        //System.out.println("checkplayershot true");
        return true;
    }
    
    //Similar to placeShip, but with firing.
    private static String prepFire(Request req) {

    	
    	
    	//------------------------------Parsing and execution of the player's turn


        //System.out.println("prepFire called.");
        BattleshipModel model = getModelFromReq(req);
        if(model == null){
            model = new BattleshipModel();
        }
        Gson gson = new Gson();
        int[] pos = new int[]{0, 0};                                //to be passed to the position helper function
        pos[0] = Integer.parseInt(req.params(":col"));
        pos[1] = Integer.parseInt(req.params(":row"));
        Coord shot = new Coord(pos[0], pos[1]);

        //>>>>>This if statement makes sure that the user has not already fired at the location. if they have it will prevent
        // the AI from fireing that turn and NOT add to the users hits/misses to prevent that array from having
        // multiple of the same hit/ miss
        model = fireAt(model, shot);
    	//Check to see if the game is over now

        game_over(model);
        
    	 return gson.toJson(model);
    
    }

    public static BattleshipModel fireAt(BattleshipModel model, Coord shot){


        if(checkPlayerShot(model, shot)) {
            playerShot(model, shot);

            //------------------------------Execution of the AI's turn

            AIFire(model);
        }
        return model;
    }

    private static void playerShot(BattleshipModel model, Coord shot) {
        //if we register any hits
        if (posHelper(model.getAIaircraftCarrier(), shot) || posHelper(model.getAIbattleship(), shot) || posHelper(model.getAIsubmarine(), shot)) {
            //mark as a hit for the player
            model.add_computer_hit(shot);
        }
        else if(posHelper(model.getComputer_clipper(), shot)){
            model.getComputer_clipper().hit(model, true);
        }
        else if(posHelper(model.getComputer_dinghy(), shot)){
            model.getComputer_dinghy().hit(model, true);
        }
        else{
            //mark as a miss for the player
            model.add_computer_miss(shot);

        }
    }

    private static void AIFire(BattleshipModel model) {

        Coord mycoord;

        //Get the coordinate
        if(model.isHard())
        //If it is hard mode
        mycoord = AIHardFire(model);
        else
        //If set to easy mode
        mycoord = AIEasyFire(model);

        //check to see if the shot hits or misses

        //if we register any hits
        if (posHelper(model.getAircraftCarrier(), mycoord) || posHelper(model.getBattleship(), mycoord) || posHelper(model.getSubmarine(), mycoord)) {
            //mark as a hit for the computer
            model.add_player_hit(mycoord);
            model.setAIShot(mycoord);
        }
        else if( posHelper(model.getClipper(), mycoord)){
            model.getClipper().hit(model, false);
            model.setAIShot(mycoord);
        }
        else if( posHelper(model.getDinghy(), mycoord )){
            model.getDinghy().hit(model, false);
            model.setAIShot(mycoord);
        }
        else {
            //mark as a miss for the computer
            model.add_player_miss(mycoord);
            model.setAIShot(null);

        }
    }

    private static Coord AIHardFire(BattleshipModel model) {
        System.out.println("In the mix");
        Random rand = new Random(System.currentTimeMillis());
        Coord mycoord;
        Coord AIShot = model.getAIShot();

        //If it was a hit
        //Shot the areas that are around the preious shot because that is likely to be another hit
        if(AIShot != null)
        {
            System.out.println("It was not null");
            //Checks to see if any surrounding areas are available to be shot at
            for(int i = 0; i < 4; i++)
            {
                switch(i) {
                    case 0:
                        mycoord = new Coord(AIShot.get_x() + 1, AIShot.get_y());
                        break;
                    case 1:
                        mycoord = new Coord(AIShot.get_x() - 1, AIShot.get_y());
                        break;

                    case 2:
                        mycoord = new Coord(AIShot.get_x(), AIShot.get_y()+1);
                        break;

                    case 3:
                        mycoord = new Coord(AIShot.get_x(), AIShot.get_y()-1);
                        break;
                    default:
                        mycoord = new Coord(-1,-1);
                        break;
                }
                if (checkValidShot(model, mycoord))
                    return mycoord;
                System.out.println("Didn't find");
            }

        }
        System.out.println("Was null");
        //If the previious shot was not a hit, shoot randomly
        do {
            mycoord = new Coord(rand.nextInt(boardHeight+1), rand.nextInt(boardWidth+1));

        } while (!checkValidShot(model, mycoord));//while the shot has already been done



        return mycoord;
    }

    private static Coord AIEasyFire(BattleshipModel model) {
        //Just a random shot for easy mode
        Random rand = new Random(System.currentTimeMillis());
        Coord mycoord;
        //Must be changed, some pattern for firing must be created
        do {
            mycoord = new Coord(rand.nextInt(boardHeight+1), rand.nextInt(boardWidth+1));

        } while (!checkValidShot(model, mycoord));//while the shot has already been done

       return mycoord;
    }

    public static boolean posHelper(Ship model, Coord pos){
        Coord start = model.get_start();
        Coord end = model.get_end();

        if(pos.get_x() >= start.get_x() && pos.get_x() <= end.get_x()) {           //if the x of the shot is within x bounds of ship
            if (pos.get_y() >= start.get_y() && pos.get_y() <= end.get_y()) {      //if the y of the shot is within y bounds of ship
                //System.out.println("True");
                return true;
            }
        }
        //System.out.println("False");
        return false;
    }
     public static void game_over(BattleshipModel model){

        if(model.get_player_hits().size() == max_hits)
        {
            game_complete(model, true);
        }

        if(model.get_computer_hits().size() == max_hits)
        {
            game_complete(model, false);
        }
     }
     
     public static void game_complete(BattleshipModel model, boolean isPlayer)
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
    		 temp.add(new Coord(5,4));temp.add(new Coord(5,5));temp.add(new Coord(6,4));
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
    		 temp.add(new Coord(5,4));temp.add(new Coord(5,5));temp.add(new Coord(6,4));
    		 temp.add(new Coord(6,5));temp.add(new Coord(7,4));temp.add(new Coord(7,5));
    		 temp.add(new Coord(8,4));temp.add(new Coord(8,5));temp.add(new Coord(8,6));
    		 temp.add(new Coord(8,7));temp.add(new Coord(8,8));temp.add(new Coord(9,4));
    		 temp.add(new Coord(9,5));temp.add(new Coord(9,6));temp.add(new Coord(9,7));
    		 temp.add(new Coord(9,8));
    		 
    		 model.setPlayerHits(temp);
    	 }
     }
}
