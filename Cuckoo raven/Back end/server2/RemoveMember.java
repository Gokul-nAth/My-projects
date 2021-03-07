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
@WebServlet("/RemoveMember")
public class RemoveMember extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Reuse.getInstance().addHeaders(response);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		Map<String,Object> map=Reuse.getInstance().jsonStringToMapParse(request);
		//System.out.print(map.get("userId").toString());
		try
		{
			PreparedStatement ps=Reuse.getInstance().getConnection().prepareStatement("select `currentHouse` from `users` where `Id`=?");
			ps.setString(1,map.get("userId").toString());
			ResultSet rs=ps.executeQuery();
			rs.next();
			String str=rs.getString(1);
			rs.close();
			ps.close();
			
			ps=Reuse.getInstance().getConnection().prepareStatement("update users set `currentHouse`='' where `Id`=?");
			ps.setString(1,map.get("userId").toString());
			ps.execute();
			ps.close();
			
			ps=Reuse.getInstance().getConnection().prepareStatement("select `tenants`,`CurrentlyOccupied` from `houses` where `HouseId`=?");
			ps.setString(1,str);
			rs=ps.executeQuery();
			ArrayList<String> al=null;
			int c=0;
			rs.next();
			al=Reuse.getInstance().stringToArrayList(rs.getString(1));
			if(al.indexOf(map.get("userId").toString())!=-1){
				al.remove(map.get("userId").toString());
				if(!rs.getString(2).equals(""))
				{
				c=Integer.parseInt(rs.getString(2));
				c--;
				}
			}
			
			//rs.close();
			//ps.close();
			
			String str1=Reuse.getInstance().arrayListToString(al);
			ps=Reuse.getInstance().getConnection().prepareStatement("update `houses` set `tenants`=?,`CurrentlyOccupied`=? where `HouseId`=?");
			ps.setString(1,str1);
			if(!rs.getString(2).equals(""))
			{
			ps.setString(2,String.valueOf(c));
			}
			else
			{
				ps.setString(2, "");
			}
			ps.setString(3,str);
			ps.execute();
			rs.close();
			ps.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
