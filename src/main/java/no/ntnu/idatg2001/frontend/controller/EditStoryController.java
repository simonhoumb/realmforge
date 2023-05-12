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
import no.ntnu.idatg2001.backend.gameinformation.Link;
import no.ntnu.idatg2001.backend.gameinformation.Passage;
import no.ntnu.idatg2001.backend.gameinformation.Story;
import no.ntnu.idatg2001.dao.StoryDAO;
import no.ntnu.idatg2001.frontend.view.AddLinkDialog;
import no.ntnu.idatg2001.frontend.view.AddPassageDialog;
import no.ntnu.idatg2001.frontend.view.CreateStoryView;
import no.ntnu.idatg2001.frontend.view.EditStoryView;

public class EditStoryController {

  private EditStoryView editStoryView;
  private AddPassageDialog addPassageDialog;
  private AddLinkDialog addLinkDialog;
  private Story selectedStory;

  public EditStoryController(EditStoryView editStoryView) {
    this.editStoryView = editStoryView;
    configurePassageList();
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


  private void onAddActingButtonPressed() {

  }

  public void onAddLinkToPassageAddButton() {
    getSelectedPassageInPassageList().addLink(new Link(addLinkDialog.getLinkTextField().getText(),
        addLinkDialog.getReferenceTextField().getText()));
      StoryDAO.getInstance().update(selectedStory);
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

  public void configureTableView() {
    editStoryView.getPassageTableColumn().setCellValueFactory(new PropertyValueFactory<>("title"));
    editStoryView.getLinkTableColumn().setCellValueFactory(new PropertyValueFactory<>("text"));
  }



  private Passage getSelectedPassageInPassageList() {
    return editStoryView.getPassageTableView().getSelectionModel().getSelectedItem();
  }



  private void configurePassageList() {
    editStoryView.getPassageTableView().getSelectionModel().selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        setPassageContentTextInTextArea();
        populateLinkTableView();
      }
    });
  }



  private void setPassageContentTextInTextArea() {
    editStoryView.getPassageContentTextArea().setText(getSelectedPassageInPassageList().getContent());
  }

}
