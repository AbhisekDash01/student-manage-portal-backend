package com.students;

import java.io.IOException; // Add this line
import java.io.PrintWriter;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class DeleteStudentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String roll = request.getParameter("roll");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/student_db", "root", "abhisek@2006"
            );

            String sql = "DELETE FROM students WHERE rollno=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, roll);

            int rows = ps.executeUpdate();
            conn.close();

            if(rows > 0) {
                out.println("<h2>Student Deleted Successfully!</h2>");
            } else {
                out.println("<h2>No student found with Roll No: " + roll + "</h2>");
            }
            out.println("<a href='viewStudents'>Back to View</a>");

        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }
}