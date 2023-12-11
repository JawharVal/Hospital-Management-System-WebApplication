package org.example.DAO;

import org.example.Cache.Cache;
import org.example.DI.Component;
import org.example.DI.Inject;
import org.example.Session.Session;
import org.example.models.Department;
import org.example.repositories.DepartmentRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


@Component
public class DepartmentDAO implements DepartmentRepository {
    @Inject
    private Session session;
    @Inject
    private Cache cache;

    // Create (Insert)
    public void addDepartment(Department department) throws SQLException {
        String sql = "INSERT INTO departments (name, number_of_patients) VALUES (?, ?)";


        PreparedStatement statement = session.getConnection().prepareStatement(sql);
        statement.setString(1, department.getName());
        statement.setInt(2, department.getNumberOfPatients());
        statement.executeUpdate();
        session.commit();  // Commit the transaction

        // Add the department to the cache
        cache.addupdateDepartment(department);
    }

    public Department getDepartmentById(int id) throws SQLException {
        // First, try to get the department from the cache
        Department department = cache.getDepartment(id);

        if (department != null) {
            // If the department is in the cache, return it
            return department;
        } else {
            // If the department is not in the cache, query the database
            try {
                session.beginTransaction();

                String sql = "SELECT * FROM departments WHERE id = ?";
                PreparedStatement statement = session.getConnection().prepareStatement(sql);
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    department = new Department();
                    department.setId(resultSet.getInt("id"));
                    department.setName(resultSet.getString("name"));
                    department.setNumberOfPatients(resultSet.getInt("number_of_patients"));

                    // Add the department to the cache
                    cache.addupdateDepartment(department);

                    session.commit();
                    return department;
                } else {
                    session.rollback();
                    return null;
                }
            } catch (SQLException e) {
                session.rollback();  // Rollback the transaction in case of an error
                throw e;
            }
        }
    }

    public int getNumberOfPatientsInDepartment(int departmentId) throws SQLException {
        try {
            session.beginTransaction();  // Start a transaction
            String sql = "SELECT COUNT(*) FROM patients WHERE department_id = ?";
            PreparedStatement statement = session.getConnection().prepareStatement(sql);
            statement.setInt(1, departmentId);
            ResultSet resultSet = statement.executeQuery();
            // Commit the transaction
            session.commit();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            // Rollback the transaction in case of an error
            session.rollback();
            throw e;
        }
    }

    public List<Department> getAllDepartments() throws SQLException {
        try {
            session.beginTransaction();
            List<Department> departments = new ArrayList<>();
            String sql = "SELECT * FROM departments";
            Statement statement = session.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Department department = new Department();
                department.setId(resultSet.getInt("id"));
                department.setName(resultSet.getString("name"));
                int numberOfPatients = getNumberOfPatientsInDepartment(department.getId());
                department.setNumberOfPatients(numberOfPatients);
                departments.add(department);
                // Update the department in the cache
                cache.addupdateDepartment(department);
            }
            session.commit();
            return departments;
        } catch (SQLException e) {
            session.rollback();
            throw e;
        }
    }

    // Update (verification done in cache class method)
    public void updateDepartment(Department department) throws SQLException {
        try {
            session.beginTransaction();

            String sql = "UPDATE departments SET name = ?, number_of_patients = ? WHERE id = ?";
            PreparedStatement statement = session.getConnection().prepareStatement(sql);
            statement.setString(1, department.getName());
            statement.setInt(2, department.getNumberOfPatients());
            statement.setInt(3, department.getId());
            statement.executeUpdate();

            session.commit();

            // Update the department in the cache
            cache.addupdateDepartment(department);
        } catch (SQLException e) {
            session.rollback();
            throw e;
        }
    }

    // Delete
    public void deleteDepartment(int id) throws SQLException {
        try {
            // Start a transaction
            session.beginTransaction();

            // Delete the patients from this department
            String sqlDeletePatients = "DELETE FROM patients WHERE department_id = ?";
            PreparedStatement statementDeletePatients = session.getConnection().prepareStatement(sqlDeletePatients);
            statementDeletePatients.setInt(1, id);
            statementDeletePatients.executeUpdate();

//            // Intentionally introduce an error ( to see the use of transactions, this wont delete the patient and roll it back from the dead to the deparment, without transaction the patient will be deleted from the department
//            if (true) {
//                throw new SQLException("Intentional error");
//            }

            // Now delete the department
            String sqlDeleteDepartment = "DELETE FROM departments WHERE id = ?";
            PreparedStatement statementDeleteDepartment = session.getConnection().prepareStatement(sqlDeleteDepartment);
            statementDeleteDepartment.setInt(1, id);
            statementDeleteDepartment.executeUpdate();

            // Commit the transaction
            session.commit();

            // Remove the department from the cache
            cache.removeDepartmentFromCache(id);
        } catch (SQLException e) {
            // Rollback the transaction in case of an error
            session.rollback();
            throw e;
        }
    }

    public void updateDepartmentName(int departmentId, String departmentName) throws SQLException {
        String sql = "UPDATE departments SET name = ? WHERE id = ?";
        PreparedStatement statement = session.getConnection().prepareStatement(sql);
        statement.setString(1, departmentName);
        statement.setInt(2, departmentId);
        statement.executeUpdate();
    }
}