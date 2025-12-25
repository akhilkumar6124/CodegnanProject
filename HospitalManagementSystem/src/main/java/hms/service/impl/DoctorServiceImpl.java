package hms.service.impl;

import hms.model.Doctor;
import hms.repository.DoctorRepository;
import hms.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    @Override
    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor updateDoctor(Long id, Doctor doctor) {
        Optional<Doctor> existingDoctor = doctorRepository.findById(id);
        if (existingDoctor.isPresent()) {
            Doctor updatedDoctor = existingDoctor.get();
            updatedDoctor.setFirstName(doctor.getFirstName());
            updatedDoctor.setLastName(doctor.getLastName());
            updatedDoctor.setSpecialization(doctor.getSpecialization());
            updatedDoctor.setPhone(doctor.getPhone());
            updatedDoctor.setEmail(doctor.getEmail());
            updatedDoctor.setLicenseNumber(doctor.getLicenseNumber());
            updatedDoctor.setHireDate(doctor.getHireDate());
            updatedDoctor.setDepartment(doctor.getDepartment());
            return doctorRepository.save(updatedDoctor);
        } else {
            throw new RuntimeException("Doctor not found with id: " + id);
        }
    }

    @Override
    public void deleteDoctor(Long id) {
        if (doctorRepository.existsById(id)) {
            doctorRepository.deleteById(id);
        } else {
            throw new RuntimeException("Doctor not found with id: " + id);
        }
    }
}
