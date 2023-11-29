package org.example.Servlets.DepartmentServlets;

import org.example.DI.Component;
import org.example.DI.Inject;
import org.example.Servlets.BaseServlet;
import org.example.repositories.DepartmentRepository;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@Component
@WebServlet("/DeleteDepartmentServlet")
public class DeleteDepartmentServlet extends BaseServlet {
    @Inject
    private DepartmentRepository departmentRepository;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the department ID to delete from the request parameter
        String departmentIdParam = request.getParameter("departmentId");

        if (departmentIdParam != null) {
            try {
                int departmentId = Integer.parseInt(departmentIdParam);
                departmentRepository.deleteDepartment(departmentId);
            } catch (NumberFormatException | SQLException e) {
                e.printStackTrace();
            }
        }
        // Redirect back to the viewDepartmentsWithDelete.jsp page after deletion
        response.sendRedirect(request.getContextPath() + "/departments.jsp");
    }
}
