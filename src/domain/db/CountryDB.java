package domain.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import domain.model.Country;

public class CountryDB {
	private Statement statement;
	
	public CountryDB() {
		Properties properties = new Properties();
		String url = "jdbc:postgresql://databanken.ucll.be:51718/lector?currentSchema=web3";
		properties.setProperty("user", "r123456");
		properties.setProperty("password", "pass");
		Secret.setPass(properties);
		properties.setProperty("ssl", "true");
		properties.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
		Connection connection;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, properties);
			statement = connection.createStatement();
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			throw new DbException(e.getMessage(), e);
		}

	}
	
	public void add(Country country){
		if(country == null){
			throw new DbException("Nothing to add.");
		}
		String sql = "INSERT INTO country (name, capital, inhabitants, votes) VALUES ('"
				+ country.getName() + "', '" + country.getCapital() + "', "
				+ country.getNumberInhabitants() + ", "+ country.getVotes() + ")";
		try {
			statement.execute(sql);
		} catch (SQLException e) {
			throw new DbException(e);
		}
	}
	
	public List<Country> getAll(){
		List<Country> countries = new ArrayList<Country>();
		try {
			ResultSet result = statement.executeQuery( "SELECT * FROM country" );
			while(result.next()){
				String name = result.getString("name");
				String capital = result.getString("capital");
				int numberOfVotes = Integer.parseInt(result.getString("votes"));
				int numberOfInhabitants = Integer.parseInt(result.getString("inhabitants"));
				Country country= new Country(name, numberOfInhabitants,capital, numberOfVotes);
				countries.add(country);
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
		return countries;
	}
}
