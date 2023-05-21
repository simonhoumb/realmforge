package no.ntnu.idatg2001.frontend.view;

import com.jfoenix.controls.JFXButton;
import java.util.ResourceBundle;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import no.ntnu.idatg2001.backend.gameinformation.GameSave;
import no.ntnu.idatg2001.backend.SettingsModel;
import no.ntnu.idatg2001.backend.gameinformation.Link;
import no.ntnu.idatg2001.backend.gameinformation.Passage;
import no.ntnu.idatg2001.frontend.controller.GameController;

public class GameView extends BorderPane {
  private ResourceBundle resourceBundle;
  private GameController controller;
  private TextArea contentTextArea;
  private GridPane playerStatsGridPane;
  private Label nameLabel; //done
  private Label playerNameLabel; //done
  private Label passageLabel;
  private StackPane container;
  private Label scoreLabel;
  private Label scoreAmountLabel;
  private JFXButton goalButton;
  private JFXButton menuButton; //done
  private ProgressBar healthBar; //done
  private Label healthLabel; //done
  private ProgressBar manaBar; // done
  private Label manaLabel; //done
  private Label goldLabel; //done
  private Label goldAmountLabel; //done
  private Label inventoryLabel; //done
  private ListView<String> playerInventoryListView;
  private VBox gameVBox;
  private FlowPane buttonHbox;
  private Separator separatorTop;
  private Separator separatorBottom;
  private Separator separatorLeft;
  private GameSave currentGameSave;
  private Passage currentPassage;

  public GameView(GameSave currentGameSave) {
    resourceBundle = ResourceBundle
      .getBundle("languages/GameView", SettingsModel.getInstance().getLocale());
    getStylesheets().add("/css/GameView.css");
    createPlayerStatsGridPane();
    createCenter();
    createPlayerInventoryListView();
    createLayout();
    setBackground(new Background(new BackgroundFill(Color.rgb(25, 25, 25, 0.9), null, null)));
    loadGameSave(currentGameSave);
  }

  private void createLayout() {
    VBox playerInfoVBox = new VBox();
    playerInfoVBox.setSpacing(10);
    VBox buttonVBox = new VBox();
    buttonVBox.setAlignment(Pos.CENTER);
    buttonVBox.setSpacing(10);
    buttonVBox.setPadding(new Insets(20, 0, 0, 0));
    buttonVBox.getChildren().addAll(goalButton, menuButton);
    playerInfoVBox.getChildren().addAll(container,inventoryLabel, playerInventoryListView, buttonVBox);
    setRight(playerInfoVBox);
    gameVBox = new VBox();
    this.addEventFilter(KeyEvent.KEY_PRESSED, event -> controller.onEscapeButtonPressed(event));
  }

  private void createPlayerStatsGridPane() {
    playerStatsGridPane = new GridPane();
    playerStatsGridPane.setAlignment(Pos.TOP_CENTER);
    playerStatsGridPane.setHgap(10);
    playerStatsGridPane.setVgap(10);
    ColumnConstraints columnConstraints = new ColumnConstraints();
    columnConstraints.setHalignment(HPos.CENTER);
    playerStatsGridPane.getColumnConstraints().addAll(columnConstraints, columnConstraints);

    creatNameLabel();
    createScoreAmountLabel();
    createScoreLabel();
    createPlayerNameLabel();
    createHealthBar();
    createHealthLabel();
    createManaBar();
    createInventoryLabel();
    createManaLabel();
    createGoldLabel();
    createGoldAmountLabel();
    createMenuButton();
    createGoalButton();
    playerStatsGridPane.add(nameLabel, 0, 0);
    playerStatsGridPane.add(playerNameLabel, 1, 0);
    playerStatsGridPane.add(healthBar, 0, 1);
    playerStatsGridPane.add(healthLabel, 1, 1);
    playerStatsGridPane.add(manaBar, 0, 2);
    playerStatsGridPane.add(manaLabel, 1, 2);
    playerStatsGridPane.add(goldLabel, 0, 3);
    playerStatsGridPane.add(goldAmountLabel, 1, 3);
    playerStatsGridPane.add(scoreLabel, 0, 4);
    playerStatsGridPane.add(scoreAmountLabel, 1, 4);
    container = new StackPane(playerStatsGridPane);
    //container.setStyle("-fx-border-color: #1E1E1E; -fx-border-width: 0 0 0 3px;");
  }

  private void createCenter() {
    // Top region with passage name
    createSeparatorTop();
    createSeparatorBottom();
    createPassageLabel();
    createContentTextArea();
    passageLabel = new Label(resourceBundle.getString("passageLabel"));
    passageLabel.setStyle("-fx-font-size: 24px;");
    VBox topVBox = new VBox(passageLabel);
    topVBox.setAlignment(Pos.CENTER);
    topVBox.setPadding(new Insets(100,0,0,0));
    topVBox.setPadding(new Insets(10));


    buttonHbox = new FlowPane();
    buttonHbox.setHgap(10);
    buttonHbox.setVgap(10);
    buttonHbox.setAlignment(Pos.CENTER);


    // Center region with content and button bar
    VBox centerVBox = new VBox(topVBox,contentTextArea, buttonHbox);
    centerVBox.setSpacing(10);
    centerVBox.setAlignment(Pos.CENTER);
    centerVBox.setPadding(new Insets(10));

    // Set the center regions in the BorderPane
    this.setCenter(centerVBox);
  }

