package hms.service;

import hms.model.Patient;
import java.time.LocalDate;
import java.util.List;

public interface PatientService {
    List<Patient> getAllPatients();
    Patient getPatientById(Long id);
    Patient createPatient(Patient patient);
    Patient updatePatient(Long id, Patient patient);
    void deletePatient(Long id);

    // Additional methods for searching
    List<Patient> getPatientsByGender(String gender);
    List<Patient> getPatientsByDateOfBirth(LocalDate dateOfBirth);
    List<Patient> getPatientsByDateOfBirthBetween(LocalDate startDate, LocalDate endDate);
    List<Patient> getPatientsByNameContaining(String name);
}
