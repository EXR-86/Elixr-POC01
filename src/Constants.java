public class Constants {
    public static final int TOTAL_NO_OF_ARGUMENTS = 2;
    public static final String TXT_EXTENSION = "txt";
    public static final String JSON_EXTENSION = "json";
    public static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    public static final String MYSQL_URL = "jdbc:mysql://localhost:3306/elixr1";
    public static final String USERNAME = "root";
    public static final String PASSWORD_OF_DATABASE = "Ashu@2406";
    public static final String DATE_AND_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";
    public static final String QUERY_TO_CREATE_TABLE = "create table audit (PathToTheFile varchar(100), SearchedWord varchar(45), DateAndTimeOfSearch varchar(45), result varchar(45), WordCount int, ErrorMessage varchar(100))";
}

