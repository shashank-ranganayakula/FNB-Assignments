package com.example.studentapi.service;

import com.example.studentapi.dto.StudentDTO;
import com.example.studentapi.dto.StudentDTO.*;
import com.example.studentapi.exception.DuplicateEmailException;
import com.example.studentapi.exception.StudentNotFoundException;
import com.example.studentapi.model.Student;
import com.example.studentapi.model.Student.EnrollmentStatus;
import com.example.studentapi.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;

    // ── CREATE ──────────────────────────────────────────────────────────────
    public Response createStudent(StudentDTO.Request request) {
        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException(request.getEmail());
        }
        Student student = toEntity(request);
        return toResponse(studentRepository.save(student));
    }

    // ── READ ALL ─────────────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<Response> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // ── READ ONE ─────────────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public Response getStudentById(Long id) {
        return toResponse(findStudentOrThrow(id));
    }

    // ── UPDATE ───────────────────────────────────────────────────────────────
    public Response updateStudent(Long id, StudentDTO.Request request) {
        Student existing = findStudentOrThrow(id);

        // Allow e-mail change only if it isn't taken by another student
        if (!existing.getEmail().equalsIgnoreCase(request.getEmail())
                && studentRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException(request.getEmail());
        }

        existing.setFirstName(request.getFirstName());
        existing.setLastName(request.getLastName());
        existing.setEmail(request.getEmail());
        existing.setDateOfBirth(request.getDateOfBirth());
        existing.setMajor(request.getMajor());
        existing.setGpa(request.getGpa());
        if (request.getStatus() != null) {
            existing.setStatus(request.getStatus());
        }

        return toResponse(studentRepository.save(existing));
    }

    // ── PARTIAL UPDATE (PATCH) ────────────────────────────────────────────────
    public Response patchStudent(Long id, StudentDTO.Request request) {
        Student existing = findStudentOrThrow(id);

        if (request.getFirstName()   != null) existing.setFirstName(request.getFirstName());
        if (request.getLastName()    != null) existing.setLastName(request.getLastName());
        if (request.getMajor()       != null) existing.setMajor(request.getMajor());
        if (request.getGpa()         != null) existing.setGpa(request.getGpa());
        if (request.getStatus()      != null) existing.setStatus(request.getStatus());
        if (request.getDateOfBirth() != null) existing.setDateOfBirth(request.getDateOfBirth());

        if (request.getEmail() != null
                && !existing.getEmail().equalsIgnoreCase(request.getEmail())) {
            if (studentRepository.existsByEmail(request.getEmail())) {
                throw new DuplicateEmailException(request.getEmail());
            }
            existing.setEmail(request.getEmail());
        }

        return toResponse(studentRepository.save(existing));
    }

    // ── DELETE ───────────────────────────────────────────────────────────────
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        studentRepository.deleteById(id);
    }

    // ── SEARCH & FILTER ──────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<Response> searchStudents(String keyword) {
        return studentRepository.searchByKeyword(keyword)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Response> getStudentsByStatus(EnrollmentStatus status) {
        return studentRepository.findByStatus(status)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Response> getStudentsByMajor(String major) {
        return studentRepository.findByMajorIgnoreCase(major)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Response> getStudentsByGpaRange(double minGpa, double maxGpa) {
        return studentRepository.findByGpaRange(minGpa, maxGpa)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // ── HELPERS ──────────────────────────────────────────────────────────────
    private Student findStudentOrThrow(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    private Student toEntity(StudentDTO.Request request) {
        return Student.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .dateOfBirth(request.getDateOfBirth())
                .major(request.getMajor())
                .gpa(request.getGpa())
                .status(request.getStatus() != null
                        ? request.getStatus()
                        : EnrollmentStatus.ACTIVE)
                .build();
    }

    private Response toResponse(Student student) {
        return Response.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .fullName(student.getFirstName() + " " + student.getLastName())
                .email(student.getEmail())
                .dateOfBirth(student.getDateOfBirth())
                .major(student.getMajor())
                .gpa(student.getGpa())
                .status(student.getStatus())
                .createdAt(student.getCreatedAt())
                .updatedAt(student.getUpdatedAt())
                .build();
    }
}
