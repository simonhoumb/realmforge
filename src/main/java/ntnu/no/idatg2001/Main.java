package ntnu.no.idatg2001;

<<<<<<< HEAD
import ntnu.no.idatg2001.entityinformation.Entity;
import ntnu.no.idatg2001.entityinformation.playerclasses.Ranger;
=======
import ntnu.no.idatg2001.playerinformation.Mage;
import ntnu.no.idatg2001.playerinformation.Player;
import ntnu.no.idatg2001.playerinformation.Rogue;
import ntnu.no.idatg2001.playerinformation.Warrior;
>>>>>>> 457b37d (Added Rogue subclass)

public class Main {

  public static void main(String[] args) {
<<<<<<< HEAD
    Entity ranger = new Ranger("Max");
    System.out.println(ranger);
=======
    Player player1 = new Mage("Harry Potter");
    Player player2 = new Warrior("Aragorn");
    Player player3 = new Rogue("Bilbo");
    System.out.println(player1);
    System.out.println(player2);
    System.out.println(player3);
>>>>>>> 457b37d (Added Rogue subclass)
  }
}