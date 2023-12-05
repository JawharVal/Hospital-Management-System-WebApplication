package org.example.Servlets.PatientServlets;

import org.example.DAO.PatientDAO;
import org.example.DI.Component;
import org.example.DI.Inject;
import org.example.Servlets.BaseServlet;
import org.example.models.Patient;
import org.example.repositories.PatientRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@Component
@WebServlet("/EditPatientServlet")
public class EditPatientServlet extends BaseServlet {

    @Inject
    private PatientRepository patientRepository;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String patientIdParam = request.getParameter("patientId");

        if (patientIdParam != null) {
            try {
                int patientId = Integer.parseInt(patientIdParam);
                Patient patient = patientRepository.getPatientById(patientId);

                // Set the patient data as request attributes
                request.setAttribute("patientId", patient.getId());
                request.setAttribute("patientName", patient.getFullName());
                request.setAttribute("patientAge", patient.getAge());
                request.setAttribute("patientGender", patient.getGender());

                // Forward to the UpdatePatient.jsp page

                request.getRequestDispatcher("/UpdatePatient.jsp").forward(request, response);
            } catch (NumberFormatException | SQLException e) {
                e.printStackTrace();
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/UpdatePatient.jsp");
        }
    }
}
