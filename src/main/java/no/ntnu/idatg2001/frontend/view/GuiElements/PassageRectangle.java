package no.ntnu.idatg2001.frontend.view.GuiElements;

import javafx.scene.shape.Rectangle;
import no.ntnu.idatg2001.backend.gameinformation.Passage;

public class PassageRectangle extends Rectangle {
  private Passage passage;

  public PassageRectangle(Passage passage, double x, double y, double width, double height) {
    super(x, y, width, height);
    this.passage = passage;
  }

  public Passage getPassage() {
    return passage;
  }

  public double getCenterX() {
    return getX() + getWidth() / 2;
  }

  public double getCenterY() {
    return getY() + getHeight() / 2;
  }
}