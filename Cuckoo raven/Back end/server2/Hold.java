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
@WebServlet("/hold")
public class Hold extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Reuse.getInstance().addHeaders(response);
		Map<String,Object> map=Reuse.getInstance().jsonStringToMapParse(request);
		try
		{
			PreparedStatement ps;
			if(map.get("flag").toString().equals("hold"))
			{
				ps=Reuse.getInstance().getConnection().prepareStatement("update houses set flag='2' where HouseId=?");
			}
			else
			{
				ps=Reuse.getInstance().getConnection().prepareStatement("update houses set flag='1' where HouseId=?");
			}
			ps.setString(1,map.get("houseId").toString());
			ps.execute();
			ps.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
