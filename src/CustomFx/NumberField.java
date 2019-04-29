package CustomFx;

import javafx.scene.control.TextField;


public class NumberField extends TextField {
    private String regex = "\\d{0,10}([.]\\d{0,4})?";
    public NumberField() {
        super();
        this.setOnKeyTyped(e -> NumberField.this.setStyle(null));
        this.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(regex))
                NumberField.this.setText(oldValue);
        });
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public boolean isEmpty() {
        if (this.getText().isEmpty()) {
            this.setStyle("-fx-border-color:red;-fx-border-radius:3px;-fx-border-size: 1px;");
            return true;
        }
        return false;
    }
}
