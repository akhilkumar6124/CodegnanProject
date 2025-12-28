package hms.service;

import hms.model.Department;
import hms.model.Doctor;
import java.util.List;

public interface DoctorService {
    List<Doctor> getAllDoctors();
    Doctor getDoctorById(Long id);
    Doctor createDoctor(Doctor doctor);
    Doctor updateDoctor(Long id, Doctor doctor);
    void deleteDoctor(Long id);

    // Additional methods for searching
    List<Doctor> getDoctorsBySpecialization(String specialization);
    List<Doctor> getDoctorsBySpecializationContaining(String specialization);
    List<Doctor> getDoctorsByNameContaining(String name);
    List<Doctor> getDoctorsByDepartment(Department department);
}
