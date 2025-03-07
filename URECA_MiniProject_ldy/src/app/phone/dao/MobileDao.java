package app.phone.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.phone.common.DBManager;
import app.phone.dto.MobileDto;

public class MobileDao {
	
	// 휴대폰 등록
	public int insertMobile(MobileDto mobile) {
		int ret = -1;
		String sql = "insert into mobile (model, brand, price, stock) values (?, ?, ?, ?); ";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, mobile.getBrand());
			pstmt.setString(2, mobile.getModel());
			pstmt.setDouble(3, mobile.getPrice());
			pstmt.setInt(4, mobile.getStock());
			
			ret = pstmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(pstmt, con);		
		}
		
		return ret;
	}
	
	// 휴대폰 정보 수정
	public int updateMobile(MobileDto mobile) {
		int ret = -1;
		String sql = "update mobile set brand = ?, model = ?, price = ?, stock = ? where mobileid = ?; ";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, mobile.getBrand());
			pstmt.setString(2, mobile.getModel());
			pstmt.setDouble(3, mobile.getPrice());
			pstmt.setInt(4, mobile.getStock());
			pstmt.setInt(5,  mobile.getMobileId());
			
			ret = pstmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(pstmt, con);		
		}
		
		return ret;
	}
	
	// 휴대폰 삭제
	public int deleteMobile(int mobileId) {
		int ret = -1;
		String sql = "delete from mobile where mobileid = ?; ";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DBManager.getConnection();
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, mobileId);
			
			ret = pstmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.releaseConnection(pstmt, con);		
		}
		
		return ret;
	}
	
	// 모든 휴대폰 목록 조회
	public List<MobileDto> listMobile(){
		List<MobileDto> list = new ArrayList<>();
		
		String sql = "select * from mobile; ";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBManager.getConnection();			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MobileDto mobile = new MobileDto();
				mobile.setMobileId(rs.getInt("mobileid"));
				mobile.setBrand(rs.getString("brand"));
				mobile.setModel(rs.getString("model"));
				mobile.setPrice(rs.getDouble("price"));
				mobile.setStock(rs.getInt("stock"));
				list.add(mobile);
			
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBManager.releaseConnection(rs, pstmt, con);
		}
		
		return list;
	}
	
	// 특정 휴대폰 검색 (모델명 기준)
	public List<MobileDto> listMobile(String searchWord){
		List<MobileDto> list = new ArrayList<>();
		
		String sql = "select * from mobile where model like ?; ";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBManager.getConnection();			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + searchWord + "%");
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MobileDto mobile = new MobileDto();
				mobile.setMobileId(rs.getInt("mobileid"));
				mobile.setBrand(rs.getString("brand"));
				mobile.setModel(rs.getString("model"));
				mobile.setPrice(rs.getDouble("price"));
				mobile.setStock(rs.getInt("stock"));
				list.add(mobile);			
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBManager.releaseConnection(pstmt, con);
		}
		
		return list;
	}
	
	// 특정 핸드폰 정보 상세 조회
	public MobileDto detailMobile(int mobileId){
		MobileDto mobile = null;
		
		String sql = "select * from mobile where mobileid = ?; ";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBManager.getConnection();			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1,  mobileId);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				mobile = new MobileDto();
				mobile.setMobileId(rs.getInt("mobileid"));
				mobile.setBrand(rs.getString("brand"));
				mobile.setModel(rs.getString("model"));
				mobile.setPrice(rs.getDouble("price"));
				mobile.setStock(rs.getInt("stock"));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBManager.releaseConnection(pstmt, con);
		}
		
		return mobile;
	}
	
	
}
