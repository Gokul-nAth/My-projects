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

@WebServlet("/myhouses")
public class Houses extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Reuse.getInstance().addHeaders(resp);
		ArrayList<HashMap<String,Object>> list = new ArrayList<>();
		
		try {
			PreparedStatement preparedStatement = Reuse.getInstance().getConnection().prepareStatement("Select `Street`,`Town`,`District`,`HouseId`,`image`,`flag` from `houses` where `OwnerId`=?");
			preparedStatement.setString(1, Reuse.getInstance().tokenHeader(req));
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				if(!resultSet.getString(6).equals("0"))
				{
				HashMap<String, Object> hashMap = new HashMap<String,Object>();
				hashMap.put("address", resultSet.getString(1)+", "+resultSet.getString(2)+", "+resultSet.getString(3));
				hashMap.put("houseId", resultSet.getString(4));

				Map xBox=new Gson().fromJson(resultSet.getString(5),new TypeToken<Map<String,Object>>(){}.getType());
				ArrayList<Object> g=(ArrayList<Object>)xBox.get("images");
				hashMap.put("image", g.get(0));
				list.add(hashMap);
				}
			}
			Map<String,ArrayList<HashMap<String,Object>>> map = new HashMap<String,ArrayList<HashMap<String,Object>>>();
			map.put("myHouses",list);
			
			resp.getWriter().print(new Gson().toJson(map,new TypeToken<Map<String,ArrayList<HashMap<String,Object>>>>(){}.getType()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
