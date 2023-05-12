package no.ntnu.idatg2001.frontend.controller;

import javafx.scene.Scene;
import no.ntnu.idatg2001.frontend.view.CreateStoryView;
import no.ntnu.idatg2001.frontend.view.EditStoryView;

public class EditStoryController extends Controller {

  public EditStoryController(EditStoryView editStoryView) {

  }

  private void onBackButtonPressed() {
    CreateStoryView createStoryView = new CreateStoryView();
    Scene newScene = createStoryView.getScene();
    CreateStoryController createStoryController = new CreateStoryController(createStoryView);
    createStoryView.setController(createStoryController);
    newScene.setRoot(createStoryView);
  }

  @Override
  public void onSettingsViewButtonPressed() {
    throw new UnsupportedOperationException();
  }
}
