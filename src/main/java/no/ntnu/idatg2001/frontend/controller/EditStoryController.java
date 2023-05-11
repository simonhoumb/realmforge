package no.ntnu.idatg2001.frontend.controller;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import no.ntnu.idatg2001.frontend.view.CreateStoryView;
import no.ntnu.idatg2001.frontend.view.EditStoryView;
import no.ntnu.idatg2001.frontend.view.SettingsDialog;

public class EditStoryController {

  private EditStoryView editStoryView;

  public EditStoryController(EditStoryView editStoryView) {
    this.editStoryView = editStoryView;
  }

  public void onBackButtonPressed() {
    CreateStoryView createStoryView = new CreateStoryView();
    Scene newScene = editStoryView.getScene();
    CreateStoryController createStoryController = new CreateStoryController(createStoryView);
    createStoryView.setController(createStoryController);
    newScene.setRoot(createStoryView);
  }

  private void onAddPassageButtonPressed() {

  }
  private void onAddLinkButtonPressed() {

  }

  private void onAddActingButtonPressed() {

  }
}