  private void createPlayerInventoryListView() {
    playerInventoryListView = new ListView<>();
  }

  public void addLinksToButtons(Passage currentPassage) {
    buttonHbox.getChildren().clear();
    passageLabel.setText(currentPassage.getTitle());
    contentTextArea.setText(currentPassage.getContent().toString());
    this.currentPassage = currentPassage;
    updateStats();
    for (Link link : currentPassage.getLinks()) {
      JFXButton button = new JFXButton(link.getText());
      button.setWrapText(true);
      button.setOnAction(event -> {
        controller.onLinkPressed(event, link);
      });
      buttonHbox.getChildren().add(button);
    }
  }

  public void setController(GameController controller) {
    this.controller = controller;
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
    scoreAmountLabel.setText(String.valueOf(currentGameSave.getGame().getUnit().getUnitScore()));
  }

  public void loadGameSave(GameSave gameSaveToLoad) {
    this.currentGameSave = gameSaveToLoad;
    if (currentGameSave.getLastSavedPassage() == null) {
      this.currentPassage = currentGameSave.getGame().begin();
    } else {
      this.currentPassage = currentGameSave.getLastSavedPassage();
    }
    playerNameLabel.setText(currentGameSave.getGame().getUnit().getUnitName());
    addLinksToButtons(currentPassage);
  }

  private Separator createSeparatorTop() {
    separatorTop = new Separator();
    separatorTop.setOrientation(Orientation.HORIZONTAL);
    separatorTop.setPrefWidth(Double.MAX_VALUE);
    separatorTop.setPadding(new Insets(10,0,10,0));
    this.setTop(separatorTop);
    return separatorTop;
  }

  private Separator createSeparatorBottom() {
    separatorBottom = new Separator();
    separatorBottom.setOrientation(Orientation.HORIZONTAL);
    separatorBottom.setPrefWidth(Double.MAX_VALUE);
    separatorBottom.setPadding(new Insets(10,0,10,0));
    this.setBottom(separatorBottom);
    return separatorBottom;
  }

  private Label creatNameLabel() {
    nameLabel = new Label(resourceBundle.getString("game.name") + ":");
    return nameLabel;
  }

  private Label createPlayerNameLabel() {
    playerNameLabel = new Label();
    return playerNameLabel;
  }

  private Label createHealthLabel() {
    healthLabel = new Label(resourceBundle.getString("game.health") + ":");
    return healthLabel;
  }

  private Label createManaLabel() {
    manaLabel = new Label(resourceBundle.getString("game.mana") + ":");
    return manaLabel;
  }

  private Label createScoreAmountLabel() {
    scoreAmountLabel = new Label();
    return scoreAmountLabel;
  }

  private Label createGoldLabel() {
    goldLabel = new Label(resourceBundle.getString("game.gold") + ":");
    return goldLabel;
  }

  private Label createInventoryLabel() {
    inventoryLabel = new Label(resourceBundle.getString("game.inventory") + ":");
    return inventoryLabel;
  }

  private Label createGoldAmountLabel() {
    goldAmountLabel = new Label();
    return goldAmountLabel;
  }

  private Label createPassageLabel() {
    passageLabel = new Label();
    return passageLabel;
  }

  private Label createScoreLabel() {
    scoreLabel = new Label(resourceBundle.getString("game.score") + ":");
    return scoreLabel;
  }

  private JFXButton createMenuButton() {
    menuButton = new JFXButton(resourceBundle.getString("game.menu"));
    menuButton.setPrefWidth(200);
    menuButton.setOnAction(event -> controller.onMenuButtonPressed(event));
    return menuButton;
  }

  private JFXButton createGoalButton() {
    goalButton = new JFXButton(resourceBundle.getString("game.goalButton"));
    goalButton.setPrefWidth(200);
    goalButton.setOnAction(event -> controller.onSaveButtonPressed(event));
    return goalButton;
  }

  private ProgressBar createHealthBar() {
    healthBar = new ProgressBar();
    healthBar.setPrefWidth(100);
    healthBar.setId("health-bar");
    healthBar.setProgress(1);
    return healthBar;
  }

  private ProgressBar createManaBar() {
    manaBar = new ProgressBar();
    manaBar.setProgress(1);
    manaBar.setId("mana-bar");
    return manaBar;
  }

  private TextArea createContentTextArea() {
    contentTextArea = new TextArea();
    contentTextArea.setEditable(false);
    contentTextArea.setWrapText(true);
    return contentTextArea;
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
