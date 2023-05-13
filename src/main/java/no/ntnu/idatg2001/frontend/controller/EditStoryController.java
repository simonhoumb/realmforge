package no.ntnu.idatg2001.frontend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import no.ntnu.idatg2001.backend.actions.Action;
import no.ntnu.idatg2001.backend.actions.ActionType;
import no.ntnu.idatg2001.backend.actions.GoldAction;
import no.ntnu.idatg2001.backend.actions.HealthAction;
import no.ntnu.idatg2001.backend.actions.InventoryAction;
import no.ntnu.idatg2001.backend.gameinformation.Link;
import no.ntnu.idatg2001.backend.gameinformation.Passage;
import no.ntnu.idatg2001.backend.gameinformation.Story;
import no.ntnu.idatg2001.dao.StoryDAO;
import no.ntnu.idatg2001.frontend.view.AddActionDialog;
import no.ntnu.idatg2001.frontend.view.AddLinkDialog;
import no.ntnu.idatg2001.frontend.view.AddPassageDialog;
import no.ntnu.idatg2001.frontend.view.CreateStoryView;
import no.ntnu.idatg2001.frontend.view.EditStoryView;

public class EditStoryController {

  private EditStoryView editStoryView;
  private AddPassageDialog addPassageDialog;
  private AddLinkDialog addLinkDialog;
  private AddActionDialog addActionDialog;
  private Story selectedStory;

  public EditStoryController(EditStoryView editStoryView) {
    this.editStoryView = editStoryView;
    configurePassageList();
    configureLinkTableView();
  }

  public void onBackButtonPressed() {
    CreateStoryView createStoryView = new CreateStoryView();
    Scene newScene = editStoryView.getScene();
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
    addPassageDialog.initOwner(editStoryView.getScene().getWindow());
    addPassageDialog.showAndWait();
  }

  public void onAddPassageAddButtonPressed() {
    selectedStory.addPassage(new Passage(addPassageDialog.getRoomNameTextField().getText(),
        addPassageDialog.getRoomContentTextArea().getText()));
    StoryDAO.getInstance().update(selectedStory);
  }

  public void onAddLinkButtonPressed() {
    addLinkDialog = new AddLinkDialog(this);
    addLinkDialog.initOwner(editStoryView.getScene().getWindow());
    addLinkDialog.showAndWait();
    getSelectedPassageInPassageList();
  }


  public void onAddActingButtonPressed() {
    addActionDialog = new AddActionDialog(this);
    addActionDialog.initOwner(editStoryView.getScene().getWindow());
    addActionDialog.showAndWait();
    getSelectedLinkInLinkList();
  }

  public void onAddLinkToPassageAddButton() {
    getSelectedPassageInPassageList().addLink(new Link(addLinkDialog.getLinkTextField().getText(),
        addLinkDialog.getReferenceTextField().getText()));
      StoryDAO.getInstance().update(selectedStory);
  }

  public void onAddActionOnAddButtonPressed() {

    System.out.println((getSelectedLinkInLinkList().addAction(createActionInstance(addActionDialog.getSelectedActionType(),
        addActionDialog.getSelectedValue()))));
    StoryDAO.getInstance().update(selectedStory);
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


  public void setSelectedStory(Story story) {
    this.selectedStory = story;
    configureTableView();
    populateTableView();
  }

  private void populateTableView() {
    editStoryView.getPassageTableView().getItems().clear();
    List<Passage> passageList = selectedStory.getPassages().values().stream().toList();
    ObservableList<Passage> list = FXCollections.observableArrayList(passageList);
    if (selectedStory.getOpeningPassage() != null) {
      list.add(selectedStory.getOpeningPassage());
    }
    editStoryView.getPassageTableView().setItems(list);
  }

  private void populateLinkTableView() {
    editStoryView.getLinkTableView().getItems().clear();
    List<Link> linkList = getSelectedPassageInPassageList().getLinks().stream().toList();
    ObservableList<Link> list = FXCollections.observableArrayList(linkList);
    editStoryView.getLinkTableView().setItems(list);
  }

  private void populateActionTableView() {
    editStoryView.getActionTableView().getItems().clear();
    if (getSelectedLinkInLinkList() != null) {
      List<Action> actionList = getSelectedLinkInLinkList().getActions().stream().toList();
      ObservableList<Action> list = FXCollections.observableArrayList(actionList);
      editStoryView.getActionTableView().setItems(list);
    }
  }

  public void configureTableView() {
    editStoryView.getPassageTableColumn().setCellValueFactory(new PropertyValueFactory<>("title"));
    editStoryView.getLinkTableLinkNameColumn().setCellValueFactory(new PropertyValueFactory<>("text"));
    editStoryView.getLinkTableLinkReferenceColumn().setCellValueFactory(new PropertyValueFactory<>("reference"));
    editStoryView.getActionTableColumn().setCellValueFactory(new PropertyValueFactory<>("actionType"));
    editStoryView.getActionTableActionColumn().setCellValueFactory(new PropertyValueFactory<>("value"));
  }



  private Passage getSelectedPassageInPassageList() {
    return editStoryView.getPassageTableView().getSelectionModel().getSelectedItem();
  }

  private Link getSelectedLinkInLinkList() {
    return editStoryView.getLinkTableView().getSelectionModel().getSelectedItem();
  }

  private void configurePassageList() {
    editStoryView.getPassageTableView().getSelectionModel().selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        setPassageContentTextInTextArea();
        populateLinkTableView();
        populateActionTableView();
      }
    });
  }

  private void configureLinkTableView() {
    editStoryView.getLinkTableView().getSelectionModel().selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        populateActionTableView();
      }
    });
  }



  private void setPassageContentTextInTextArea() {
    editStoryView.getPassageContentTextArea().setText(getSelectedPassageInPassageList().getContent());
  }

}
