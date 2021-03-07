package server1;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Reuseable.Reuse;

@WebServlet("/sentRequests")
public class SentRequest extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Reuse.getInstance().addHeaders(resp);
		String current = Reuse.getInstance().tokenHeader(req);
		ArrayList<HashMap<String,Object>> toBeGiven = new ArrayList<HashMap<String,Object>>();

		try {
			PreparedStatement preparedStatement = Reuse.getInstance().getConnection().prepareStatement("select `SentId` from `users` where `Id`=?");
			preparedStatement.setString(1, current);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			ArrayList<String> HouseIds = Reuse.getInstance().stringToArrayList(resultSet.getString(1));
			for(String i:HouseIds) {
				PreparedStatement preparedStatement2 = Reuse.getInstance().getConnection().prepareStatement("Select `contact`,`image`,`OwnerName` from `Houses` where `HouseId`=?");
				preparedStatement2.setString(1, i);
				ResultSet resultSet1 = preparedStatement2.executeQuery();
				resultSet1.next();
				
				HashMap<String,Object> hm = new HashMap<String, Object>();
				hm.put("contact", resultSet1.getObject(1));
				
				String str=resultSet1.getString(2);
				Map xBox=new Gson().fromJson(str,new TypeToken<Map<String,Object>>(){}.getType());
				ArrayList<Object> g=(ArrayList<Object>)xBox.get("images");
				
				hm.put("image", g.get(0));
				hm.put("name", resultSet1.getObject(3));
				hm.put("requestId", i);
				toBeGiven.add(hm);
			}
			Map<String,ArrayList<HashMap<String,Object>>> map = new HashMap<String,ArrayList<HashMap<String,Object>>>();
			map.put("sentRequests",toBeGiven);
			resp.getWriter().print(new Gson().toJson(map,new TypeToken<Map<String,ArrayList<HashMap<String,Object>>>>(){}.getType()));
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}