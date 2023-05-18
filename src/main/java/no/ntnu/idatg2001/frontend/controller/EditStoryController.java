package no.ntnu.idatg2001.frontend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import no.ntnu.idatg2001.backend.actions.Action;
import no.ntnu.idatg2001.backend.actions.ActionType;
import no.ntnu.idatg2001.backend.actions.GoldAction;
import no.ntnu.idatg2001.backend.actions.HealthAction;
import no.ntnu.idatg2001.backend.actions.InventoryAction;
import no.ntnu.idatg2001.backend.gameinformation.Link;
import no.ntnu.idatg2001.backend.gameinformation.Passage;
import no.ntnu.idatg2001.backend.gameinformation.Story;
import no.ntnu.idatg2001.backend.gameinformation.StoryWriter;
import no.ntnu.idatg2001.dao.StoryDAO;
import no.ntnu.idatg2001.frontend.view.AddActionDialog;
import no.ntnu.idatg2001.frontend.view.AddLinkDialog;
import no.ntnu.idatg2001.frontend.view.AddPassageDialog;
import no.ntnu.idatg2001.frontend.view.CreateStoryView;
import no.ntnu.idatg2001.frontend.view.EditLinkDialog;
import no.ntnu.idatg2001.frontend.view.EditPassageDialog;
import no.ntnu.idatg2001.frontend.view.EditStoryView;
import no.ntnu.idatg2001.frontend.view.StoryMapCanvas;


public class EditStoryController extends Controller<EditStoryView> {

  private AddPassageDialog addPassageDialog;
  private AddLinkDialog addLinkDialog;
  private AddActionDialog addActionDialog;
  private EditPassageDialog editPassageDialog;
  private EditLinkDialog editLinkDialog;
  private Story selectedStory;

  public EditStoryController(EditStoryView view) {
    this.view = view;
    configurePassageList();
  }

  public void onBackButtonPressed() {
    CreateStoryView createStoryView = new CreateStoryView();
    Scene newScene = view.getScene();
    CreateStoryController createStoryController = new CreateStoryController(createStoryView);
    createStoryView.setController(createStoryController);
    newScene.setRoot(createStoryView);
  }

  public void onCloseSource(ActionEvent event) {
    Node source = (Node) event.getSource();
    Stage stage = (Stage) source.getScene().getWindow();
    stage.close();
  }

  public void onAddPassageButtonPressed() {
    addPassageDialog = new AddPassageDialog(this);
    addPassageDialog.initOwner(view.getScene().getWindow());
    addPassageDialog.showAndWait();
  }

  public void onAddPassageAddButtonPressed() {
    selectedStory.addPassage(new Passage(addPassageDialog.getRoomNameTextField(),
        new StringBuilder(addPassageDialog.getRoomContentTextArea())));
    StoryDAO.getInstance().update(selectedStory);
    populateTableView();
  }

  public void onAddLinkButtonPressed() {
    if (getSelectedPassageInPassageList() != null) {
      addLinkDialog = new AddLinkDialog(this);
      addLinkDialog.initOwner(view.getScene().getWindow());
      configureAddLinkTableView();
      populateAddLinkPassageTableView();
      getSelectedPassageInPassageList();
      addLinkDialog.showAndWait();
    } else {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("Warning");
      alert.setHeaderText(null);
      alert.initOwner(view.getScene().getWindow());
      alert.setContentText("You have to select a passage to add a link");
      alert.showAndWait();
    }
  }


  public void onAddActingButtonPressed() {
    if (getSelectedLinkInLinkList() != null && view.getLinkTableView() != null) {
    addActionDialog = new AddActionDialog(this);
    addActionDialog.initOwner(view.getScene().getWindow());
    addActionDialog.showAndWait();
    getSelectedLinkInLinkList();
    } else {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("Warning");
      alert.setHeaderText(null);
      alert.initOwner(view.getScene().getWindow());
      alert.setContentText("You have to select a link to add an action");
      alert.showAndWait();
    }
  }

