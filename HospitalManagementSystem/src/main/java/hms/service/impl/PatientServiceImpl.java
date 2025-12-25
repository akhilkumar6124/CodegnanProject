package hms.service.impl;

import hms.model.Patient;
import hms.repository.PatientRepository;
import hms.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    @Override
    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Patient updatePatient(Long id, Patient patient) {
        Optional<Patient> existingPatient = patientRepository.findById(id);
        if (existingPatient.isPresent()) {
            Patient updatedPatient = existingPatient.get();
            updatedPatient.setFirstName(patient.getFirstName());
            updatedPatient.setLastName(patient.getLastName());
            updatedPatient.setDateOfBirth(patient.getDateOfBirth());
            updatedPatient.setGender(patient.getGender());
            updatedPatient.setPhone(patient.getPhone());
            updatedPatient.setEmail(patient.getEmail());
            updatedPatient.setAddress(patient.getAddress());
            return patientRepository.save(updatedPatient);
        } else {
            throw new RuntimeException("Patient not found with id: " + id);
        }
    }

    @Override
    public void deletePatient(Long id) {
        if (patientRepository.existsById(id)) {
            patientRepository.deleteById(id);
        } else {
            throw new RuntimeException("Patient not found with id: " + id);
        }
    }
}
