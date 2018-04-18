package mars.common;

public class MySqlDBUtil {
	private static final String HOST_NAME = "localhost";
	private static final String PORT_NUM = "3306"; 
	private static final String DB_NAME = "project";
	
	public static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
	public static final String USERNAME = "root";
	public static final String PASSWORD = "root";
	public static final String URL = "jdbc:mysql://" + HOST_NAME + ":" + PORT_NUM + "/" + DB_NAME + "?serverTimezone=UTC";
}

