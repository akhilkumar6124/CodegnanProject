package hms.repository;

import hms.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    /**
     * Find patient by email (case-insensitive)
     */
    Optional<Patient> findByEmailIgnoreCase(String email);

    /**
     * Find patient by phone (case-insensitive)
     */
    Optional<Patient> findByPhoneIgnoreCase(String phone);

    /**
     * Check if patient exists by email
     */
    boolean existsByEmail(String email);

    /**
     * Check if patient exists by phone
     */
    boolean existsByPhone(String phone);

    /**
     * Find patients by gender
     */
    List<Patient> findByGender(String gender);

    /**
     * Find patients by date of birth
     */
    List<Patient> findByDateOfBirth(LocalDate dateOfBirth);

    /**
     * Find patients by date of birth range
     */
    @Query("SELECT p FROM Patient p WHERE p.dateOfBirth BETWEEN :startDate AND :endDate")
    List<Patient> findByDateOfBirthBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    /**
     * Find patients by first name or last name containing the given text (case-insensitive)
     */
    @Query("SELECT p FROM Patient p WHERE LOWER(p.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Patient> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(@Param("name") String name);
}
