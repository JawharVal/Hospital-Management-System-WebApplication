package org.example.repositories;

import org.example.models.Patient;

import java.sql.SQLException;
import java.util.List;

public interface PatientRepository {
    void addPatient(Patient patient) throws SQLException;
    List<Patient> getPatientsByName(String name) throws SQLException;
    List<Patient> getAllPatients() throws SQLException;
    void updatePatient(Patient patient) throws SQLException;
    void deletePatient(int id) throws SQLException;
    Patient getPatientById(int id) throws SQLException;
}