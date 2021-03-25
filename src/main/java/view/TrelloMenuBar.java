package view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import mvvm.TrelloViewModel;

public class TrelloMenuBar extends MenuBar {
    private final TrelloViewModel trelloViewModel;

    public TrelloMenuBar(TrelloViewModel trelloViewModel){
        super();
        this.trelloViewModel = trelloViewModel;
        buildMenu();
    }

    public void buildMenu(){
        buildMenuFichier();
        buildMenuEdition();
    }

    private void buildMenuFichier() {
        Menu fileMenu = new Menu("Fichier");

        // --- new column ---

        var createColumn = new MenuItem("Nouvelle Colonne");
        createColumn.setOnAction(e -> trelloViewModel.commandCreateColumn());
        createColumn.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));

        // --- new card ---

        var createCard = new MenuItem("Nouvelle Carte");
        createCard.setOnAction(e -> trelloViewModel.commandCreateCard());
        createCard.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
        createCard.disableProperty().bind(trelloViewModel.noColumnSelectedProperty());

        // --- quit ---

        var quit = new MenuItem("Quitter");
        quit.setOnAction(e -> trelloViewModel.quit());
        quit.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));

        // --- ajout au menu ---

        fileMenu.getItems().addAll(createColumn, createCard, quit);
        getMenus().add(fileMenu);
    }

    private void buildMenuEdition() {
        Menu editionMenu = new Menu("Edition");

        // --- undo ---

        var undo = new MenuItem("Annuler");
        undo.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        undo.textProperty().bind(trelloViewModel.nextUndoableProperty());
        undo.disableProperty().bind(trelloViewModel.hasNoUndoableProperty());
        undo.setOnAction(e -> trelloViewModel.undo());

        // --- redo ---

        var redo = new MenuItem("Refaire");
        redo.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));
        redo.textProperty().bind(trelloViewModel.nextRedoableProperty());
        redo.disableProperty().bind(trelloViewModel.hasNoRedoableProperty());
        redo.setOnAction(e -> trelloViewModel.redo());

        // --- ajout au menu ---

        editionMenu.getItems().addAll(undo, redo);
        getMenus().add(editionMenu);
    }
}
