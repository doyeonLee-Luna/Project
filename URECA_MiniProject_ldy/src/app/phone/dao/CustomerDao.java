package app.phone.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.phone.common.DBManager;
import app.phone.dto.CustomerDto;

public class CustomerDao {
	
	// 고객 추가
	public int insertCustomer(CustomerDto customer) {
		int ret = -1;
		String sql = "insert into customer (name, address, phone) values (?, ?, ?); ";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, customer.getName());
			pstmt.setString(2, customer.getAddress());
			pstmt.setString(3, customer.getPhone());
			
			ret = pstmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(pstmt, con);		
		}
		
		return ret;
	}
	
	// 고객 정보 수정
	public int updateCustomer(CustomerDto customer) {
		int ret = -1;
		String sql = "update customer set name = ?, address = ?, phone = ? where custid = ?; ";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, customer.getName());
			pstmt.setString(2, customer.getAddress());
			pstmt.setString(3, customer.getPhone());
			pstmt.setInt(4, customer.getCustId());
			
			ret = pstmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(pstmt, con);		
		}
		
		return ret;
	}
	
	// 고객 삭제
	public int deleteCustomer(int custId) {
		int ret = -1;
		String sql = "delete from customer where custid = ?; ";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, custId);
			
			ret = pstmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(pstmt, con);		
		}
		
		return ret;
	}
	
	// 고객 목록 조회
	public List<CustomerDto> listCustomer(){
		List<CustomerDto> list = new ArrayList<>();
		
		String sql = "select * from customer; ";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBManager.getConnection();			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				CustomerDto customer = new CustomerDto();
				customer.setCustId(rs.getInt("custid"));
				customer.setName(rs.getString("name"));
				customer.setAddress(rs.getString("address"));
				customer.setPhone(rs.getString("phone"));
				list.add(customer);
			
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		
		return list;
	}
	
	// 고객 정보 검색
	public List<CustomerDto> listCustomer(String searchWord){
		List<CustomerDto> list = new ArrayList<>();
		
		String sql = "select * from customer where name like ?; ";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBManager.getConnection();			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + searchWord + "%");
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				CustomerDto customer = new CustomerDto();
				customer.setCustId(rs.getInt("custid"));
				customer.setName(rs.getString("name"));
				customer.setAddress(rs.getString("address"));
				customer.setPhone(rs.getString("phone"));
				list.add(customer);
			
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBManager.releaseConnection(pstmt, con);
		}
		
		return list;
	}
	
	// 고객 상세 정보 조회
	public CustomerDto detailCustomer(int custId){
		CustomerDto customer = null;
		
		String sql = "select * from customer where custid = ?; ";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBManager.getConnection();			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1,  custId);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				customer = new CustomerDto();
				customer.setCustId(rs.getInt("custid"));
				customer.setName(rs.getString("name"));
				customer.setAddress(rs.getString("address"));
				customer.setPhone(rs.getString("phone"));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBManager.releaseConnection(pstmt, con);
		}
		
		return customer;
	}
	
	
}
