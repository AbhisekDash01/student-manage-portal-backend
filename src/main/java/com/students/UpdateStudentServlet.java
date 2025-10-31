package com.students;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class UpdateStudentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String roll = request.getParameter("roll"); // Identify student
        String name = request.getParameter("name");
        String cls = request.getParameter("class");
        String marks = request.getParameter("marks");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/student_db", "root", "abhisek@2006");
                 PreparedStatement ps = conn.prepareStatement(
                     "UPDATE students SET name=?, class=?, marks=? WHERE rollno=?")) {
                
                ps.setString(1, name);
                ps.setString(2, cls);
                ps.setInt(3, Integer.parseInt(marks));
                ps.setString(4, roll);

                int rows = ps.executeUpdate();

                if(rows > 0) {
                    out.println("<h2>Student Updated Successfully!</h2>");
                } else {
                    out.println("<h2>No student found with Roll No: " + roll + "</h2>");
                }
                out.println("<a href='viewStudents'>Back to View</a>");
            }
        } catch (SQLException | ClassNotFoundException e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
            e.printStackTrace();
        }
    }
}