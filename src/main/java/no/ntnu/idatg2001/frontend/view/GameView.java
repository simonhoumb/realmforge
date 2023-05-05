package no.ntnu.idatg2001.frontend.view;

import java.util.ResourceBundle;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import no.ntnu.idatg2001.backend.SettingsModel;
import no.ntnu.idatg2001.backend.gameinformation.Passage;
import no.ntnu.idatg2001.frontend.controller.GameController;

public class GameView extends BorderPane {
  private TextFlow gameTextFlow;
  private VBox gameVBox;
  private ResourceBundle resourceBundle;
  private GameController controller;

  public GameView() {
    createLayout();
    addToGameTextFlow(new Passage("testPassage", "This is a test passage"));
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
    String passageString = String.format("%s: %s",
        currentPassage.getTitle(), currentPassage.getContent());
    gameTextFlow.getChildren().add(new Text(passageString));
  }

  public void clearGameTextFlow() {
    gameTextFlow.getChildren().removeAll();
  }

  public void setController(GameController gameController) {
    this.controller = gameController;
  }
}
