package no.ntnu.idatg2001.frontend.view;

import java.util.ResourceBundle;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import no.ntnu.idatg2001.backend.gameinformation.GameSave;
import no.ntnu.idatg2001.backend.SettingsModel;
import no.ntnu.idatg2001.backend.gameinformation.Link;
import no.ntnu.idatg2001.backend.gameinformation.Passage;
import no.ntnu.idatg2001.frontend.controller.GameController;

public class GameView extends BorderPane {
  private ResourceBundle resourceBundle;
  private GameController controller;
  private TextFlow gameTextFlow;
  private GridPane playerStatsGridPane;
  private Label nameLabel;
  private Label playerNameLabel;
  private ProgressBar healthBar;
  private Label healthLabel;
  private ProgressBar manaBar;
  private Label manaLabel;
  private Label goldLabel;
  private Label goldAmountLabel;
  private ListView<String> playerInventoryListView;
  private VBox gameVBox;
  private Text choiceText;
  private GameSave currentGameSave;
  private Passage currentPassage;

  public GameView(GameSave currentGameSave) {
    resourceBundle = ResourceBundle
      .getBundle("languages/GameView", SettingsModel.getInstance().getLocale());
    getStylesheets().add("/css/GameView.css");
    createPlayerStatsGridPane();
    createPlayerInventoryListView();
    createLayout();
    loadGameSave(currentGameSave);
  }

  private void createLayout() {
    VBox playerInfoVBox = new VBox();
    playerInfoVBox.setAlignment(Pos.CENTER);
    playerInfoVBox.setSpacing(10);
    playerInfoVBox.getChildren().addAll(playerStatsGridPane, playerInventoryListView);
    setRight(playerInfoVBox);
    gameVBox = new VBox();
    gameVBox.setAlignment(Pos.CENTER);
    gameTextFlow = new TextFlow();
    gameTextFlow.setFocusTraversable(true);
    gameTextFlow.setMinSize(300,300);
    //gameTextFlow.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    gameVBox.getChildren().add(gameTextFlow);
    this.setCenter(gameVBox);
    this.addEventFilter(KeyEvent.KEY_PRESSED, event -> controller.onEscapeButtonPressed(event));
  }

  private void createPlayerStatsGridPane() {
    playerStatsGridPane = new GridPane();
    playerStatsGridPane.setAlignment(Pos.CENTER);
    playerStatsGridPane.setHgap(10);
    playerStatsGridPane.setVgap(10);
    ColumnConstraints columnConstraints = new ColumnConstraints();
    columnConstraints.setHalignment(HPos.CENTER);
    playerStatsGridPane.getColumnConstraints().addAll(columnConstraints, columnConstraints);
    nameLabel = new Label(resourceBundle.getString("game.name") + ":");
    playerNameLabel = new Label();
    healthBar = new ProgressBar();
    healthBar.setPrefWidth(100);
    healthBar.setId("health-bar");
    healthBar.setProgress(1);
    healthLabel = new Label();
    manaBar = new ProgressBar();
    manaBar.setProgress(1);
    manaBar.setId("mana-bar");
    manaLabel = new Label();
    goldLabel = new Label(resourceBundle.getString("game.gold") + ":");
    goldAmountLabel = new Label();
    playerStatsGridPane.add(nameLabel, 0, 0);
    playerStatsGridPane.add(playerNameLabel, 1, 0);
    playerStatsGridPane.add(healthBar, 0, 1);
    playerStatsGridPane.add(healthLabel, 1, 1);
    playerStatsGridPane.add(manaBar, 0, 2);
    playerStatsGridPane.add(manaLabel, 1, 2);
    playerStatsGridPane.add(goldLabel, 0, 3);
    playerStatsGridPane.add(goldAmountLabel, 1, 3);
  }

  private void createPlayerInventoryListView() {
    playerInventoryListView = new ListView<>();
  }

  public void addToGameTextFlow(Passage currentPassage) {
    this.currentPassage = currentPassage;
    updateStats();
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

  public void updateStats() {
    healthBar.setProgress((double) currentGameSave.getGame().getUnit().getUnitHealth()
        / currentGameSave.getGame().getUnit().getUnitHealthMax());
    healthLabel.setText(String.format("%d/%d", currentGameSave.getGame().getUnit().getUnitHealth(),
        currentGameSave.getGame().getUnit().getUnitHealthMax()));
    manaBar.setProgress((double) currentGameSave.getGame().getUnit().getUnitMana()
        / currentGameSave.getGame().getUnit().getUnitManaMax());
    manaLabel.setText(String.format("%d/%d", currentGameSave.getGame().getUnit().getUnitMana(),
        currentGameSave.getGame().getUnit().getUnitManaMax()));
    goldAmountLabel.setText(String.valueOf(currentGameSave.getGame().getUnit().getGold()));
  }

  public void loadGameSave(GameSave gameSaveToLoad) {
    this.currentGameSave = gameSaveToLoad;
    if (currentGameSave.getLastSavedPassage() == null) {
      this.currentPassage = currentGameSave.getGame().begin();
    } else {
      this.currentPassage = currentGameSave.getLastSavedPassage();
    }
    playerNameLabel.setText(currentGameSave.getGame().getUnit().getUnitName());
    clearGameTextFlow();
    addToGameTextFlow(this.currentPassage);
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

  public ListView<String> getPlayerInventoryListView() {
    return this.playerInventoryListView;
  }
}
