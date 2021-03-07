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

import Reuseable.Reuse;

@WebServlet("/detailedview")
public class DetailedView extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Reuse.getInstance().addHeaders(resp);
//		String reqId = Reuse.getInstance().jsonStringToMapParse(req).get("requestId").toString();
		Map <String,Object> m = Reuse.getInstance().jsonStringToMapParse(req);
		if(m!=null) {
		String reqId = m.get("requestId").toString();
//		System.out.print(reqId);
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			PreparedStatement preparedStatement = Reuse.getInstance().getConnection().prepareStatement("Select `Phone`,`Street`,`Town`,`District`,`Logo`,`Name` from `users` where `Id`=?");
			preparedStatement.setString(1, reqId);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			map.put("contact", resultSet.getString(1));
			map.put("street", resultSet.getString(2));
			map.put("town", resultSet.getString(3));
			map.put("district", resultSet.getString(4));
			map.put("logo", resultSet.getString(5));
			map.put("name", resultSet.getString(6));
			map.put("requestId", reqId);
			resp.getWriter().print(Reuse.getInstance().mapToJsonParse(map));
		} catch (Exception e) {
			e.printStackTrace();
		}}
		else {
			
		}
	}

}
