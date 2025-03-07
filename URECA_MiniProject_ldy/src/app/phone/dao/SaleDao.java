package app.phone.dao;

import app.phone.common.DBManager;
import app.phone.dto.SaleDto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaleDao {

    // 판매 등록
    public int insertSale(SaleDto sale) {
        int ret = -1;
        String sql = "INSERT INTO sale (custid, mobileid, quantity, total_price, sale_date) VALUES (?, ?, ?, ?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, sale.getCustId());
            pstmt.setInt(2, sale.getMobileId());
            pstmt.setInt(3, sale.getQuantity());
            pstmt.setDouble(4, sale.getTotalPrice());
            pstmt.setString(5, sale.getSaleDate());

            ret = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(pstmt, con);
        }

        return ret;
    }

    // 판매 정보 수정
    public int updateSale(SaleDto sale) {
        int ret = -1;
        String sql = "UPDATE sale SET custid = ?, mobileid = ?, quantity = ?, total_price = ?, sale_date = ? WHERE saleid = ?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, sale.getCustId());
            pstmt.setInt(2, sale.getMobileId());
            pstmt.setInt(3, sale.getQuantity());
            pstmt.setDouble(4, sale.getTotalPrice());
            pstmt.setString(5, sale.getSaleDate());
            pstmt.setInt(6, sale.getSaleId());

            ret = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(pstmt, con);
        }

        return ret;
    }

    // 판매 삭제
    public int deleteSale(int saleId) {
        int ret = -1;
        String sql = "DELETE FROM sale WHERE saleid = ?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, saleId);

            ret = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(pstmt, con);
        }

        return ret;
    }

    // 모든 판매 목록 조회
    public List<SaleDto> listSales() {
        List<SaleDto> list = new ArrayList<>();
        String sql = "SELECT * FROM sale";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                SaleDto sale = new SaleDto(
                        rs.getInt("saleid"),
                        rs.getInt("custid"),
                        rs.getInt("mobileid"),
                        rs.getInt("quantity"),
                        rs.getDouble("total_price"),
                        rs.getString("sale_date")
                );
                list.add(sale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(rs, pstmt, con);
        }

        return list;
    }

    // 특정 판매 검색 (고객 ID 기준)
    public List<SaleDto> listSales(String searchWord) {
        List<SaleDto> list = new ArrayList<>();
        String sql = "SELECT * FROM sale WHERE sale_date LIKE ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "%" + searchWord + "%");

            rs = pstmt.executeQuery();

            while (rs.next()) {
                SaleDto sale = new SaleDto(
                        rs.getInt("saleid"),
                        rs.getInt("custid"),
                        rs.getInt("mobileid"),
                        rs.getInt("quantity"),
                        rs.getDouble("total_price"),
                        rs.getString("sale_date")
                );
                list.add(sale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(rs, pstmt, con);
        }

        return list;
    }

    // 특정 판매 정보 상세 조회
    public SaleDto detailSale(int saleId) {
        SaleDto sale = null;
        String sql = "SELECT * FROM sale WHERE saleid = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, saleId);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                sale = new SaleDto(
                        rs.getInt("saleid"),
                        rs.getInt("custid"),
                        rs.getInt("mobileid"),
                        rs.getInt("quantity"),
                        rs.getDouble("total_price"),
                        rs.getString("sale_date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(rs, pstmt, con);
        }

        return sale;
    }
}
