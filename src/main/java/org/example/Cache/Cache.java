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

    //public void putDepartment(Department department) {
    //    departmentCache.put(department.getId(), department);
    //}

    public boolean isDepartmentInCache(int id) {
        return departmentCache.containsKey(id);
    }

    public void removeDepartmentFromCache(int id) {
        departmentCache.remove(id);
    }

    // Patient cache methods
//    public Patient getPatient(int id) {
//        return patientCache.get(id);
//    }

    public void putPatient(Patient patient) {
        patientCache.put(patient.getId(), patient);
    }

    public boolean isPatientInCache(int id) {
        return patientCache.containsKey(id);
    }

    public List<Department> getAllDepartments() {
        return new ArrayList<>(departmentCache.values());
    }

    public List<Patient> getAllPatients() {
        return new ArrayList<>(patientCache.values());
    }


    public void addupdateDepartment(Department department) {
        Department existingDepartment = departmentCache.get(department.getId());
        if (existingDepartment != null) {
            // If the department already exists in the cache, update it
            existingDepartment.setName(department.getName());
            existingDepartment.setNumberOfPatients(department.getNumberOfPatients());
            System.out.println("***Returned department from cache");
        } else {
            // If the department doesn't exist in the cache, add it
            departmentCache.put(department.getId(), department);
            System.out.println("***Added department to cache");
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
            System.out.println("***Returned patient from cache");
        } else {
            // If the patient doesn't exist in the cache, add it
            patientCache.put(patient.getId(), patient);
            System.out.println("***Added patient to cache");
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
