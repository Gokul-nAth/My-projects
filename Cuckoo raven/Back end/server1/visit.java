package server1;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;

import Reuseable.Reuse;
@WebServlet("/visit")
public class visit extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Reuse.getInstance().addHeaders(resp);
		try {
			PreparedStatement preparedStatement = Reuse.getInstance().getConnection().prepareStatement("SELECT `OwnerName`,"
					+ " `MonthlyRent`,"
					+ " `Feature`, `MaxSharing`, `CurrentlyOccupied`, `Street`, `Town`, "
					+ "`District`, `Contact`, `image`,`type`"
					+ "FROM `houses` WHERE `HouseId`=?");
			preparedStatement.setString(1, Reuse.getInstance().jsonStringToMapParse(req).get("houseId").toString());
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("ownerName",resultSet.getString(1));
			map.put("monthlyRent",String.valueOf(resultSet.getInt(2)));
			map.put("feature",resultSet.getString(3));
			map.put("maximumSharing",resultSet.getString(4));
			map.put("currentlyOccupied",resultSet.getString(5));
			map.put("street",resultSet.getString(6));
			map.put("town",resultSet.getString(7));
			map.put("district",resultSet.getString(8));
			map.put("contact",resultSet.getString(9));
			map.put("rentalType",resultSet.getString(11));
			Gson gson = new Gson();
			Map<String,Object> tempMap = gson.fromJson(resultSet.getString(10), new TypeToken<Map<String, Object>>(){}.getType());
			
			map.put("images",tempMap.get("images"));
//			resp.getWriter().print(resultSet.getString(10).substring(10));
			resp.getWriter().print(Reuse.getInstance().mapToJsonParse(map));
			
			
//			System.out.println(Reuse.getInstance().jsonStringToMapParse(req));
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}