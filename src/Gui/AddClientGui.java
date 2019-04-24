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
import java.util.ArrayList;
import java.util.Random;

public class AddClientGui {
    ClientListGui gui;

    Label lnameLabel;
    Label fnameLabel;
    Label cinLabel;
    Label typeLabel;
    Label dateLabel;
    Label soldLabel;

    DatePicker s_datePK;
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    textField lname;
    textField fname;
    textField cin;
    NumberField soldInitial;

    final ToggleGroup radioGroup;
    RadioButton compteBancaireRB;
    RadioButton comptePayantRB;
    RadioButton compteEpargneRB;

    Stage window;
    public AddClientGui(ClientListGui gui){
        window = new Stage();
        this.gui = gui;
        lnameLabel = new Label("Nom");
        fnameLabel = new Label("Prenom");
        cinLabel = new Label("CIN");
        typeLabel = new Label("Type de compte");
        dateLabel = new Label("Date de naissance");
        soldLabel = new Label("Solde initial");

        lname = new textField();
        fname = new textField();
        cin = new textField();
        soldInitial = new NumberField();
        s_datePK  = new DatePicker();
        s_datePK.setPromptText("dd-mm-yyyy");
        s_datePK.setOnAction(event -> s_datePK.setStyle(null));

        //type de compte bancaire
        radioGroup = new ToggleGroup();
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


        GridPane.setConstraints(lnameLabel, 0, 0);
        GridPane.setConstraints(lname, 1, 0);

        GridPane.setConstraints(fnameLabel, 0, 1);
        GridPane.setConstraints(fname, 1, 1);

        GridPane.setConstraints(cinLabel, 0, 2);
        GridPane.setConstraints(cin, 1, 2);

        GridPane.setConstraints(dateLabel, 0, 3);
        GridPane.setConstraints(s_datePK, 1, 3);

        GridPane.setConstraints(typeLabel, 0, 4);
        GridPane.setConstraints(radioLayout, 1, 4, 2, 1);

        GridPane.setConstraints(soldLabel, 0, 5);
        GridPane.setConstraints(soldInitial, 1, 5);

        form.getChildren().addAll(lnameLabel, lname, fnameLabel, fname,cinLabel,
                                    cin,dateLabel,s_datePK,typeLabel, radioLayout,soldLabel,soldInitial);


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
        client.setMatricule(CreateClientMatricule());
        client.getCompte().setCode(CreateCompteCode());
        return client;
    }

    private int CreateClientMatricule() {
        Random rand = new Random();
        int newClientMatricule = rand.nextInt(10000);

        while (MatriculeExist(newClientMatricule)) {
            newClientMatricule = rand.nextInt(10000);
        }
        return newClientMatricule;

    }

    private int CreateCompteCode() {
        Random rand = new Random();
        int newCompteMatricule = rand.nextInt(10000);
        boolean found = true;
        while (found) {
            found = false;
            for (Compte compte : gui.agence.lesComptes) {
                if (compte.getCode() == newCompteMatricule) {
                    found = true;
                    newCompteMatricule = rand.nextInt(10000);
                    break;
                }
            }
        }
        return newCompteMatricule;
    }

    private boolean MatriculeExist(int newMatricule) {
        ArrayList<Client> list = gui.getClientList();
        for (Client client : list) {
            if (client.getMatricule() == newMatricule)
                return true;
        }
        return false;
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

    public boolean checkFields(){
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
