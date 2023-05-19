package no.ntnu.idatg2001.frontend.view.GuiElements;

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


/**
 The StoryMapCanvas class represents a canvas for displaying a story map.
 It extends the Canvas class and provides functionality for generating and
 rendering the layout of passages and resolving collisions between them.
 */
public class StoryMapCanvas extends Canvas {


  private final GraphicsContext gc;
  private Story story;
  private final List<PassageRectangle> drawnRectangles;
  private final List<LinkLine> drawnLines;
  private final Set<Passage> processedPassages = new HashSet<>();
  private PassageRectangle selectedRectangle;

  private double offsetX;
  private double offsetY;
  private ScrollPane scrollPane;

  /**
   * Constructor for the StoryMapCanvas class.
   *
   * @param width  the width of the canvas.
   * @param height the height of the canvas.
   */
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
          offsetX = event.getX() - rectangle.getX() + scrollPane.getHvalue()
              * (scrollPane.getContent().getBoundsInLocal().getWidth()
              - scrollPane.getViewportBounds().getWidth());
          offsetY = event.getY() - rectangle.getY() + scrollPane.getVvalue()
              * (scrollPane.getContent().getBoundsInLocal().getHeight()
                  - scrollPane.getViewportBounds().getHeight());

        }
      } else {
        deselectRectangle();
      }
    });

    setOnMouseDragged(event -> {
      if (selectedRectangle != null && event.getButton() == MouseButton.PRIMARY) {
        double newX = event.getX() - offsetX + scrollPane.getHvalue()
            * (scrollPane.getContent().getBoundsInLocal().getWidth()
            - scrollPane.getViewportBounds().getWidth());
        double newY = event.getY() - offsetY + scrollPane.getVvalue()
            * (scrollPane.getContent().getBoundsInLocal().getHeight()
            - scrollPane.getViewportBounds().getHeight());

        // Check if the new position is within the canvas bounds
        newX = Math.max(0, Math.min(newX, getWidth() - selectedRectangle.getWidth()));
        newY = Math.max(0, Math.min(newY, getHeight() - selectedRectangle.getHeight()));

        selectedRectangle.setX(newX);
        selectedRectangle.setY(newY);
        redrawCanvas();
        detectCollisions();
        adjustOutOfBoundsRectangles();

      }
    });
  }

  /**
   * setStory method for the StoryMapCanvas class.
   *
   * @param story the selected story the canvas is going inside.
   * @param scrollPane the selected story the canvas is going inside.
   */
  public void setStory(Story story, ScrollPane scrollPane) {
    this.story = story;
    this.scrollPane = scrollPane;
    generateLayout(); // Generate the layout
    updateScrollPaneViewport();
  }

  /**
   * generateLayout method for the StoryMapCanvas class.
   */
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
    double maxX = drawnRectangles.stream().mapToDouble(rectangle -> rectangle.getX()
        + rectangle.getWidth()).max().orElse(0);
    double maxY = drawnRectangles.stream().mapToDouble(rectangle -> rectangle.getY()
        + rectangle.getHeight()).max().orElse(0);
    double newCanvasWidth = Math.max(maxX + startX, scrollPane.getViewportBounds().getWidth());
    double newCanvasHeight = Math.max(maxY + startY, scrollPane.getViewportBounds().getHeight());

    setWidth(newCanvasWidth);
    setHeight(newCanvasHeight);

    // Update the viewport of the ScrollPane
    scrollPane.setPrefViewportWidth(newCanvasWidth);
    scrollPane.setPrefViewportHeight(newCanvasHeight);

    updateDrawnLines();
    redrawCanvas();
  }

  /**
   * draws the passages the wait you want the generating to be.
   *
   * @param passage the passage that is being drawn.
   * @param x the x position of the passage.
   * @param y the y position of the passage.
   */
  private void generatePassageLayout(Passage passage, double x, double y) {
    if (processedPassages.contains(passage)) {
      return; // Skip if the passage has already been processed
    }

    double width = (passage.getTitle().length() * 10) + 20.0;
    double height = 40;

    PassageRectangle rectangle = new PassageRectangle(passage, x, y, width, height);
    drawnRectangles.add(rectangle);
    processedPassages.add(passage);

    // Calculate the position for the next passage
    double nextX = x;
    double nextY = y + height + 50;

    List<Link> links = passage.getLinks();


    for (Link link : links) {
      Passage linkedPassage = story.getPassage(link);
      if (linkedPassage != null) {
        generatePassageLayout(linkedPassage, nextX, nextY);
        nextX += width + getWidth();
      }
    }
  }


  /**
   * detects if a passage is overlapping another passage.
   */
  private void detectCollisions() {
    for (int i = 0; i < drawnRectangles.size() - 1; i++) {
      PassageRectangle rectangle1 = drawnRectangles.get(i);
      for (int j = i + 1; j < drawnRectangles.size(); j++) {
        PassageRectangle rectangle2 = drawnRectangles.get(j);
        if (passagesOverlap(rectangle1, rectangle2)) {
          adjustPositions(rectangle1, rectangle2);
        }
      }
    }
  }

  /**
   * checks if a passage is outside the bounds of the canvas. and if it is it resizes the canvas.
   */
  private void adjustOutOfBoundsRectangles() {
    double minX = 0;
    double minY = 0;
    double maxX = getWidth();
    double maxY = getHeight();
    boolean needsResize = false;

    for (PassageRectangle rectangle : drawnRectangles) {
      double rectangleX = rectangle.getX();
      double rectangleY = rectangle.getY();
      double rectangleWidth = rectangle.getWidth();
      double rectangleHeight = rectangle.getHeight();

      if (rectangleX < minX || rectangleX + rectangleWidth > maxX) {
        rectangle.setX(Math.max(minX, Math.min(maxX - rectangleWidth, rectangleX)));
        needsResize = true;
      }

      if (rectangleY < minY || rectangleY + rectangleHeight > maxY) {
        rectangle.setY(Math.max(minY, Math.min(maxY - rectangleHeight, rectangleY)));
        needsResize = true;
      }
    }

    if (needsResize) {
      resizeCanvas();
    }
  }



  /**
   * passagesOverlap method for the StoryMapCanvas class.
   *
   * @param rectangle1 the first rectangle.
   * @param rectangle2 the second rectangle.
   * @return true if the passages overlap.
   */
  private boolean passagesOverlap(PassageRectangle rectangle1, PassageRectangle rectangle2) {
    Bounds bounds1 = rectangle1.getBoundsInParent();
    Bounds bounds2 = rectangle2.getBoundsInParent();

    boolean xoverlap = bounds1.getMaxX() >= bounds2.getMinX() && bounds2.getMaxX()
        >= bounds1.getMinX();
    boolean yoverlap = bounds1.getMaxY() >= bounds2.getMinY() && bounds2.getMaxY()
        >= bounds1.getMinY();

    return xoverlap && yoverlap;
  }

  /**
   * adjustPositions method for the StoryMapCanvas class.
   *
   * @param rectangle1 the first rectangle.
   * @param rectangle2 the second rectangle.
   */
  private void adjustPositions(PassageRectangle rectangle1, PassageRectangle rectangle2) {
    // Get the bounds of the rectangles
    Bounds bounds1 = rectangle1.getBoundsInParent();
    Bounds bounds2 = rectangle2.getBoundsInParent();

    // Calculate the desired displacements for both rectangles
    double displacement = 15.0;

    // Calculate the displacement based on the overlap of the edges
    double displacementX = 5.0;
    double displacementY = 5.0;

    if (bounds1.getMaxX() >= bounds2.getMinX() && bounds2.getMaxX() >= bounds1.getMinX()) {
      // Rectangles overlap horizontally
      double overlapX = Math.min(bounds1.getMaxX() - bounds2.getMinX(), bounds2.getMaxX()
          - bounds1.getMinX());
      displacementX = displacement * (overlapX / (bounds1.getWidth() + bounds2.getWidth()));
    }

    if (bounds1.getMaxY() >= bounds2.getMinY() && bounds2.getMaxY() >= bounds1.getMinY()) {
      // Rectangles overlap vertically
      double overlapY = Math.min(bounds1.getMaxY() - bounds2.getMinY(), bounds2.getMaxY()
          - bounds1.getMinY());
      displacementY = displacement * (overlapY / (bounds1.getHeight() + bounds2.getHeight()));
    }

    // Move rectangle1 and rectangle2 based on the calculated displacements
    rectangle1.setX(rectangle1.getX() - displacementX);
    rectangle1.setY(rectangle1.getY() - displacementY);
    rectangle2.setX(rectangle2.getX() + displacementX);
    rectangle2.setY(rectangle2.getY() + displacementY);
  }

  /**
   * Returns the closest point on the edge of the rectangle to the given point.
   */
  private void updateDrawnLines() {
    drawnLines.clear();

    for (PassageRectangle startRectangle : drawnRectangles) {
      Passage startPassage = startRectangle.getPassage();
      for (Link link : startPassage.getLinks()) {
        String referencePassageId = link.getReference();
        PassageRectangle endRectangle = getPassageRectangleByPassage(referencePassageId);
        if (endRectangle != null) {
          boolean lineExists = drawnLines.stream()
              .anyMatch(line -> line.getSourcePassage() == startRectangle
                  && line.getTargetPassage() == endRectangle);

          if (!lineExists) {
            Point2D startPoint = getClosestPoint(startRectangle, endRectangle);
            Point2D endPoint = getClosestPoint(endRectangle, startRectangle);

            LinkLine line = new LinkLine(startPoint.getX(), startPoint.getY(),
                endPoint.getX(), endPoint.getY(), link, startRectangle, endRectangle);

            drawnLines.add(line);
          }
        }
      }
    }
  }

  /**
   * Redraws the canvas.
   */
  private void redrawCanvas() {
    gc.clearRect(0, 0, getWidth(), getHeight());
    drawRectangles();
    drawLines();
  }

  /**
   * Draws a rectangle for each passage.
   */
  private void drawRectangles() {
    for (PassageRectangle rectangle : drawnRectangles) {
      gc.setFill(Color.LIGHTGRAY);
      gc.setStroke(Color.BLACK);
      gc.fillRect(rectangle.getX(), rectangle.getY(),
          rectangle.getWidth(), rectangle.getHeight());
      gc.strokeRect(rectangle.getX(), rectangle.getY(),
          rectangle.getWidth(), rectangle.getHeight());
      gc.setFill(Color.BLACK);
      gc.setTextAlign(TextAlignment.CENTER);
      gc.fillText(rectangle.getPassage().getTitle(), rectangle.getX()
          + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2);
    }
  }

  /**
   * Draws a line between two passages.
   * If the passages intersect, the line will be drawn around the passages.
   */
  private void drawLines() {
    for (LinkLine line : drawnLines) {
      PassageRectangle startRectangle = line.getSourcePassage();
      PassageRectangle endRectangle = line.getTargetPassage();

      Point2D start = getClosestPoint(startRectangle, endRectangle);
      Point2D end = getClosestPoint(endRectangle, startRectangle);

      gc.setStroke(Color.BLACK);

      if (passagesIntersect(startRectangle, endRectangle)) {
        // Passages intersect, find a path around the passages
        List<Point2D> path = findPathAroundPassages(startRectangle, endRectangle);
        drawPath(path);
      } else {
        // Passages do not intersect, draw the line
        gc.strokeLine(start.getX(), start.getY(), end.getX(), end.getY());

        double arrowSize = 10;
        double arrowAngle = Math.toRadians(30);

        double lineAngle = Math.atan2(end.getY() - start.getY(), end.getX() - start.getX());


        double arrowX1 = end.getX() - arrowSize * Math.cos(lineAngle + arrowAngle);
        double arrowY1 = end.getY() - arrowSize * Math.sin(lineAngle + arrowAngle);
        double arrowX2 = end.getX() - arrowSize * Math.cos(lineAngle - arrowAngle);
        double arrowY2 = end.getY() - arrowSize * Math.sin(lineAngle - arrowAngle);

        gc.setFill(Color.BLACK);
        gc.fillPolygon(new double[]{end.getX(), arrowX1, arrowX2},
            new double[]{end.getY(), arrowY1, arrowY2}, 3);
      }
    }
  }

  /**
   * gets the closest unused point on the rectangle.
   *
   * @param rectangle the rectangle.
   * @return the closest unused point on the rectangle.
   */
  private Point2D getClosestUnusedPoint(PassageRectangle rectangle) {
    List<Point2D> candidatePoints = rectangle.getPoints();

    for (Point2D point : candidatePoints) {
      boolean isUsed = drawnLines.stream()
          .anyMatch(line -> line.getSourcePassage() == rectangle
              && line.getStartX() == point.getX() && line.getStartY() == point.getY()
              || line.getTargetPassage() == rectangle
              && line.getEndX() == point.getX() && line.getEndY() == point.getY());

      if (!isUsed) {
        return point.add(rectangle.getX(), rectangle.getY());
      }
    }

    return candidatePoints.get(0).add(rectangle.getX(), rectangle.getY());
  }

  /**
   * Returns the closest point on the rectangle to the given point.
   *
   * @param startRectangle The rectangle
   * @param endRectangle     The point
   * @return The closest point on the rectangle to the given point
   */
  private List<Point2D> findPathAroundPassages(PassageRectangle startRectangle,
      PassageRectangle endRectangle) {
    List<Point2D> path = new ArrayList<>();

    Point2D startPoint = getClosestUnusedPoint(startRectangle);
    Point2D endPoint = getClosestUnusedPoint(endRectangle);

    if (startPoint == null || endPoint == null) {
      return path;
    }
    path.add(startPoint);
    path.add(endPoint);
    return path;
  }

  /**
   * draws a path on the canvas.
   *
   * @param path the path to draw.
   */
  private void drawPath(List<Point2D> path) {
    if (path.size() < 2) {
      return;
    }

    gc.setStroke(Color.BLACK);
    gc.setLineWidth(2.0);

    Point2D start = path.get(0);
    for (int i = 1; i < path.size(); i++) {
      Point2D end = path.get(i);
      gc.strokeLine(start.getX(), start.getY(), end.getX(), end.getY());
      start = end;
    }
  }

  /**
   * Checks for the closest point on the rectangle to the target rectangle.
   *
   * @param rectangle the rectangle.
   * @param targetRectangle the target rectangle.
   * @return the closest point on the rectangle to the target rectangle.
   */
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

  /**
   * Checks if the given passage rectangle intersects with any other passage rectangle.
   *
   * @param rectangle1 the passage rectangle
   * @return true if the given passage rectangle
   *      intersects with any other passage rectangle, false otherwise.
   */
  private boolean passagesIntersect(PassageRectangle rectangle1, PassageRectangle rectangle2) {
    Bounds bounds1 = rectangle1.getBoundsInParent();
    Bounds bounds2 = rectangle2.getBoundsInParent();

    return bounds1.intersects(bounds2);
  }

  /**
   * Gets the passage rectangle at the given position.
   *
   * @param x the x position
   * @param y the y position
   * @return the passage rectangle at the given position
   */
  private PassageRectangle getPassageRectangleAtPosition(double x, double y) {
    for (PassageRectangle rectangle : drawnRectangles) {
      if (rectangle.contains(x, y)) {
        return rectangle;
      }
    }
    return null;
  }

  /**
   * Gets the passage rectangle with the given passage ID.
   *
   * @param passageId the passage ID
   * @return the passage rectangle with the given passage ID
   */
  private PassageRectangle getPassageRectangleByPassage(String passageId) {
    for (PassageRectangle rectangle : drawnRectangles) {
      if (rectangle.getPassage().getTitle().equals(passageId)) {
        return rectangle;
      }
    }
    return null;
  }

  /**
   * Deselects the currently selected rectangle.
   */
  private void deselectRectangle() {
    selectedRectangle = null;
  }

  /**
   * Resizes the canvas to fit the drawn rectangles.
   */
  private void resizeCanvas() {
    double maxX = drawnRectangles.stream()
        .mapToDouble(rectangle -> rectangle.getX() + rectangle.getWidth())
        .max()
        .orElse(0);
    double maxY = drawnRectangles.stream()
        .mapToDouble(rectangle -> rectangle.getY() + rectangle.getHeight())
        .max()
        .orElse(0);
    double newCanvasWidth = Math.max(maxX + 20, scrollPane.getViewportBounds().getWidth());
    double newCanvasHeight = Math.max(maxY + 20, scrollPane.getViewportBounds().getHeight());

    setWidth(newCanvasWidth);
    setHeight(newCanvasHeight);

    // Update the viewport of the ScrollPane
    scrollPane.setPrefViewportWidth(newCanvasWidth);
    scrollPane.setPrefViewportHeight(newCanvasHeight);

    redrawCanvas();
  }

  /**
   * Redraws the canvas.
   */
  private void updateScrollPaneViewport() {
    scrollPane.setViewportBounds(new BoundingBox(0, 0, getWidth(), getHeight()));
  }
}
