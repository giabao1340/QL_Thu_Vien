/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qlthuvien.dao;

import com.mycompany.qlthuvien.DatabaseConnection;
import com.mycompany.qlthuvien.model.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import memberState.ActiveMemberState;
import memberState.ExpiredMemberState;
import memberState.MemberContext;
import memberState.MemberState;

/**
 *
 * @author minh9
 */
public class MemberDao {

    private Connection connection;
    private JTable jMemTable;

    public MemberDao(JTable jMemTable) {
        this.jMemTable = jMemTable;
    }

    public MemberDao() {
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();
        Connection connection = dbConnection.getConnection();
    }

    public void addMem(Member member) {
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();;
        Connection connection = dbConnection.getConnection();
        String sql = "Insert into DocGia values (?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            java.sql.Date birthDate = new java.sql.Date(member.getNgaySinh().getTime());
            java.sql.Date createDate = new java.sql.Date(member.getNgayLapThe().getTime());
            java.sql.Date expiryDate = new java.sql.Date(member.getNgayHetHan().getTime());
            ps.setString(1, member.getHoTen());
            ps.setDate(2, birthDate);
            ps.setString(3, member.getEmail());
            ps.setDate(4, createDate);
            ps.setDate(5, expiryDate); 
            ps.setString(6, member.getTrangThai()); ///
            ps.setInt(7, member.getGioiTinh());
            ps.setBytes(8, member.getHinh());
            ps.executeUpdate();
            System.out.println("Them member thanh cong");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Them member that bai");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public List<Member> loadMem() {
        List<Member> members = new ArrayList<>();
        String sql = "Select * from DocGia";
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();
        Connection connection = dbConnection.getConnection(); //ket noi database
        

        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery(); //Query du lieu trong sql
            while (rs.next()) {
                Member member = new Member(
                        rs.getString("MaDocGia"),
                        rs.getString("HoTen"),
                        rs.getDate("NgaySinh"),
                        rs.getString("Email"),
                        rs.getDate("NgayLapThe"),
                        rs.getDate("NgayHetHan"),
                        rs.getString("TrangThaiThe"),
                        rs.getInt("GioiTinh"),
                        rs.getBytes("Hinh"));
                members.add(member);

            }
            System.out.println("Load thanh cong");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Load that bai");
        }
        return members;
    }
//////Them du lieu vao jTable

    public void showMem() {
        List<Member> list = loadMem();
        DefaultTableModel model = (DefaultTableModel) jMemTable.getModel();
        Object[] row = new Object[9];
        
        MemberContext memberContext = new MemberContext();
        Date today = new Date(); // Lấy ngày hiện tại
        
        for (int i = 0; i < list.size(); i++) { //Them du lieu vao tung dong cua jTable
            row[0] = list.get(i).getMaDocGia();
            row[1] = list.get(i).getHoTen();
            row[2] = list.get(i).getNgaySinh();
            row[3] = list.get(i).getEmail();
            row[4] = list.get(i).getNgayLapThe();
            row[5] = list.get(i).getNgayHetHan();
            int trangThaiThe;
            try {
                trangThaiThe = Integer.parseInt(list.get(i).getTrangThai());
            } catch (NumberFormatException e) {
                trangThaiThe = -1; // Giá trị mặc định nếu dữ liệu không hợp lệ
            }
            int ma = Integer.parseInt(list.get(i).getMaDocGia());
            if (list.get(i).getNgayHetHan().after(today)) { 
            memberContext.setState(new ExpiredMemberState());
            memberContext.ChangeState(ma, 0);
            }
            String trangThaiText = (trangThaiThe == 1) ? "Thẻ còn hoạt động" :(trangThaiThe == 2) ? "Thẻ bị tạm ngưng" :(trangThaiThe == 3) ? "Thẻ bị khóa" :(trangThaiThe == 0) ? "Thẻ hết hạn" :"Không xác định";
            row[6] = trangThaiText;
//            row[6] = list.get(i).getTrangThai(); 
            row[7] = list.get(i).getGioiTinh() == 0 ? "Nam" : "Nữ";
            model.addRow(row);
        }
    }
/////////////////////////////////////////

    public void deleteMem() {
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        DefaultTableModel model = (DefaultTableModel) jMemTable.getModel();
        String sql = "Delete from DocGia where MaDocGia = ?";
        try  (PreparedStatement ps = connection.prepareStatement(sql)) {
            if (connection != null) {
                int selectedRow = jMemTable.getSelectedRow(); //Gan gia tri cho dong can xoa trong jtable
                System.out.println("Selected Row: " + selectedRow);
                if (selectedRow >= 0) {
                    String maDG = jMemTable.getValueAt(selectedRow, 0).toString(); // Gan gia tri cho dong can xoa trong sql
                    System.out.println("Selected MailID: " + maDG);
                    ps.setString(1, maDG);
                    int affectedRows = ps.executeUpdate(); // Execute delete sql
                    if (affectedRows > 0) {
                        model.removeRow(selectedRow); // Xoa muc da chon trong jtable
                        System.out.println("Delete successful");
                    } else {
                        System.out.println("No row deleted");
                    }
                } else {
                    System.out.println("No row selected");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Delete Fail: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
             try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String getEmailByReaderId(int maDocGia) {
        String email = null;
        String sql = "SELECT Email FROM DocGia WHERE MaDocGia = ?";
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, maDocGia);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    email = rs.getString("Email");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return email;
    }
}
