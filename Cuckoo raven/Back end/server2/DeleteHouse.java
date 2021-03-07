package server2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.sql.*;
import Reuseable.Reuse;
@WebServlet("/DeleteHouse")
public class DeleteHouse extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Reuse.getInstance().addHeaders(response);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		Map<String,Object> map = Reuse.getInstance().jsonStringToMapParse(request);
		try
		{
			PreparedStatement ps=Reuse.getInstance().getConnection().prepareStatement("select `tenants` from houses where `HouseId`=?");
			ps.setString(1,map.get("houseId").toString());
			ResultSet rs=ps.executeQuery();
			rs.next();

			//System.out.print(rs.getString(1).equals(""));
			
			if(!rs.getString(1).equals(""))
			{
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				rs.close();
				ps.close();
			}
			else
			{
			ps=Reuse.getInstance().getConnection().prepareStatement("UPDATE `houses` SET `flag`='0' WHERE `HouseId`=?");
			ps.setString(1,map.get("houseId").toString());
			ps.execute();
			ps.close();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
