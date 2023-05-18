package no.ntnu.idatg2001.frontend.view.GuiElements;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import no.ntnu.idatg2001.backend.gameinformation.Passage;

public class PassageRectangle extends Rectangle {

  private double x;
  private double y;
  private double width;
  private double height;
  private Passage passage;
  private Point2D velocity;
  private Point2D force;
  private Point2D position;

  public PassageRectangle(Passage passage, double x, double y, double width, double height) {
    super(x, y, width, height);
    this.passage = passage;
    this.velocity = new Point2D(0, 0);
    this.force = new Point2D(0, 0);
    this.position = new Point2D(x, y);
  }

  public Passage getPassage() {
    return passage;
  }

  public Rectangle getRectangle() {
    return this;
  }

  public void updateVelocity(double damping) {
    velocity = velocity.multiply(damping);
  }

  public void updatePosition() {
    x += velocity.getX();
    y += velocity.getY();
  }

  public double getCenterX() {
    return getX() + getWidth() / 2;
  }

  public double getCenterY() {
    return getY() + getHeight() / 2;
  }

  public Point2D getVelocity() {
    return velocity;
  }

  public Point2D getForce() {
    return force;
  }

  public PassageRectangle setVelocity(Point2D velocity) {
    this.velocity = velocity;
    return this;
  }

  public Point2D getPosition() {
    return position;
  }

  public PassageRectangle setPosition(Point2D position) {
    setX(position.getX());
    setY(position.getY());
    return this;
  }


  public void resetForce() {
    force = Point2D.ZERO;
  }

  public double getClosestX(PassageRectangle targetRectangle) {
    double sourceX = getCenterX();
    double targetX = targetRectangle.getCenterX();

    if (sourceX < targetX) {
      return getX() + getWidth();
    } else if (sourceX > targetX) {
      return getX();
    } else {
      double leftDistance = Math.abs(getX() - targetRectangle.getX());
      double rightDistance = Math.abs(
          (getX() + getWidth()) - (targetRectangle.getX() + targetRectangle.getWidth()));

      if (leftDistance < rightDistance) {
        return getX();
      } else {
        return getX() + getWidth();
      }
    }
  }


  public double getClosestY(PassageRectangle targetRectangle) {
    double sourceY = getCenterY();
    double targetY = targetRectangle.getCenterY();

    if (sourceY < targetY) {
      return getY() + getHeight();
    } else if (sourceY > targetY) {
      return getY();
    } else {
      double topDistance = Math.abs(getY() - targetRectangle.getY());
      double bottomDistance = Math.abs(
          (getY() + getHeight()) - (targetRectangle.getY() + targetRectangle.getHeight()));

      if (topDistance < bottomDistance) {
        return getY();
      } else {
        return getY() + getHeight();
      }
    }
  }


  public double getSideX(PassageRectangle targetRectangle) {
    double sourceX = getCenterX();
    double targetX = targetRectangle.getCenterX();

    if (sourceX < targetX) {
      return getX() + getWidth();
    } else if (sourceX > targetX) {
      return getX();
    } else {
      return sourceX;
    }
  }

  public double getSideY(PassageRectangle targetRectangle) {
    double sourceY = getCenterY();
    double targetY = targetRectangle.getCenterY();

    if (sourceY < targetY) {
      return getY() + getHeight();
    } else if (sourceY > targetY) {
      return getY();
    } else {
      return sourceY;
    }
  }

  public boolean isColliding(PassageRectangle targetRectangle) {
    Bounds sourceBounds = getBoundsInParent();
    Bounds targetBounds = targetRectangle.getBoundsInParent();

    return sourceBounds.intersects(targetBounds);
  }

  public void addForce(Point2D additionalForce) {
    force = force.add(additionalForce);
  }

  public static PassageRectangle getPassageRectangleByPassage(Passage passage,
      List<PassageRectangle> rectangles) {
    for (PassageRectangle rectangle : rectangles) {
      if (rectangle.getPassage().equals(passage)) {
        return rectangle;
      }
    }
    return null;
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
