package server2;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.io.IOException;
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
@WebServlet("/RoomMates")
public class RoomMates extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Reuse.getInstance().addHeaders(response);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		try
		{
			PreparedStatement ps=Reuse.getInstance().getConnection().prepareStatement("select `currentHouse` from `users` where `Id`=?");
			ps.setString(1,Reuse.getInstance().tokenHeader(request));
			ResultSet rs=ps.executeQuery();
			rs.next();
			PreparedStatement vs=Reuse.getInstance().getConnection().prepareStatement("select `MonthlyRent`,`CurrentlyOccupied` from `houses` where `HouseId`=?");
			vs.setString(1,rs.getString(1));
			//System.out.println(rs.getString(1));
			ResultSet sr=vs.executeQuery();
			sr.next();
			int amount;
			
			amount=Integer.parseInt(sr.getString(1));
			if(!sr.getString(2).equals("") && Integer.parseInt(sr.getString(2))!=0){
			amount=amount/(Integer.parseInt(sr.getString(2)));
			}
			
			sr.close();
			ps=Reuse.getInstance().getConnection().prepareStatement("select `Id` from users where `currentHouse`=?");
			ps.setString(1,rs.getString(1));
			//ps.setString(2,Reuse.getInstance().tokenHeader(request));
			rs.close();
			rs=ps.executeQuery();
			HashMap<String,ArrayList<HashMap<String,Object>>> array=new HashMap<>();
			ArrayList<HashMap<String,Object>> al=new ArrayList<>();
			//System.out.println(rs.next());
			while(rs.next())
			{
				ps=Reuse.getInstance().getConnection().prepareStatement("select * from users where `Id`=?");
				ps.setString(1,rs.getString(1));
				//System.out.println(rs.getString(1));
				sr=ps.executeQuery();
				sr.next();
				if(sr.getString(9).equals("owner"))
				{
					continue;
				}
				
				HashMap<String,Object> hMap=new HashMap<>();
				hMap.put("name",sr.getString(1));
				hMap.put("contact",sr.getString(4));
				hMap.put("district",sr.getString(8));
				hMap.put("gender",sr.getString(10));
				hMap.put("logo",sr.getString(11));
				hMap.put("amount",amount);
				al.add(hMap);
				sr.close();
			}
			rs.close();
			array.put("result",al);
			response.getWriter().print(new Gson().toJson(array,new TypeToken<Map<String,ArrayList<HashMap<String,Object>>>>(){}.getType()));
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

}
