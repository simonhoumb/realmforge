package ntnu.no.idatg2001;

import ntnu.no.idatg2001.entityinformation.Entity;
import ntnu.no.idatg2001.entityinformation.playerclasses.Ranger;

public class Main {

  public static void main(String[] args) {
    Entity ranger = new Ranger("Max");
    System.out.println(ranger);
  }
}