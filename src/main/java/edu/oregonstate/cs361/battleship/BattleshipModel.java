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
	private Ship computeraircraftCarrier;
	private Ship computerbattleship;
	private Ship computercruiser;
	private Ship computerdestroyer;
	private Ship computersubmarine;
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
		computeraircraftCarrier = new Ship("AircraftCarrier", 5, 0, 0, 0, 0);
		computerbattleship = new Ship("BattleShip", 4, 0, 0, 0, 0);
		computercruiser = new Ship("Cruiser", 3, 0, 0, 0, 0);
		computerdestroyer = new Ship("Destroyer", 2, 0, 0, 0, 0);
		computersubmarine = new Ship("Submarine", 2, 0, 0, 0, 0);
		//Put the AIs into the unplaced ArrayList
		unplacedAIShips.add(computeraircraftCarrier);
		unplacedAIShips.add(computerbattleship);
		unplacedAIShips.add(computercruiser);
		unplacedAIShips.add(computerdestroyer);
		unplacedAIShips.add(computersubmarine);
		
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
		return computeraircraftCarrier;
	}

	public void setAIaircraftCarrier(Ship aIaircraftCarrier) {
		computeraircraftCarrier = aIaircraftCarrier;
	}

	public Ship getAIbattleship() {
		return computerbattleship;
	}

	public void setAIbattleship(Ship aIbattleship) {
		computerbattleship = aIbattleship;
	}

	public Ship getAIcruiser() {
		return computercruiser;
	}

	public void setAIcruiser(Ship aIcruiser) {
		computercruiser = aIcruiser;
	}

	public Ship getAIdestroyer() {
		return computerdestroyer;
	}

	public void setAIdestroyer(Ship aIdestroyer) {
		computerdestroyer = aIdestroyer;
	}

	public Ship getAIsubmarine() {
		return computersubmarine;
	}

	public void setAIsubmarine(Ship aIsubmarine) {
		computersubmarine = aIsubmarine;
	}


	public Coord getAIShot() {
		return AIShot;
	}


	public void setAIShot(Coord aIShot) {
		AIShot = aIShot;
	}

	

   

}


