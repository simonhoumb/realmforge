package no.ntnu.idatg2001.frontend.view;

import java.util.ResourceBundle;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import no.ntnu.idatg2001.backend.SettingsModel;
import no.ntnu.idatg2001.backend.gameinformation.Link;
import no.ntnu.idatg2001.backend.gameinformation.Passage;
import no.ntnu.idatg2001.backend.gameinformation.Story;
import no.ntnu.idatg2001.frontend.controller.GameController;

public class GameView extends BorderPane {
  private TextFlow gameTextFlow;
  private VBox gameVBox;
  private ResourceBundle resourceBundle;
  private GameController controller;
  private Story story;

  public GameView() {
    //TODO: slett denne test story og passage
    Passage testPassage1 = new Passage("OpeningPassage", "This is a test passage and the opening passage");
    Passage testPassage2 = new Passage("Dungeon", "This is the dungeon");
    testPassage1.addLink(new Link("Go to dungeon", "Dungeon"));
    this.story = new Story("myStory", testPassage1);
    this.story.addPassage(testPassage2);
    createLayout();
    addToGameTextFlow(story.getOpeningPassage());
  }

  private void createLayout() {
    resourceBundle = ResourceBundle.getBundle("languages/exitDialog", SettingsModel.getInstance().getLocale());
    gameVBox = new VBox();
    gameVBox.setAlignment(Pos.CENTER);
    gameTextFlow = new TextFlow();
    gameTextFlow.setMinSize(300,300);
    gameTextFlow.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DOTTED,
        CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    gameVBox.getChildren().add(gameTextFlow);
    this.setCenter(gameVBox);
  }

  public void addToGameTextFlow(Passage currentPassage) {
    String passageString = String.format("%s: %s%n",
        currentPassage.getTitle(), currentPassage.getContent());
    gameTextFlow.getChildren().add(new Text(passageString));
    gameTextFlow.getChildren().add(new Text("Your Choices Are: \n"));
    for (Link link : currentPassage.getLinks()) {
      Hyperlink hyperLink = new Hyperlink(String.format("%s ",link.getText()));
      hyperLink.setOnAction(event -> {
        clearGameTextFlow();
        addToGameTextFlow(story.getPassage(link));
      });
      gameTextFlow.getChildren().add(hyperLink);
    }
  }

  public void clearGameTextFlow() {
    gameTextFlow.getChildren().clear();
  }

  public void setController(GameController gameController) {
    this.controller = gameController;
  }
}
