package view;

import javafx.beans.binding.Bindings;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Column;
import mvvm.BoardViewModel;

import java.util.Optional;

public class BoardView extends VBox {
    private final EditableLabel elTitle = new EditableLabel();
    private final ListView<Column> lvColumns = new ListView<>();
    private final BoardViewModel boardViewModel;


    public BoardView(BoardViewModel boardViewModel) {
        this.boardViewModel = boardViewModel;
        build();
    }

    public void build() {
        buildGraphicComponents();
        configBindings();
        configActions();
    }

    public void buildGraphicComponents() {
        makeComponentsHierarchy();
        configStyles();
        configColumnFactory();
    }

    //  CONFIG GRAPHIC COMPONENTS

    public void makeComponentsHierarchy() {
        getChildren().addAll(elTitle, lvColumns);
    }

    public void configStyles() {
        configStyleVBox();
        configStyleEditableLabel();
        configStyleListView();
    }

    //  VBOX

    private void configStyleVBox() {
        VBox.setVgrow(lvColumns, Priority.ALWAYS);
    }

    //  EDITABLE LABEL

    //TODO: refactoriser tous les éditables labels
    private void configStyleEditableLabel() {
        elTitle.tf.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        elTitle.tf.setAlignment(Pos.CENTER_LEFT);
        elTitle.setPrefHeight(50);
        elTitle.tf.setPrefHeight(49);
        elTitle.tf.setStyle("-fx-background-color: #fcfcfc");
    }
    
    //   CONFIG LISTVIEW

    private void configStyleListView() {
        lvColumns.setOrientation(Orientation.HORIZONTAL);
        lvColumns.setStyle("-fx-padding: 0; -fx-background-color: #ffffff;");
        lvColumns.setMaxHeight(Double.MAX_VALUE);
    }

    private void configColumnFactory() {
        lvColumns.setCellFactory(columnView -> new ListCell<>(){
            @Override
            protected void updateItem(Column column, boolean empty) {
                super.updateItem(column, empty);
                ColumnView columnView = null;
                if (column != null) {
                    columnView = new ColumnView(column);
                }
                setStyle("-fx-background-color: #ffffff; -fx-padding: 6");
                setGraphic(columnView);
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //  CONFIG BINDINGS & LISTENERS

    private void configBindings() {
        elTitle.tf.textProperty().bindBidirectional(boardViewModel.boardTitleProperty());
        lvColumns.itemsProperty().bindBidirectional(boardViewModel.columnsListProperty());
        boardViewModel.bindEditAborted(elTitle.editAbortedProperty());
        boardViewModel.bindFfocusedTitle(elTitle.tfFocusedProperty());

        TrelloViewModel.getInstance().bindSelectedColumn(lvColumns.getSelectionModel().selectedItemProperty());
    }

    // CONFIG MOUSE EVENT

    private void configActions() {
        configMouseEvents();
    }

    private void configMouseEvents() {
        lvColumns.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                boardViewModel.addColumn();
            }
        });
    }
}