package server1;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Reuseable.Reuse;

@WebServlet("/acceptrequest")
public class AcceptRequest extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Reuse.getInstance().addHeaders(resp);
		Map<String,Object> map = Reuse.getInstance().jsonStringToMapParse(req);
		ArrayList<String> sentId = null;
		ArrayList<String> currentHouse =null;
		if(map!=null) {
//		System.out.println(map);
			try {
				//user
				PreparedStatement preparedStatement = Reuse.getInstance().getConnection().prepareStatement("Select `SentId`,`currentHouse` from `users` where `Id`=?");
				preparedStatement.setString(1, map.get("requestId").toString());
				ResultSet resultSet = preparedStatement.executeQuery();
				if(resultSet.next()) {
				sentId = Reuse.getInstance().stringToArrayList(resultSet.getString(1));
				currentHouse = Reuse.getInstance().stringToArrayList(resultSet.getString(2));
				boolean flag=sentId.remove(map.get("houseId").toString());
				if(flag)
				{
				currentHouse.add(map.get("houseId").toString());
				}
				else
				{
					resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				}
				}
				preparedStatement = Reuse.getInstance().getConnection().prepareStatement("Update `users` set `SentId`=?,`currentHouse`=? where `Id`=?");
				preparedStatement.setString(3, map.get("requestId").toString());
				preparedStatement.setString(1, Reuse.getInstance().arrayListToString(sentId));
				preparedStatement.setString(2, Reuse.getInstance().arrayListToString(currentHouse));
				preparedStatement.execute();
				
//				//houses
				int currently = -1;
				 preparedStatement = Reuse.getInstance().getConnection().prepareStatement("Select `RequestId`,`tenants`,`CurrentlyOccupied` from `houses` where `HouseId`=?");
				 preparedStatement.setString(1, map.get("houseId").toString());
				 resultSet = preparedStatement.executeQuery();
				 if(resultSet.next()) {
					sentId = Reuse.getInstance().stringToArrayList(resultSet.getString(1));
					currentHouse = Reuse.getInstance().stringToArrayList(resultSet.getString(2));
					boolean flag=sentId.remove(map.get("requestId").toString());
					if(flag)
					{
					currentHouse.add(map.get("requestId").toString());
					}
					else
					{
						resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					}
					if(!resultSet.getString(3).equals("")) {
//						System.out.println(resultSet.getString(3)+"|fasfas");
						currently = Integer.parseInt(resultSet.getString(3))+1;
					}
//					System.out.println(sentId+"|"+currentHouse);
				 }
				 preparedStatement = Reuse.getInstance().getConnection().prepareStatement("Update `houses` set `RequestId`=?,`tenants`=?,`CurrentlyOccupied`=? where `HouseId`=?");
				 preparedStatement.setString(4, map.get("houseId").toString());
				 preparedStatement.setString(1, Reuse.getInstance().arrayListToString(sentId));
				 preparedStatement.setString(2, Reuse.getInstance().arrayListToString(currentHouse));
				 if(currently==-1) {
					 preparedStatement.setString(3, "");
				 }
				 else {
					 preparedStatement.setString(3, String.valueOf(currently));
				 }
				 preparedStatement.execute();
//				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		
		}
		
	}
}