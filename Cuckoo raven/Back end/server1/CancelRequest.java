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

import Reuseable.Reuse;

@WebServlet("/cancelrequest")
public class CancelRequest extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Reuse.getInstance().addHeaders(resp);
		Map<String,Object> m = Reuse.getInstance().jsonStringToMapParse(req);
		if(m!=null) {
		String houseId = m.get("requestId").toString();
		String userId = Reuse.getInstance().tokenHeader(req);
//		System.out.println(Reuse.getInstance().jsonStringToMapParse(req));

		try {
			
			//users
			PreparedStatement preparedStatement = Reuse.getInstance().getConnection().prepareStatement("Select `SentId` from `users` where `Id`=?");
			preparedStatement.setString(1, userId);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				ArrayList<String> temp = Reuse.getInstance().stringToArrayList(resultSet.getString(1));
				temp.remove(houseId);
				PreparedStatement ps = Reuse.getInstance().getConnection().prepareStatement("update `users` SET `SentId`=? where `Id`=?");
				ps.setString(2, userId);
				ps.setString(1, Reuse.getInstance().arrayListToString(temp));
				ps.execute();
			}
			preparedStatement.close();
			resultSet.close();
			
			//house
			PreparedStatement preparedStatement1 = Reuse.getInstance().getConnection().prepareStatement("Select `RequestId` from `houses` where `HouseId`=?");
			preparedStatement1.setString(1, houseId);
			resultSet = preparedStatement1.executeQuery();
			if(resultSet.next()) {
				ArrayList<String> temp = Reuse.getInstance().stringToArrayList(resultSet.getString(1));
				temp.remove(userId);
				PreparedStatement ps = Reuse.getInstance().getConnection().prepareStatement("update `houses` SET `RequestId`=? where `HouseId`=?");
				ps.setString(1, Reuse.getInstance().arrayListToString(temp));
				ps.setString(2, houseId);
				ps.execute();
			}	
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
	}
}