package no.ntnu.idatg2001.frontend.view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import no.ntnu.idatg2001.backend.gameinformation.Link;
import no.ntnu.idatg2001.backend.gameinformation.Passage;
import no.ntnu.idatg2001.backend.gameinformation.Story;
import no.ntnu.idatg2001.frontend.view.GuiElements.LinkLine;
import no.ntnu.idatg2001.frontend.view.GuiElements.PassageRectangle;

public class StoryMapCanvas extends Canvas {


  private GraphicsContext gc;
  private Story story;
  private List<PassageRectangle> drawnRectangles;
  private List<LinkLine> drawnLines;
  private PassageRectangle selectedRectangle;
  private LinkLine drawingLine;
  private double offsetX;
  private double offsetY;
  private ScrollPane scrollPane;

  public StoryMapCanvas(double width, double height, ScrollPane scrollPane) {
    super(width, height);
    gc = getGraphicsContext2D();
    drawnRectangles = new ArrayList<>();
    drawnLines = new ArrayList<>();
    selectedRectangle = null;
    this.scrollPane = scrollPane;

    // Add event handlers for mouse press and drag events
    setOnMousePressed(event -> {
      PassageRectangle rectangle = getPassageRectangleAtPosition(event.getX(), event.getY());
      if (rectangle != null) {
        if (event.getButton() == MouseButton.PRIMARY) {
          selectedRectangle = rectangle;
          // Calculate the offset relative to the rectangle's position on the canvas
          offsetX = event.getX() - rectangle.getX() + scrollPane.getHvalue() * (scrollPane.getContent().getBoundsInLocal().getWidth() - scrollPane.getViewportBounds().getWidth());
          offsetY = event.getY() - rectangle.getY() + scrollPane.getVvalue() * (scrollPane.getContent().getBoundsInLocal().getHeight() - scrollPane.getViewportBounds().getHeight());

        } else if (drawingLine != null && event.getButton() == MouseButton.SECONDARY) {
          // Handle right-click event
        }
      } else {
        deselectRectangle();
      }
    });

    setOnMouseDragged(event -> {
      if (selectedRectangle != null && event.getButton() == MouseButton.PRIMARY) {
        double newX = event.getX() - offsetX + scrollPane.getHvalue() * (scrollPane.getContent().getBoundsInLocal().getWidth() - scrollPane.getViewportBounds().getWidth());
        double newY = event.getY() - offsetY + scrollPane.getVvalue() * (scrollPane.getContent().getBoundsInLocal().getHeight() - scrollPane.getViewportBounds().getHeight());

        // Check if the new position is within the canvas bounds
        newX = Math.max(0, Math.min(newX, getWidth() - selectedRectangle.getWidth()));
        newY = Math.max(0, Math.min(newY, getHeight() - selectedRectangle.getHeight()));

        selectedRectangle.setX(newX);
        selectedRectangle.setY(newY);
        redrawCanvas();
        resolveCollisions();
      }
    });
  }

  public void setStory(Story story, ScrollPane scrollPane) {
    this.story = story;
    this.scrollPane = scrollPane;
    generateLayout(); // Generate the layout
    updateScrollPaneViewport();
  }

  public void generateLayout() {
    double startX = 20; // Starting X position for the grid
    double startY = 20; // Starting Y position for the grid

    drawnRectangles.clear();
    drawnLines.clear(); // Clear the lines

    Passage openingPassage = story.getOpeningPassage();

    if (openingPassage != null) {
      generatePassageLayout(openingPassage, startX, startY);
    }

    // Calculate the new canvas size
    double maxX = drawnRectangles.stream().mapToDouble(rectangle -> rectangle.getX() + rectangle.getWidth()).max().orElse(0);
    double maxY = drawnRectangles.stream().mapToDouble(rectangle -> rectangle.getY() + rectangle.getHeight()).max().orElse(0);
    double newCanvasWidth = Math.max(maxX + startX, scrollPane.getViewportBounds().getWidth());
    double newCanvasHeight = Math.max(maxY + startY, scrollPane.getViewportBounds().getHeight());

    setWidth(newCanvasWidth);
    setHeight(newCanvasHeight);

    // Update the viewport of the ScrollPane
    scrollPane.setPrefViewportWidth(newCanvasWidth);
    scrollPane.setPrefViewportHeight(newCanvasHeight);

    updateDrawnLines();
    resolveCollisions();
    redrawCanvas();
  }

