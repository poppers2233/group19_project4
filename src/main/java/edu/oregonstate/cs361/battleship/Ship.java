package edu.oregonstate.cs361.battleship;


//This is the ship object
public class Ship {

	private String name;
	private int length;
	private int start[];
	private int end[];
	
	public Ship(String name, int length, int startX, int startY, int endX, int endY) {
		
		this.name = name;
		this.length = length;
		
		start = new int[2];
		end = new int[2];
		
		start[0] = startX;
		start[1] = startY;
		
		end[0] = endX;
		end[1] = endY;
		
	}

}
