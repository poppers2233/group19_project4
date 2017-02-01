package edu.oregonstate.cs361.battleship;

/**
 * Created by michaelhilton on 1/26/17.
 */
public class BattleshipModel {
  String name;
  int length; 
  int startAcross;
  int startDown;
  int endAcross;
  int endDown;
  //format: [startAcross, startDown, endAcross, endDown]
  int[] coordinates;
  
  private static String getName(){
    return name; 
  }
  private static int getLength(){
    return length;
  }
  private static int[] getCoordinates(){
    coordinates = new int[]{startAcross, startDown, endAcross, endDown};
    return coordinates;
  }
    
}
