package ro.stancalau.springfx.gui;

import org.springframework.beans.factory.annotation.Autowired;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ro.stancalau.springfx.model.MessageModel;
import sol.workers.calendar.config.ScreensConfig;

public class PopupPresentation extends Modal {

    public PopupPresentation(ScreensConfig config) {
        super(config);
    }

    @Autowired
    private MessageModel model;

    @FXML
    TextField messageTf;

    @FXML
    void initialize() {
        messageTf.setText(model.getMessage());
    }

    @FXML
    void clickedOk(ActionEvent event) {
        dialog.close();
    }

    @FXML
    void onApply(ActionEvent event) {
        model.setMessage(messageTf.getText());
    }
}