  private Set<Passage> processedPassages = new HashSet<>();

  private void generatePassageLayout(Passage passage, double x, double y) {
    if (processedPassages.contains(passage)) {
      return; // Skip if the passage has already been processed
    }

    double width = passage.getTitle().length() * 10 + 20;
    double height = 40;

    PassageRectangle rectangle = new PassageRectangle(passage, x, y, width, height);
    drawnRectangles.add(rectangle);
    processedPassages.add(passage);

    // Calculate the position for the next passage
    double nextX = x;
    double nextY = y + height + 50;

    List<Link> links = passage.getLinks();
    int linkCount = links.size();
    double totalWidth = width * linkCount + 50 * (linkCount - 1);

    double startX = x - totalWidth / 2;
    double startY = nextY;

    for (Link link : links) {
      Passage linkedPassage = story.getPassage(link);
      if (linkedPassage != null) {
        generatePassageLayout(linkedPassage, nextX, nextY);
        nextX += width + 50;
      }
    }
  }

  private void resolveCollisions() {
    double minX = 0;
    double minY = 0;
    double maxX = getWidth();
    double maxY = getHeight();
    boolean needsResize = true;

    for (int i = 0; i < drawnRectangles.size(); i++) {
      PassageRectangle rectangle1 = drawnRectangles.get(i);
      for (int j = i + 1; j < drawnRectangles.size(); j++) {
        PassageRectangle rectangle2 = drawnRectangles.get(j);
        if (passagesOverlap(rectangle1, rectangle2)) {
          adjustPositions(rectangle1, rectangle2);
        }
      }
    }
    for (PassageRectangle rectangle1 : drawnRectangles) {
      // Check if the rectangle is going outside the canvas bounds and adjust its position
      double rectangleX = rectangle1.getX();
      double rectangleY = rectangle1.getY();
      double rectangleWidth = rectangle1.getWidth();
      double rectangleHeight = rectangle1.getHeight();

      if (rectangleX < minX) {
        rectangle1.setX(minX);
        needsResize = true;
      } else if (rectangleX + rectangleWidth > maxX) {
        rectangle1.setX(maxX - rectangleWidth);
        needsResize = true;
      }

      if (rectangleY < minY) {
        rectangle1.setY(minY);
        needsResize = true;
      } else if (rectangleY + rectangleHeight > maxY) {
        rectangle1.setY(maxY - rectangleHeight);
        needsResize = true;
      }

      // Check if the rectangle overlaps with any other passages and adjust its position
      for (PassageRectangle rectangle2 : drawnRectangles) {
        if (rectangle2 != rectangle1 && passagesOverlap(rectangle1, rectangle2)) {
          needsResize = true;
          adjustPositions(rectangle1, rectangle2);
        }
      }
    }

    if (needsResize) {
      resizeCanvas();
    }
  }

  private boolean passagesOverlap(PassageRectangle rectangle1, PassageRectangle rectangle2) {
    // Get the bounds of the rectangles
    Bounds bounds1 = rectangle1.getBoundsInParent();
    Bounds bounds2 = rectangle2.getBoundsInParent();

    // Check if the rectangles overlap by comparing their edges
    boolean xOverlap = bounds1.getMaxX() >= bounds2.getMinX() && bounds2.getMaxX() >= bounds1.getMinX();
    boolean yOverlap = bounds1.getMaxY() >= bounds2.getMinY() && bounds2.getMaxY() >= bounds1.getMinY();

    return xOverlap && yOverlap;
  }

