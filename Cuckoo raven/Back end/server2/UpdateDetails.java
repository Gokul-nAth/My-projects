package server2;
import java.math.*;
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
@WebServlet("/UpdateDetails")
public class UpdateDetails extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Reuse.getInstance().addHeaders(response);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		Map<String,Object> map=Reuse.getInstance().jsonStringToMapParse(request);
		try
		{
			PreparedStatement ps=Reuse.getInstance().getConnection().prepareStatement("update `houses` set `MonthlyRent`=?,`Feature`=?,`MaxSharing`=?,`CurrentlyOccupied`=?,`Street`=?,`Town`=?,`District`=?,`Contact`=?,`type`=? where `HouseId`=?");
			ps.setString(1,map.get("monthlyRent").toString());
			ps.setString(2,map.get("feature").toString());
			ps.setString(3,map.get("maximumSharing").toString());
			ps.setString(4,map.get("currentlyOccupied").toString().replaceAll("\\.?0*$", ""));
			//System.out.println(String.format("%d",(Double)map.get("currentlyOccupied")));
			
			ps.setString(5,map.get("street").toString());
			ps.setString(6,map.get("town").toString());
			ps.setString(7,map.get("district").toString());
			ps.setString(8,map.get("contact").toString());
			ps.setString(9,map.get("rentalType").toString());
			ps.setString(10,map.get("houseId").toString());
			ps.execute();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
