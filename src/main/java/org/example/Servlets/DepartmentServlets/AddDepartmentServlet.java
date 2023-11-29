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
@WebServlet("/addDepartment")
public class AddDepartmentServlet extends BaseServlet {

    @Inject
    private DepartmentRepository departmentRepository;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        Department department = new Department();
        department.setName(name);
        try {
            departmentRepository.addDepartment(department);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("departments.jsp");
    }
}

