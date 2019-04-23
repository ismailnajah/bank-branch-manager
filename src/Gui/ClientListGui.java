package Gui;

import Classes.Agence;
import Classes.Client;
import javafx.application.Application;
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
import javafx.stage.Stage;

import java.util.ArrayList;


public class ClientListGui extends Application {
    private Agence agence;
    private TableView<Client> table = new TableView();
    private ObservableList<Client> data;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        ArrayList<String> savesNames = Agence.getStoredAgences("Agences");
        if(savesNames.size()>0){
            agence = Agence.loadAgence(savesNames.get(0));
            Client.nbClient = agence.lesClients.size();
        }else{
            agence = new Agence(100);
            agence.setNom("BMCE");
        }
        data  = FXCollections.observableArrayList(agence.lesClients);
    }

    @Override
    public void start(Stage window) throws Exception {

        final Label clientListLabel = new Label("Liste des Clients");
        clientListLabel.setFont(new Font(20));

        table.setEditable(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn matriculCol = createCol("Matricule",100);
        TableColumn firstNameCol = createCol("Prenom",150);
        TableColumn lastNameCol = createCol("Nom",150);
        TableColumn cinCol = createCol("CIN",200);


        table.setItems(data);
        table.getColumns().addAll(matriculCol,firstNameCol, lastNameCol, cinCol);


        final Button addB = new Button("Ajouter");
        addB.setOnAction(event->{
            new AddClientGui(ClientListGui.this);
        });

        final Button removeB = new Button("Supprimer");
        removeB.setOnAction(event->{
            if(agence.lesClients.size()>0){
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

        final VBox layout = new VBox();
        layout.setSpacing(5);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(clientListLabel, table,footer);

        Scene scene = new Scene(layout);


        window.setScene(scene);
        window.setTitle("Gestionnaire d'Agence Bancaire");
        window.show();
        window.setOnCloseRequest(event->{
            AlertGui alertGui = new AlertGui("Vous voulez sauvgarder?","Savgarder les Changements");
            alertGui.createCancelB();
            alertGui.yesB.setOnAction(e -> {
                agence.save();
                alertGui.close();
                Platform.exit();
            });
            event.consume();
        });
    }

    public TableColumn createCol(String colName,int width){
        TableColumn col = new TableColumn(colName);
        col.setMinWidth(width);
        col.setCellValueFactory(new PropertyValueFactory<Client, String>(colName.toLowerCase()));
        col.setOnEditStart(event->{
            Client client = table.getSelectionModel().getSelectedItem();
            System.out.println(client.toString());
        });
        return col;
    }

    public void addClient(Client client) {
        agence.addClient(client);
        data.add(client);
    }


    public Client getSelectedClient() {
        return table.getSelectionModel().getSelectedItem();
    }

    public void removeClient(Client client) {
        table.getItems().remove(client);
        agence.removeClient(client);
    }
}
