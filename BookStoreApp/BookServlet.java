import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;

public class BookServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String search = request.getParameter("search");
        String deleteId = request.getParameter("delete");

        out.println("<html><head>");
        out.println("<title>BookStore Dashboard</title>");

        // Bootstrap + Icons
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css' rel='stylesheet'>");

        // Custom Styling
        out.println("<style>");
        out.println("body { background: linear-gradient(to right, #4facfe, #00f2fe); }");
        out.println(".card { border-radius:15px; }");
        out.println("table { background:white; border-radius:10px; overflow:hidden; }");
        out.println("tr:hover { transform:scale(1.01); transition:0.2s; }");
        out.println("</style>");

        out.println("</head><body>");

        // 🔥 NAVBAR
        out.println("<nav class='navbar navbar-dark bg-dark'>");
        out.println("<div class='container-fluid'>");
        out.println("<span class='navbar-brand mb-0 h1'><i class='bi bi-book'></i> BookStore Dashboard</span>");
        out.println("</div></nav>");

        out.println("<div class='container mt-4'>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3307/ebookstore", "root", "");

            // DELETE
            if (deleteId != null) {
                PreparedStatement ps = con.prepareStatement(
                        "DELETE FROM ebookshop WHERE book_id=?");
                ps.setInt(1, Integer.parseInt(deleteId));
                ps.executeUpdate();
            }

            // TOTAL BOOK COUNT (CARD)
            Statement countStmt = con.createStatement();
            ResultSet countRs = countStmt.executeQuery("SELECT COUNT(*) FROM ebookshop");
            countRs.next();
            int totalBooks = countRs.getInt(1);

            out.println("<div class='row mb-4'>");
            out.println("<div class='col-md-4'>");
            out.println("<div class='card text-white bg-primary shadow'>");
            out.println("<div class='card-body'>");
            out.println("<h5>Total Books</h5>");
            out.println("<h2>" + totalBooks + "</h2>");
            out.println("</div></div></div>");
            out.println("</div>");

            // SEARCH
            out.println("<form method='get' class='mb-3'>");
            out.println("<div class='input-group'>");
            out.println("<input type='text' name='search' class='form-control' placeholder='🔍 Search books...'>");
            out.println("<button class='btn btn-dark'>Search</button>");
            out.println("</div></form>");

            // ADD FORM
            out.println("<div class='card p-3 mb-4 shadow'>");
            out.println("<h5><i class='bi bi-plus-circle'></i> Add New Book</h5>");
            out.println("<form method='post'>");
            out.println("<div class='row g-2'>");
            out.println("<div class='col'><input name='id' class='form-control' placeholder='ID' required></div>");
            out.println("<div class='col'><input name='title' class='form-control' placeholder='Title' required></div>");
            out.println("<div class='col'><input name='author' class='form-control' placeholder='Author' required></div>");
            out.println("<div class='col'><input name='price' class='form-control' placeholder='Price' required></div>");
            out.println("<div class='col'><input name='qty' class='form-control' placeholder='Qty' required></div>");
            out.println("</div>");
            out.println("<button class='btn btn-success mt-3'><i class='bi bi-save'></i> Add Book</button>");
            out.println("</form></div>");

            // QUERY
            String query = "SELECT * FROM ebookshop";
            if (search != null && !search.isEmpty()) {
                query += " WHERE book_title LIKE '%" + search + "%'";
            }

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // TABLE
            out.println("<div class='card p-3 shadow'>");
            out.println("<h5><i class='bi bi-table'></i> Book List</h5>");
            out.println("<table class='table table-hover'>");
            out.println("<thead class='table-dark'>");
            out.println("<tr><th>ID</th><th>Title</th><th>Author</th><th>Price</th><th>Qty</th><th>Action</th></tr>");
            out.println("</thead><tbody>");

            while (rs.next()) {
                int id = rs.getInt("book_id");

                out.println("<tr>");
                out.println("<td>" + id + "</td>");
                out.println("<td>" + rs.getString("book_title") + "</td>");
                out.println("<td>" + rs.getString("book_author") + "</td>");
                out.println("<td>₹ " + rs.getDouble("book_price") + "</td>");
                out.println("<td>" + rs.getInt("quantity") + "</td>");

                out.println("<td>");
                out.println("<a href='?delete=" + id + "' "
                        + "onclick=\"return confirm('Delete this book?')\" "
                        + "class='btn btn-danger btn-sm'>");
                out.println("<i class='bi bi-trash'></i>");
                out.println("</a>");
                out.println("</td>");

                out.println("</tr>");
            }

            out.println("</tbody></table></div>");

            con.close();

        } catch (Exception e) {
            out.println("<div class='alert alert-danger'>Error: " + e + "</div>");
        }

        // FOOTER
        out.println("<footer class='text-center text-white mt-4'>");
        out.println("<p>© 2026 BookStore | Developed by You 🚀</p>");
        out.println("</footer>");

        out.println("</div></body></html>");
    }

    // ADD BOOK
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3307/ebookstore", "root", "");

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO ebookshop VALUES (?,?,?,?,?)");

            ps.setInt(1, Integer.parseInt(request.getParameter("id")));
            ps.setString(2, request.getParameter("title"));
            ps.setString(3, request.getParameter("author"));
            ps.setDouble(4, Double.parseDouble(request.getParameter("price")));
            ps.setInt(5, Integer.parseInt(request.getParameter("qty")));

            ps.executeUpdate();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("books");
    }
}