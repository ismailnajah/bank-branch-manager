package Gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AboutGui {

    AboutGui() {
        Label devL = new Label("Developper par");
        Label ismail = new Label("NAJAH Ismail");
        Label ouahib = new Label("OUAHIB Abdourrahmane");
        Label left = new Label("GL6");
        Label middle = new Label("2018/2019");
        Label right = new Label("v1.0");

        String style = "-fx-font-weight:bold;-fx-font-size:14";
        ismail.setStyle(style);
        ouahib.setStyle(style);

        String s = "-fx-font-size : 10;-fx-text-inner-color:#DDDDDD;";
        left.setStyle(s);
        middle.setStyle(s);
        right.setStyle(s);

        HBox dev = new HBox();
        dev.getChildren().add(devL);
        dev.setAlignment(Pos.CENTER_LEFT);


        VBox body = new VBox(10);
        body.setAlignment(Pos.CENTER);
        body.setPadding(new Insets(20));
        body.getChildren().addAll(dev, ouahib, ismail);


        BorderPane footer = new BorderPane();
        footer.setLeft(left);
        footer.setCenter(middle);
        footer.setRight(right);

        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(10, 20, 5, 20));
        layout.setCenter(body);
        layout.setBottom(footer);

        Stage window = new Stage();
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.setTitle("À propos");
        window.setResizable(false);
        window.show();

    }

}
