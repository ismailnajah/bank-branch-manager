package Gui;

import Classes.Client;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertGui {
    Stage window = new Stage();
    VBox layout = new VBox();
    HBox buttons = new HBox();
    Scene scene = new Scene(layout);
    Button yesB = new Button("Oui");
    Button noB = new Button("Non");
    Button cancelb;
    Label message;

    public AlertGui(String Message,String title){
        message = new Label(Message);
        yesB.setMinWidth(20);
        noB.setMinWidth(20);
        buttons.setSpacing(50);
        buttons.getChildren().addAll(yesB,noB);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(15,0,0,0));

        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(message,buttons);

        noB.setOnAction(event->{
            window.close();
        });

        window.setScene(scene);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(scene);
        window.setTitle(title);
        window.setMinWidth(340);
        window.setResizable(false);
        window.show();
    }

    public void createCancelB(){
        cancelb = new Button("Annuler");
        noB.setOnAction(event -> Platform.exit());
        cancelb.setOnAction(event -> window.close());
        buttons.getChildren().add(cancelb);
    }

    public void close() {
        window.close();
    }
}
