package no.ntnu.idatg2001.FrontEnd.View;

import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import no.ntnu.idatg2001.BackEnd.Model.SettingsModel;
import no.ntnu.idatg2001.FrontEnd.Controller.MainMenuController;
import no.ntnu.idatg2001.SavedGames;

public class LoadGameDialog extends Dialog {

  private ResourceBundle resourceBundle;
  private Button backButton;
  private Button loadGameButton;
  private VBox layout;
  private MainMenuController controller;
  private TableView<SavedGames> savedGamesTableView;

  public LoadGameDialog(MainMenuController controller) {
    this.controller = controller;
    initStyle(StageStyle.TRANSPARENT);
    Locale locale = new Locale(SettingsModel.getInstance().getLocale().toString());
    resourceBundle = ResourceBundle.getBundle("languages/exitDialog", locale);
    createTableView();
    createLoadGameButton();
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
    buttonBox.getChildren().addAll(loadGameButton,backButton);
    layout.getChildren().add(buttonBox);
    layout.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_PREF_SIZE);
    layout.setAlignment(Pos.CENTER);
    layout.setSpacing(20);
    layout.setPadding(new Insets(20));
    layout.setPrefSize(644,350);
  }

  private void createTableView() {
    //This is used for testing; remove later!!!
    ObservableList<SavedGames> savedGames = FXCollections.observableArrayList(
        new SavedGames("Saved games 1", new Date(),"Player 1"),
        new SavedGames("Saved games 2", new Date(),"Player 2"),
        new SavedGames("Saved games 3", new Date(),"Player 3"));

    savedGamesTableView = new TableView<>();
    savedGamesTableView.setEditable(false);
    savedGamesTableView.setItems(FXCollections.observableArrayList(
        savedGames.subList(0, Math.min(3, savedGames.size()))));

    TableColumn<GameSave, String> nameColum = new TableColumn<>(
        resourceBundle.getString("loadGameTableName"));
    nameColum.setCellValueFactory(new PropertyValueFactory<>("name"));
    nameColum.setResizable(false);
    nameColum.setSortable(false);
    nameColum.setReorderable(false);
    nameColum.setPrefWidth(200);

    TableColumn<GameSave, Date> dateColum = new TableColumn<>(
        resourceBundle.getString("loadGameTableDate"));
    dateColum.setCellValueFactory(new PropertyValueFactory<>("date"));
    dateColum.setResizable(false);
    dateColum.setSortable(false);
    dateColum.setReorderable(false);
    dateColum.setPrefWidth(250);

    TableColumn<GameSave, String> playerColum = new TableColumn<>(
        resourceBundle.getString("loadGameTablePlayer"));
    playerColum.setCellValueFactory(new PropertyValueFactory<>("playerName"));
    playerColum.setResizable(false);
    playerColum.setSortable(false);
    playerColum.setReorderable(false);
    playerColum.setPrefWidth(150);

    savedGamesTableView.getColumns().addAll(nameColum, dateColum, playerColum);
    savedGamesTableView.setFixedCellSize(30);
    savedGamesTableView.setPrefHeight(250);
  }

  private void createBackToMainMenuButton() {
    backButton = new Button(resourceBundle.getString("backToMainMenuButton"));
    backButton.setAlignment(Pos.CENTER);
    backButton.setWrapText(true);
    backButton.setOnAction(event -> {
     controller.onCloseSource(event);
    });
  }

  private void createLoadGameButton() {
    loadGameButton = new Button(resourceBundle.getString("loadGameButton"));
    loadGameButton.setAlignment(Pos.CENTER);
    loadGameButton.setWrapText(true);
    loadGameButton.setOnAction(event -> {
      throw new UnsupportedOperationException();
    });
  }
}
