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

import practice.Mail;
import Reuseable.Reuse;

@WebServlet("/SendRequest")
public class SendRequest extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Reuse.getInstance().addHeaders(resp);
		Map<String, Object> map = Reuse.getInstance().jsonStringToMapParse(req);
		String name=null;
		boolean flag = true;
		try {
			
			PreparedStatement preparedStatement4 = Reuse.getInstance().getConnection().prepareStatement("select `currentHouse`,`Name` from `users` where Id=? ");
			preparedStatement4.setString(1, Reuse.getInstance().tokenHeader(req));
			ResultSet rs = preparedStatement4.executeQuery();rs.next();
			if(!rs.getString(1).equals("")) {
				flag = false;
			}
			else
			{
				name=rs.getString(2);
				/*PreparedStatement ps=Reuse.getInstance().getConnection().prepareStatement("select `OwnerId` from `houses` where `HouseId`=?");
				ps.setString(1,rs.getString(1));
				ResultSet v=ps.executeQuery();
				v.next();
				ps.close();
				
				ps=Reuse.getInstance().getConnection().prepareStatement("select `Email` from `users` where Id=?");
				ps.setString(1, v.getString(1));
				v.close();
				v=ps.executeQuery();
				v.next();*/
				
				
			}
		}
		catch(Exception e) {}
		
		
		
		
		if(flag) {
		
		
		
		
		
		
		try {
			PreparedStatement preparedStatement = Reuse.getInstance().getConnection().prepareStatement("Select `RequestId` from `houses` where `HouseId`=?");
			preparedStatement.setString(1, map.get("houseId").toString());
			
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			String str = resultSet.getString(1);
			
			if(name.equals("Dikshit"))
			{
			PreparedStatement p2=Reuse.getInstance().getConnection().prepareStatement("select `OwnerId` from `houses` where `RequestId`=?");
			p2.setString(1, str);
			ResultSet rs2=p2.executeQuery();
			rs2.next();
			//p2.close();
			PreparedStatement p3=Reuse.getInstance().getConnection().prepareStatement("select `Email` from `users` where `Id`=?");
			p3.setString(1, rs2.getString(1));
			//rs2.close();
			ResultSet rs3=p3.executeQuery();
			rs3.next();
			
			new Mail(rs3.getString(1),name);
			}
			
			
			PreparedStatement preparedStatement1 = Reuse.getInstance().getConnection().prepareStatement("Update `houses` Set `RequestId`=? where `HouseId`=?");
			preparedStatement1.setString(2, map.get("houseId").toString());

			ArrayList<String> array = null;
			if(str==null || str=="") {
				array = new ArrayList<>();
				array.add(Reuse.getInstance().tokenHeader(req));
				preparedStatement1.setString(1, Reuse.getInstance().arrayListToString(array));
			}
			else {
				array = Reuse.getInstance().stringToArrayList(str);
				if(array.indexOf(Reuse.getInstance().tokenHeader(req))==-1) {
					array.add(Reuse.getInstance().tokenHeader(req));
					preparedStatement1.setString(1, Reuse.getInstance().arrayListToString(array));
				}
				else {
					preparedStatement.close();
					resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				}
			}
			preparedStatement1.execute();
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			PreparedStatement preparedStatement = Reuse.getInstance().getConnection().prepareStatement("Select `SentId` from `users` where `Id`=?");
			preparedStatement.setString(1, Reuse.getInstance().tokenHeader(req));
			
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			String str = resultSet.getString(1);
			
			
			
			PreparedStatement preparedStatement1 = Reuse.getInstance().getConnection().prepareStatement("Update `users` Set `SentId`=? where `Id`=?");
			preparedStatement1.setString(2, Reuse.getInstance().tokenHeader(req));

			ArrayList<String> array = null;
			if(str==null || str =="") {
				array = new ArrayList<>();
				array.add(map.get("houseId").toString());
				preparedStatement1.setString(1, Reuse.getInstance().arrayListToString(array));
			}
			else {
				array = Reuse.getInstance().stringToArrayList(str);
				if(array.indexOf(map.get("houseId").toString())==-1) {
					array.add(map.get("houseId").toString());
					preparedStatement1.setString(1, Reuse.getInstance().arrayListToString(array));
				}
				else {
					preparedStatement.close();
					resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				}
			}
			preparedStatement1.execute();
			
		
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		else {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		
		
	}
}