package sol.workers.calendar.config;

import java.sql.Connection;

import javax.inject.Provider;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.sqlite.SQLiteDataSource;

import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.SQLiteTemplates;
import com.querydsl.sql.spring.SpringConnectionProvider;
import com.querydsl.sql.spring.SpringExceptionTranslator;

import sol.workers.calendar.business.repository.impl.WorkerRepositoryImpl;

@Configuration
@Lazy
public class JdbcConfiguration {

	@Bean
	public DataSource dataSource() {
		SQLiteDataSource dataSource = new SQLiteDataSource();
		 dataSource.setUrl("jdbc:sqlite:" + getClass().getResource("workersData.db"));
//		dataSource.setUrl("jdbc:sqlite:../data/workersData.db");

		// dataSource.setDriverClass(env.getRequiredProperty("jdbc.driver"));
		return dataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}

	@Bean
	public com.querydsl.sql.Configuration querydslConfiguration() {
		SQLTemplates templates = SQLiteTemplates.builder().build();
		com.querydsl.sql.Configuration configuration = new com.querydsl.sql.Configuration(templates);
		configuration.setExceptionTranslator(new SpringExceptionTranslator());
		return configuration;
	}

	@Bean
	public SQLQueryFactory queryFactory() {
		Provider<Connection> provider = new SpringConnectionProvider(dataSource());
		return new SQLQueryFactory(querydslConfiguration(), provider);
	}

	@Bean
	public WorkerRepositoryImpl loadWorkerRepository() {
		return new WorkerRepositoryImpl();
	}

}
