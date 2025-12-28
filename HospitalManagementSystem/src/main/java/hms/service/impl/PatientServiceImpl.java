package hms.service.impl;

import hms.exception.ResourceNotFoundException;
import hms.model.Patient;
import hms.repository.PatientRepository;
import hms.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", id));
    }

    @Override
    public Patient createPatient(Patient patient) {
        // Check if patient with same email or phone already exists
        if (patient.getEmail() != null && patientRepository.findByEmailIgnoreCase(patient.getEmail()).isPresent()) {
            throw new RuntimeException("Patient with email '" + patient.getEmail() + "' already exists");
        }
        if (patient.getPhone() != null && patientRepository.findByPhoneIgnoreCase(patient.getPhone()).isPresent()) {
            throw new RuntimeException("Patient with phone '" + patient.getPhone() + "' already exists");
        }
        return patientRepository.save(patient);
    }

    @Override
    public Patient updatePatient(Long id, Patient patient) {
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", id));

        // Check if another patient with the same email already exists (case-insensitive)
        if (patient.getEmail() != null) {
            patientRepository.findByEmailIgnoreCase(patient.getEmail()).ifPresent(existingPatientWithEmail -> {
                if (!existingPatientWithEmail.getId().equals(id)) {
                    throw new RuntimeException("Patient with email '" + patient.getEmail() + "' already exists");
                }
            });
        }

        // Check if another patient with the same phone already exists (case-insensitive)
        if (patient.getPhone() != null) {
            patientRepository.findByPhoneIgnoreCase(patient.getPhone()).ifPresent(existingPatientWithPhone -> {
                if (!existingPatientWithPhone.getId().equals(id)) {
                    throw new RuntimeException("Patient with phone '" + patient.getPhone() + "' already exists");
                }
            });
        }

        existingPatient.setFirstName(patient.getFirstName());
        existingPatient.setLastName(patient.getLastName());
        existingPatient.setDateOfBirth(patient.getDateOfBirth());
        existingPatient.setGender(patient.getGender());
        existingPatient.setPhone(patient.getPhone());
        existingPatient.setEmail(patient.getEmail());
        existingPatient.setAddress(patient.getAddress());
        return patientRepository.save(existingPatient);
    }

    @Override
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patient", "id", id);
        }
        patientRepository.deleteById(id);
    }

    @Override
    public List<Patient> getPatientsByGender(String gender) {
        return patientRepository.findByGender(gender);
    }

    @Override
    public List<Patient> getPatientsByDateOfBirth(LocalDate dateOfBirth) {
        return patientRepository.findByDateOfBirth(dateOfBirth);
    }

    @Override
    public List<Patient> getPatientsByDateOfBirthBetween(LocalDate startDate, LocalDate endDate) {
        return patientRepository.findByDateOfBirthBetween(startDate, endDate);
    }

    @Override
    public List<Patient> getPatientsByNameContaining(String name) {
        return patientRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name);
    }
}
