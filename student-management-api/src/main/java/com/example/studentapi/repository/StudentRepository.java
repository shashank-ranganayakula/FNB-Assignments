package com.example.studentapi.repository;

import com.example.studentapi.model.Student;
import com.example.studentapi.model.Student.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Student> findByStatus(EnrollmentStatus status);

    List<Student> findByMajorIgnoreCase(String major);

    @Query("SELECT s FROM Student s WHERE " +
           "LOWER(s.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.lastName)  LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.email)     LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Student> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT s FROM Student s WHERE s.gpa >= :minGpa AND s.gpa <= :maxGpa")
    List<Student> findByGpaRange(@Param("minGpa") double minGpa,
                                 @Param("maxGpa") double maxGpa);
}
