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
@WebServlet("/AddHouse")
public class AddHouse extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Reuse.getInstance().addHeaders(response);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		Map<String,Object> map = Reuse.getInstance().jsonStringToMapParse(request);
		try
		{
			PreparedStatement ps = Reuse.getInstance().getConnection().prepareStatement("insert into `houses`(`OwnerName`,`MonthlyRent`,`Feature`,`MaxSharing`,`CurrentlyOccupied`,`Street`,`Town`,`District`,`Contact`,`OwnerId`,`HouseId`,`image`,`Request`,`type`) "
					+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			//ps.setString(1, map.get("ownerName").toString());
			PreparedStatement q=Reuse.getInstance().getConnection().prepareStatement("select `Name` from `users` where `Id`=?");
			q.setString(1,Reuse.getInstance().tokenHeader(request));
			ResultSet rs=q.executeQuery();
			rs.next();
			ps.setString(1,rs.getString(1));
			rs.close();
			ps.setInt(2,Integer.parseInt(map.get("monthlyRent").toString()));
			ps.setString(3, map.get("feature").toString());
			ps.setString(4,map.get("maximumSharing").toString());
			ps.setString(5,map.get("currentlyOccupied").toString());
			ps.setString(6, map.get("street").toString());
			ps.setString(7, map.get("town").toString());
			ps.setString(8, map.get("district").toString());
			ps.setString(9, map.get("contact").toString());
			ps.setString(11,String.format("%06d", Reuse.getInstance().getSizeOf()));
			ps.setString(10,Reuse.getInstance().tokenHeader(request));
			ps.setInt(13,0);
			ps.setString(14,map.get("rentalType").toString());
			HashMap<String,Object> x=new HashMap<String,Object>();
			x.put("images",map.get("images"));
			Gson gson = new Gson();
			ps.setString(12, gson.toJson(x,new TypeToken<Map<String,Object>>(){}.getType()));
			ps.execute();
			PreparedStatement s=Reuse.getInstance().getConnection().prepareStatement("update `users` set `AccountType`='owner' where `Id`=?");
			s.setString(1,Reuse.getInstance().tokenHeader(request));
			s.execute(); 
			response.setStatus(200);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
