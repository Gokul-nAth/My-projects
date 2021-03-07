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

@WebServlet("/incomingRequests")
public class InRequest extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Reuse.getInstance().addHeaders(resp);
		ArrayList<HashMap<String,Object>> toBeGiven = new ArrayList<HashMap<String,Object>>();
		Map<String,Object> m = Reuse.getInstance().jsonStringToMapParse(req);
//		System.out.println(m);
		if(m!=null) {
		String idHouse = m.get("houseId").toString();
		try {
			PreparedStatement preparedStatement = Reuse.getInstance().getConnection().prepareStatement("Select `RequestId` from `houses` where `HouseId`=? ");
			preparedStatement.setString(1, idHouse);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			ArrayList<String> RequestId = Reuse.getInstance().stringToArrayList(resultSet.getString(1));
			for(String i:RequestId) {
				PreparedStatement preparedStatement2 = Reuse.getInstance().getConnection().prepareStatement("Select `Name`,`Phone`,`Logo` from `users` where `Id`=?");
				preparedStatement2.setString(1, i);
				ResultSet resultSet1 = preparedStatement2.executeQuery();
				resultSet1.next();
				HashMap<String,Object> hm = new HashMap<String, Object>();
				hm.put("contact", resultSet1.getObject(2));
				hm.put("image", resultSet1.getObject(3));
				hm.put("name", resultSet1.getObject(1));
				hm.put("requestId", i);
				toBeGiven.add(hm);
			}
			Map<String,ArrayList<HashMap<String,Object>>> map = new HashMap<String,ArrayList<HashMap<String,Object>>>();
			map.put("incomingRequests",toBeGiven);
			resp.getWriter().print(new Gson().toJson(map,new TypeToken<Map<String,ArrayList<HashMap<String,Object>>>>(){}.getType()));
			resp.setStatus(HttpServletResponse.SC_OK);
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		}
		else {
			System.out.print("SUCCCESSSS");
//			resp.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
		}
	}
}