package Gui;

import Classes.Client;
import Classes.ComptePayant;
import CustomFx.NumberField;
import CustomFx.textField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EditeClientGui {
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    ClientListGui gui;


    Label lnameLabel = new Label("Nom");
    Label fnameLabel = new Label("Prenom");
    Label cinLabel = new Label("CIN");
    Label typeLabel = new Label("Type de compte");
    Label dateLabel = new Label("Date de naissance");
    Label soldLabel = new Label("Solde");
    Label type;
    Label preview = new Label();
    NumberField verserNF = new NumberField();
    NumberField retirerNF = new NumberField();
    Button verserB = new Button("Verser");
    Button retirerB = new Button("Retirer");
    DatePicker s_datePK = new DatePicker();
    textField lname = new textField();
    textField fname = new textField();
    textField cin = new textField();

    Label tauxInteretLB = new Label("Taux d'Interet");
    Label decouvertLB = new Label("Decouvert");


    NumberField tauxInteretNF;
    NumberField decouvertNF;

    Label sold = new Label();

    Stage window;
    Client client;
    String typeCompte;
    public EditeClientGui(ClientListGui gui) {
        window = new Stage();
        this.gui = gui;
        client = gui.getSelectedClient();
        typeCompte = client.getCompte().getType();
        s_datePK.setPromptText("dd-mm-yyyy");
        s_datePK.setOnAction(event -> s_datePK.setStyle(null));

        //fill fields
        lname.setText(client.getNom());
        fname.setText(client.getPrenom());
        cin.setText(client.getCin());
        sold.setText(client.getCompte().getSolde() + " Dhs");

        s_datePK.setValue(client.getDate_naissance().toLocalDate());
        s_datePK.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate object) {
                if (object != null)
                    return formatter.format(object);
                return null;
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.trim().isEmpty())
                    return LocalDate.parse(string, formatter);
                return null;
            }
        });
        //type de compte bancaire
        type = new Label("Compte " + typeCompte);

        BorderPane footer = new BorderPane();

        Button enregistrer = new Button("Enregistrer");
        enregistrer.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        enregistrer.setOnAction(e -> {
            if (checkFields()) {
                boolean b = SaveChanges();
                if (b) {
                    gui.isSaved(false);
                    gui.updateTable();
                }
                window.close();
            }
        });

        Button annuler = new Button("Annuler");
        annuler.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        annuler.setOnAction(event -> window.close());


        verserB.setPrefWidth(60);
        retirerB.setPrefWidth(60);
        verserB.setStyle("-fx-background-color:#45FE32;-fx-font-weight:bold;");
        retirerB.setStyle("-fx-background-color:#FE4532;-fx-font-weight:bold;");

        verserNF.textProperty().addListener((obs, oldText, newText) -> {
            updatePreview(newText, 1);
        });

        retirerNF.textProperty().addListener((obs, oldText, newText) -> {
            updatePreview(newText, -1);
        });

        verserB.setOnAction(event -> {
            if (!verserNF.isEmpty()) {
                float verseValue = Float.parseFloat(verserNF.getText());
                float soldvalue = getSolde();
                float newSold = soldvalue + verseValue - tauxOperation();
                sold.setText(newSold + " Dhs");
                updatePreview(verserNF.getText(), 1);
            }
        });

        retirerB.setOnAction(event -> {
            if (!retirerNF.isEmpty()) {
                float retirValue = Float.parseFloat(retirerNF.getText());
                float soldvalue = getSolde();
                if (soldvalue + client.getCompte().getDecouvert() > retirValue) {
                    float newSold = soldvalue - retirValue - tauxOperation();
                    sold.setText(newSold + " Dhs");
                    updatePreview(retirerNF.getText(), -1);
                }
            }
        });


        footer.setLeft(annuler);
        footer.setRight(enregistrer);

        GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(20);
        form.setPadding(new Insets(10, 20, 30, 20));


        GridPane.setConstraints(lnameLabel, 0, 0);
        GridPane.setConstraints(lname, 1, 0);

        GridPane.setConstraints(fnameLabel, 0, 1);
        GridPane.setConstraints(fname, 1, 1);

        GridPane.setConstraints(cinLabel, 0, 2);
        GridPane.setConstraints(cin, 1, 2);

        GridPane.setConstraints(dateLabel, 0, 3);
        GridPane.setConstraints(s_datePK, 1, 3);

        GridPane.setConstraints(typeLabel, 0, 4);
        GridPane.setConstraints(type, 1, 4, 2, 1);
        form.getChildren().addAll(lnameLabel, lname, fnameLabel, fname, cinLabel, cin, dateLabel, s_datePK, typeLabel, type);
        createAccountSetting(form);

        BorderPane soldBox = new BorderPane();
        soldBox.setPadding(new Insets(0, 0, 30, 20));
        HBox s = new HBox();
        s.setAlignment(Pos.CENTER_LEFT);
        s.setSpacing(20);
        s.getChildren().addAll(soldLabel, sold);
        sold.setStyle("-fx-font-weight:bold;-fx-font-size:18");
        preview.setStyle("-fx-font-size:14");
        preview.setTextFill(Color.web("#515151"));
        soldBox.setLeft(s);
        soldBox.setRight(preview);

        GridPane operationGP = new GridPane();
        operationGP.setHgap(20);
        operationGP.setVgap(20);
        operationGP.setAlignment(Pos.CENTER);
        GridPane.setConstraints(verserNF, 0, 0);
        GridPane.setConstraints(verserB, 1, 0);

        GridPane.setConstraints(retirerNF, 0, 1);
        GridPane.setConstraints(retirerB, 1, 1);


        operationGP.getChildren().addAll(verserB, verserNF, retirerB, retirerNF);


        BorderPane soldePanel = new BorderPane();
        soldePanel.setTop(soldBox);
        soldePanel.setCenter(operationGP);

        VBox sceneLayout = new VBox();
        sceneLayout.setSpacing(20);
        sceneLayout.setPadding(new Insets(20));
        sceneLayout.setAlignment(Pos.CENTER);
        sceneLayout.getChildren().addAll(form, soldePanel, footer);


        Scene scene = new Scene(sceneLayout);
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(scene);
        window.setTitle("Cilent Matricule : " + client.getMatricule());
        window.setResizable(false);
        window.show();
    }

    private void createAccountSetting(GridPane form) {
        tauxInteretNF = new NumberField();
        decouvertNF = new NumberField();
        tauxInteretNF.setMaxWidth(70);
        decouvertNF.setMaxWidth(70);
        tauxInteretNF.setRegex("\\d{0,2}([\\.]\\d{0,4})?");
        if (typeCompte.equals("Bancaire")) {
            decouvertNF.setText(client.getCompte().getDecouvert() + "");
            GridPane.setConstraints(decouvertLB, 0, 5);
            GridPane.setConstraints(decouvertNF, 1, 5);
            form.getChildren().addAll(decouvertLB, decouvertNF);
        } else if (typeCompte.equals("Epargne")) {
            GridPane.setConstraints(tauxInteretLB, 0, 5);
            GridPane.setConstraints(tauxInteretNF, 1, 5);
            form.getChildren().addAll(tauxInteretLB, tauxInteretNF);
        }
    }

    private void updatePreview(String newValue, int op) {
        if (newValue.isEmpty()) {
            preview.setText("");
        } else if (newValue.matches("\\d{0,10}([\\.]\\d{0,4})?")) {
            float soldeVlaue = getSolde();
            float value = Float.parseFloat(newValue);
            float result;
            if (op == -1) {
                if (soldeVlaue + Decouvert() < value) {
                    preview.setText("( Solde insuffisant! )");
                } else {
                    result = soldeVlaue - value - tauxOperation();
                    preview.setText("( " + result + " Dhs )");
                }
            } else {
                result = soldeVlaue + value - tauxOperation();
                preview.setText("( " + result + " Dhs )");
            }
        }
    }


    public boolean checkFields() {
        //test all text fields and show red boarder if there is an error!
        boolean test = true;
        if (fname.isEmpty()) {
            test = false;
        }
        if (lname.isEmpty()) {
            test = false;
        }
        if (cin.isEmpty()) {
            test = false;
        }
        if (typeCompte.equals("Bancaire")) {
            if (decouvertNF.isEmpty())
                test = false;
        } else if (typeCompte.equals("Epargne")) {
            if (tauxInteretNF.isEmpty())
                test = false;
        }
        if (s_datePK.getValue() == null) {
            s_datePK.setStyle("-fx-border-color:red;-fx-border-radius:3px;-fx-border-size: 1px;");
            test = false;
        }

        return test;
    }

    public boolean SaveChanges() {
        String nom = lname.getText();
        String prenom = fname.getText();
        String CIN = cin.getText();
        Date date = Date.valueOf(s_datePK.getValue());
        float newSolde = getSolde();
        boolean changed = false;
        if (!client.getNom().equals(nom)) {
            client.setNom(nom);
            changed = true;
        }
        if (!client.getPrenom().equals(prenom)) {
            client.setPrenom(prenom);
            changed = true;
        }
        if (!client.getCin().equals(CIN)) {
            client.setCin(CIN);
            changed = true;
        }
        if (!client.getDate_naissance().equals(date)) {
            client.setDate_naissance(date);
            changed = true;
        }
        if (typeCompte.equals("Bancaire")) {
            float decouvert = Float.parseFloat(decouvertNF.getText());
            if (client.getCompte().getDecouvert() != decouvert) {
                client.getCompte().setDecouvert(decouvert);
                changed = true;
            }
        } else if (typeCompte.equals("Epargne")) {
            float taux = Float.parseFloat(tauxInteretNF.getText());
            if (client.getCompte().getTaux() != taux) {
                client.getCompte().setTaux(Float.parseFloat(tauxInteretNF.getText()));
                changed = true;
            }
        }
        if (client.getCompte().getSolde() != newSolde) {
            client.getCompte().setSolde(newSolde);
            changed = true;
        }
        return changed;
    }

    public float tauxOperation() {
        if (typeCompte.equals("Payant"))
            return ComptePayant.tauxOperation;
        else
            return 0;
    }

    private float Decouvert() {
        float decouvert = 0;
        if (typeCompte.equals("Bancaire"))
            decouvert = Float.parseFloat(decouvertNF.getText());
        return decouvert;
    }

    private float getSolde() {
        return Float.parseFloat(sold.getText().split(" ")[0]);
    }

}

