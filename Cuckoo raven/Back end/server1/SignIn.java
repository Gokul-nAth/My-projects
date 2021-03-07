package server1;

import java.io.IOException;
//import java.net.http.HttpRequest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Reuseable.Reuse;
@WebServlet("/SignIn")
public class SignIn extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Reuse.getInstance().addHeaders(resp);
		resp.setContentType("application/json");resp.setCharacterEncoding("UTF-8");
		Map<String,Object> map = Reuse.getInstance().jsonStringToMapParse(req);
		try {
			Reuse.getInstance().getConnection();
//			System.out.print("Success");
			PreparedStatement preparedStatement =  Reuse.getInstance().getConnection().prepareStatement("SELECT  `Email`, `Password`,`Id`,`AccountType`,`Logo`,`Name` FROM `users` WHERE `Email`=?");
			preparedStatement.setString(1, map.get("email").toString());
			ResultSet resultSet = preparedStatement.executeQuery();
			try {
			resultSet.next();
			if(map.get("email").equals(resultSet.getString(1)) && map.get("password").equals(resultSet.getString(2))) {
				resp.setStatus(HttpServletResponse.SC_ACCEPTED);
				String str = resultSet.getString(3);
				if(resultSet.getString(5).equals("") || resultSet.getString(5)==null) {
				resp.getWriter().print(Reuse.getInstance().mapToJsonParse(new HashMap<String, Object>(){{put("token", str);put("userType",resultSet.getString(4));put("logo",null);put("userName",resultSet.getString(6));}}));
				}
				else {
					resp.getWriter().print(Reuse.getInstance().mapToJsonParse(new HashMap<String, Object>(){{put("token", str);put("userType",resultSet.getString(4));put("logo",resultSet.getString(5));put("userName",resultSet.getString(6));}}));
					
				}
				
			}
			else {
				resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
			}
			}
			catch(SQLException s) {resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);}
		
		
		} 
		
		
		
		
		catch (Exception e) {
			e.printStackTrace();
		} 
	}
}