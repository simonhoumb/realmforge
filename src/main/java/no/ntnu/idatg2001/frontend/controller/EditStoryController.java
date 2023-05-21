package no.ntnu.idatg2001.frontend.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import no.ntnu.idatg2001.backend.actions.Action;
import no.ntnu.idatg2001.backend.actions.ActionType;
import no.ntnu.idatg2001.backend.actions.GoldAction;
import no.ntnu.idatg2001.backend.actions.HealthAction;
import no.ntnu.idatg2001.backend.actions.InventoryAction;
import no.ntnu.idatg2001.backend.gameinformation.Link;
import no.ntnu.idatg2001.backend.gameinformation.Passage;
import no.ntnu.idatg2001.backend.gameinformation.Story;
import no.ntnu.idatg2001.backend.gameinformation.StoryWriter;
import no.ntnu.idatg2001.backend.utility.AlertHelper;
import no.ntnu.idatg2001.dao.StoryDAO;
import no.ntnu.idatg2001.frontend.view.CreateStoryView;
import no.ntnu.idatg2001.frontend.view.EditStoryView;
import no.ntnu.idatg2001.frontend.view.GuiElements.StoryMapCanvas;
import no.ntnu.idatg2001.frontend.view.dialogs.AddActionDialog;
import no.ntnu.idatg2001.frontend.view.dialogs.AddLinkDialog;
import no.ntnu.idatg2001.frontend.view.dialogs.AddPassageDialog;

public class EditStoryController extends Controller<EditStoryView> {

  private AddPassageDialog addPassageDialog;
  private AddLinkDialog addLinkDialog;
  private AddActionDialog addActionDialog;
  private Story selectedStory;
  private boolean isPassageBeingEdited = false;
  private String warning;

  public EditStoryController(EditStoryView view) {
    this.view = view;
    view.getResourceBundle().getString("warning");
    configurePassageList();
  }

  public EventHandler<ActionEvent> onBackButtonPressed() {
    CreateStoryView createStoryView = new CreateStoryView();
    Scene newScene = view.getScene();
    CreateStoryController createStoryController = new CreateStoryController(createStoryView);
    createStoryView.setController(createStoryController);
    newScene.setRoot(createStoryView);
    return null;
  }

  public void onAddPassageButtonPressed() {
    addPassageDialog = new AddPassageDialog(this);
    addPassageDialog.initOwner(view.getScene().getWindow());
    addPassageDialog.initStyle(StageStyle.UNDECORATED);
    addPassageDialog.showAndWait();
  }

  public void onAddPassageAddButtonPressed() {
    String passageTitle = addPassageDialog.getRoomNameTextField();

    if (isPassageBeingEdited) {
      Passage selectedPassage = getSelectedPassageInPassageList();
      String oldTitle = selectedPassage.getTitle();
      String newTitle = passageTitle;

      if (!oldTitle.equals(newTitle) && isPassageTitleExists(newTitle)) {
        AlertHelper.showWarningAlert(addPassageDialog.getDialogPane().getScene().getWindow(),
            warning,
            view.getResourceBundle().getString("passageAlreadyExists"));
        return;
      }

      selectedPassage.setTitle(newTitle);
      selectedPassage.setContent(new StringBuilder(addPassageDialog.getRoomContentTextArea()));

      if (!oldTitle.equals(newTitle)) {
        editLinkReference(oldTitle, newTitle);
      }

      StoryDAO.getInstance().update(selectedStory);
      populateTableView();
      isPassageBeingEdited = false;
    } else {
      if (isPassageTitleExists(passageTitle)) {
        AlertHelper.showWarningAlert(addPassageDialog.getDialogPane().getScene().getWindow(), warning,
            view.getResourceBundle().getString("passageAlreadyExists"));
      } else {
        Passage newPassage = new Passage(passageTitle, new StringBuilder(addPassageDialog.getRoomContentTextArea()));
        selectedStory.addPassage(newPassage);
        StoryDAO.getInstance().update(selectedStory);
        populateTableView();
      }
    }
  }

  public void setPassageBeingEdited(boolean isBeingEdited) {
    isPassageBeingEdited = isBeingEdited;
  }

  public void onAddLinkButtonPressed() {
    if (getSelectedPassageInPassageList() != null) {
      addLinkDialog = new AddLinkDialog(this);
      addLinkDialog.initOwner(view.getScene().getWindow());
      addLinkDialog.initStyle(StageStyle.UNDECORATED);
      configureAddLinkTableView();
      populateAddLinkPassageTableView();
      getSelectedPassageInPassageList();
      addLinkDialog.showAndWait();
    } else {
      AlertHelper.showWarningAlert(view.getScene().getWindow(), warning,
          view.getResourceBundle().getString("selectPassageToAddLink"));
    }
  }


