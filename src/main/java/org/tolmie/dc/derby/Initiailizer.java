package org.tolmie.dc.derby;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.derby.tools.ij;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tolmie.dc.derby.service.RedcaIntegrityInspectionSVC;

public class Initiailizer {

	private static final Logger logger = LoggerFactory.getLogger(Initiailizer.class);

	public static void main(String[] args) throws Exception
	{
		
		List<String> arguments = new ArrayList<String>();
		
		
		for (String arg : args) {
			String key = null, value = null;
			if(arg.startsWith("--")) {
				//skip 
				//arguments.add(arg);

			} else if(arg.startsWith("-")) {
				key = arg.substring(1);
			}
			arguments.add(key);
		}
		
		if(arguments.contains("INIT".toLowerCase()) ||   arguments.contains("INIT".toUpperCase())   )
		{
			
			URL derbySchemaCreateSqlUrl = Initiailizer.class.getClassLoader().getResource("script/derby-schema-install.sql");
			String[] derbySchemaCreateSql = {derbySchemaCreateSqlUrl.toString().replaceAll("file:/", "")}; 
			ij.main( derbySchemaCreateSql );
			
			URL derbyDataInsertSqlUrl = Initiailizer.class.getClassLoader().getResource("script/derby-initial-data.sql");
			String[] derbyDataInsertSql = { derbyDataInsertSqlUrl.toString().replaceAll("file:/", "") };
			
			ij.main( derbyDataInsertSql );
			
			System.exit(1);
		}
		else if(arguments.contains("Compare".toLowerCase()) ||   arguments.contains("Compare".toUpperCase())   )
		{
			logger.debug("Integrity check started..");
	
			ApplicationContext ctx = new ClassPathXmlApplicationContext(
					"classpath*:spring/context.xml", 
					"classpath*:spring/context-properties.xml", 
					"classpath*:queries/query-*.xml"
					);
			RedcaIntegrityInspectionSVC svc = (RedcaIntegrityInspectionSVC) ctx.getBean("svc");
	
			svc.compare();
			svc.report();
		}
	}
	
	static boolean isFile(String filePathString) {
		File f = new File(filePathString);
		if(f.exists() && !f.isDirectory())
			return true;
		else return false;
	}
	
}
