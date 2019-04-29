package Gui;

import Classes.Agence;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Label header = new Label("Gestion d'une Agence Bancaire");
        BorderPane footer = new BorderPane();
        Button newAgenceB = new Button("Crée une Agence Bancaire");
        Button importeAgenceB = new Button("Charger une Agence Bancaire");

        //Styles
        header.setStyle("-fx-font-weight:bold;-fx-font-size:25");
        header.setPadding(new Insets(50, 50, 50, 50));
        header.setAlignment(Pos.CENTER);

        String style = "-fx-font-weight:bold;-fx-font-size:14";
        newAgenceB.setStyle(style);
        importeAgenceB.setStyle(style);

        //footer
        String s = "-fx-font-size : 10;-fx-text-inner-color:#DDDDDD;";
        Label names = new Label("Devs : Abdourrahmane OUAHIB / Ismail NAJAH");
        Label prom = new Label("GL6 2018/2019");
        Label version = new Label("v1.0");

        names.setStyle(s);
        prom.setStyle(s);
        version.setStyle(s);
        footer.setLeft(names);
        footer.setCenter(prom);
        footer.setRight(version);
        footer.setPadding(new Insets(50, 5, 5, 5));

        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(30);
        box.getChildren().addAll(newAgenceB, importeAgenceB);

        BorderPane layout = new BorderPane();
        layout.setCenter(box);
        layout.setTop(header);
        layout.setBottom(footer);

        newAgenceB.setOnAction(even -> new ClientListGui(primaryStage, new Agence()));
        importeAgenceB.setOnAction(event -> {
            ClientListGui gui = new ClientListGui(primaryStage, new Agence());
            gui.header.openSave.fire();
        });


        Scene scene = new Scene(layout);
        primaryStage.setTitle("Gestion Agence Bancaire");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
