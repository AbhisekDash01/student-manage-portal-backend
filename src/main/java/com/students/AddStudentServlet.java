
package com.students;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class AddStudentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String roll = request.getParameter("roll");
        String cls = request.getParameter("class");
        String marks = request.getParameter("marks");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/student_db", "root", "your_password"
            );

            String sql = "INSERT INTO students(name, rollno, class, marks) VALUES(?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, roll);
            ps.setString(3, cls);
            ps.setInt(4, Integer.parseInt(marks));

            ps.executeUpdate();
            conn.close();

            out.println("<h2>Student Added Successfully!</h2>");
            out.println("<a href='index.html'>Back</a>");

        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }
}