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

@Component
@WebServlet("/EditDepartmentServlet")
public class EditDepartmentServlet extends BaseServlet {
    @Inject
    private DepartmentRepository departmentRepository;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the department ID to edit from the request parameter
        String departmentIdParam = request.getParameter("departmentId");

        if (departmentIdParam != null) {
            try {
                int departmentId = Integer.parseInt(departmentIdParam);
                Department department = departmentRepository.getDepartmentById(departmentId);

                // Set the department data as request attributes
                request.setAttribute("departmentId", department.getId());
                request.setAttribute("departmentName", department.getName());

                // Forward to the editDepartment.jsp page
                request.getRequestDispatcher("/editDepartment.jsp").forward(request, response);
            } catch (NumberFormatException | SQLException e) {
                e.printStackTrace();
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/departments.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String departmentIdParam = request.getParameter("departmentId");
        String departmentName = request.getParameter("departmentName");

        if (departmentIdParam != null && departmentName != null) {
            try {
                int departmentId = Integer.parseInt(departmentIdParam);
                departmentRepository.updateDepartmentName(departmentId, departmentName);

                response.sendRedirect(request.getContextPath() + "/departments.jsp");
            } catch (NumberFormatException | SQLException e) {
                e.printStackTrace();
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/departments.jsp");
        }
    }
}
