package CustomFx;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;


public class NumberField extends TextField {

    public NumberField() {
        super();
        this.setOnKeyTyped(e -> NumberField.this.setStyle(null));
        this.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,10}([\\.]\\d{0,4})?"))
                    NumberField.this.setText(oldValue);
            }
        });
    }

    public boolean isEmpty() {
        if (this.getText().isEmpty()) {
            this.setStyle("-fx-border-color:red;-fx-border-radius:3px;-fx-border-size: 1px;");
            return true;
        }
        return false;
    }
}
