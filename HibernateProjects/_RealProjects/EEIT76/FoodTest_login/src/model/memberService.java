package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class memberService {
	private DataSource ds;
	
	public memberService(){
		try {
			InitialContext context = new InitialContext();
			 ds = (DataSource)context.lookup("java:comp/env/jdbc/FoodDelivery");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	public String login(String account,String password){
		String passkey = null;
		try {
			String sql = "select * from member where account = ?";
			Connection conn = ds.getConnection();
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1,account);
			ResultSet rs = pStmt.executeQuery();
			
			while(rs.next()){
				passkey=rs.getString("password");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return passkey;
		}
		
		
		return passkey;
	}
	

}
