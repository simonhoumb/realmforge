package no.ntnu.idatg2001.frontend.view;

import java.util.ResourceBundle;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.KeyEvent;
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
import no.ntnu.idatg2001.GameSave;
import no.ntnu.idatg2001.backend.SettingsModel;
import no.ntnu.idatg2001.backend.gameinformation.Link;
import no.ntnu.idatg2001.backend.gameinformation.Passage;
import no.ntnu.idatg2001.frontend.controller.GameController;

public class GameView extends BorderPane {
  private TextFlow gameTextFlow;
  private VBox gameVBox;
  private ResourceBundle resourceBundle;
  private GameController controller;
  private Text choiceText;
  private GameSave currentGameSave;
  private Passage currentPassage;

  public GameView(GameSave currentGameSave) {
    createLayout();
    loadGameSave(currentGameSave);
  }

  private void createLayout() {
    resourceBundle = ResourceBundle
        .getBundle("languages/GameView", SettingsModel.getInstance().getLocale());
    gameVBox = new VBox();
    gameVBox.setAlignment(Pos.CENTER);
    gameTextFlow = new TextFlow();
    gameTextFlow.setFocusTraversable(true);
    gameTextFlow.setMinSize(300,300);
    gameTextFlow.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DOTTED,
        CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    gameVBox.getChildren().add(gameTextFlow);
    this.setCenter(gameVBox);
    this.addEventFilter(KeyEvent.KEY_PRESSED, event -> controller.onEscapeButtonPressed(event));
  }

  public void addToGameTextFlow(Passage currentPassage) {
    this.currentPassage = currentPassage;
    //TODO: vurder å bruk begin() og go() fra Game klassen
    String passageString = String.format("%s: %s%n",
        currentPassage.getTitle(), currentPassage.getContent());
    gameTextFlow.getChildren().add(new Text(passageString));
    choiceText = new Text(String.format("%s:%n", resourceBundle.getString("game.choices")));
    gameTextFlow.getChildren().add(choiceText);
    for (Link link : currentPassage.getLinks()) {
      Hyperlink hyperLink = new Hyperlink(String.format("%s ",link.getText()));
      hyperLink.setOnAction(event -> controller.onLinkPressed(event, link));
      gameTextFlow.getChildren().add(hyperLink);
    }
  }

  public void clearGameTextFlow() {
    gameTextFlow.getChildren().clear();
  }

  public void setController(GameController controller) {
    this.controller = controller;
  }

  public void update() {
    resourceBundle = ResourceBundle
        .getBundle("languages/gameView", SettingsModel.getInstance().getLocale());
    choiceText.setText(String.format("%s:%n", resourceBundle.getString("game.choices")));
  }

  public GameSave getCurrentGameSave() {
    return this.currentGameSave;
  }

  public Passage getCurrentPassage() {
    return this.currentPassage;
  }

  public void setCurrentPassage(Passage passage) {
    this.currentPassage = passage;
  }

  public void loadGameSave(GameSave gameSaveToLoad) {
    this.currentGameSave = gameSaveToLoad;
    if (currentGameSave.getLastSavedPassage() == null) {
      this.currentPassage = currentGameSave.getGame().begin();
    } else {
      this.currentPassage = currentGameSave.getLastSavedPassage();
    }
    clearGameTextFlow();
    addToGameTextFlow(this.currentPassage);
  }
}
