package no.ntnu.idatg2001.frontend.view;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.idatg2001.backend.gameinformation.Link;
import no.ntnu.idatg2001.backend.gameinformation.Passage;
import no.ntnu.idatg2001.frontend.controller.EditStoryController;

public class EditStoryView extends BorderPane {

  private EditStoryController controller;
  private String cssFile = "/CSS/EditStoryView.css";

  private JFXButton addPassageButton;
  private JFXButton addLinkButton;
  private JFXButton addActionButton;
  private JFXButton editButton;
  private JFXButton deleteButton;
  private JFXButton mapButton;
  private JFXButton saveButton;
  private JFXButton backButton;
  private TableView<Passage> passageTableView;
  private TableColumn<Passage, String> passageTableColumn;
  private TableView<Link> linkTableView;
  private TableColumn<Link, String> linkTableColumn;
  private ListView<?> actionList;
  private ButtonBar buttonBar;
  private JFXTextArea passageContentTextArea;
  private JFXTextArea linkContentTextArea;


  public EditStoryView() {
    init();
  }

  public void init() {
    createNewPassageButton();
    createNewLinkButton();
    createNewActionButton();
    createEditButton();
    createDeleteButton();
    createMapButton();
    createSaveButton();
    createBackButton();
    createPassageTableView();
    createButtonBar();
    createLinkTableView();
    createActionList();
    createPassageContentTextArea();
    createLinkContentTextArea();
    setPadding(new Insets(10));
    getStylesheets().add(cssFile);
    HBox buttonBox = new HBox();
    buttonBox.getChildren().add(buttonBar);
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.setPadding(new Insets(5,0,10,0));
    setTop(buttonBox);
    HBox centerHBox = new HBox();
    centerHBox.setPadding(new Insets(20));

    VBox contentBox = new VBox();

    HBox linkBox = new HBox();

    linkBox.getChildren().addAll(linkTableView, actionList, linkContentTextArea);
    contentBox.getChildren().addAll(passageContentTextArea, linkBox);
    centerHBox.getChildren().add(contentBox);

    setCenter(centerHBox);
  }

  private void createNewPassageButton() {
    addPassageButton = new JFXButton("Add passage");
    addPassageButton.setOnAction(event -> {
      controller.onAddPassageButtonPressed();
    });
  }

  private void  createNewLinkButton() {
    addLinkButton = new JFXButton("Add link");
    addLinkButton.setOnAction(event -> {
      controller.onAddLinkButtonPressed();
    });
  }
  private void createNewActionButton() {
    addActionButton = new JFXButton("Add action");
    addActionButton.setOnAction(event -> {
      //controller.addAction();
    });
  }

  private void createEditButton() {
    editButton = new JFXButton("Edit");
    editButton.setOnAction(event -> {
      //controller.edit();
    });
  }

  private void createDeleteButton() {
    deleteButton = new JFXButton("Delete");
    deleteButton.setOnAction(event -> {
      //controller.delete();
    });
  }

  private void createMapButton() {
    mapButton = new JFXButton("Map");
    mapButton.setOnAction(event -> {
      //controller.map();
    });
  }

  private void createSaveButton() {
    saveButton = new JFXButton("Save");
    saveButton.setOnAction(event -> {
      //controller.save();
    });
  }

  private void createBackButton() {
    backButton = new JFXButton("Back");
    backButton.setOnAction(event1 -> {
      controller.onBackButtonPressed();
    });
  }

  private void createButtonBar() {
    buttonBar = new ButtonBar();
    buttonBar.setPrefHeight(40.0);
    buttonBar.getButtons().addAll(addPassageButton, addLinkButton, addActionButton,
        editButton,deleteButton, mapButton, saveButton, backButton);
  }


  private void createPassageTableView() {
    createPassageTableColumn();
    passageTableView = new TableView<>();
    passageTableView.setPrefWidth(400);
    passageTableView.setPadding(new Insets(10, 0, 10, 10));
    passageTableView.getColumns().add(passageTableColumn);
    setLeft(passageTableView);
  }

  private void createPassageTableColumn() {
    passageTableColumn = new TableColumn<>("Passage Name");
    passageTableColumn.setPrefWidth(400);
  }

  private void createLinkTableView() {
    createLinkTableColumn();
    linkTableView = new TableView<>();
    linkTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    linkTableView.getColumns().add(linkTableColumn);
  }

  private void createLinkTableColumn() {
    linkTableColumn = new TableColumn<>("Link Name");
  }

  private void createActionList() {
    actionList = new ListView<>();
  }

  private void createPassageContentTextArea() {
    passageContentTextArea = new JFXTextArea();
    passageContentTextArea.setPrefHeight(380);
  }

  private void createLinkContentTextArea() {
    linkContentTextArea = new JFXTextArea();
  }

  public TableView<Passage> getPassageTableView() {
    return passageTableView;
  }

  public TableView<Link> getLinkTableView() {
    return linkTableView;
  }

  public ListView<?> getActionList() {
    return actionList;
  }

  public JFXTextArea getPassageContentTextArea() {
    return passageContentTextArea;
  }

  public JFXTextArea getLinkContentTextArea() {
    return linkContentTextArea;
  }

  public void setController(EditStoryController controller) {
    this.controller = controller;
  }

  public TableColumn<Passage, String> getPassageTableColumn() {
    return passageTableColumn;
  }

  public TableColumn<Link, String> getLinkTableColumn() {
    return linkTableColumn;
  }
}

