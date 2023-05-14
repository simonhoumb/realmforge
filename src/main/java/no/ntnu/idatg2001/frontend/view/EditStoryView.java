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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import no.ntnu.idatg2001.backend.actions.Action;
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
  private TableColumn<Link, String> linkTableLinkNameColumn;
  private TableColumn<Link, String> linkTableLinkReferenceColumn;
  private TableView<Action> actionTableView;
  private TableColumn<Action, String> actionTableColumn;
  private TableColumn<Action, String> actionTableActionColumn;
  private ButtonBar buttonBar;
  private JFXTextArea passageContentTextArea;



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
    createActionTableView();
    createPassageContentTextArea();
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

    linkBox.getChildren().addAll(linkTableView, actionTableView);
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
      controller.onAddActingButtonPressed();
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
    passageTableView.getColumns().add(passageTableColumn);
    setLeft(passageTableView);
  }

  private void createPassageTableColumn() {
    passageTableColumn = new TableColumn<>("Passage Name");
    passageTableColumn.setPrefWidth(400);
    passageTableColumn.setReorderable(false);
  }

  private void createLinkTableView() {
    createLinkTableLinkNameColumn();
    createLinkTableLinkReferenceColumn();
    linkTableView = new TableView<>();
    linkTableView.setPrefWidth(400);
    linkTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    linkTableView.getColumns().addAll(linkTableLinkNameColumn, linkTableLinkReferenceColumn);
  }

  private void createLinkTableLinkNameColumn() {
    linkTableLinkNameColumn = new TableColumn<>("Link Name");
    linkTableLinkNameColumn.setReorderable(false);
  }

  private void createLinkTableLinkReferenceColumn() {
    linkTableLinkReferenceColumn = new TableColumn<>("Link Reference");
    linkTableLinkReferenceColumn.setReorderable(false);
  }

  private void createActionTableView() {
    createActionTableColum();
    createActionTableActionColumn();
    actionTableView = new TableView<>();
    actionTableView.setPrefWidth(400);
    actionTableView.setEditable(false);
    actionTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    actionTableView.getColumns().addAll(actionTableColumn, actionTableActionColumn);
  }

  private void createActionTableColum() {
    actionTableColumn = new TableColumn<>("Action Name");
    actionTableColumn.setReorderable(false);

  }

  private void createActionTableActionColumn() {
    actionTableActionColumn = new TableColumn<>("Action");
    actionTableActionColumn.setReorderable(false);
  }

  private void createPassageContentTextArea() {
    passageContentTextArea = new JFXTextArea();
    passageContentTextArea.setPrefHeight(380);
    passageContentTextArea.setEditable(false);
    passageContentTextArea.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    passageContentTextArea.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
  }

  public TableView<Passage> getPassageTableView() {
    return passageTableView;
  }

  public TableView<Link> getLinkTableView() {
    return linkTableView;
  }

  public TableView<Action> getActionTableView() {
    return actionTableView;
  }

  public JFXTextArea getPassageContentTextArea() {
    return passageContentTextArea;
  }

  public void setController(EditStoryController controller) {
    this.controller = controller;
  }

  public TableColumn<Passage, String> getPassageTableColumn() {
    return passageTableColumn;
  }

  public TableColumn<Link, String> getLinkTableLinkNameColumn() {
    return linkTableLinkNameColumn;
  }

  public TableColumn<Link, String> getLinkTableLinkReferenceColumn() {
    return linkTableLinkReferenceColumn;
  }

  public TableColumn<Action, String> getActionTableColumn() {
    return actionTableColumn;
  }

  public TableColumn<Action, String> getActionTableActionColumn() {
    return actionTableActionColumn;
  }
}

