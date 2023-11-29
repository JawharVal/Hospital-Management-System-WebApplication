package org.example.Servlets.PatientServlets;

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
import java.util.List;

@Component
@WebServlet("/ViewPatientsServlet")
public class ViewPatientsServlet extends BaseServlet {
    @Inject
    private PatientRepository patientRepository;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Patient> patients = patientRepository.getAllPatients();
            request.setAttribute("patients", patients);
            request.getRequestDispatcher("/patients.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
