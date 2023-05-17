package no.ntnu.idatg2001.frontend.view;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
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
  private double offsetX;
  private double offsetY;
  private boolean isRectangleSelected;
  private ScrollPane scrollPane;

  public StoryMapCanvas(double width, double height, ScrollPane scrollPane) {
    super(width, height);
    gc = getGraphicsContext2D();
    drawnRectangles = new ArrayList<>();
    drawnLines = new ArrayList<>();
    isRectangleSelected = false;
    selectedRectangle = null;
    this.scrollPane = scrollPane;

    // Add event handlers for mouse press and drag events
    setOnMousePressed(event -> {
      PassageRectangle rectangle = getPassageRectangleAtPosition(event.getX(), event.getY());
      if (rectangle != null) {
        selectedRectangle = rectangle;
        offsetX = event.getX() - rectangle.getX();
        offsetY = event.getY() - rectangle.getY();
      } else {
        deselectRectangle();
      }
    });

    setOnMouseDragged(event -> {
      if (selectedRectangle != null) {
        double newX = event.getX() - offsetX;
        double newY = event.getY() - offsetY;
        selectedRectangle.setX(newX);
        selectedRectangle.setY(newY);
        redrawCanvas();
        updateScrollPaneViewport();
      }
    });
  }

  public void setStory(Story story, ScrollPane scrollPane) {
    this.story = story;
    this.scrollPane = scrollPane;
    updateDrawnRectangles();
    redrawCanvas();
    updateScrollPaneViewport();
  }

  private void updateDrawnLines() {
    drawnLines.clear();

    for (PassageRectangle sourceRectangle : drawnRectangles) {
      Passage sourcePassage = sourceRectangle.getPassage();

      // Add the opening passage as a target
      Passage openingPassage = story.getOpeningPassage();
      if (openingPassage != null && openingPassage != sourcePassage) {
        PassageRectangle openingRectangle = getPassageRectangleByPassage(openingPassage);
        if (openingRectangle != null) {
          Line line = new Line(
              sourceRectangle.getCenterX(),
              sourceRectangle.getCenterY(),
              openingRectangle.getCenterX(),
              openingRectangle.getCenterY()
          );
          LinkLine linkLine = new LinkLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY(), null);
          drawnLines.add(linkLine);
        }
      }

      for (Link link : sourcePassage.getLinks()) {
        String targetPassageTitle = link.getReference();
        List<Passage> targetPassages = getPassagesByTitle(targetPassageTitle);

        for (Passage targetPassage : targetPassages) {
          PassageRectangle targetRectangle = getPassageRectangleByPassage(targetPassage);
          if (targetRectangle != null) {
            Line line = new Line(
                sourceRectangle.getCenterX(),
                sourceRectangle.getCenterY(),
                targetRectangle.getCenterX(),
                targetRectangle.getCenterY()
            );
            LinkLine linkLine = new LinkLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY(), link);
            drawnLines.add(linkLine);
          }
        }
      }
    }
  }

  private List<Passage> getPassagesByTitle(String title) {
    List<Passage> passages = new ArrayList<>();
    for (Passage passage : story.getPassages().values()) {
      if (passage.getTitle().equals(title)) {
        passages.add(passage);
      }
    }
    return passages;
  }


  private void updateDrawnRectangles() {
    drawnRectangles.clear();
    List<Passage> passages = new ArrayList<>(story.getPassages().values());
    double passageWidth = 150;
    double passageHeight = 80;
    double x = 20;
    double y = 20;

    // Add the opening passage as a drawn rectangle
    Passage openingPassage = story.getOpeningPassage();
    if (openingPassage != null) {
      PassageRectangle openingRectangle = new PassageRectangle(openingPassage, x, y, passageWidth, passageHeight);
      drawnRectangles.add(openingRectangle);
    }

    for (Passage passage : passages) {
      if (passage == openingPassage) {
        // Skip the opening passage as it has already been added
        continue;
      }

      PassageRectangle rectangle = new PassageRectangle(passage, x, y, passageWidth, passageHeight);
      drawnRectangles.add(rectangle);

      x += passageWidth + 20;
      if (x + passageWidth > getWidth()) {
        x = 20;
        y += passageHeight + 20;
      }
    }
  }

  private void redrawCanvas() {
    gc.clearRect(0, 0, getWidth(), getHeight());
    for (PassageRectangle rectangle : drawnRectangles) {
      Passage passage = rectangle.getPassage();

      // Draw the passage rectangle
      gc.setFill(Color.WHITE);
      gc.setStroke(Color.BLACK);

      gc.fillRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
      gc.strokeRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());

      // Draw the passage title
      gc.setFill(Color.BLACK);
      gc.setTextAlign(TextAlignment.LEFT);
      gc.fillText(passage.getTitle(), rectangle.getX() + 5, rectangle.getY() + rectangle.getHeight() / 2);
    }

    updateDrawnLines(); // Update the drawn lines
    for (LinkLine linkLine : drawnLines) {
      gc.setStroke(Color.RED);
      gc.strokeLine(linkLine.getStartX(), linkLine.getStartY(), linkLine.getEndX(), linkLine.getEndY());
    }

    updateScrollPaneViewport();
  }

  private PassageRectangle getPassageRectangleAtPosition(double x, double y) {
    for (PassageRectangle rectangle : drawnRectangles) {
      if (rectangle.contains(x, y)) {
        return rectangle;
      }
    }
    return null;
  }

  private PassageRectangle getPassageRectangleByPassage(Passage passage) {
    for (PassageRectangle rectangle : drawnRectangles) {
      if (rectangle.getPassage() == passage) {
        return rectangle;
      }
    }
    return null;
  }

  private void deselectRectangle() {
    selectedRectangle = null;
  }

  private void updateScrollPaneViewport() {
    // Calculate the bounds of all drawn rectangles
    double minX = Double.MAX_VALUE;
    double minY = Double.MAX_VALUE;
    double maxX = Double.MIN_VALUE;
    double maxY = Double.MIN_VALUE;

    for (PassageRectangle rectangle : drawnRectangles) {
      minX = Math.min(minX, rectangle.getX());
      minY = Math.min(minY, rectangle.getY());
      maxX = Math.max(maxX, rectangle.getX() + rectangle.getWidth());
      maxY = Math.max(maxY, rectangle.getY() + rectangle.getHeight());
    }

    // Adjust the scroll pane viewport
    double scrollX = scrollPane.getHvalue() * (maxX - getWidth());
    double scrollY = scrollPane.getVvalue() * (maxY - getHeight());
    double visibleWidth = getWidth();
    double visibleHeight = getHeight();

    scrollPane.setHvalue(scrollX / Math.max(1, maxX - visibleWidth));
    scrollPane.setVvalue(scrollY / Math.max(1, maxY - visibleHeight));
    scrollPane.setHmax(Math.max(maxX, visibleWidth));
    scrollPane.setVmax(Math.max(maxY, visibleHeight));
  }
}
