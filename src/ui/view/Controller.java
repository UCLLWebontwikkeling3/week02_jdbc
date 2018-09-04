package ui.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.model.Country;
import domain.service.CountryService;

@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CountryService service = new CountryService();
       
    public Controller() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String destination = showCountries(request, response);
		
		RequestDispatcher view = request.getRequestDispatcher(destination);
		view.forward(request, response);		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Country country = new Country();

		List<String> result = new ArrayList<String>();
		getName(country, request, result);
		getCapital(country, request, result);
		getNumberOfInhabitants(country, request, result);
		getNumberOfVotes(country, request, result);

		String destination;
		if (result.size() > 0) {
			request.setAttribute("country", country);
			request.setAttribute("result", result);
			destination = "countryForm.jsp";
		} else {
			service.addCountry(country);
			destination = showCountries(request, response);
		}

		RequestDispatcher view = request.getRequestDispatcher(destination);
		view.forward(request, response);
	}

	private void getName(Country country, HttpServletRequest request,
			List<String> result) {
		String name = request.getParameter("name");
		try {
			country.setName(name);
			request.setAttribute("nameClass", "has-success");
		} catch (Exception exc) {
			request.setAttribute("nameClass", "has-error");
			result.add(exc.getMessage());
		}
	}

	private void getCapital(Country country, HttpServletRequest request,
			List<String> result) {
		String capital = request.getParameter("capital");
		try {
			country.setCapital(capital);
			request.setAttribute("capitalClass", "has-success");
		} catch (Exception exc) {
			request.setAttribute("capitalClass", "has-error");
			result.add(exc.getMessage());
		}
	}

	private void getNumberOfVotes(Country country, HttpServletRequest request,
			List<String> result) {
		String votes = request.getParameter("votes");
		try {
			int numberOfVotes = Integer.parseInt(votes);
			country.setVotes(numberOfVotes);
			request.setAttribute("votesClass", "has-success");
		} catch (NumberFormatException exc) {
			request.setAttribute("votesClass", "has-error");
			result.add("Please enter a valid number of votes!");
		} catch (Exception exc) {
			request.setAttribute("votesClass", "has-error");
			result.add(exc.getMessage());
		}
	}

	private void getNumberOfInhabitants(Country country,
			HttpServletRequest request, List<String> result) {
		String inhabitants = request.getParameter("inhabitants");
		try {
			int numberOfInhabitants = Integer.parseInt(inhabitants);
			country.setNumberInhabitants(numberOfInhabitants);
			request.setAttribute("inhabitantsClass", "has-success");
		} catch (NumberFormatException exc) {
			request.setAttribute("inhabitantsClass", "has-error");
			result.add("Please enter a valid number of inhabitants!");
		} catch (Exception exc) {
			request.setAttribute("inhabitantsClass", "has-error");
			result.add(exc.getMessage());
		}
	}

	private String showCountries(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Country mostPopular = service.getMostPopularCountry();
		request.setAttribute("popular", mostPopular);
		List<Country> countries = service.getCountries();
		request.setAttribute("countries", countries);
		return "countryOverview.jsp";
	}
}