  public void onAddLinkToPassageAddButton(ActionEvent event) {
    Passage selectedPassage = addLinkDialog.getPassageTableView().getSelectionModel().getSelectedItem();
    if (selectedPassage != null && selectedPassage != getSelectedPassageInPassageList()) {
      getSelectedPassageInPassageList().addLink(new Link(addLinkDialog.getLinkTextField().getText(),
          addLinkDialog.getPassageTableView().getSelectionModel().getSelectedItem().getTitle()));
      StoryDAO.getInstance().update(selectedStory);
      populateLinkTableView();
      onCloseSource(event);
    } else {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("Warning");
      alert.setHeaderText(null);
      alert.initOwner(addLinkDialog.getDialogPane().getScene().getWindow());
      alert.setContentText("You can't link to the same passage");
      alert.showAndWait();
    }
  }

  public void onEditButtonIsPressed() {
    if (getSelectedPassageInPassageList() != null && getSelectedLinkInLinkList() == null
    && getSelectedActionInActionList() == null) {
      editPassageDialog = new EditPassageDialog(getSelectedPassageInPassageList(), this);
      editPassageDialog.initOwner(view.getScene().getWindow());
      editPassageDialog.showAndWait();
    } else if (getSelectedLinkInLinkList() != null && getSelectedActionInActionList() == null) {
      editLinkDialog = new EditLinkDialog(getSelectedLinkInLinkList(),this);
      editLinkDialog.initOwner(view.getScene().getWindow());
      editLinkDialog.showAndWait();
    } else if (getSelectedActionInActionList() != null) {
    //TODO: Add edit action dialog if needed.
    } else {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("Warning");
      alert.setHeaderText(null);
      alert.initOwner(view.getScene().getWindow());
      alert.setContentText("You have to select a passage to edit");
      alert.showAndWait();
    }
  }

  public void onEditPassageSaveButtonPressed(ActionEvent event) {
      String newName = editPassageDialog.getRoomNameTextField();
      editLinkReference(getSelectedPassageInPassageList(), newName);
      getSelectedPassageInPassageList().setTitle(editPassageDialog.getRoomNameTextField());
      getSelectedPassageInPassageList().setContent(
          new StringBuilder(editPassageDialog.getRoomContentTextArea()));
      StoryDAO.getInstance().update(selectedStory);
      populateTableView();
      onCloseSource(event);
  }

  public void onEditLinkSaveButtonPressed(ActionEvent event) {
    getSelectedLinkInLinkList().setText(editLinkDialog.getLinkText());
    StoryDAO.getInstance().update(selectedStory);
    populateLinkTableView();
    onCloseSource(event);
  }



  public void onAddActionOnAddButtonPressed(ActionEvent event) {
    getSelectedLinkInLinkList().addAction(createActionInstance(addActionDialog.getSelectedActionType(),
        addActionDialog.getSelectedValue()));
    StoryDAO.getInstance().update(selectedStory);
    populateActionTableView();
    onCloseSource(event);
  }

  private Action createActionInstance(ActionType actionType, String selectedValue) {
    switch (actionType) {
      case GOLD:
        // Create and return an instance of the GoldAction class with the selected value
        return new GoldAction(Integer.parseInt(selectedValue));
      case HEALTH:
        // Create and return an instance of the HealthAction class with the selected value
        return new HealthAction(Integer.parseInt(selectedValue));
      case DAMAGE:
        // Create and return an instance of the DamageAction class with the selected value
        return new InventoryAction(selectedValue);
      // Handle other action types as needed
      default:
        return null;
    }
  }

  public void onSavePress() {
    StoryWriter storyWriter = new StoryWriter();
    storyWriter.writeStoryToFile(selectedStory);
  }


  public void onMapPressed() {
    // Create a ScrollPane and set the canvas as its content
    ScrollPane scrollPane = new ScrollPane();
    // Create an instance of your StoryMapCanvas
    StoryMapCanvas storyMapCanvas = new StoryMapCanvas(100, 100, scrollPane);

    // Set the size of the canvas (optional)


    scrollPane.setContent(storyMapCanvas);

    // Call the drawStoryMap method to draw the story map on the canvas
    storyMapCanvas.setStory(selectedStory, scrollPane);

    // Set the preferred size of the ScrollPane
    scrollPane.setPrefSize(800, 600);

    // Create a new stage and set the ScrollPane as the root of your application's scene
    Scene scene = new Scene(scrollPane);

    // Create a new stage and set the scene
    Stage stage = new Stage();
    stage.setScene(scene);

    // Show the stage
    stage.show();
  }


