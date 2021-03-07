package server2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Reuseable.Reuse;
@WebServlet("/MyHouseDetails")
public class MyHouseDetails extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Reuse.getInstance().addHeaders(response);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		Map<String,Object> map=Reuse.getInstance().jsonStringToMapParse(request);
		try
		{
			PreparedStatement ps=Reuse.getInstance().getConnection().prepareStatement("select * from houses where `HouseId`=?");
			String houseId=map.get("houseId").toString();
			ps.setString(1,houseId);
			ResultSet rs=ps.executeQuery();
			rs.next();
			HashMap<String,Object> hMap=new HashMap<>();
			hMap.put("monthlyRent",rs.getString(2));
			hMap.put("feature",rs.getString(3));
			hMap.put("maximumSharing",rs.getString(4));
			hMap.put("currentlyOccupied",rs.getString(5));
			hMap.put("street",rs.getString(6));
			hMap.put("town",rs.getString(7));
			hMap.put("district",rs.getString(8));
			hMap.put("contact",rs.getString(9));
			hMap.put("rentalType",rs.getString(14));
			if(rs.getInt(16)==2) {
				hMap.put("hold", true);
			}
			else if(rs.getInt(16)==1) {
				hMap.put("hold", false);
			}
			rs.close();
			
			response.getWriter().print(new Gson().toJson(hMap,new TypeToken<Map<String,Object>>(){}.getType()));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
