package sol.workers.calendar.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import ro.stancalau.springfx.model.LanguageModel;
import sol.workers.calendar.config.AppConfig;
import sol.workers.calendar.config.ScreensConfig;

@Service
public class MainWorkersCalendar extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start(Stage stage) throws Exception {
		Platform.setImplicitExit(true);

		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		AppConfig appConfig = context.getBean(AppConfig.class);
		appConfig.setContext(context);
		ScreensConfig screens = context.getBean(ScreensConfig.class);
		LanguageModel lang = context.getBean(LanguageModel.class);
		
		screens.setLangModel(lang);
		((ScreensConfig) screens).setPrimaryStage(stage);
		((ScreensConfig) screens).showMainScreen();
		((ScreensConfig) screens).loadWorkersCalendar();
		// screens.loadFirst();
	}
}
