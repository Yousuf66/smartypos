package ksa.so.dbBackup;

import java.io.File;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.smattme.MysqlExportService;

@Component
public class ScheduledBackup {
	
	private Logger logger = LoggerFactory.getLogger(ScheduledBackup.class);
	
//	@Scheduled(cron = "0/30 * * * * ?")
	@Scheduled(cron = "	0 0 * */3 * ?")	//every three days
	public void sendBackup() throws Exception {
		try{
			//required properties for exporting of db
			Properties properties = new Properties();
//			properties.setProperty(MysqlExportService.JDBC_CONNECTION_STRING, "jdbc:mysql://ca1.risknucleus.com/");
//			properties.setProperty(MysqlExportService.JDBC_DRIVER_NAME, "com.mysql.cj.jdbc.Driver");
			properties.setProperty(MysqlExportService.DB_NAME, "smarty_new");
			properties.setProperty(MysqlExportService.DB_USERNAME, "bm");
			properties.setProperty(MysqlExportService.DB_PASSWORD, "benchmatrix786?");
			//properties relating to email config
			properties.setProperty(MysqlExportService.EMAIL_HOST, "smtp.gmail.com");
			properties.setProperty(MysqlExportService.EMAIL_PORT, "587");
			properties.setProperty(MysqlExportService.EMAIL_USERNAME, "swift.coffee.ksa@gmail.com");
			properties.setProperty(MysqlExportService.EMAIL_PASSWORD, "bmbm@123?");
			properties.setProperty(MysqlExportService.EMAIL_FROM, "swift.coffee.ksa@gmail.com");
			properties.setProperty(MysqlExportService.EMAIL_TO, "marium.hashmi@benchmatrix.ca");

			//set the outputs temp dir
			properties.setProperty(MysqlExportService.TEMP_DIR, new File("external").getPath());
			
			MysqlExportService mysqlExportService = new MysqlExportService(properties);
			mysqlExportService.export();
			String generatedSql = mysqlExportService.getGeneratedSql();
      
		}
		catch (Exception e){
			System.out.println("error" + e);
		}
    }
	
	
    
}
