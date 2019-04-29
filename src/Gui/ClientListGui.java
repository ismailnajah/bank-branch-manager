package Gui;

import Classes.Agence;
import Classes.Client;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


class ClientListGui {
    static boolean ChangeMade = false;
    Agence agence;
    private TableView<Client> table = new TableView();
    private ObservableList<Client> data;
    Stage window;
    private String SaveFileName;
    Label clientListLabel;
    menuBar header;
    private String windowTitle;


    ClientListGui(Stage primaryStage, Agence agence) {
        window = primaryStage;
        this.agence = agence;
        data  = FXCollections.observableArrayList(agence.lesClients);
        windowTitle = "Gestionnaire d'Agence Bancaire : ";
        SaveFileName = "Nouveau.sv";

        clientListLabel = new Label("Liste des Clients d'Agence ");
        clientListLabel.setFont(new Font(20));

        header = new menuBar(this);

        table.setEditable(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn matriculCol = createCol("Matricule",100);
        TableColumn firstNameCol = createCol("Prenom",150);
        TableColumn lastNameCol = createCol("Nom",150);
        TableColumn cinCol = createCol("CIN",200);


        table.setItems(data);
        table.getColumns().addAll(matriculCol,firstNameCol, lastNameCol, cinCol);
        table.setPlaceholder(new Label("Cree des Clients"));

        final Button addB = new Button("Ajouter");
        addB.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        addB.setOnAction(event -> new AddClientGui(ClientListGui.this));

        final Button removeB = new Button("Supprimer");
        removeB.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        removeB.setOnAction(event->{
            System.out.println("delete : " + getAgence().lesClients.size());

            if (getAgence().lesClients.size() > 0) {

                Client client = getSelectedClient();
                String message = "Vouz voulez supprimer le client "+client.getNom()+" "+client.getPrenom()+" ?";
                AlertGui alertGui = new AlertGui(message,"Supprimer un Compte");
                alertGui.yesB.setOnAction(e -> {
                    removeClient(client);
                    alertGui.close();
                });
            }
        });


        final BorderPane footer = new BorderPane();
        footer.setRight(addB);
        footer.setLeft(removeB);

        final VBox mainPanel = new VBox();
        mainPanel.setSpacing(5);
        mainPanel.setPadding(new Insets(10));
        mainPanel.getChildren().addAll(clientListLabel, table, footer);

        final VBox layout = new VBox();
        layout.getChildren().addAll(header, mainPanel);

        Scene scene = new Scene(layout);


        window.setScene(scene);
        window.setTitle(windowTitle + SaveFileName + "(non enregistrer)");
        window.show();
        header.editeAgence.fire();
        window.setOnCloseRequest(event->{
            Leave();
            event.consume();
        });
    }

    private TableColumn createCol(String colName, int width) {
        TableColumn col = new TableColumn(colName);
        col.setMinWidth(width);
        col.setCellValueFactory(new PropertyValueFactory<Client, String>(colName.toLowerCase()));
        col.setOnEditStart(event->{
            new EditeClientGui(ClientListGui.this);
        });
        return col;
    }

    void addClient(Client client) {
        agence.addClient(client);
        data.add(client);
        isSaved(false);
    }


    Client getSelectedClient() {
        return table.getSelectionModel().getSelectedItem();
    }

    private void removeClient(Client client) {
        table.getItems().remove(client);
        isSaved(false);
        getAgence().removeClient(client);
    }

    Agence getAgence() {
        return agence;
    }

    void setAgence(Agence agence) {
        this.agence = agence;
        data = FXCollections.observableArrayList(agence.lesClients);
        table.setItems(data);
    }

    void isSaved(boolean value) {
        String title;
        if (!value) {
            title = windowTitle + SaveFileName + "(non enregistrer)";
        } else {
            title = windowTitle + SaveFileName;
        }
        window.setTitle(title);
        ChangeMade = !value;
    }

    void Leave() {
        if (ChangeMade) {
            AlertGui alertGui = new AlertGui("Vous voulez enregistrer les modificaions?", "Enregistrer");
            alertGui.createCancelB();
            alertGui.yesB.setOnAction(e -> {
                header.save.fire();
                alertGui.close();
                Platform.exit();
            });
        } else {
            AlertGui alertGui = new AlertGui("Vous voulez quitter?", "Quitter");
            alertGui.yesB.setOnAction(e -> {
                alertGui.close();
                Platform.exit();
            });
        }
    }

    void updateTitle(String name) {
        window.setTitle(windowTitle + name);
        SaveFileName = name;
    }

    void newAgence() {
        setAgence(new Agence());
        SaveFileName = "Nouveau.sv";
        clientListLabel.setText("Liste des Clients d'Agence ");
        updateTitle(SaveFileName);
    }

    void editAgence() {
        new EditAgenceGui(this);
    }

    void updateTable() {
        data.clear();
        data.addAll(agence.lesClients);
    }
}