  public void setSelectedStory(Story story) {
    this.selectedStory = story;
    configureTableView();
    populateTableView();
  }

  public void populateTableView() {
    view.getPassageTableView().getItems().clear();// Clear the selection
    List<Passage> passageList = selectedStory.getPassages().values().stream().toList();
    ObservableList<Passage> list = FXCollections.observableArrayList(passageList);
    if (selectedStory.getOpeningPassage() != null) {
      list.add(selectedStory.getOpeningPassage());
    }
    view.getPassageTableView().setItems(list);
  }



  private void populateLinkTableView() {
    view.getLinkTableView().getItems().clear();
    clearSelectedItemInActionList();
    clearSelectedItemInLinkList();// Clear the selection
    List<Link> linkList = getSelectedPassageInPassageList().getLinks().stream().toList();
    if (!linkList.isEmpty()) {
      ObservableList<Link> list = FXCollections.observableArrayList(linkList);
      view.getLinkTableView().setItems(list);
    }
  }

  private void populateActionTableView() {
    view.getActionTableView().getItems().clear();// Clear the selection
    List<Action> actionList = getSelectedLinkInLinkList().getActions().stream().toList();
    if (!actionList.isEmpty()) {
      ObservableList<Action> list = FXCollections.observableArrayList(actionList);
      view.getActionTableView().setItems(list);
    }
  }

  public void configureTableView() {
    view.getPassageTableColumn().setCellValueFactory(new PropertyValueFactory<>("title"));
    view.getLinkTableLinkNameColumn().setCellValueFactory(new PropertyValueFactory<>("text"));
    view.getLinkTableLinkReferenceColumn().setCellValueFactory(new PropertyValueFactory<>("reference"));
    view.getActionTableColumn().setCellValueFactory(new PropertyValueFactory<>("actionType"));
    view.getActionTableActionColumn().setCellValueFactory(new PropertyValueFactory<>("value"));
  }


  private void populateAddLinkPassageTableView() {
    addLinkDialog.getPassageTableView().getItems().clear();
    List<Passage> passageList = selectedStory.getPassages().values().stream().toList();
    ObservableList<Passage> list = FXCollections.observableArrayList(passageList);
    if (selectedStory.getOpeningPassage() != null) {
      list.add(selectedStory.getOpeningPassage());
    }
    addLinkDialog.getPassageTableView().setItems(list);
  }



  private void configureAddLinkTableView() {
    addLinkDialog.getAddLinkPassageColumn().setCellValueFactory(new PropertyValueFactory<>("title"));
  }


  private Passage getSelectedPassageInPassageList() {
    return view.getPassageTableView().getSelectionModel().getSelectedItem();
  }

  private Link getSelectedLinkInLinkList() {
    return view.getLinkTableView().getSelectionModel().getSelectedItem();
  }

  private Action getSelectedActionInActionList() {
    return view.getActionTableView().getSelectionModel().getSelectedItem();
  }

