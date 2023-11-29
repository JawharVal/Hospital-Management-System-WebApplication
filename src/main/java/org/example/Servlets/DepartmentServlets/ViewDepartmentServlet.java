package org.example.Servlets.DepartmentServlets;

import org.example.DI.Component;
import org.example.DI.Inject;
import org.example.Servlets.BaseServlet;
import org.example.models.Department;
import org.example.repositories.DepartmentRepository;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Component
@WebServlet("/viewDepartment")
public class ViewDepartmentServlet extends BaseServlet {
    @Inject
    private DepartmentRepository departmentRepository;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Department> departments = departmentRepository.getAllDepartments();
            request.setAttribute("departments", departments);
            request.getRequestDispatcher("departments.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
