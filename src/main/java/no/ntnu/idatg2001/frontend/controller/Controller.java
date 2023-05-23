package no.ntnu.idatg2001.frontend.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import no.ntnu.idatg2001.dao.GameDAO;
import no.ntnu.idatg2001.dao.GameSaveDAO;
import no.ntnu.idatg2001.dao.PassageDAO;
import no.ntnu.idatg2001.dao.StoryDAO;
import no.ntnu.idatg2001.dao.UnitDAO;

/**
 *Abstract class for all controllers.
 *
 * @param <T> The controller class.
 * @author Simon Husås Houmb
 * @version 1.0
 */
public abstract class   Controller<T> {

  protected T view;

  /**
   *Closes the window associated with the given event.
   *
   * @param event The action event.
   */
  public void onCloseSource(ActionEvent event) {
    Node source = (Node) event.getSource();
    Stage stage = (Stage) source.getScene().getWindow();
    stage.close();
  }

  /**
   *Exits the application.
   *
   * @param event The action event.
   */
  public void onExitApplication(ActionEvent event) {
    event.consume();
    GameDAO.getInstance().close();
    GameSaveDAO.getInstance().close();
    StoryDAO.getInstance().close();
    PassageDAO.getInstance().close();
    UnitDAO.getInstance().close();
    Platform.exit();
    System.exit(0);
  }

  /**
   *Handles the save button pressed event in the settings.
   *
   * @param event The action event.
   */
  public void onSettingSaveButtonPressed(ActionEvent event) {
    // Default implementation
  }

  /**
   *Handles the load game button pressed event.
   *
   *@param event The action event.
   */
  public void onLoadGameButtonPressed(ActionEvent event) {}

  /**
   *Handles the load selected game event.
   *
   *@param event The action event.
   */
  public void onLoadSelectedGame(ActionEvent event) {}

  public void onDeleteGameButton(ActionEvent event) {}

  /**
   *Configures the saved games table view.
   *
   * @param event The action event.
   */
  public void configureSavedGamesTableView(ActionEvent event) {}

  /**
   *Populates the saved games table view.
   *
   * @param event The action event.
   */
  public void populateSavedGamesTableView(ActionEvent event) {}
}
