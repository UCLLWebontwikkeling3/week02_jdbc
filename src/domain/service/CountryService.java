package domain.service;

import java.util.List;

import domain.db.CountryDB;
import domain.model.Country;

public class CountryService {
	private CountryDB db = new CountryDB();
	
	public void addCountry(Country country){
		db.add(country);
	}
	
	public List<Country> getCountries(){
		return db.getAll();
	}
	
	public void create(String name){
		addCountry(new Country(name));
	}

	public Country getMostPopularCountry() {
		int highestVotes = -1;
		Country mostPopular = null;
		List<Country> countryList = getCountries();
		for(int i = 0; i < countryList.size(); i++){
			if(countryList.get(i).getVotes() > highestVotes){
				mostPopular = countryList.get(i);
				highestVotes = mostPopular.getVotes();
			}
		}
		return mostPopular;
	}
}
