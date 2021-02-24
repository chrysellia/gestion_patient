package application.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	
	private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	private final String DB_URL = "jdbc:mysql://localhost:3306/gestion_patient?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	
	//  Database credentials
	private final String USER = "root";
	private final String PASS = "";

	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
	public Database () {}
	
	public void connect() {
		try {
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			
			//STEP 3: Open a connection
			System.out.println("Connecting to database...");
			this.conn = DriverManager.getConnection(this.DB_URL, this.USER, this.PASS);
			
			this.stmt = this.conn.createStatement();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void disconnect() {
		try {
			this.rs.close();
			this.stmt.close();
			this.conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ResultSet execute(String sql) {
		try {
			this.rs = this.stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return this.rs;
	}
	
	public ResultSet update(String sql) {
		try {
			this.stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public Statement prepare(String sql, String[] columns) {
		Statement stmt = null;
		try {
			stmt = this.conn.prepareStatement(sql, columns);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return stmt;
	}
}
