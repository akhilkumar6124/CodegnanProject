package hms.repository;

import hms.model.Department;
import hms.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    /**
     * Find doctor by email (case-insensitive)
     */
    Optional<Doctor> findByEmailIgnoreCase(String email);

    /**
     * Find doctor by license number (case-insensitive)
     */
    Optional<Doctor> findByLicenseNumberIgnoreCase(String licenseNumber);

    /**
     * Check if doctor exists by email
     */
    boolean existsByEmail(String email);

    /**
     * Check if doctor exists by license number
     */
    boolean existsByLicenseNumber(String licenseNumber);

    /**
     * Find doctors by department
     */
    List<Doctor> findByDepartment(Department department);

    /**
     * Find doctors by specialization
     */
    List<Doctor> findBySpecialization(String specialization);

    /**
     * Find doctors by specialization containing the given text (case-insensitive)
     */
    @Query("SELECT d FROM Doctor d WHERE LOWER(d.specialization) LIKE LOWER(CONCAT('%', :specialization, '%'))")
    List<Doctor> findBySpecializationContainingIgnoreCase(@Param("specialization") String specialization);

    /**
     * Find doctors by first name or last name containing the given text (case-insensitive)
     */
    @Query("SELECT d FROM Doctor d WHERE LOWER(d.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(d.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Doctor> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(@Param("name") String name);
}
