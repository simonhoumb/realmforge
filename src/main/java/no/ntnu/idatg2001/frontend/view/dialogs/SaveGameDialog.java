package no.ntnu.idatg2001.frontend.view.dialogs;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import no.ntnu.idatg2001.GameSave;
import no.ntnu.idatg2001.backend.SettingsModel;
import no.ntnu.idatg2001.frontend.controller.Controller;
import no.ntnu.idatg2001.frontend.controller.GameController;

public class SaveGameDialog<T extends Controller<T>> extends Dialog {

  private ResourceBundle resourceBundle;
  private Button backButton;
  private Button saveGameButton;
  private VBox layout;
  private GameController controller;
  private TableView<GameSave> savedGamesTableView;
  private TableColumn<GameSave, String> nameColumn;
  private TableColumn<GameSave, LocalDateTime> dateTimeColumn;
  private TableColumn<GameSave, String> playerColumn;

  public SaveGameDialog(GameController controller) {
    this.controller = controller;
    initStyle(StageStyle.TRANSPARENT);
    Locale locale = new Locale(SettingsModel.getInstance().getLocale().toString());
    resourceBundle = ResourceBundle.getBundle("languages/saveGameDialog", locale);
    createTableView();
    createSaveGameButton();
    createBackToMainMenuButton();
    createLayout();
    getDialogPane().setContent(layout);
    getDialogPane().getScene().setFill(Color.TRANSPARENT);
    getDialogPane().getStylesheets().add("css/LoadGameDialog.css");
  }

  private void createLayout() {
    layout = new VBox();
    layout.getChildren().add(savedGamesTableView);
    layout.setAlignment(Pos.CENTER);
    HBox buttonBox = new HBox();
    buttonBox.setSpacing(20);
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.setPadding(new Insets(10));
    buttonBox.getChildren().addAll(saveGameButton, backButton);
    layout.getChildren().add(buttonBox);
    layout.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_PREF_SIZE);
    layout.setAlignment(Pos.CENTER);
    layout.setSpacing(20);
    layout.setPadding(new Insets(20));
    layout.setPrefSize(644, 350);
  }

  private void createTableView() {
    savedGamesTableView = new TableView<>();
    savedGamesTableView.setEditable(false);
    savedGamesTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    nameColumn = new TableColumn<>(
        resourceBundle.getString("saveGameTableName"));
    nameColumn.setResizable(false);
    nameColumn.setSortable(false);
    nameColumn.setReorderable(false);
    nameColumn.setPrefWidth(200);

    dateTimeColumn = new TableColumn<>(
        resourceBundle.getString("saveGameTableDate"));
    dateTimeColumn.setResizable(false);
    dateTimeColumn.setSortable(false);
    dateTimeColumn.setReorderable(false);
    dateTimeColumn.setPrefWidth(250);

    playerColumn = new TableColumn<>(
        resourceBundle.getString("saveGameTablePlayer"));
    playerColumn.setResizable(false);
    playerColumn.setSortable(false);
    playerColumn.setReorderable(false);
    playerColumn.setPrefWidth(150);

    savedGamesTableView.getColumns().addAll(nameColumn, dateTimeColumn, playerColumn);
    savedGamesTableView.setFixedCellSize(30);
    savedGamesTableView.setPrefHeight(250);
  }

  private void createBackToMainMenuButton() {
    backButton = new Button(resourceBundle.getString("backToMainMenuButton"));
    backButton.setCancelButton(true);
    backButton.setAlignment(Pos.CENTER);
    backButton.setWrapText(true);
    backButton.setOnAction(event -> controller.onCloseSource(event));
  }

  private void createSaveGameButton() {
    saveGameButton = new Button(resourceBundle.getString("saveGameButton"));
    saveGameButton.setAlignment(Pos.CENTER);
    saveGameButton.setWrapText(true);
    saveGameButton.setOnAction(event -> controller.onSaveSelectedGame(event));
  }

  public GameSave getSelectedGameSave() {
    return savedGamesTableView.getSelectionModel().getSelectedItem();
  }

  public TableView<GameSave> getSavedGamesTableView() {
    return savedGamesTableView;
  }

  public TableColumn<GameSave, String> getNameColumn() {
    return nameColumn;
  }

  public TableColumn<GameSave, LocalDateTime> getDateTimeColumn() {
    return dateTimeColumn;
  }

  public TableColumn<GameSave, String> getPlayerColumn() {
    return playerColumn;
  }
}