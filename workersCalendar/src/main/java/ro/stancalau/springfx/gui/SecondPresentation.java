package ro.stancalau.springfx.gui;

import java.util.Observable;
import java.util.Observer;

import org.springframework.beans.factory.annotation.Autowired;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ro.stancalau.springfx.model.MessageModel;
import sol.workers.calendar.config.ScreensConfig;
import sol.workers.calendar.presenter.AbstractPresenter;

public class SecondPresentation extends AbstractPresenter {

	public SecondPresentation(ScreensConfig config) {
		super(config);
	}
	
	@Autowired
	private MessageModel model;
	
	@FXML
	TextField messageTf;
	
	@FXML
	void initialize() {
		messageTf.setText(model.getMessage());
		
		model.addObserver(new Observer() {
			
			public void update(Observable o, Object arg) {
				messageTf.setText(model.getMessage());
			}
		});
	}
	
	@FXML 
	void onApply(ActionEvent event){
		model.setMessage(messageTf.getText());
	}
	
	@FXML
	void prevView(ActionEvent event){
		config.loadFirst();
	}
	
	@FXML 
	void openPopup(ActionEvent event){
		config.loadPopup();
	}
	

}
