package ntnu.no.idatg2001;

import ntnu.no.idatg2001.playerinformation.Mage;
import ntnu.no.idatg2001.playerinformation.PlayerClass;

public class Main {

  public static void main(String[] args) {
    Mage mage = new Mage("Harry Potter", 150, 0, 0,
        PlayerClass.MAGE, 150);
    System.out.println(mage);
  }
}