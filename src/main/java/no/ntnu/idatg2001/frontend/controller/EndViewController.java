package no.ntnu.idatg2001.frontend.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import no.ntnu.idatg2001.frontend.view.EndGameView;
import no.ntnu.idatg2001.frontend.view.MainMenuView;

public class EndViewController {

  private EndGameView view;

  public EndViewController(EndGameView view) {
    this.view = view;
  }

  public void onBackToMainMenuButtonPressed(ActionEvent event) throws IOException {
    event.consume();
    MainMenuView mainMenuView = new MainMenuView();
    Scene newScene = view.getScene();
    MainMenuController menuController = new MainMenuController(mainMenuView);
    mainMenuView.setController(menuController);
    newScene.setRoot(mainMenuView);
  }

}
