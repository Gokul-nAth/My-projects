package server2;
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
@WebServlet("/ViewMembers")
public class ViewMembers extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Reuse.getInstance().addHeaders(response);
		Map<String,Object> map = Reuse.getInstance().jsonStringToMapParse(request);
		try{
		PreparedStatement ps=Reuse.getInstance().getConnection().prepareStatement("select `tenants` from houses where `HouseId`=?");
		ps.setString(1,map.get("houseId").toString());
		//System.out.print(map.get("houseId").toString());
		ResultSet rs=ps.executeQuery();
		rs.next();
		ArrayList<String> ar=Reuse.getInstance().stringToArrayList(rs.getString(1));
		HashMap<String,ArrayList<HashMap<String,Object>>> array=new HashMap<>();
		ArrayList<HashMap<String,Object>> al=new ArrayList<>();
		int index=0;
		while(index<ar.size())
		{
			//System.out.println(ar.get(index));
			PreparedStatement v=Reuse.getInstance().getConnection().prepareStatement("select * from users where `Id`=?");
			v.setString(1,ar.get(index));
			ResultSet sr=v.executeQuery();
			sr.next();
			HashMap<String,Object> hMap=new HashMap<>();
			hMap.put("name",sr.getString(1));
			hMap.put("email",sr.getString(2));
			hMap.put("contact",sr.getString(4));
			hMap.put("district",sr.getString(8));
			hMap.put("gender",sr.getString(10));
			hMap.put("image",sr.getString(11));
			hMap.put("userId",sr.getString(5));
			al.add(hMap);
			index++;
			sr.close();
		}
		array.put("result",al);
		response.getWriter().print(new Gson().toJson(array,new TypeToken<Map<String,ArrayList<HashMap<String,Object>>>>(){}.getType()));
		rs.close();
		
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
	}
}
