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


class EditAgenceGui {
    private textField nameTF = new textField();
    private NumberField codeTF = new NumberField();
    private Stage window = new Stage();
    private NumberField tauxOperationNF = new NumberField();

    EditAgenceGui(ClientListGui gui) {
        Label codeLabele = new Label("Code d'Agence");
        Label nameLabel = new Label("Nom d'Agence");
        Label tauxOperationtLB = new Label("Taux d'Operation(Compte Payant)");

        Label nombreClient = new Label("Nombre des clients " + gui.agence.lesClients.size());
        nombreClient.setStyle("-fx-font-weight:bold;-fx-font-size:14");
        GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(20);

        nameTF.setText(gui.getAgence().getNom());
        codeTF.setText("" + gui.getAgence().getCode());
        tauxOperationNF.setText("" + ComptePayant.tauxOperation);
        tauxOperationNF.setMaxWidth(50);
        tauxOperationNF.setAlignment(Pos.CENTER);


        form.add(nameLabel, 0, 0);
        form.add(nameTF, 1, 0);
        form.add(codeLabele, 0, 1);
        form.add(codeTF, 1, 1);
        form.add(tauxOperationtLB, 0, 2);
        form.add(tauxOperationNF, 1, 2);
        form.add(nombreClient, 0, 3);

        Button enregistrer = new Button("Enregistrer");
        enregistrer.setOnAction(event -> {
            if (checkFields()) {
                gui.agence.setNom(nameTF.getText());
                gui.agence.setCode(codeTF.getText());
                if (!tauxOperationNF.isEmpty())
                    ComptePayant.tauxOperation = Float.parseFloat(tauxOperationNF.getText());
                gui.clientListLabel.setText("Liste des Clients d'Agence " + nameTF.getText());
                gui.header.save.fire();
                window.close();
            }
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

    private boolean checkFields() {
        //test all text fields and show red boarder if there is an error!
        boolean test = true;
        if (nameTF.isEmpty())
            test = false;
        if (tauxOperationNF.isEmpty())
            test = false;

        if (codeTF.isEmpty())
            test = false;

        return test;
    }
}
