package org.example.DAO;

import org.example.Cache.Cache;
import org.example.DI.Component;
import org.example.DI.Inject;
import org.example.Session.Session;
import org.example.models.Department;
import org.example.models.Patient;
import org.example.repositories.PatientRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


@Component
public class PatientDAO implements PatientRepository {
    @Inject
    private Session session;
    @Inject
    private DepartmentDAO departmentDAO;
    @Inject
    private Cache cache;

    // Create (Insert)
    public void addPatient(Patient patient) throws SQLException {
        try {
            session.beginTransaction();  // Start a transaction
            if (departmentDAO != null) {
                String sql = "INSERT INTO patients (fullname, age, gender, department_id) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = session.getConnection().prepareStatement(sql);
                statement.setString(1, patient.getFullName());
                statement.setInt(2, patient.getAge());
                statement.setString(3, patient.getGender());
                statement.setInt(4, patient.getDepartment().getId());
                statement.executeUpdate();
            } else {
                // Handle the case when DepartmentDAO is not injected
                System.err.println("DepartmentDAO is not injected and equal null. Unable to add patient.");
            }
            Department department = patient.getDepartment();
            department.incrementPatients(); // Increment the count
            departmentDAO.updateDepartment(department); // Update the department in the database

            session.commit();
            cache.addUpdatePatient(patient);
            System.out.println("***Patient added to cache.");
        } catch (SQLException e) {
            // Rollback the transaction in case of an error
            session.rollback();
            throw e;
        }
    }

    // Update
    public void updatePatient(Patient patient) throws SQLException {
        try {
            session.beginTransaction();  // Start a transaction
            String sql = "UPDATE patients SET fullname = ?, age = ?, gender = ?, department_id = ? WHERE id = ?";
            PreparedStatement statement = session.getConnection().prepareStatement(sql);
            statement.setString(1, patient.getFullName());
            statement.setInt(2, patient.getAge());
            statement.setString(3, patient.getGender());
            statement.setInt(4, patient.getDepartment().getId());
            statement.setInt(5, patient.getId());
            statement.executeUpdate();

            session.commit();

            // Update the patient in the cache
            cache.addUpdatePatient(patient);
        } catch (SQLException e) {
            // Rollback the transaction in case of an error
            session.rollback();
            throw e;
        }
    }

    // Delete
    public void deletePatient(int id) throws SQLException {
        try {
            // Start a transaction
            session.beginTransaction();

            // Retrieve the patient from the database
            Patient patient = getPatientById(id);

            if (patient != null) {
                // Decrement the department's patient count
                Department department = patient.getDepartment();
                department.decrementPatients();

            }

            // Now delete the patient
            String sql = "DELETE FROM patients WHERE id = ?";
            PreparedStatement statement = session.getConnection().prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();

            // Commit the transaction
            session.commit();

            // Remove the patient from the cache
            cache.removePatientFromCache(id);
        } catch (SQLException e) {
            // Rollback the transaction in case of an error
            session.rollback();
            throw e;
        }
    }

    public Patient getPatientByName(String name) throws SQLException {
        // Since we don't have a cache by name, we need to query the database
        try {
            session.beginTransaction();  // Start a transaction
            String sql = "SELECT * FROM patients WHERE fullname = ?";
            PreparedStatement statement = session.getConnection().prepareStatement(sql);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Patient patient = new Patient();
                patient.setId(resultSet.getInt("id"));
                patient.setFullName(resultSet.getString("fullname"));
                patient.setAge(resultSet.getInt("age"));
                patient.setGender(resultSet.getString("gender"));
                // department_id is a foreign key in patients table
                int departmentId = resultSet.getInt("department_id");
                Department department = departmentDAO.getDepartmentById(departmentId);
                patient.setDepartment(department);
                // Update the patient in the cache
                cache.addUpdatePatient(patient);
                session.commit();
                return patient;
            } else {
                session.commit();
                return null;
            }

        } catch (SQLException e) {
            // Rollback the transaction in case of an error
            session.rollback();
            throw e;
        }
    }

    public List<Patient> getPatientsByName(String name) throws SQLException {
        // Since we don't have a cache by name, we need to query the database
        try {
            session.beginTransaction();  // Start a transaction
            List<Patient> patients = new ArrayList<>();
            String sql = "SELECT * FROM patients WHERE fullname = ?";
            PreparedStatement statement = session.getConnection().prepareStatement(sql);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Patient patient = new Patient();
                patient.setId(resultSet.getInt("id"));
                patient.setFullName(resultSet.getString("fullname"));
                patient.setAge(resultSet.getInt("age"));
                patient.setGender(resultSet.getString("gender"));
                // department_id is a foreign key in patients table
                int departmentId = resultSet.getInt("department_id");
                Department department;
                if (cache.isDepartmentInCache(departmentId)) {
                    department = cache.getDepartment(departmentId);
                } else {
                    department = departmentDAO.getDepartmentById(departmentId);
                    cache.addupdateDepartment(department);
                }
                patient.setDepartment(department);
                patients.add(patient);
                session.commit();
                // Add each patient to the cache
                cache.addUpdatePatient(patient);
            }

            return patients;
        } catch (SQLException e) {
            // Rollback the transaction in case of an error
            session.rollback();
            throw e;
        }
    }

    public List<Patient> getAllPatients() throws SQLException {
        try {
            session.beginTransaction();
            List<Patient> patients = new ArrayList<>();
            String sql = "SELECT * FROM patients";
            Statement statement = session.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Patient patient = new Patient();
                patient.setId(resultSet.getInt("id"));
                patient.setFullName(resultSet.getString("fullname"));
                patient.setAge(resultSet.getInt("age"));
                patient.setGender(resultSet.getString("gender"));
                // department_id is a foreign key in patients table
                Department department;
                // department_id is a foreign key in patients table
                int departmentId = resultSet.getInt("department_id");
                if (cache.isDepartmentInCache(departmentId)) {
                    department = cache.getDepartment(departmentId);
                } else {
                    department = departmentDAO.getDepartmentById(departmentId);
                    cache.addupdateDepartment(department);
                }
                patient.setDepartment(department);
                patients.add(patient);
                // Use addUpdatePatient method to add or update each patient in the cache
                cache.addUpdatePatient(patient);
            }

            session.commit();
            return patients;
        } catch (SQLException e) {
            // Rollback the transaction in case of an error
            session.rollback();
            throw e;
        }
    }


    public Patient getPatientById(int id) throws SQLException {
        // Query the database to retrieve a patient by ID
        try {
            session.beginTransaction();  // Start a transaction
            String sql = "SELECT * FROM patients WHERE id = ?";
            PreparedStatement statement = session.getConnection().prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Patient patient = new Patient();
                patient.setId(resultSet.getInt("id"));
                patient.setFullName(resultSet.getString("fullname"));
                patient.setAge(resultSet.getInt("age"));
                patient.setGender(resultSet.getString("gender"));
                // department_id is a foreign key in the patients table
                int departmentId = resultSet.getInt("department_id");
                Department department;
                if (cache.isDepartmentInCache(departmentId)) {
                    department = cache.getDepartment(departmentId);
                } else {
                    department = departmentDAO.getDepartmentById(departmentId);
                    cache.addupdateDepartment(department);
                }
                patient.setDepartment(department);

                // Add the patient to the cache
                cache.addUpdatePatient(patient);
                session.commit();
                return patient;
            } else {
                session.commit();
                return null;
            }
        } catch (SQLException e) {
            // Rollback the transaction in case of an error
            session.rollback();
            throw e;
        }
    }
}
