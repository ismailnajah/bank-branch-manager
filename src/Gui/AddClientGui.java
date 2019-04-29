package Gui;

import Classes.*;
import CustomFx.NumberField;
import CustomFx.textField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Random;

class AddClientGui {
    private static HashSet<Integer> codes = new HashSet<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private DatePicker s_datePK;
    private textField lname;
    private textField fname;
    private textField cin;
    private NumberField soldInitial;

    private RadioButton compteBancaireRB;
    private RadioButton comptePayantRB;
    private RadioButton compteEpargneRB;

    private Stage window;

    AddClientGui(ClientListGui gui) {
        window = new Stage();

        lname = new textField();
        fname = new textField();
        cin = new textField();
        soldInitial = new NumberField();
        s_datePK  = new DatePicker();
        s_datePK.setPromptText("dd-mm-yyyy");
        s_datePK.setOnAction(event -> s_datePK.setStyle(null));

        //type de compte bancaire
        ToggleGroup radioGroup = new ToggleGroup();
        compteBancaireRB = new RadioButton("Compte Bancaire");
        compteBancaireRB.setToggleGroup(radioGroup);
        compteBancaireRB.setSelected(true);

        comptePayantRB = new RadioButton("Compte Payant");
        comptePayantRB.setToggleGroup(radioGroup);

        compteEpargneRB = new RadioButton("Compte Epargne");
        compteEpargneRB.setToggleGroup(radioGroup);

        HBox radioLayout = new HBox();
        radioLayout.setSpacing(30);
        radioLayout.getChildren().addAll(compteBancaireRB,compteEpargneRB,comptePayantRB);

        HBox footer = new HBox();
        footer.setAlignment(Pos.CENTER);

        Button create = new Button("Ajouter");
        create.setFont(Font.font("Arial", FontWeight.BOLD,15));

        create.setOnAction(event->{
            if(checkFields()){
                Client client = createClient();
                gui.addClient(client);
                window.close();
            }
        });


        s_datePK.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate object) {
                if(object != null)
                    return formatter.format(object);
                return null;
            }

            @Override
            public LocalDate fromString(String string) {
                if(string != null && !string.trim().isEmpty())
                    return LocalDate.parse(string,formatter);
                return null;
            }
        });


        footer.getChildren().add(create);

        GridPane form = new GridPane();
        form.setVgap(20);
        form.setHgap(20);


        Label lnameLabel = new Label("Nom");
        form.add(lnameLabel, 0, 0);
        form.add(lname, 1, 0);

        Label fnameLabel = new Label("Prenom");
        form.add(fnameLabel, 0, 1);
        form.add(fname, 1, 1);

        Label cinLabel = new Label("CIN");
        form.add(cinLabel, 0, 2);
        form.add(cin, 1, 2);

        Label dateLabel = new Label("Date de naissance");
        form.add(dateLabel, 0, 3);
        form.add(s_datePK, 1, 3);

        Label typeLabel = new Label("Type de compte");
        form.add(typeLabel, 0, 4);
        form.add(radioLayout, 1, 4, 2, 1);

        Label soldLabel = new Label("Solde initial");
        form.add(soldLabel, 0, 5);
        form.add(soldInitial, 1, 5);


        VBox sceneLayout = new VBox();
        sceneLayout.setSpacing(20);
        sceneLayout.setPadding(new Insets(20));
        sceneLayout.setAlignment(Pos.CENTER);
        sceneLayout.getChildren().addAll(form,footer);


        Scene scene = new Scene(sceneLayout);
        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(scene);
        window.setTitle("Nouveau Client");
        window.setResizable(false);
        window.show();
    }

    private Client createClient() {
        String nom = lname.getText();
        String prenom = fname.getText();
        String CIN = cin.getText();
        Date daten = Date.valueOf(s_datePK.getValue());

        Client client = new Client(nom,prenom,CIN,daten);
        client.affecterCompte(getSelectedCompte());
        client.setMatricule(CreateCode());
        client.getCompte().setCode(CreateCode());
        return client;
    }

    private int CreateCode() {
        Random rand = new Random();
        int newCode = rand.nextInt(10000);

        while (!codes.add(newCode)) {
            newCode = rand.nextInt(10000);
        }
        return newCode;
    }

    private Compte getSelectedCompte() {
        String soldeET = soldInitial.getText();
        float solde = 0;
        if(!soldeET.isEmpty()){
            solde = Float.parseFloat(soldeET);
        }
        if(compteEpargneRB.isSelected()){
            return new CompteEpargne(solde);
        }
        if(comptePayantRB.isSelected()){
            return new ComptePayant(solde);
        }
        return new CompteBancaire(solde);
    }

    private boolean checkFields() {
        //test all text fields and show red boarder if there is an error!
        boolean test = true;
        if(!compteBancaireRB.isSelected()){
            test = !soldInitial.isEmpty();
        }
        if(fname.isEmpty()){
            test=false;
        }
        if(lname.isEmpty()){
            test=false;
        }
        if(cin.isEmpty()){
            test=false;
        }
        if(s_datePK.getValue() == null){
            s_datePK.setStyle("-fx-border-color:red;-fx-border-radius:3px;-fx-border-size: 1px;");
            test=false;
        }

        return test;
    }
}
