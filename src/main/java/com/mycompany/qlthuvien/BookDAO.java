package com.mycompany.qlthuvien;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    private Connection connection;

    public BookDAO() {
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();
        this.connection = dbConnection.getConnection();  // Initialize the connection attribute
    }

    // Add a book to the database
    public static void addBook(Book book) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Establish database connection
            DatabaseConnection db = DatabaseConnection.getInstance();
            conn = db.getConnection();

            // Retrieve MaTheLoai from the TheLoai table
            String sqlGetMaTheLoai = "SELECT MaTheLoai FROM TheLoai WHERE TenTheLoai = ?";
            pstmt = conn.prepareStatement(sqlGetMaTheLoai);
            pstmt.setString(1, book.getTheLoai());
            rs = pstmt.executeQuery();

            int maTheLoai = -1;
            if (rs.next()) {
                maTheLoai = rs.getInt("MaTheLoai");
            } else {
                throw new SQLException("Thể loại không tồn tại.");
            }

            // Insert book into the Sach table
            String sqlInsertSach = "INSERT INTO Sach (TenSach, MaTheLoai, TenTacGia, NamNXB, TrangThai, NXB, Hinh, MoTaSach, GiaSach) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sqlInsertSach);
            pstmt.setString(1, book.getTenSach());
            pstmt.setInt(2, maTheLoai);
            pstmt.setString(3, book.getTenTacGia());
            pstmt.setInt(4, book.getNamNXB());
            pstmt.setInt(5, book.getTrangThai());
            pstmt.setString(6, book.getNXB());
            pstmt.setBytes(7, book.getHinh());
            pstmt.setString(8, book.getMoTaSach());
            pstmt.setFloat(9, book.getGiaSach());

            pstmt.executeUpdate();

        } finally {
            // Close resources
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
    // Get a list of all books
    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Establish database connection
            DatabaseConnection db = DatabaseConnection.getInstance();
            conn = db.getConnection();

            // SQL query to get all books along with category name
            String sql = "SELECT s.MaSach, s.TenSach, s.TenTacGia, s.NamNXB, s.NXB, s.GiaSach, tl.TenTheLoai, s.MoTaSach "
                    + "FROM Sach s "
                    + "JOIN TheLoai tl ON s.MaTheLoai = tl.MaTheLoai";

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            // Process the result set
            while (rs.next()) {
                int maSach = rs.getInt("MaSach");
                String tenSach = rs.getString("TenSach");
                String tenTacGia = rs.getString("TenTacGia");
                int namNXB = rs.getInt("NamNXB");
                String nXB = rs.getString("NXB");
                float giaSach = rs.getFloat("GiaSach");
                String theLoai = rs.getString("TenTheLoai");
                String moTaSach = rs.getString("MoTaSach");

                // Create Book object and add it to the list
                Book book = new Book(maSach, tenSach, tenTacGia, namNXB, 0, nXB, null, moTaSach, giaSach, theLoai);
                books.add(book);
            }
        } finally {
            // Close resources
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return books;
    }
    // Delete a book by its ID
    public void deleteBook(int maSach) {
        String deleteSql = "DELETE FROM Sach WHERE MaSach = ?";
        try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
            deleteStatement.setInt(1, maSach);
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

public List<Book> searchBooks(String keyword) {
    List<Book> books = new ArrayList<>();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
        // Thiết lập kết nối cơ sở dữ liệu
        DatabaseConnection db = DatabaseConnection.getInstance();
        conn = db.getConnection();

        // Tạo câu lệnh SQL để tìm kiếm sách
        String sql = "SELECT Sach.MaSach, Sach.TenSach, Sach.TenTacGia, Sach.NamNXB, Sach.NXB, Sach.GiaSach, TheLoai.TenTheLoai, Sach.MoTaSach "
                + "FROM Sach "
                + "JOIN TheLoai ON Sach.MaTheLoai = TheLoai.MaTheLoai "
                + "WHERE Sach.TenSach LIKE ? OR Sach.TenTacGia LIKE ? OR TheLoai.TenTheLoai LIKE ?";

        pstmt = conn.prepareStatement(sql);
        String searchKeyword = "%" + keyword + "%";
        pstmt.setString(1, searchKeyword);
        pstmt.setString(2, searchKeyword);
        pstmt.setString(3, searchKeyword);

        rs = pstmt.executeQuery();

        // Xử lý kết quả truy vấn và thêm vào danh sách sách
        while (rs.next()) {
            int maSach = rs.getInt("MaSach");
            String tenSach = rs.getString("TenSach");
            String tenTacGia = rs.getString("TenTacGia");
            int namNXB = rs.getInt("NamNXB");
            String nXB = rs.getString("NXB");
            float giaSach = rs.getFloat("GiaSach");
            String tenTheLoai = rs.getString("TenTheLoai");
            String moTaSach = rs.getString("MoTaSach");
            // Khởi tạo đối tượng Book và thêm vào danh sách
            Book book = new Book(maSach, tenSach, tenTacGia, namNXB, nXB, moTaSach,  giaSach, tenTheLoai);
            books.add(book);
        }

        if (books.isEmpty()) {
            System.out.println("No books found matching the search criteria.");
        }

    } catch (SQLException ex) {
        System.err.println("Error executing searchBooks: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        // Đóng tài nguyên
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("Error closing resources: " + e.getMessage());
            e.printStackTrace();
        }
    }

    return books;
}




    public static void main(String[] args) {
        // Tạo đối tượng BookDAO
        BookDAO bookDAO = new BookDAO();

        // Từ khóa tìm kiếm
        String keyword = "300"; // Thay đổi từ khóa tìm kiếm tùy ý

        // Gọi phương thức searchBooks và lưu kết quả
        List<Book> searchResults = bookDAO.searchBooks(keyword);
    }

    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        String query = "SELECT tenTheLoai FROM TheLoai"; // Thay 'TheLoai' bằng tên bảng trong CSDL của bạn

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                categories.add(rs.getString("tenTheLoai"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }


    public boolean addCategory(String categoryName) {
        String sql = "INSERT INTO TheLoai (TenTheLoai) VALUES (?)";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, categoryName);
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0; // Returns true if a category was added

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Returns false if there was an error
        }
    }
    public boolean isCategoryExists(String categoryName) {
        String query = "SELECT COUNT(*) FROM TheLoai WHERE tenTheLoai = ?";
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, categoryName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Nếu số lượng > 0, thể loại đã tồn tại
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
