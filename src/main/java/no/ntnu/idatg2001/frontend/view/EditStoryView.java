package no.ntnu.idatg2001.frontend.view;

import javafx.scene.layout.BorderPane;
import no.ntnu.idatg2001.frontend.controller.Controller;
import no.ntnu.idatg2001.frontend.controller.EditStoryController;

public class EditStoryView extends BorderPane implements View {

  private EditStoryController controller;


  public EditStoryView() {

  }

  public void init() {

  }

  @Override
  public void setController(Controller controller) {
    this.controller = (EditStoryController) controller;
  }
}
