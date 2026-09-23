package com.example.crud.servlet;

import com.example.crud.dao.StudentDAO;
import com.example.crud.model.Student;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/student")
public class StudentServlet extends HttpServlet {

    private final StudentDAO studentDAO = new StudentDAO();

    // =========================
    // GET REQUESTS
    // =========================
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null || action.trim().isEmpty()) {
            action = "list";
        }

        System.out.println("ACTION = " + action);

        try {

            switch (action) {

                // Show all students
                case "list":
                    listStudents(request, response);
                    break;

                // Show Add Student form
                case "new":
                    showNewForm(request, response);
                    break;

                // Show Edit Student form
                case "edit":
                    showEditForm(request, response);
                    break;

                // Delete student
                case "delete":
                    deleteStudent(request, response);
                    break;

                default:
                    listStudents(request, response);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }


    // =========================
    // POST REQUESTS
    // =========================
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null || action.trim().isEmpty()) {
            action = "";
        }

        System.out.println("POST ACTION = " + action);

        try {

            switch (action) {

                // Add student
                case "insert":
                    insertStudent(request, response);
                    break;

                // Update student
                case "update":
                    updateStudent(request, response);
                    break;

                default:
                    listStudents(request, response);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }


    // =========================
    // LIST STUDENTS
    // =========================
    private void listStudents(HttpServletRequest request,
                              HttpServletResponse response)
            throws ServletException, IOException {

        List<Student> list = studentDAO.getAllStudents();

        request.setAttribute("studentList", list);

        request.getRequestDispatcher("/student-list.jsp")
                .forward(request, response);
    }


    // =========================
    // SHOW ADD STUDENT FORM
    // =========================
    private void showNewForm(HttpServletRequest request,
                             HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("SHOWING NEW STUDENT FORM");

        request.getRequestDispatcher("/student-form.jsp")
                .forward(request, response);
    }


    // =========================
    // SHOW EDIT STUDENT FORM
    // =========================
    private void showEditForm(HttpServletRequest request,
                              HttpServletResponse response)
            throws ServletException, IOException {

        String idParameter = request.getParameter("id");

        if (idParameter == null || idParameter.isEmpty()) {
            response.sendRedirect(
                    request.getContextPath() + "/student?action=list"
            );
            return;
        }

        int id = Integer.parseInt(idParameter);

        Student existingStudent = studentDAO.getStudentById(id);

        if (existingStudent == null) {
            response.sendRedirect(
                    request.getContextPath()
                            + "/student?action=list&error=notfound"
            );
            return;
        }

        request.setAttribute("student", existingStudent);

        request.getRequestDispatcher("/student-form.jsp")
                .forward(request, response);
    }


    // =========================
    // INSERT STUDENT
    // =========================
    private void insertStudent(HttpServletRequest request,
                               HttpServletResponse response)
            throws IOException {

        Student student = buildStudentFromRequest(request);

        boolean success = studentDAO.addStudent(student);

        response.sendRedirect(
                request.getContextPath()
                        + "/student?action=list&status="
                        + (success ? "added" : "error")
        );
    }


    // =========================
    // UPDATE STUDENT
    // =========================
    private void updateStudent(HttpServletRequest request,
                               HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(
                request.getParameter("id")
        );

        Student student = buildStudentFromRequest(request);

        student.setId(id);

        boolean success = studentDAO.updateStudent(student);

        response.sendRedirect(
                request.getContextPath()
                        + "/student?action=list&status="
                        + (success ? "updated" : "error")
        );
    }


    // =========================
    // DELETE STUDENT
    // =========================
    private void deleteStudent(HttpServletRequest request,
                               HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(
                request.getParameter("id")
        );

        boolean success = studentDAO.deleteStudent(id);

        response.sendRedirect(
                request.getContextPath()
                        + "/student?action=list&status="
                        + (success ? "deleted" : "error")
        );
    }


    // =========================
    // BUILD STUDENT OBJECT
    // =========================
    private Student buildStudentFromRequest(
            HttpServletRequest request) {

        String name = request.getParameter("name");
        String email = request.getParameter("email");

        int age = Integer.parseInt(
                request.getParameter("age")
        );

        return new Student(name, email, age);
    }
}