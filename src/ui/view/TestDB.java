package ui.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import domain.db.Secret;
import domain.model.Country;

public class TestDB {
	public static void main(String[] args) throws SQLException {
		Properties properties = new Properties();
		String url = "jdbc:postgresql://databanken.ucll.be:51718/lector";
		properties.setProperty("user", "");
		properties.setProperty("password", "");
		Secret.setPass(properties );
		properties.setProperty("ssl", "true");
		properties.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
		
		Connection connection = DriverManager.getConnection(url,properties);
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery( "SELECT * FROM greetje.products" );
		
		while(result.next()){
			String name = result.getString("name");
			int price = Integer.parseInt(result.getString("price"));
			String color = result.getString("color");

			System.out.println(name);
		}
		statement.close();
		connection.close();
	}
}