  private void adjustPositions(PassageRectangle rectangle1, PassageRectangle rectangle2) {
    // Get the bounds of the rectangles
    Bounds bounds1 = rectangle1.getBoundsInParent();
    Bounds bounds2 = rectangle2.getBoundsInParent();

    // Calculate the desired displacements for both rectangles
    double displacement = 10.0; // Adjust this value to control how much passages should be moved away from each other

    // Calculate the displacement based on the overlap of the edges
    double displacementX = 0.0;
    double displacementY = 0.0;

    if (bounds1.getMaxX() >= bounds2.getMinX() && bounds2.getMaxX() >= bounds1.getMinX()) {
      // Rectangles overlap horizontally
      double overlapX = Math.min(bounds1.getMaxX() - bounds2.getMinX(), bounds2.getMaxX() - bounds1.getMinX());
      displacementX = displacement * (overlapX / (bounds1.getWidth() + bounds2.getWidth()));
    }

    if (bounds1.getMaxY() >= bounds2.getMinY() && bounds2.getMaxY() >= bounds1.getMinY()) {
      // Rectangles overlap vertically
      double overlapY = Math.min(bounds1.getMaxY() - bounds2.getMinY(), bounds2.getMaxY() - bounds1.getMinY());
      displacementY = displacement * (overlapY / (bounds1.getHeight() + bounds2.getHeight()));
    }

    // Move rectangle1 and rectangle2 based on the calculated displacements
    rectangle1.setX(rectangle1.getX() - displacementX);
    rectangle1.setY(rectangle1.getY() - displacementY);
    rectangle2.setX(rectangle2.getX() + displacementX);
    rectangle2.setY(rectangle2.getY() + displacementY);
  }


  private void updateDrawnLines() {
    drawnLines.clear();

    for (PassageRectangle startRectangle : drawnRectangles) {
      Passage startPassage = startRectangle.getPassage();
      for (Link link : startPassage.getLinks()) {
        String referencePassageId = link.getReference();
        PassageRectangle endRectangle = getPassageRectangleByPassage(referencePassageId);
        if (endRectangle != null) {
          boolean lineExists = drawnLines.stream()
              .anyMatch(line -> line.getSourcePassage() == startRectangle && line.getTargetPassage() == endRectangle);

          if (!lineExists) {
            Point2D startPoint = getClosestUnusedPoint(startRectangle);
            Point2D endPoint = getClosestPoint(endRectangle, startRectangle);

            LinkLine line = new LinkLine(startPoint.getX(), startPoint.getY(),
                endPoint.getX(), endPoint.getY(), link, startRectangle, endRectangle);

            drawnLines.add(line);
            System.out.println("Added line: " + line); // Print the line to the command line
          }
        }
      }
    }
  }

  private void clearCanvas() {
    gc.clearRect(0, 0, getWidth(), getHeight());
    drawnRectangles.clear();
    drawnLines.clear();
  }

  private void redrawCanvas() {
    gc.clearRect(0, 0, getWidth(), getHeight());
    drawRectangles();
    drawLines();
  }

  private void drawRectangles() {
    for (PassageRectangle rectangle : drawnRectangles) {
      gc.setFill(Color.LIGHTBLUE);
      gc.fillRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
      gc.setFill(Color.BLACK);
      gc.setTextAlign(TextAlignment.CENTER);
      gc.fillText(rectangle.getPassage().getTitle(), rectangle.getX() + rectangle.getWidth() / 2,
          rectangle.getY() + rectangle.getHeight() / 2);
    }
  }

  private void drawLines() {
    for (LinkLine line : drawnLines) {
      PassageRectangle startRectangle = line.getSourcePassage();
      PassageRectangle endRectangle = line.getTargetPassage();

      Point2D start = getClosestPoint(startRectangle, endRectangle);
      Point2D end = getClosestPoint(endRectangle, startRectangle);

      gc.setStroke(Color.BLACK);
      gc.strokeLine(start.getX(), start.getY(), end.getX(), end.getY());

      double arrowSize = 10;
      double arrowAngle = Math.toRadians(30); // Adjust this value to control the angle of the arrowhead

      // Calculate the angle and length of the line segment for the arrowhead
      double lineAngle = Math.atan2(end.getY() - start.getY(), end.getX() - start.getX());
      double lineLength = Math.sqrt(Math.pow(end.getX() - start.getX(), 2) + Math.pow(end.getY() - start.getY(), 2));

      // Calculate the coordinates of the arrowhead points
      double arrowX1 = end.getX() - arrowSize * Math.cos(lineAngle + arrowAngle);
      double arrowY1 = end.getY() - arrowSize * Math.sin(lineAngle + arrowAngle);
      double arrowX2 = end.getX() - arrowSize * Math.cos(lineAngle - arrowAngle);
      double arrowY2 = end.getY() - arrowSize * Math.sin(lineAngle - arrowAngle);

      gc.setFill(Color.BLACK);
      gc.fillPolygon(new double[]{end.getX(), arrowX1, arrowX2},
          new double[]{end.getY(), arrowY1, arrowY2}, 3);
    }
  }


