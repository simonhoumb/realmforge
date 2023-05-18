package no.ntnu.idatg2001.frontend.view.GuiElements;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import no.ntnu.idatg2001.backend.gameinformation.Passage;

public class PassageRectangle extends Rectangle {

  private double x;
  private double y;
  private double width;
  private double height;
  private Passage passage;

  public PassageRectangle(Passage passage, double x, double y, double width, double height) {
    super(x, y, width, height);
    this.passage = passage;
  }

  public Passage getPassage() {
    return passage;
  }

  public List<Point2D> getPoints() {
    // Return the list of points representing the rectangle
    List<Point2D> points = new ArrayList<>();
    // Add the four corner points of the rectangle
    points.add(new Point2D(x, y));                           // Top-left point
    points.add(new Point2D(x + width, y));                   // Top-right point
    points.add(new Point2D(x + width, y + height));          // Bottom-right point
    points.add(new Point2D(x, y + height));                  // Bottom-left point
    return points;
  }
}
