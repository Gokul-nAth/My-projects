package server2;
import java.io.IOException;
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
@WebServlet("/checkauthstatus")
public class Logo extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Reuse.getInstance().addHeaders(response);
		try
		{
			PreparedStatement ps=Reuse.getInstance().getConnection().prepareStatement("select `Logo`,`Name`,`AccountType` from `users` where `Id`=?");
			ps.setString(1,Reuse.getInstance().tokenHeader(request));
			ResultSet rs=ps.executeQuery();
			rs.next();
			Map<String,Object> map=new HashMap<String,Object>();
			if(!rs.getString(1).equals("") || rs.getString(1)!=null){
				map.put("logo",rs.getString(1));	
			}
			else
			{
				map.put("logo",null);
			}
			map.put("userName", rs.getString(2));
			map.put("userType", rs.getString(3));
			response.getWriter().print(Reuse.getInstance().mapToJsonParse(map));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
