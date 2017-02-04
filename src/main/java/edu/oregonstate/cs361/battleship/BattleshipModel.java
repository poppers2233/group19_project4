package edu.oregonstate.cs361.battleship;

import java.util.ArrayList;
import java.util.Random;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


/**
 * This class is the model that contains all of the board information
 */
public class BattleshipModel {


	private Ship aircraftCarrier;
	private Ship battleship;
	private Ship cruiser;
	private Ship destroyer;
	private Ship submarine;
	private Ship computer_aircraftCarrier;
	private Ship computer_battleship;
	private Ship computer_cruiser;
	private Ship computer_destroyer;
	private Ship computer_submarine;
	private ArrayList<Coord> computerHits = new ArrayList<Coord>();
	private ArrayList<Coord> computerMisses = new ArrayList<Coord>();
	private ArrayList<Coord> playerHits = new ArrayList<Coord>();
	private ArrayList<Coord> playerMisses = new ArrayList<Coord>();
	private ArrayList<Ship> unplacedAIShips;
	//private Coord AIShot;
	
	
	public BattleshipModel() {
		//AIShot = null;
		//Create the ship objects
		aircraftCarrier = new Ship("AircraftCarrier", 5, 0, 0, 0, 0);
		battleship = new Ship("BattleShip", 4, 0, 0, 0, 0);
		cruiser = new Ship("Cruiser", 3, 0, 0, 0, 0);
		destroyer = new Ship("Destroyer", 2, 0, 0, 0, 0);
		submarine = new Ship("Submarine", 2, 0, 0, 0, 0);
		computer_aircraftCarrier = new Ship("AircraftCarrier", 5, 0, 0, 0, 0);
		computer_battleship = new Ship("BattleShip", 4, 0, 0, 0, 0);
		computer_cruiser = new Ship("Cruiser", 3, 0, 0, 0, 0);
		computer_destroyer = new Ship("Destroyer", 2, 0, 0, 0, 0);
		computer_submarine = new Ship("Submarine", 2, 0, 0, 0, 0);

		//Put the AIs into the unplaced ArrayList
		/*unplacedAIShips.add(computer_aircraftCarrier);
		unplacedAIShips.add(computer_battleship);
		unplacedAIShips.add(computer_cruiser);
		unplacedAIShips.add(computer_destroyer);
		unplacedAIShips.add(computer_submarine);*/
		
	}
	
	
	/*public Ship getUnplacedShip()//Gets one of the unplaced that the AI has yet to place
	{
		Random rand = new Random(System.currentTimeMillis());
		Ship temp = unplacedAIShips.get(rand.nextInt(unplacedAIShips.size()));
		unplacedAIShips.remove(temp);
		
		return temp;
	}*/
	

	public Ship getaircraftCarrier() {
		return aircraftCarrier;
	}

	public void aircraftCarrier(Ship aircraftCarrier) {
		this.aircraftCarrier = aircraftCarrier;
	}
	
	public Ship getAircraftCarrier() {
		return aircraftCarrier;
	}

	public void setAircraftCarrier(Ship aircraftCarrier) {
		this.aircraftCarrier = aircraftCarrier;
	}

	public Ship getBattleship() {
		return battleship;
	}

	public void setBattleship(Ship battleship) {
		this.battleship = battleship;
	}

	public Ship getCruiser() {
		return cruiser;
	}

	public void setCruiser(Ship cruiser) {
		this.cruiser = cruiser;
	}

	public Ship getDestroyer() {
		return destroyer;
	}

	public void setDestroyer(Ship destroyer) {
		this.destroyer = destroyer;
	}

	public Ship getSubmarine() {
		return submarine;
	}

	public void setSubmarine(Ship submarine) {
		this.submarine = submarine;
	}

	public Ship getAIaircraftCarrier() {
		return computer_aircraftCarrier;
	}

	public void setAIaircraftCarrier(Ship aIaircraftCarrier) {
		computer_aircraftCarrier = aIaircraftCarrier;
	}

	public Ship getAIbattleship() {
		return computer_battleship;
	}

	public void setAIbattleship(Ship aIbattleship) {
		computer_battleship = aIbattleship;
	}

	public Ship getAIcruiser() {
		return computer_cruiser;
	}

	public void setAIcruiser(Ship aIcruiser) {
		computer_cruiser = aIcruiser;
	}

	public Ship getAIdestroyer() {
		return computer_destroyer;
	}

	public void setAIdestroyer(Ship aIdestroyer) {
		computer_destroyer = aIdestroyer;
	}

	public Ship getAIsubmarine() {
		return computer_submarine;
	}

	public void setAIsubmarine(Ship aIsubmarine) {
		computer_submarine = aIsubmarine;
	}

	public void add_player_hit(Coord new_hit){
		playerHits.add(new_hit);
	}
	public void add_player_miss(Coord new_miss){
		playerMisses.add(new_miss);
	}
	public void add_computer_miss(Coord new_miss){
		computerMisses.add(new_miss);
	}
	public void add_computer_hit(Coord new_hit){
		computerHits.add(new_hit);
	}

	public ArrayList<Coord> get_player_hits(){ return playerHits; }

	public ArrayList<Coord> get_player_misses() {return playerMisses; }

	public ArrayList<Coord> get_computer_hits(){ return computerHits; }

	public ArrayList<Coord> get_computer_misses(){ return computerMisses; }
	/*public Coord getAIShot() {
		return AIShot;
	}


	public void setAIShot(Coord aIShot) {
		AIShot = aIShot;
	}*/

	

   

}


