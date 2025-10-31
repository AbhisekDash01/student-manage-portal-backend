package com.students;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class ViewStudentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>View Students</title>");
        out.println("<style>");
        
        out.println("body { font-family: Arial; background: lightgray; text-align: center; margin:0; padding:0; }");
        out.println("h1 { background: green; color: white; padding: 15px; margin:0; }");
        out.println("table { margin: 30px auto; border-collapse: collapse; width: 90%; max-width: 100%; background: white; box-shadow: gray 0px 0px 10px; border-radius: 10px; overflow: hidden; }");
        out.println("th, td { padding: 10px; border-bottom: 1px solid gray; font-size: 14px; }");
        out.println("th { background: green; color: white; }");
        out.println("tr:hover { background: #f1f1f1; }");
        out.println("input.inline { width: 80%; padding:5px; font-size:14px; }");
        out.println("button { padding:5px 10px; border:none; cursor:pointer; font-weight:bold; border-radius:5px; }");
        out.println("button.update { background: blue; color:white; }");
        out.println("button.delete { background: red; color:white; }");
        out.println("button:hover { opacity:0.8; }");
        out.println("a { display:inline-block; margin:20px; font-size:18px; color: green; text-decoration:none; }");
        out.println("a:hover { text-decoration: underline; }");
        out.println("@media (max-width:600px) { th, td { font-size:12px; padding:6px; } input.inline { width:70%; } button { padding:4px 6px; font-size:12px; } }");

        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Student Records</h1>");
        out.println("<table>");
        out.println("<tr><th>Roll No</th><th>Name</th><th>Class</th><th>Marks</th><th>Actions</th></tr>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/student_db", "root", "abhisek@2006"
            );
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM students");

            while (rs.next()) {
                String roll = rs.getString("rollno");
                String name = rs.getString("name");
                String cls = rs.getString("class");
                int marks = rs.getInt("marks");

                out.println("<tr>");
                // Update form
                out.println("<form action='updateStudent' method='post'>");
                out.println("<td><input type='text' name='roll' value='" + roll + "' readonly class='inline'></td>");
                out.println("<td><input type='text' name='name' value='" + name + "' class='inline' required></td>");
                out.println("<td><input type='text' name='class' value='" + cls + "' class='inline' required></td>");
                out.println("<td><input type='number' name='marks' value='" + marks + "' class='inline' required></td>");
                out.println("<td>");
                out.println("<button type='submit' class='update'>✏ Update</button>");
                out.println("</form>");

                // Delete form
                out.println("<form action='deleteStudent' method='post' style='display:inline;'>");
                out.println("<input type='hidden' name='roll' value='" + roll + "'>");
                out.println("<button type='submit' class='delete'>🗑 Delete</button>");
                out.println("</form>");
                out.println("</td></tr>");
            }

            conn.close();
        } catch (Exception e) {
            out.println("<tr><td colspan='5'>Error: " + e.getMessage() + "</td></tr>");
        }

        out.println("</table>");
        out.println("<a href='index.html'>🏠 Back to Home</a>");
        out.println("</body></html>");
    }
}