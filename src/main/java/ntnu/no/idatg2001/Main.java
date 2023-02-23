package ntnu.no.idatg2001;

import ntnu.no.idatg2001.entityinformation.Entity;
import ntnu.no.idatg2001.entityinformation.playerclasses.Ranger;

public class Main {

  public static void main(String[] args) {
    Entity player1 = new Ranger("Legolas");
    System.out.println(player1);
  }
}