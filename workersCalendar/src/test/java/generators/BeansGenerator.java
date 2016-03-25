package generators;

import java.io.File;
import java.sql.SQLException;

import org.sqlite.SQLiteConnection;

import com.querydsl.codegen.BeanSerializer;
import com.querydsl.sql.codegen.MetaDataExporter;

public class BeansGenerator {

	public static void main(String[] args) {
		
		java.sql.Connection conn;
		try {
			conn = new SQLiteConnection("jdbc:sqlite", "src/main/resources/sol/workers/calendar/config/workersData.db");
			MetaDataExporter exporter = new MetaDataExporter();
			exporter.setBeanSuffix("DTO");
			
			exporter.setInnerClassesForKeys(true);
			exporter.setExportForeignKeys(true);
			
			exporter.setPackageName("sol.workers.calendar.business.model");
			exporter.setTargetFolder(new File("src/main/java"));
			exporter.setBeanSerializer(new BeanSerializer());
			exporter.export(conn.getMetaData());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
