package edu.oregonstate.cs361.battleship;

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
	
	public BattleshipModel() {
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

	

   

}


