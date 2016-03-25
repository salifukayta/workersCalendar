package sol.workers.calendar.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import ro.stancalau.springfx.control.LanguageController;
import ro.stancalau.springfx.model.LanguageModel;
import ro.stancalau.springfx.model.MessageModel;


@Configuration
@EnableTransactionManagement
@Import({ScreensConfig.class, JdbcConfiguration.class})
public class AppConfig {
	
    private ApplicationContext context;

	@Bean
    LanguageModel languageModel() {
        return new LanguageModel();
    }

    @Bean
    LanguageController languageController() {
        return new LanguageController(languageModel());
    }

    @Bean
    MessageModel messageModel() {
        return new MessageModel();
    }

	public void setContext(ApplicationContext context) {
		this.context = context;
	}

	public ApplicationContext getContext() {
		return context;
	}
	
}