  private Point2D getClosestUnusedPoint(PassageRectangle rectangle) {
    List<Point2D> candidatePoints = rectangle.getPoints();

    for (Point2D point : candidatePoints) {
      boolean isUsed = drawnLines.stream()
          .anyMatch(line -> line.getSourcePassage() == rectangle && line.getStartX() == point.getX() && line.getStartY() == point.getY()
              || line.getTargetPassage() == rectangle && line.getEndX() == point.getX() && line.getEndY() == point.getY());

      if (!isUsed) {
        return point.add(rectangle.getX(), rectangle.getY());
      }
    }

    return candidatePoints.get(0).add(rectangle.getX(), rectangle.getY());
  }

  private Point2D getClosestPoint(PassageRectangle rectangle, PassageRectangle targetRectangle) {
    double rectX = rectangle.getX();
    double rectY = rectangle.getY();
    double rectWidth = rectangle.getWidth();
    double rectHeight = rectangle.getHeight();

    // Calculate the closest point on the rectangle
    double targetX = targetRectangle.getX();
    double targetY = targetRectangle.getY();
    double targetWidth = targetRectangle.getWidth();
    double targetHeight = targetRectangle.getHeight();

    double closestX = Math.max(rectX, Math.min(targetX + targetWidth, rectX + rectWidth));
    double closestY = Math.max(rectY, Math.min(targetY + targetHeight, rectY + rectHeight));

    return new Point2D(closestX, closestY);
  }

  private void updateDrawnRectangles() {
    drawnRectangles.clear();

    // Create a PassageRectangle for the opening passage
    Passage openingPassage = story.getOpeningPassage();
    double width = openingPassage.getTitle().length() * 10 + 20;
    double height = 40;
    double x = Math.random() * (getWidth() - width);
    double y = Math.random() * (getHeight() - height);
    PassageRectangle openingRectangle = new PassageRectangle(openingPassage, x, y, width, height);
    drawnRectangles.add(openingRectangle);

    // Create PassageRectangles for other passages
    for (Passage passage : story.getPassages().values()) {
      if (passage != openingPassage) {
        width = passage.getTitle().length() * 10 + 20;
        height = 40;
        x = Math.random() * (getWidth() - width);
        y = Math.random() * (getHeight() - height);
        PassageRectangle rectangle = new PassageRectangle(passage, x, y, width, height);
        drawnRectangles.add(rectangle);
      }
    }
  }

  private PassageRectangle getPassageRectangleAtPosition(double x, double y) {
    for (PassageRectangle rectangle : drawnRectangles) {
      if (rectangle.contains(x, y)) {
        return rectangle;
      }
    }
    return null;
  }

  private PassageRectangle getPassageRectangleByPassage(String passageId) {
    for (PassageRectangle rectangle : drawnRectangles) {
      if (rectangle.getPassage().getTitle().equals(passageId)) {
        return rectangle;
      }
    }
    return null;
  }

  private PassageRectangle findPassageRectangle(Passage passage) {
    for (PassageRectangle rectangle : drawnRectangles) {
      if (rectangle.getPassage() == passage) {
        return rectangle;
      }
    }
    return null;
  }

  private LinkLine getLineAtPosition(double x, double y) {
    for (LinkLine line : drawnLines) {
      if (line.contains(x, y)) {
        return line;
      }
    }
    return null;
  }

  private void deselectRectangle() {
    selectedRectangle = null;
    drawingLine = null;
  }

  private void resizeCanvas() {
    double maxX = drawnRectangles.stream().mapToDouble(rectangle -> rectangle.getX() + rectangle.getWidth()).max().orElse(0);
    double maxY = drawnRectangles.stream().mapToDouble(rectangle -> rectangle.getY() + rectangle.getHeight()).max().orElse(0);
    double newCanvasWidth = Math.max(maxX + 20, scrollPane.getViewportBounds().getWidth());
    double newCanvasHeight = Math.max(maxY + 20, scrollPane.getViewportBounds().getHeight());

    setWidth(newCanvasWidth);
    setHeight(newCanvasHeight);

    // Update the viewport of the ScrollPane
    scrollPane.setPrefViewportWidth(newCanvasWidth);
    scrollPane.setPrefViewportHeight(newCanvasHeight);

    redrawCanvas();
  }

  private void updateScrollPaneViewport() {
    scrollPane.setViewportBounds(new BoundingBox(0, 0, getWidth(), getHeight()));
  }
}
