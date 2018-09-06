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
		String url = "jdbc:postgresql://databanken.ucll.be:51718/lector?currentSchema=web3";
		properties.setProperty("user", "");
		properties.setProperty("password", "");
		Secret.setPass(properties );	// implements line 17 and 18
		properties.setProperty("ssl", "true");
		properties.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
		
		Connection connection = DriverManager.getConnection(url,properties);
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery( "SELECT * FROM country" );
		
		while(result.next()){
			String name = result.getString("name");
			String capital = result.getString("capital");
			int numberOfVotes = Integer.parseInt(result.getString("votes"));
			int numberOfInhabitants = Integer.parseInt(result.getString("inhabitants"));
			try {	// validation of data stored in database
				Country country = new Country(name, numberOfInhabitants, capital, numberOfVotes);
				System.out.println(country.toString());
			}
			catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		}
		statement.close();
		connection.close();
	}
}
