package org.example.Cache;

import org.example.DI.Component;
import org.example.models.Department;
import org.example.models.Patient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Cache {
    private final Map<Integer, Department> departmentCache;
    private final Map<Integer, Patient> patientCache;

    public Cache() {
        departmentCache = new HashMap<>();
        patientCache = new HashMap<>();
    }

    // Department cache methods
    public Department getDepartment(int id) {
        return departmentCache.get(id);
    }

    public boolean isDepartmentInCache(int id) {
        return departmentCache.containsKey(id);
    }

    public void removeDepartmentFromCache(int id) {
        departmentCache.remove(id);
    }

    public Patient getPatient(int id) {
        return patientCache.get(id);
   }

    public boolean isPatientInCache(int id) {
        return patientCache.containsKey(id);
    }

    public void addupdateDepartment(Department department) {
        Department existingDepartment = departmentCache.get(department.getId());
        if (existingDepartment != null) {
            // If the department already exists in the cache, update it
            existingDepartment.setName(department.getName());
            existingDepartment.setNumberOfPatients(department.getNumberOfPatients());
        } else {
            // If the department doesn't exist in the cache, add it
            departmentCache.put(department.getId(), department);
        }
    }

    public void addUpdatePatient(Patient patient) {
        Patient existingPatient = patientCache.get(patient.getId());
        if (existingPatient != null) {
            // If the patient already exists in the cache, update it
            existingPatient.setFullName(patient.getFullName());
            existingPatient.setAge(patient.getAge());
            existingPatient.setGender(patient.getGender());
            existingPatient.setDepartment(patient.getDepartment());
        } else {
            // If the patient doesn't exist in the cache, add it
            patientCache.put(patient.getId(), patient);
        }
    }

    public void removePatientFromCache(int id) {
        patientCache.remove(id);
    }

    // Clear cache
    public void clearCache() {
        departmentCache.clear();
        patientCache.clear();
    }
}
