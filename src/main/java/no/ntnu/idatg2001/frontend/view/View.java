package no.ntnu.idatg2001.frontend.view;

import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import no.ntnu.idatg2001.frontend.controller.Controller;

public interface View<T> {

  public void setController(Controller<T> controller);
}
