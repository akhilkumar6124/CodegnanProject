package hms.service.impl;

import hms.exception.ResourceNotFoundException;
import hms.model.Department;
import hms.model.Doctor;
import hms.repository.DoctorRepository;
import hms.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", id));
    }

    @Override
    public Doctor createDoctor(Doctor doctor) {
        // Check if doctor with same email or license number already exists
        if (doctor.getEmail() != null && doctorRepository.findByEmailIgnoreCase(doctor.getEmail()).isPresent()) {
            throw new RuntimeException("Doctor with email '" + doctor.getEmail() + "' already exists");
        }
        if (doctor.getLicenseNumber() != null && doctorRepository.findByLicenseNumberIgnoreCase(doctor.getLicenseNumber()).isPresent()) {
            throw new RuntimeException("Doctor with license number '" + doctor.getLicenseNumber() + "' already exists");
        }
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor updateDoctor(Long id, Doctor doctor) {
        Doctor existingDoctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", id));

        // Check if another doctor with the same email already exists (case-insensitive)
        if (doctor.getEmail() != null) {
            doctorRepository.findByEmailIgnoreCase(doctor.getEmail()).ifPresent(existingDoctorWithEmail -> {
                if (!existingDoctorWithEmail.getId().equals(id)) {
                    throw new RuntimeException("Doctor with email '" + doctor.getEmail() + "' already exists");
                }
            });
        }

        // Check if another doctor with the same license number already exists (case-insensitive)
        if (doctor.getLicenseNumber() != null) {
            doctorRepository.findByLicenseNumberIgnoreCase(doctor.getLicenseNumber()).ifPresent(existingDoctorWithLicense -> {
                if (!existingDoctorWithLicense.getId().equals(id)) {
                    throw new RuntimeException("Doctor with license number '" + doctor.getLicenseNumber() + "' already exists");
                }
            });
        }

        existingDoctor.setFirstName(doctor.getFirstName());
        existingDoctor.setLastName(doctor.getLastName());
        existingDoctor.setSpecialization(doctor.getSpecialization());
        existingDoctor.setPhone(doctor.getPhone());
        existingDoctor.setEmail(doctor.getEmail());
        existingDoctor.setLicenseNumber(doctor.getLicenseNumber());
        existingDoctor.setHireDate(doctor.getHireDate());
        existingDoctor.setDepartment(doctor.getDepartment());
        return doctorRepository.save(existingDoctor);
    }

    @Override
    public void deleteDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Doctor", "id", id);
        }
        doctorRepository.deleteById(id);
    }

    @Override
    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        return doctorRepository.findBySpecialization(specialization);
    }

    @Override
    public List<Doctor> getDoctorsBySpecializationContaining(String specialization) {
        return doctorRepository.findBySpecializationContainingIgnoreCase(specialization);
    }

    @Override
    public List<Doctor> getDoctorsByNameContaining(String name) {
        return doctorRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name);
    }

    @Override
    public List<Doctor> getDoctorsByDepartment(Department department) {
        return doctorRepository.findByDepartment(department);
    }
}
