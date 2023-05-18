package no.ntnu.idatg2001.frontend.view.dialogs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import no.ntnu.idatg2001.backend.actions.ActionType;
import no.ntnu.idatg2001.frontend.controller.EditStoryController;

import java.util.EnumMap;
import java.util.Map;

public class AddActionDialog extends Dialog<Void> {

  private ComboBox<ActionType> actionTypeComboBox;
  private Map<ActionType, Node> actionTypeComponents;
  private EditStoryController controller;

  public AddActionDialog(EditStoryController controller) {
    this.controller = controller;
    createComboBox();
    createActionTypeComponents();
    createGridPane();
    setTitle("Add Action");
    setHeaderText("Enter the Action Details");

    DialogPane dialogPane = getDialogPane();
    dialogPane.setContent(createGridPane());

    // Enable automatic resizing of the dialog based on content
    setResizable(true);

    // Set result converter to return null when Add button is clicked
    setResultConverter(dialogButton -> null);

    configureComboBox();
  }

  private GridPane createGridPane() {
    GridPane gridPane = new GridPane();
    gridPane.setHgap(10);
    gridPane.setVgap(10);

    Label actionLabel = new Label("Action Type:");
    gridPane.addRow(0, actionLabel, actionTypeComboBox);

    HBox buttonBox = new HBox();
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.setSpacing(10);
    buttonBox.getChildren().addAll(createAddButton(), createCancelButton());

    // Add the buttons row to the grid pane
    gridPane.addRow(2, buttonBox);

    // Set the grid pane as the content of the dialog pane
    DialogPane dialogPane = getDialogPane();
    dialogPane.setContent(gridPane);

    return gridPane;
  }

  private void createComboBox() {
    actionTypeComboBox = new ComboBox<>();
    actionTypeComboBox.setPromptText("Select Action Type");
    ObservableList<ActionType> actionTypeObservableList = FXCollections.observableArrayList(
        ActionType.values());
    actionTypeComboBox.setItems(actionTypeObservableList);
  }

  private void createActionTypeComponents() {
    actionTypeComponents = new EnumMap<>(ActionType.class);

    // Create components for each ActionType
    for (ActionType actionType : ActionType.values()) {
      Node component = createComponentForActionType(actionType);
      actionTypeComponents.put(actionType, component);
    }
  }

  private Node createComponentForActionType(ActionType actionType) {
    switch (actionType) {
      case GOLD:
      case HEALTH:
      case DAMAGE:
      case PASSAGE:
      case SCORE:
        return createTextField();
      case ARMOR:
      case WEAPON:
      case ITEM:
        return createNewComboBox();
      case GAMEOVER:
      case WIN:
      case LOSE:
      case NONE:
        return null; // No additional component needed
    }
    return null; // Default case, should not be reached
  }

  private Node createTextField() {
    return new TextField();
  }

  private ComboBox<String> createNewComboBox() {
    return new ComboBox<>();
  }

  private Button createAddButton() {
    Button addButton = new Button("Add");
    addButton.setOnAction(e -> {
      controller.onAddActionOnAddButtonPressed(e);
    });
    return addButton;
  }

  private Button createCancelButton() {
    Button cancelButton = new Button("Cancel");
    cancelButton.setOnAction(event -> {
      controller.onCloseSource(event);
    });
    return cancelButton;
  }

  private Node getComponentForActionType(ActionType actionType) {
    return actionTypeComponents.get(actionType);
  }
  private Node getCurrentComponent() {
    ActionType actionType = actionTypeComboBox.getSelectionModel().getSelectedItem();
    return getComponentForActionType(actionType);
  }

  public ActionType getSelectedActionType() {
    return actionTypeComboBox.getSelectionModel().getSelectedItem();
  }

  public String getSelectedValue() {
    Node currentComponent = getCurrentComponent();

    if (currentComponent instanceof TextField) {
      TextField textField = (TextField) currentComponent;
      return textField.getText();
    } else if (currentComponent instanceof ComboBox) {
      ComboBox<ActionType> comboBox = (ComboBox<ActionType>) currentComponent;
      Object selectedItem = comboBox.getSelectionModel().getSelectedItem();

      if (selectedItem instanceof String) {
        return (String) selectedItem;
      }
    }

    return null; // Return null if no value is found
  }

  private void configureComboBox() {
    actionTypeComboBox.setOnAction(event -> {
      ActionType selectedActionType = getSelectedActionType();
      Node component = getComponentForActionType(selectedActionType);
      updateComponent(component);

      // Adjust dialog size
      getDialogPane().getScene().getWindow().sizeToScene();
    });
  }

  private void updateComponent(Node component) {
    GridPane gridPane = (GridPane) getDialogPane().getContent();
    int actionTypeRow = GridPane.getRowIndex(actionTypeComboBox);

    // Remove the previous dynamic component if present
    gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == actionTypeRow + 1);

    // Add the new component to the grid pane
    if (component != null) {
      gridPane.add(component, 1, actionTypeRow + 1);
    }
  }
}
