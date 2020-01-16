package idv.peter.publishers.dao;

import idv.peter.publishers.Publisher;
import static idv.peter.common.Common.DRIVER_CLASS;
import static idv.peter.common.Common.URL;
import static idv.peter.common.Common.USER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import static idv.peter.common.Common.PASSWORD;

public class PublisherDao implements Dao<Publisher>{
	List<Publisher> publishers;
	
	public PublisherDao() {
		try {
			Class.forName(DRIVER_CLASS);
			
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
	}
	
	@Override
	public List<Publisher> getAll() {
		publishers = new ArrayList<Publisher>();
		try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement stmt = conn.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);) {
			String sql = "SELECT PUBLISHER_ID, PUBLISHER_NAME, PHONE, CONTACT FROM PUBLISHER";
			
			
			try(ResultSet rs = stmt.executeQuery(sql);) {
					// JDBC's index 1-base
				while (rs.next()) {
					String publisher_id = rs.getString(1);
					String publisher_name = rs.getString(2);
					String phone = rs.getString(3);
					String contact = rs.getString(4);
					Publisher publisher = new Publisher(publisher_id, publisher_name, phone, contact);
					publishers.add(publisher);
				}
			}
						
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return publishers;
	}
	
	
	@Override
	public int update(Publisher publisher) {
		int rows = 0;
		String sql = "UPDATE PUBLISHER SET PUBLISHER_NAME = ?,"
				+ "CONTACT = ?,"
				+ "PHONE = ? "
				+ "WHERE PUBLISHER_ID = ?";
		
		try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement pstmt = connection.prepareStatement(sql);) {
			pstmt.setString(1, publisher.getPublisher_name());
			pstmt.setString(2, publisher.getContact());
			pstmt.setString(3, publisher.getPhone());
			pstmt.setString(4, publisher.getPublisher_id());
			
			rows = pstmt.executeUpdate();
			
			return rows;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return rows;
		}
	}
	
	
	
	
	@Override
	public int delete(Publisher publisher) {
		int rows = 0;
		String sql = "DELETE FROM PUBLISHER "
				+ "WHERE PUBLISHER_ID = ?";
		
		try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement pstmt = connection.prepareStatement(sql);) {
			pstmt.setString(1, publisher.getPublisher_id());
			
			rows = pstmt.executeUpdate();
			
			return rows;
		} catch (SQLException sqle2) {
			sqle2.printStackTrace();
			return rows;
		}
	}
	
	
}
