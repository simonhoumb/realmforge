package no.ntnu.idatg2001.frontend.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import no.ntnu.idatg2001.dao.GameDAO;

public abstract class Controller<T> {

  protected T view;

  public void onCloseSource(ActionEvent event) {
    Node source = (Node) event.getSource();
    Stage stage = (Stage) source.getScene().getWindow();
    stage.close();
  }

  public void onExitApplication(ActionEvent event) {
    event.consume();
    GameDAO.getInstance().close();
    Platform.exit();
    System.exit(0);
  }

  public void onSettingSaveButtonPressed(ActionEvent event) {
    //Default implementation
  }
}
