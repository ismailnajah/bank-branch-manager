package Gui;

import Classes.Agence;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;

public class menuBar extends VBox {
    Menu fichierMenu;
    Menu optionMenu;
    MenuBar menubar;
    MenuItem nouveau;
    MenuItem openSave;
    MenuItem save;
    MenuItem saveAs;
    MenuItem exit;
    MenuItem editeAgence;
    MenuItem about;

    ClientListGui gui;

    public menuBar(ClientListGui gui) {
        super();
        this.gui = gui;
        // create a fichierMenu
        fichierMenu = new Menu("Fichier");
        optionMenu = new Menu("Option");
        // create a menubar
        menubar = new MenuBar();

        // create menuitems
        nouveau = new MenuItem("Nouveau");
        openSave = new MenuItem("Ouvrir...");
        save = new MenuItem("Enregistrer");
        saveAs = new MenuItem("Enregistrer sous...");
        exit = new MenuItem("Quiter");
        editeAgence = new MenuItem("Modifier");
        about = new MenuItem("À propos");

        // add fichierMenu items to fichierMenu
        fichierMenu.getItems().add(nouveau);
        fichierMenu.getItems().add(openSave);
        fichierMenu.getItems().add(save);
        fichierMenu.getItems().add(saveAs);
        fichierMenu.getItems().add(exit);

        optionMenu.getItems().add(editeAgence);
        optionMenu.getItems().add(about);

        // add fichierMenu to menubar
        menubar.getMenus().addAll(fichierMenu, optionMenu);
        this.getChildren().add(menubar);


        nouveau.setOnAction(event -> {
            if (ClientListGui.ChangeMade) {
                AlertGui alertGui = new AlertGui("Vous voulez enregistrer les modificaions?", "Enregistrer");
                alertGui.createCancelB();
                alertGui.yesB.setOnAction(e -> {
                    save.fire();
                    if (!ClientListGui.ChangeMade) {
                        gui.newAgence();
                    }
                    alertGui.close();
                });
                alertGui.noB.setOnAction(e -> {
                    gui.newAgence();
                    alertGui.close();
                });
            } else {
                gui.newAgence();
            }
        });

        openSave.setOnAction(event -> {
            if (ClientListGui.ChangeMade) {
                AlertGui alertGui = new AlertGui("Vous voulez enregistrer les modificaions?", "Enregistrer");
                alertGui.createCancelB();
                alertGui.yesB.setOnAction(e -> {
                    save.fire();
                    alertGui.close();
                    fileChooserOpen();
                });
                alertGui.noB.setOnAction(e -> {
                    alertGui.close();
                    fileChooserOpen();
                });
            } else {
                fileChooserOpen();
            }
        });

        save.setOnAction(event -> {

            String path = gui.agence.filePath;
            if (path.isEmpty()) {
                gui.isSaved(fileChooserSave());
            } else {
                gui.getAgence().save(path);
                gui.isSaved(true);
            }
        });

        saveAs.setOnAction(event -> {
            gui.isSaved(fileChooserSave());
        });

        exit.setOnAction(event -> gui.Leave());

        editeAgence.setOnAction(event -> gui.editAgence());

    }

    public boolean fileChooserSave() {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter for Save files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Save files (*.sv)", "*.sv");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialFileName("Nouveau");

        //Show save file dialog
        File file = fileChooser.showSaveDialog(gui.window);

        if (file != null) {
            gui.getAgence().save(file.getAbsolutePath());
            gui.updateTitle(file.getName());
            return true;
        }
        return false;
    }

    public void fileChooserOpen() {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter for Save files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Open files (*.sv)", "*.sv");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showOpenDialog(gui.window);

        if (file != null) {
            gui.setAgence(Agence.loadAgence(file.getAbsolutePath()));
            gui.updateTitle(file.getName());
            gui.clientListLabel.setText("Liste des Clients d'Agence " + gui.agence.getNom());
        }
    }
}
