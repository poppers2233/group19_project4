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
	private Ship AIaircraftCarrier;
	private Ship AIbattleship;
	private Ship AIcruiser;
	private Ship AIdestroyer;
	private Ship AIsubmarine;
	private ArrayList<Ship> unplacedAIShips;
	private Coord AIShot;
	
	
	public BattleshipModel() {
		AIShot = null;
		//Create the ship objects
		aircraftCarrier = new Ship("AircraftCarrier", 5, 0, 0, 0, 0);
		battleship = new Ship("BattleShip", 4, 0, 0, 0, 0);
		cruiser = new Ship("Cruiser", 3, 0, 0, 0, 0);
		destroyer = new Ship("Destroyer", 2, 0, 0, 0, 0);
		submarine = new Ship("Submarine", 2, 0, 0, 0, 0);
		AIaircraftCarrier = new Ship("AircraftCarrier", 5, 0, 0, 0, 0);
		AIbattleship = new Ship("BattleShip", 4, 0, 0, 0, 0);
		AIcruiser = new Ship("Cruiser", 3, 0, 0, 0, 0);
		AIdestroyer = new Ship("Destroyer", 2, 0, 0, 0, 0);
		AIsubmarine = new Ship("Submarine", 2, 0, 0, 0, 0);
		//Put the AIs into the unplaced ArrayList
		unplacedAIShips.add(AIaircraftCarrier);
		unplacedAIShips.add(AIbattleship);
		unplacedAIShips.add(AIcruiser);
		unplacedAIShips.add(AIdestroyer);
		unplacedAIShips.add(AIsubmarine);
		
	}
	
	
	public Ship getUnplacedShip()//Gets one of the unplaced that the AI has yet to place
	{
		Random rand = new Random(System.currentTimeMillis());
		Ship temp = unplacedAIShips.get(rand.nextInt(unplacedAIShips.size()));
		unplacedAIShips.remove(temp);
		
		return temp;
	}
	

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
		return AIaircraftCarrier;
	}

	public void setAIaircraftCarrier(Ship aIaircraftCarrier) {
		AIaircraftCarrier = aIaircraftCarrier;
	}

	public Ship getAIbattleship() {
		return AIbattleship;
	}

	public void setAIbattleship(Ship aIbattleship) {
		AIbattleship = aIbattleship;
	}

	public Ship getAIcruiser() {
		return AIcruiser;
	}

	public void setAIcruiser(Ship aIcruiser) {
		AIcruiser = aIcruiser;
	}

	public Ship getAIdestroyer() {
		return AIdestroyer;
	}

	public void setAIdestroyer(Ship aIdestroyer) {
		AIdestroyer = aIdestroyer;
	}

	public Ship getAIsubmarine() {
		return AIsubmarine;
	}

	public void setAIsubmarine(Ship aIsubmarine) {
		AIsubmarine = aIsubmarine;
	}


	public Coord getAIShot() {
		return AIShot;
	}


	public void setAIShot(Coord aIShot) {
		AIShot = aIShot;
	}

	

   

}


