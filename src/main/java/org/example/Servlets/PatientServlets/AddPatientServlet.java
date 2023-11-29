package org.example.Servlets.PatientServlets;

import org.example.DI.Component;
import org.example.DI.Inject;
import org.example.Servlets.BaseServlet;
import org.example.models.Department;
import org.example.models.Patient;
import org.example.repositories.DepartmentRepository;
import org.example.repositories.PatientRepository;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@Component
@WebServlet("/AddPatientServlet")
public class AddPatientServlet extends BaseServlet {
    @Inject
    private DepartmentRepository departmentRepository;
    @Inject
    private PatientRepository patientRepository;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        String gender = request.getParameter("gender");
        int departmentId = Integer.parseInt(request.getParameter("department"));

        try {
            Department department = departmentRepository.getDepartmentById(departmentId);
            if (department != null) {
                Patient patient = new Patient();
                patient.setFullName(name);
                patient.setAge(age);
                patient.setGender(gender);
                patient.setDepartment(department);
                patientRepository.addPatient(patient);

                response.sendRedirect(request.getContextPath() + "/patients.jsp");
            } else {
                // Handle invalid department ID
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid department ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle errors, such as an invalid department ID, invalid patient details, or database errors
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing your request.");
        }
    }
}
