package server1;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Reuseable.Reuse;
@WebServlet("/UpdateProfile")
public class UpdateProfile extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Reuse.getInstance().addHeaders(resp);
		Map<String,Object> map = Reuse.getInstance().jsonStringToMapParse(req);
		System.out.println(map);
		if(map!=null) {
			try {
				PreparedStatement preparedStatement = Reuse.getInstance().getConnection().prepareStatement("UPDATE `users` SET `Name`=?,`Email`=?,`Password`=?,`"
						+ "Phone`=?"
						+ ",`Street`=?,`Town`=?,`District`=?,`AccountType`=?,`Gender`=?,`Logo`=? WHERE `Id`=?");
				preparedStatement.setString(1, map.get("name").toString());
				preparedStatement.setString(2, map.get("email").toString());
				preparedStatement.setString(3, map.get("password").toString());
				preparedStatement.setString(4, map.get("phone").toString());
				preparedStatement.setString(5, map.get("street").toString());
				preparedStatement.setString(6, map.get("town").toString());
				preparedStatement.setString(7, map.get("district").toString());
				preparedStatement.setString(8, map.get("accountType").toString());
				preparedStatement.setString(9, map.get("gender").toString());
				if(map.get("logo")!=null) {
				preparedStatement.setString(10, map.get("logo").toString());}
				else {
					preparedStatement.setString(10, "");
				}
				preparedStatement.setString(11, Reuse.getInstance().tokenHeader(req));
				preparedStatement.execute();
				resp.setStatus(HttpServletResponse.SC_OK);
				System.out.print("Success");
			
			} catch (Exception e) {
				e.printStackTrace();
				resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
			}
		}
		
		

	}
}
