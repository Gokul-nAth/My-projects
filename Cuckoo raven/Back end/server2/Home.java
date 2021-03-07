package server2;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.sql.*;
import Reuseable.Reuse;
@WebServlet("/Home")
public class Home extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Reuse.getInstance().addHeaders(response);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		//town,district,image
		try
		{
			Statement st=Reuse.getInstance().getConnection().createStatement();
			ResultSet rs=st.executeQuery("select OwnerName,MonthlyRent,Contact,HouseId,Town,District,image,OwnerId,flag,MaxSharing,CurrentlyOccupied,tenants,type from `houses`");
			ArrayList<HashMap<String,Object>> arr=new ArrayList<HashMap<String,Object>>();
			int c=0;
			while(rs.next())
			{
				
				if(rs.getString(8).equals(Reuse.getInstance().tokenHeader(request)) || rs.getString(9).equals("0") || rs.getString(9).equals("2")){
					continue;
				}
				if(rs.getString(10).equals("") && !rs.getString(12).equals(""))
				{
					continue;
				}
				if(rs.getString(10).equals("") || Integer.parseInt(rs.getString(10))!=Integer.parseInt(rs.getString(11))) 
				{
				HashMap<String,Object> map=new HashMap<String,Object>();
				map.put("ownerName",rs.getString(1));
				map.put("monthlyRent",rs.getString(2));
				map.put("contact",rs.getString(3));
				map.put("houseId",rs.getString(4));
				map.put("town",rs.getString(5));
				map.put("district",rs.getString(6));
				map.put("rentalType",rs.getString(13));
				map.put("maximumSharing",rs.getString(10));
				String str=rs.getString(7);
				Map xBox=new Gson().fromJson(str,new TypeToken<Map<String,Object>>(){}.getType());
				ArrayList<Object> g=(ArrayList<Object>)xBox.get("images");
				map.put("image", g.get(0));
				arr.add(map);
				}
			}
			HashMap<String,ArrayList<HashMap<String,Object>>> houses=new HashMap<String,ArrayList<HashMap<String,Object>>>();
			houses.put("houses",arr);
			rs.close();
			
			response.getWriter().print(new Gson().toJson(houses,new TypeToken<Map<String,ArrayList<HashMap<String,Object>>>>(){}.getType()));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}