  public void onAddActingButtonPressed() {
    if (getSelectedLinkInLinkList() != null && view.getLinkTableView() != null) {
    addActionDialog = new AddActionDialog(this);
    addActionDialog.initOwner(view.getScene().getWindow());
    addActionDialog.initStyle(StageStyle.UNDECORATED);
    addActionDialog.showAndWait();
    getSelectedLinkInLinkList();
    } else {
      AlertHelper.showWarningAlert(view.getScene().getWindow(), warning,
          view.getResourceBundle().getString("selectLinkToAddAction"));
    }
  }

  public void onAddLinkToPassageAddButton(ActionEvent event) {
    Passage selectedPassage = addLinkDialog.getPassageTableView().getSelectionModel().getSelectedItem();
    if (!isPassageBeingEdited) {
      handleAddLinkWhenNotEditing(selectedPassage, event);
    } else {
      handleAddLinkWhenEditing(selectedPassage, event);
    }
  }

  private void handleAddLinkWhenNotEditing(Passage selectedPassage, ActionEvent event) {
    if (selectedPassage != null && selectedPassage != getSelectedPassageInPassageList()) {
      boolean passageAlreadyLinked = isPassageAlreadyLinked(selectedPassage);

      if (!passageAlreadyLinked) {
        getSelectedPassageInPassageList().addLink(
            new Link(addLinkDialog.getLinkTextField().getText(),
                selectedPassage.getTitle()));
        StoryDAO.getInstance().update(selectedStory);
        populateLinkTableView();
        onCloseSource(event);
      } else {
        showPassageAlreadyLinkedWarning();
      }
    } else {
      showSelectPassageToAddLinkWarning();
    }
  }

  private void handleAddLinkWhenEditing(Passage selectedPassage, ActionEvent event) {
    if (selectedPassage != null && selectedPassage != getSelectedPassageInPassageList()) {
      boolean passageAlreadyLinked = isPassageAlreadyLinked(selectedPassage);

      if (!passageAlreadyLinked) {
        getSelectedLinkInLinkList().setText(addLinkDialog.getLinkTextField().getText());
        getSelectedLinkInLinkList().setReference(selectedPassage.getTitle());
        StoryDAO.getInstance().update(selectedStory);
        setPassageBeingEdited(false);
        populateLinkTableView();
        onCloseSource(event);
      } else {
        showPassageAlreadyLinkedWarning();
      }
    } else {
      showSelectPassageToAddLinkWarning();
    }
  }

  private boolean isPassageAlreadyLinked(Passage selectedPassage) {
    for (Link link : getSelectedPassageInPassageList().getLinks()) {
      if (link.getReference().equals(selectedPassage.getTitle())) {
        return true;
      }
    }
    return false;
  }

  private void showPassageAlreadyLinkedWarning() {
    AlertHelper.showWarningAlert(addLinkDialog.getDialogPane().getScene().getWindow(),
        warning,
        view.getResourceBundle().getString("passageAlreadyLinked"));
  }

  private void showSelectPassageToAddLinkWarning() {
    AlertHelper.showWarningAlert(addLinkDialog.getDialogPane().getScene().getWindow(),
        warning,
        view.getResourceBundle().getString("selectPassageToAddLink"));
  }

  public void onEditButtonIsPressed() {
    if (getSelectedPassageInPassageList() != null && getSelectedLinkInLinkList() == null
    && getSelectedActionInActionList() == null) {
      setPassageBeingEdited(true);
      addPassageDialog = new AddPassageDialog(this);
      addPassageDialog.initOwner(view.getScene().getWindow());
      addPassageDialog.initStyle(StageStyle.UNDECORATED);
      addPassageDialog.setRoomNameTextField(getSelectedPassageInPassageList().getTitle());
      addPassageDialog.setRoomContentTextArea(getSelectedPassageInPassageList().getContent().toString());
      addPassageDialog.showAndWait();
    } else if (getSelectedLinkInLinkList() != null && getSelectedActionInActionList() == null) {
      setPassageBeingEdited(true);
      addLinkDialog = new AddLinkDialog(this);
      addLinkDialog.initOwner(view.getScene().getWindow());
      addLinkDialog.initStyle(StageStyle.UNDECORATED);
      addLinkDialog.setLinkTextField(getSelectedLinkInLinkList().getText());
      configureAddLinkTableView();
      populateAddLinkPassageTableView();
      getSelectedPassageInPassageList();
      addLinkDialog.showAndWait();
    } else {
      AlertHelper.showWarningAlert(view.getScene().getWindow(), warning,
          view.getResourceBundle().getString("selectPassageOrLinkOrActionToEdit"));
    }
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

  public void onExportPress() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Export Story");
    fileChooser.setInitialFileName(selectedStory.getTitle() + ".Paths");
    fileChooser.getExtensionFilters().add(
        new FileChooser.ExtensionFilter("Paths Files (*.Paths)", "*.Paths"));
    File file = fileChooser.showSaveDialog(view.getScene().getWindow());
    if (file != null) {
      StoryWriter.writeStoryToFile(selectedStory, file.getAbsoluteFile());
    }
  }


