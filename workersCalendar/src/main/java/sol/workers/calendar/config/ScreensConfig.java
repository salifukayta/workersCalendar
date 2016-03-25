package sol.workers.calendar.config;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import ro.stancalau.springfx.gui.FirstPresentation;
import ro.stancalau.springfx.gui.ModalDialog;
import ro.stancalau.springfx.gui.PopupPresentation;
import ro.stancalau.springfx.gui.SecondPresentation;
import ro.stancalau.springfx.model.LanguageModel;
import sol.workers.calendar.business.repository.impl.WorkerFileRepositoryImpl;
import sol.workers.calendar.business.services.impl.CalendarPlanificatorServiceImpl;
import sol.workers.calendar.presenter.AbstractPresenter;
import sol.workers.calendar.presenter.WorkersCalendarPresenter;

@Configuration
@Lazy
public class ScreensConfig implements Observer {

	private static final Logger logger = LogManager.getLogger(ScreensConfig.class);

	public static final int WIDTH = 480;
	public static final int HEIGHT = 320;
	private static final String APP_TITLE_ORGANISATEUR_DES_OUVRIERS = "Organisateur des ouvriers: Kiosque Agil Aguereb, Sfax";
	private static final String APP_ICON_WEEK_CALENDAR = "Calendar-Week.png";
	public static final String STYLE_FILE = "main.css";

	private Stage stage;
	private Scene scene;
	private LanguageModel lang;
	private StackPane root;

	public void setPrimaryStage(Stage primaryStage) {
		this.stage = primaryStage;
	}

	public void setLangModel(LanguageModel lang) {
		if (this.lang != null) {
			this.lang.deleteObserver(this);
		}
		lang.addObserver(this);
		this.lang = lang;
	}

	public ResourceBundle getBundle() {
		return lang.getBundle();
	}

	public void showMainScreen() {
		root = new StackPane();
		root.getStylesheets().add(STYLE_FILE);
		stage.setTitle(APP_TITLE_ORGANISATEUR_DES_OUVRIERS);
		stage.getIcons().add(new Image(getClass().getResourceAsStream(APP_ICON_WEEK_CALENDAR)));
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setMaximized(true);

		stage.setOnHiding(event -> Platform.exit()
		// TODO you could add code to open an "are you sure you want to exit?" dialog
		);

		stage.show();
	}

	private void setNode(Node node) {
		root.getChildren().setAll(node);
	}

	private void setNodeOnTop(Node node) {
		root.getChildren().add(node);
	}

	public void removeNode(Node node) {
		root.getChildren().remove(node);
	}

	public void loadWorkersCalendar() {
		setNode(getNode(loadWorkersCalendarPresentation(), getClass().getResource("WorkersCalendar.fxml")));
	}

	@Bean
	@Scope("prototype")
	WorkersCalendarPresenter loadWorkersCalendarPresentation() {
		return new WorkersCalendarPresenter(this);
	}

	@Bean
	@Deprecated
	WorkerFileRepositoryImpl loadDataManagerService() {
		return new WorkerFileRepositoryImpl();
	}

	@Bean
	CalendarPlanificatorServiceImpl loadCalendarPlanificatorService() {
		return new CalendarPlanificatorServiceImpl();
	}

	public void loadFirst() {
		setNode(getNode(firstPresentation(), getClass().getResource("First.fxml")));
	}

	public void loadSecond() {
		setNode(getNode(secondPresentation(), getClass().getResource("Second.fxml")));
	}

	public void loadPopup() {
		ModalDialog modal = new ModalDialog(popupPresentation(), getClass().getResource("Popup.fxml"), stage.getOwner(),
				lang.getBundle());
		modal.setTitle(lang.getBundle().getString("popup.title"));
		modal.getStyleSheets().add(STYLE_FILE);
		modal.show();
	}

	@Bean
	@Scope("prototype")
	FirstPresentation firstPresentation() {
		return new FirstPresentation(this);
	}

	@Bean
	@Scope("prototype")
	SecondPresentation secondPresentation() {
		return new SecondPresentation(this);
	}

	@Bean
	@Scope("prototype")
	PopupPresentation popupPresentation() {
		return new PopupPresentation(this);
	}

	private Node getNode(final AbstractPresenter control, URL location) {
		FXMLLoader loader = new FXMLLoader(location, lang.getBundle());
		loader.setControllerFactory(aClass -> control);

		try {
			return (Node) loader.load();
		} catch (IOException e) {
			logger.error("Erreur Technique. Veuillez contacter l'administrateur.", e);
			return null;
		}
	}

	public Stage getStage() {
		return stage;
	}

	public void update(Observable o, Object arg) {
		loadFirst();
	}
}
