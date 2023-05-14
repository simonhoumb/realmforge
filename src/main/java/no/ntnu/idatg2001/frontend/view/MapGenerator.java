package no.ntnu.idatg2001.frontend.view;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import no.ntnu.idatg2001.backend.gameinformation.*;

public class MapGenerator {
  private Pane mapPane;
  private Stage mapStage;

  public MapGenerator(Pane mapPane) {
    this.mapPane = mapPane;
    this.mapStage = new Stage();
  }

  public void generateMap(Story selectedStory) {
    // Clear the existing content in the pane
    mapPane.getChildren().clear();

    // Create a new canvas to draw the map
    Canvas canvas = new Canvas(mapPane.getWidth(), mapPane.getHeight());
    mapPane.getChildren().add(canvas);

    // Get the graphics context of the canvas
    GraphicsContext gc = canvas.getGraphicsContext2D();

    // Set rectangle dimensions for passages
    double passageWidth = 100;
    double passageHeight = 50;

    // Set line width for links
    double linkWidth = 2.0;

    // Set rectangle fill color for passages
    Color passageColor = Color.BLUE;

    // Set line color for links
    Color linkColor = Color.RED;

    // Draw the map using rectangles for passages and lines for links

    // Iterate over passages in the selected story
    for (Passage passage : selectedStory.getPassages().values()) {
      // Draw rectangles for passages
      Rectangle passageRectangle = new Rectangle(passageWidth, passageHeight);
      passageRectangle.setFill(passageColor);
      mapPane.getChildren().add(passageRectangle);
    }

    // Iterate over links in the selected story
    for (Link link : selectedStory.getPassages().keySet()) {
      // Get the source and target passages for the link
      String sourcePassage = link.getReference();
      String targetPassage = link.getReference();

      // Draw lines for links
      Line linkLine = new Line();
      linkLine.setStroke(linkColor);
      linkLine.setStrokeWidth(linkWidth);
      mapPane.getChildren().add(linkLine);
    }

    // Create a new scene with the map pane
    Scene mapScene = new Scene(mapPane);

    // Set the scene for the map stage
    mapStage.setScene(mapScene);

    // Show the map stage
    mapStage.show();
  }
}