  public void onMapPressed() {
    // Create a ScrollPane and set the canvas as its content
    ScrollPane scrollPane = new ScrollPane();
    // Create an instance of your StoryMapCanvas
    StoryMapCanvas storyMapCanvas = new StoryMapCanvas(100, 100, scrollPane);
    // Set the content of the ScrollPane to the canvas
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
    stage.setTitle("Story Map");
    stage.initOwner(view.getScene().getWindow());
    stage.initStyle(StageStyle.DECORATED);
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.show();
  }


  public void setSelectedStory(Story story) {
    this.selectedStory = story;
    configureTableView();
    populateTableView();
  }

  public void populateTableView() {
    if (selectedStory == null) {
      return; // Do nothing if selectedStory is null
    }
    view.getPassageTableView().getItems().clear(); // Clear the selection
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

  public void onDeletePassageButtonPressed() {
    Passage selectedPassage = getSelectedPassageInPassageList();
    Link selectedLink = getSelectedLinkInLinkList();
    Action selectedAction = getSelectedActionInActionList();
    String confirmationMessage = "";

    if (selectedPassage == null && selectedLink == null && selectedAction == null) {
      AlertHelper.showWarningAlert(view.getScene().getWindow(), warning,
          view.getResourceBundle().getString("nothingSelected"));
      return;
    }
    if (selectedPassage != null && selectedLink == null && selectedAction == null) {
      confirmationMessage = view.getResourceBundle().getString("areYouSurePassage") +
          "\n\n" + view.getResourceBundle().getString("title") + selectedPassage.getTitle();
      deletePassage(selectedPassage);
    } else if (selectedLink != null && selectedAction == null) {
      confirmationMessage = view.getResourceBundle().getString("areYouSureLink") +
          "\n\n" + view.getResourceBundle().getString("description") + selectedLink.getText();
      deleteLink(selectedPassage, selectedLink);
    } else {
      confirmationMessage = view.getResourceBundle().getString("areYouSureAction") +
          "\n\n" + view.getResourceBundle().getString("description") +
          selectedAction.getActionType();
      deleteAction(selectedLink, selectedAction);
    }

    boolean result = AlertHelper.showConfirmationAlert(view.getScene().getWindow(),
        view.getResourceBundle().getString("confirmation"), confirmationMessage);

    if (result) {
      StoryDAO.getInstance().update(selectedStory);
      populateTableView();
      clearSelectedItemInPassageList();
      view.getPassageContentTextArea().clear();
      view.getLinkTableView().getItems().clear();
      view.getActionTableView().getItems().clear();
    }
    // Do nothing if the result is false
  }

  private void deletePassage(Passage passage) {
    selectedStory.removePassageAndConnectedLinks(passage);
    getLinksByReference(getSelectedPassageInPassageList().getTitle());
    removePassage(getSelectedPassageInPassageList());
  }

  private void deleteLink(Passage passage, Link link) throws NullPointerException {
    try {
      passage.removeLink(link);
      populateLinkTableView();
      clearSelectedItemInLinkList();
      view.getActionTableView().getItems().clear();
    } catch (NullPointerException e) {
      AlertHelper.showWarningAlert(view.getScene().getWindow(),
          warning,
          view.getResourceBundle().getString("nothingSelected"));
    }
  }

  private void deleteAction(Link link, Action action) throws NullPointerException {
    try {
      link.removeAction(action);
      populateActionTableView();
      clearSelectedItemInActionList();
    } catch (NullPointerException e) {
      AlertHelper.showWarningAlert(view.getScene().getWindow(),
          warning,
          view.getResourceBundle().getString("nothingSelected"));
    }
  }


  private boolean isPassageTitleExists(String title) {
    for (Passage passage : selectedStory.getPassages().values()) {
      if (passage.getTitle().equals(title)) {
        return true;
      }
    }
    return false;
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
    return linksWithReference;
  }


  private void removePassage(Passage passage) {
    List<Link> linksToRemove = getLinksByReference(passage.getTitle());

    for (Link link : linksToRemove) {
      selectedStory.getPassages().values().forEach(p -> p.removeLink(link));
    }

    selectedStory.getPassages().values().remove(passage);
  }


  private void editLinkReference(String oldName, String newName) {
    List<Link> linksToEdit = getLinksByReference(oldName);
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
