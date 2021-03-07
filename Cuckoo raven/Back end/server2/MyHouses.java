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
@WebServlet("/MyHouses")
public class MyHouses extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Reuse.getInstance().addHeaders(response);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		try{
			PreparedStatement ps=Reuse.getInstance().getConnection().prepareStatement("select `HouseId`,`flag` from houses where `OwnerId`=?");
			String id=Reuse.getInstance().tokenHeader(request);
			ps.setString(1,id);
			ResultSet rs=ps.executeQuery();
			int size=0;
			while(rs.next()){
				if(rs.getString(2).equals("1") || rs.getString(2).equals("2"))
				{
				size+=1;
				}
			}
			rs.close();
			ps=Reuse.getInstance().getConnection().prepareStatement("select `OwnerName`,`MonthlyRent`,`Town`,`District`,`image`,`type`,`HouseId`,`flag` from houses where `OwnerId`=?");
			ps.setString(1,id);
			rs=ps.executeQuery();
			HashMap[] array=new HashMap[size];
			size=0;
			while(rs.next())
			{
				if(!rs.getString(8).equals("0"))
				{
				HashMap<String,Object> map=new HashMap<>();
				map.put("ownerName",rs.getString(1));
				map.put("monthlyRent",rs.getString(2));
				map.put("town",rs.getString(3));
				map.put("district",rs.getString(4));
				
				String str=rs.getString(5);
				Map xBox=new Gson().fromJson(str,new TypeToken<Map<String,Object>>(){}.getType());
				ArrayList<Object> g=(ArrayList<Object>)xBox.get("images");
				map.put("image", g.get(0));
				
				map.put("rentalType",rs.getString(6));
				map.put("houseId",rs.getString(7));
				if(rs.getInt(8)==2) {
					map.put("hold", true);
				}
				else if(rs.getInt(8)==1) {
					map.put("hold", false);
				}
				array[size++]=map;
				}
			}
			HashMap<String,HashMap[]> myHouses=new HashMap<String,HashMap[]>();
			myHouses.put("myHouses",array);
			rs.close();
			
			response.getWriter().print(new Gson().toJson(myHouses,new TypeToken<Map<String,HashMap[]>>(){}.getType()));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
