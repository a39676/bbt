package demo.tool.pojo.constant;

import org.apache.commons.lang.SystemUtils;

public class ToolPathConstant {

	private static String windowsPreffix = "d:";
	
	private static String tmpStorePath = "/home/tmp/";
	private static String tmpImagePath = "/home/tmp/image/";
	private static String tmpDataPath = "/home/tmp/database/";
	private static String databaseImportPath = "/home/dataImport/";
	
	private static String tomcatLogsPath = "/home/u1/tomcat/apache-tomcat-9.0.7/logs/";
	private static String tomcatOutPath = tomcatLogsPath + "catalina.out";
	
	private static String excelTmpStorePath = "/home/tmp/excelStore/";
	
	private static String excelAnalysisStorePath = "/home/u1/excelAnalysis/";
	
	public static String getExcelAnalysisStorePath() {
		return excelAnalysisStorePath;
	}
	
	public static String getExcelTmpStorePath() {
		return excelTmpStorePath;
	}
	
	public static String getTmpStorePath() {
		return tmpStorePath;
	}

	public static String getTmpImagePath() {
		return tmpImagePath;
	}

	public static String getTmpDataPath() {
		return tmpDataPath;
	}
	
	public static String getDatabaseDataImportPath() {
		return databaseImportPath;
	}
	
	public static String getTomcatLogsPath() {
		return tomcatLogsPath;
	}
	
	public static String getTomcatOutPath() {
		return tomcatOutPath;
	}

	static {
		if(SystemUtils.IS_OS_LINUX) {
			
		} else if (SystemUtils.IS_OS_WINDOWS) {
			tmpStorePath = windowsPreffix + tmpStorePath;
			tmpImagePath = windowsPreffix + tmpImagePath;
			tmpDataPath = windowsPreffix + tmpDataPath;
			databaseImportPath = windowsPreffix + databaseImportPath;
			tomcatLogsPath = windowsPreffix + tomcatLogsPath;
			tomcatOutPath = windowsPreffix + tomcatOutPath;
		}
	}
	
}
