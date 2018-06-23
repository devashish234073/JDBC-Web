import java.sql.*;

public class Database {

    private String dbType;
    private String host;
    private int port;
    private String db = "";
    private String userName;
    private String password;
    private Connection connection = null;
    private Statement statement;
    private ResultSet resultSet;

    public Database(String dbType,String host,int port,String userName,String password) {
        this.dbType = dbType;
        this.host = host;
        this.port = port;
        this.userName = userName;
        this.password = password;
    }

    public String getConnectionString() {
        if(db.equals("")) {
            return "jdbc:" + dbType + "://" + host + ":" + port;
        } else {
            return "jdbc:" + dbType + "://" + host + ":" + port + "/" + db;
        }
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection(getConnectionString(), userName, password);
        } catch (SQLException sqle) {
            connection = null;
        }
    }

    public ResultSet runQuery(String query) {
        if(connection==null) {
            connect();
        }
        if(connection!=null) {
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(query);
                return resultSet;
            } catch (SQLException sqle) {

            }
        }
        return null;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }
}
