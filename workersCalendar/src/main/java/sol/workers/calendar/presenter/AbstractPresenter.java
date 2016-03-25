package sol.workers.calendar.presenter;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sol.workers.calendar.config.ScreensConfig;

public abstract class AbstractPresenter {

	private static final String ERROR_OCCURED = "Une erreur est survenue";

	protected static final String WORKERS_SOURCE = "src/main/resources/sol/workers/calendar/presenter/workers.txt";
	
    protected ScreensConfig config;

    public AbstractPresenter(ScreensConfig config) {
        this.config = config;
    }
    
    protected void showErrorDoalog(String msg) {
		Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setTitle(ERROR_OCCURED);
		// dialog.initOwner(primaryStage);
		VBox dialogVbox = new VBox(20);
		dialogVbox.getChildren().add(new Label(msg));
		Scene dialogScene = new Scene(dialogVbox, 300, 100);
		dialog.setScene(dialogScene);
		dialog.show();
	}
    
}
