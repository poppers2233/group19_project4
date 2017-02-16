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
	private boolean scanResult = false;	
	
	public BattleshipModel() {
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


	public Ship getComputer_aircraftCarrier() {
		return computer_aircraftCarrier;
	}


	public void setComputer_aircraftCarrier(Ship computer_aircraftCarrier) {
		this.computer_aircraftCarrier = computer_aircraftCarrier;
	}


	public Ship getComputer_battleship() {
		return computer_battleship;
	}


	public void setComputer_battleship(Ship computer_battleship) {
		this.computer_battleship = computer_battleship;
	}


	public Ship getComputer_cruiser() {
		return computer_cruiser;
	}


	public void setComputer_cruiser(Ship computer_cruiser) {
		this.computer_cruiser = computer_cruiser;
	}


	public Ship getComputer_destroyer() {
		return computer_destroyer;
	}


	public void setComputer_destroyer(Ship computer_destroyer) {
		this.computer_destroyer = computer_destroyer;
	}


	public Ship getComputer_submarine() {
		return computer_submarine;
	}


	public void setComputer_submarine(Ship computer_submarine) {
		this.computer_submarine = computer_submarine;
	}


	public ArrayList<Coord> getComputerHits() {
		return computerHits;
	}


	public void setComputerHits(ArrayList<Coord> computerHits) {
		this.computerHits = computerHits;
	}


	public ArrayList<Coord> getComputerMisses() {
		return computerMisses;
	}


	public void setComputerMisses(ArrayList<Coord> computerMisses) {
		this.computerMisses = computerMisses;
	}


	public ArrayList<Coord> getPlayerHits() {
		return playerHits;
	}


	public void setPlayerHits(ArrayList<Coord> playerHits) {
		this.playerHits = playerHits;
	}


	public ArrayList<Coord> getPlayerMisses() {
		return playerMisses;
	}


	public void setPlayerMisses(ArrayList<Coord> playerMisses) {
		this.playerMisses = playerMisses;
	}



	public void scan(int rowInt, int colInt) 
	{
        	 Coordinate coor = new Coordinate(rowInt,colInt);
		 scanResult = false;
		 if(computer_aircraftCarrier.scan(coor)){
		     scanResult = true;
		 }
		 else if (computer_battleship.scan(coor)){
		     scanResult = true;
		 }else if (computer_cruiser.scan(coor)){
		     scanResult = true;
		 }else if (computer_destroyer.scan(coor)){
		     scanResult = true;
		 }else if (computer_submarine.scan(coor)){
		     scanResult = true;
		 } else {
		     scanResult = false;
		 }
	}
 
	public boolean getScanResult()
	{
		return scanResult;
	}
}