  private void configurePassageList() {
    view.getPassageTableView().getSelectionModel().selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> {
          if (newValue != null) {
            setPassageContentTextInTextArea();
            configureLinkTableView();
            populateLinkTableView(); // Pass the selected passage to the method
          } else {
            view.getPassageContentTextArea().clear();
            view.getLinkTableView().getItems().clear();
            view.getActionTableView().getItems().clear();
          }
        });
  }

  private void configureLinkTableView() {
    final ReadOnlyIntegerProperty selectedIndex = view.getLinkTableView().getSelectionModel().selectedIndexProperty();
    selectedIndex.addListener((observable, oldValue, newValue) -> {
      if (newValue.intValue() != -1 && !newValue.equals(oldValue)) {
        configureActionTableView();
        populateActionTableView(); // Update the action table view
        System.out.println("Link selected");
      } else {
        view.getActionTableView().getItems().clear(); // Clear the action table view if no link is selected
      }
    });
  }

  private void configureActionTableView() {
    view.getActionTableView().getSelectionModel().selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> {
          // No need to clear the items when a new action is selected
          if (newValue != null) {
            // Perform any necessary actions when a new action is selected
          } else {
            view.getActionTableView().getItems().clear();
          }
        });
  }

  public void onDeletePassageButtonPressed(ActionEvent event) {
    Passage selectedPassage = getSelectedPassageInPassageList();
    Link selectedLink = getSelectedLinkInLinkList();
    Action selectedAction = getSelectedActionInActionList();

    String confirmationMessage = "";
    String title = "";
    if (selectedPassage != null && selectedLink == null && selectedAction == null) {
      confirmationMessage = "Are you sure you want to delete this passage?" +
          "\n\nTitle: " + selectedPassage.getTitle();
      title = "Delete Passage";
    } else if (selectedLink != null && selectedAction == null) {
      confirmationMessage = "Are you sure you want to delete this link?" +
          "\n\nDescription: " + selectedLink.getText();
      title = "Delete Link";
    } else if (selectedAction != null) {
      confirmationMessage = "Are you sure you want to delete this action?" +
          "\n\nDescription: " + selectedAction.getActionType();
      title = "Delete Action";
    }

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.initOwner(view.getScene().getWindow());
    alert.setTitle("Confirmation");
    alert.setHeaderText(title);
    alert.setContentText(confirmationMessage);

    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
      if (selectedPassage != null && selectedLink == null && selectedAction == null) {
        selectedStory.removePassage(selectedPassage);
        getLinksByReference(getSelectedPassageInPassageList().getTitle());
        removePassage(getSelectedPassageInPassageList());
        StoryDAO.getInstance().update(selectedStory);
        populateTableView();
        clearSelectedItemInPassageList();
        view.getPassageContentTextArea().clear();
        view.getLinkTableView().getItems().clear();
        view.getActionTableView().getItems().clear();
      } else if (selectedLink != null && selectedAction == null) {
        selectedPassage.removeLink(selectedLink);
        StoryDAO.getInstance().update(selectedStory);
        populateLinkTableView();
        clearSelectedItemInLinkList();
        view.getActionTableView().getItems().clear();
      } else if (selectedAction != null) {
        selectedLink.removeAction(selectedAction);
        StoryDAO.getInstance().update(selectedStory);
        populateActionTableView();
      }
    }
  }

  public List<Link> getLinksByReference(String passageTitle) {
    List<Link> linksWithReference = new ArrayList<>();

    for (Entry<Link, Passage> entry : selectedStory.getPassages().entrySet()) {
      Passage passage = entry.getValue();
      for (Link link : passage.getLinks()) {
        if (link.getReference().equals(passageTitle)) {
          linksWithReference.add(link);
        }
      }
    }
    System.out.println(linksWithReference);
    return linksWithReference;
  }


  private void removePassage(Passage passage) {
    List<Link> linksToRemove = getLinksByReference(passage.getTitle());

    for (Link link : linksToRemove) {
      selectedStory.getPassages().values().forEach(p -> p.removeLink(link));
    }

    selectedStory.getPassages().values().remove(passage);
  }


  private void editLinkReference(Passage passage, String newName) {
    List<Link> linksToEdit = getLinksByReference(passage.getTitle());
    for (Link link : linksToEdit) {
      link.setReference(newName);
    }
  }

  private void clearSelectedItemInPassageList() {
    view.getPassageTableView().getSelectionModel().clearSelection();
  }

  public void clearSelectedItemInLinkList() {
    view.getLinkTableView().getSelectionModel().clearSelection();
  }

  public void clearSelectedItemInActionList() {
    view.getActionTableView().getSelectionModel().clearSelection();
  }

  private void setPassageContentTextInTextArea() {
    view.getPassageContentTextArea().setText(
        String.valueOf(getSelectedPassageInPassageList().getContent()));
  }
}
