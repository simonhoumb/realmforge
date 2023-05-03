package no.ntnu.idatg2001.FrontEnd.Controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import no.ntnu.idatg2001.FrontEnd.View.CreateStoryView;
import no.ntnu.idatg2001.FrontEnd.View.MainMenuView;

public class CreateStoryController {
  private CreateStoryView createStoryView;

  public CreateStoryController(CreateStoryView createStoryView) throws IOException {
    this.createStoryView = createStoryView;
  }

  public void saveStoryTest(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save Story");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.Paths"));
    File file = fileChooser.showSaveDialog(createStoryView.getScene().getWindow());
    if (file != null) {
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
        // Iterate through the entries of the HashMap and save the content of each text area
        for (Map.Entry<String, TextArea> entry : createStoryView.getRoomTextAreas().entrySet()) {
          String roomName = entry.getKey();
          TextArea textArea = entry.getValue();
          String text = textArea.getText();
          // Write the room name and the content of the text area to the file
          writer.write("::" + roomName + "\n");
          writer.write(text);
          writer.write("\n\n");
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void onBackToMainMenuButtonPressed(ActionEvent event) throws IOException {
    MainMenuView mainMenuView = new MainMenuView();
    Scene newScene = createStoryView.getScene();
    MainMenuController menuController = new MainMenuController(mainMenuView);
    mainMenuView.setController(menuController);
    newScene.setRoot(mainMenuView);
  }

  public void onCloseSource(ActionEvent event) {
    Node source = (Node) event.getSource();
    Stage stage = (Stage) source.getScene().getWindow();
    stage.close();
  }
}
