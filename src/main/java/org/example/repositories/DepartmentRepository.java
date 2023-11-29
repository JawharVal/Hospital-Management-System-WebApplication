package org.example.repositories;

import org.example.models.Department;

import java.sql.SQLException;
import java.util.List;

public interface DepartmentRepository {
    void addDepartment(Department department) throws SQLException;
    Department getDepartmentById(int id) throws SQLException;
    int getNumberOfPatientsInDepartment(int departmentId) throws SQLException;
    List<Department> getAllDepartments() throws SQLException;
    Department getDepartmentByName(String name) throws SQLException;
    void updateDepartment(Department department) throws SQLException;
    void deleteDepartment(int id) throws SQLException;

    void updateDepartmentName(int id, String name) throws SQLException;
}