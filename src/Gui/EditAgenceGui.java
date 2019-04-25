package Gui;

import Classes.ComptePayant;
import CustomFx.NumberField;
import CustomFx.textField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class EditAgenceGui {
    Label nameLabel = new Label("Nom d'Agence");
    Label codeLabele = new Label("Code d'Agence");
    Label nombreClient;
    textField nameTF = new textField();
    NumberField codeTF = new NumberField();
    Stage window = new Stage();
    NumberField tauxOperationNF = new NumberField();
    Label tauxOperationtLB = new Label("Taux d'Operation(Compte Payant)");

    public EditAgenceGui(ClientListGui gui) {
        nombreClient = new Label("Nombre des clients " + gui.agence.lesClients.size());
        nombreClient.setStyle("-fx-font-weight:bold;-fx-font-size:14");
        GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(20);

        nameTF.setText(gui.getAgence().getNom());
        codeTF.setText("" + gui.getAgence().getCode());
        tauxOperationNF.setText("" + ComptePayant.tauxOperation);
        tauxOperationNF.setMaxWidth(50);
        tauxOperationNF.setAlignment(Pos.CENTER);

        GridPane.setConstraints(nameLabel, 0, 0);
        GridPane.setConstraints(nameTF, 1, 0);
        GridPane.setConstraints(codeLabele, 0, 1);
        GridPane.setConstraints(codeTF, 1, 1);
        GridPane.setConstraints(tauxOperationtLB, 0, 2);
        GridPane.setConstraints(tauxOperationNF, 1, 2);
        GridPane.setConstraints(nombreClient, 0, 3);

        form.getChildren().addAll(nameLabel, nameTF, codeLabele, codeTF, tauxOperationtLB, tauxOperationNF, nombreClient);

        Button enregistrer = new Button("Enregistrer");
        enregistrer.setOnAction(event -> {
            gui.agence.setNom(nameTF.getText());
            gui.agence.setCode(codeTF.getText());
            if (!tauxOperationNF.isEmpty())
                ComptePayant.tauxOperation = Float.parseFloat(tauxOperationNF.getText());
            gui.clientListLabel.setText("Liste des Clients d'Agence " + nameTF.getText());
            gui.header.save.fire();
            window.close();
        });


        HBox footer = new HBox();
        footer.setAlignment(Pos.CENTER);
        footer.getChildren().add(enregistrer);

        VBox sceneLayout = new VBox();
        sceneLayout.setSpacing(20);
        sceneLayout.setPadding(new Insets(20));
        sceneLayout.setAlignment(Pos.CENTER);
        sceneLayout.getChildren().addAll(form, footer);


        Scene scene = new Scene(sceneLayout);
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(scene);
        window.setTitle("Parametres Agence");
        window.setResizable(false);
        window.show();
    }
}
