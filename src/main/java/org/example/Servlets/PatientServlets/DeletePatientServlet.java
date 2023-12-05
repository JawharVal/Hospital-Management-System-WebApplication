package org.example.Servlets.PatientServlets;

import org.example.DAO.PatientDAO;
import org.example.DI.Component;
import org.example.DI.Inject;
import org.example.Servlets.BaseServlet;
import org.example.repositories.PatientRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@Component
@WebServlet("/DeletePatientServlet")
public class DeletePatientServlet extends BaseServlet {
    @Inject
    private PatientRepository patientRepository;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String patientIdParam = request.getParameter("patientId");

        if (patientIdParam != null) {
            try {
                int patientId = Integer.parseInt(patientIdParam);
                patientRepository.deletePatient(patientId);

                response.sendRedirect(request.getContextPath() + "/patients.jsp");
            } catch (NumberFormatException | SQLException e) {
                e.printStackTrace();
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/patients.jsp");
        }
    }
}
