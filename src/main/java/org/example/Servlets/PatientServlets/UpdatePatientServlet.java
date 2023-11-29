package org.example.Servlets.PatientServlets;

import org.example.DAO.PatientDAO;
import org.example.DI.Component;
import org.example.DI.Inject;
import org.example.Servlets.BaseServlet;
import org.example.models.Department;
import org.example.models.Patient;
import org.example.repositories.DepartmentRepository;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@Component
@WebServlet("/UpdatePatientServlet")
public class UpdatePatientServlet extends BaseServlet {
    @Inject
    private PatientDAO patientDAO;
    @Inject
    private DepartmentRepository departmentRepository;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int patientId = Integer.parseInt(request.getParameter("patientId"));
        String patientName = request.getParameter("name");
        int patientAge = Integer.parseInt(request.getParameter("age"));
        String patientGender = request.getParameter("gender");
        int departmentId = Integer.parseInt(request.getParameter("department"));

        try {
            Department department = departmentRepository.getDepartmentById(departmentId);
            if (department != null) {
                Patient patient = patientDAO.getPatientById(patientId);
                patient.setFullName(patientName);
                patient.setAge(patientAge);
                patient.setGender(patientGender);
                patient.setDepartment(department);
                patientDAO.updatePatient(patient);

                response.sendRedirect(request.getContextPath() + "/patients.jsp");
            } else {
                // Handle invalid department ID
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid department ID.");
            }
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            // Handle errors, such as an invalid department ID, invalid patient details, or database errors
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing your request.");
        }
    }
}
