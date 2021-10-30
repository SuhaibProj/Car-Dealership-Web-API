package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Vehicle;
import models.VehicleDAO;

@SuppressWarnings("serial")
public class ServletRegex extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		RequestDispatcher view = req.getRequestDispatcher("admin.jsp");
		view.forward(req, res);

	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		VehicleDAO dao = new VehicleDAO();
		ArrayList<Vehicle> allVehicles = new ArrayList<Vehicle>();
		
		System.out.print("Checking license_number registrations...");
		try {
			allVehicles = dao.getAllVehicles();
		
			String regEx = "^([A-Z]{3}\\s?(\\d{3}|\\d{2}|d{1})\\s?[A-Z])|([A-Z]\\s?(\\d{3}|\\d{2}|\\d{1})\\s?[A-Z]{3})|(([A-HK-PRSVWY][A-HJ-PR-Y])\\s?([0][2-9]|[1-9][0-9])\\s?[A-HJ-PR-Z]{3})$";
			for (Vehicle v: allVehicles) {
				PrintWriter out = res.getWriter();
				Pattern pattern = Pattern.compile(regEx);
				Matcher identical =  pattern.matcher(v.getLicense());
				if (identical.find() && v.getLicense().length() <= 7) {
					System.out.println(v);
					System.out.println("------------------------------");
					System.out.println("Valid License Registration");
					System.out.println("------------------------------");
					//out.println(v);
				}else {
					System.out.println(v);
					System.out.println("------------------------------");
					System.out.println("Not Valid License Registration");
					System.out.println("------------------------------");
				}
			}
		} catch (SQLException e) {	e.printStackTrace();	}
	}
	
}
