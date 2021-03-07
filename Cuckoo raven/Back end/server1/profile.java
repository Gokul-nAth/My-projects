package server1;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Reuseable.Reuse;
@WebServlet("/profile")
public class profile extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Reuse.getInstance().addHeaders(resp);
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			PreparedStatement preparedStatement = Reuse.getInstance().getConnection().prepareStatement("SELECT `Name`, `Password`, `Phone`,"
					+ " `AccountType`, `Gender`,`Street`,`Town`,`District`,"
					+ "`Email`,`Logo` FROM `users` WHERE `Id`=?");
			preparedStatement.setString(1, Reuse.getInstance().tokenHeader(req));
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
//			map
			map.put("name", resultSet.getString(1));
			map.put("password", resultSet.getString(2));
			map.put("accountType", resultSet.getString(4));
			map.put("gender", resultSet.getString(5));
			map.put("phone", resultSet.getString(3));
			map.put("street", resultSet.getString(6));
			map.put("town", resultSet.getString(7));
			map.put("district", resultSet.getString(8));
			map.put("email", resultSet.getString(9));
			map.put("logo", resultSet.getString(10));
			try {
				resp.getWriter().print(Reuse.getInstance().mapToJsonParse(map));
				resp.setStatus(HttpServletResponse.SC_ACCEPTED);
			}
			catch(Exception e) {
				resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
				
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
