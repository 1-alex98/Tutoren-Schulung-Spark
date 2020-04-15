package JDBC;

import static spark.Spark.*;

import java.sql.*;

public class HelloWorld {
	public static Connection conn;
	
    public static void main(String[] args) throws SQLException {
    	connectDB();
    	
        get("/:number", (req, res) -> {
        	String result = req.params(":number");
        	int number = Integer.parseInt(result);
        	return getFact(number);
        });
        
        post("/:number", (req, res) -> {
        	String result = req.params(":number");
        	int number = Integer.parseInt(result);
        	postFact(number, req.body().toString());
        	return "Fact inserted!";
        });
        
    }
    
    public static void postFact(int number, String body) {
    	try {
			PreparedStatement statement = conn.prepareStatement("INSERT INTO fact(id, fact, number) values(?, ?, ?);");
			statement.setInt(1, 1);
			statement.setString(2, body);
			statement.setInt(3, number);
			statement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static String getFact(int number) {
    	System.out.println("hier");
    	try {
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM fact WHERE number=?;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			statement.setInt(1, number);
			ResultSet result = statement.executeQuery();
			result.first();
			return result.getString("fact");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    
    public static void connectDB() throws SQLException {
    	String url =  "jdbc:postgresql://localhost:5432/vitalij?currentSchema=public";
    	conn = DriverManager.getConnection(url,"postgres","");
    }
    
}