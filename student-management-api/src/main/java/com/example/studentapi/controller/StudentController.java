package com.example.studentapi.controller;

import com.example.studentapi.dto.StudentDTO;
import com.example.studentapi.dto.StudentDTO.*;
import com.example.studentapi.model.Student.EnrollmentStatus;
import com.example.studentapi.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // ── POST /api/v1/students ────────────────────────────────────────────────
    @PostMapping
    public ResponseEntity<ApiResponse<Response>> createStudent(
            @Valid @RequestBody StudentDTO.Request request) {
        Response student = studentService.createStudent(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Student created successfully", student));
    }

    // ── GET /api/v1/students ─────────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<ApiResponse<List<Response>>> getAllStudents() {
        List<Response> students = studentService.getAllStudents();
        return ResponseEntity.ok(ApiResponse.success(
                "Retrieved " + students.size() + " students", students));
    }

    // ── GET /api/v1/students/{id} ────────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Response>> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.success("Student retrieved successfully",
                        studentService.getStudentById(id)));
    }

    // ── PUT /api/v1/students/{id} ─────────────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Response>> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentDTO.Request request) {
        return ResponseEntity.ok(
                ApiResponse.success("Student updated successfully",
                        studentService.updateStudent(id, request)));
    }

    // ── PATCH /api/v1/students/{id} ───────────────────────────────────────────
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Response>> patchStudent(
            @PathVariable Long id,
            @RequestBody StudentDTO.Request request) {  // No @Valid — partial updates OK
        return ResponseEntity.ok(
                ApiResponse.success("Student patched successfully",
                        studentService.patchStudent(id, request)));
    }

    // ── DELETE /api/v1/students/{id} ─────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok(ApiResponse.success("Student deleted successfully", null));
    }

    // ── GET /api/v1/students/search?keyword=john ─────────────────────────────
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Response>>> search(
            @RequestParam String keyword) {
        List<Response> results = studentService.searchStudents(keyword);
        return ResponseEntity.ok(ApiResponse.success(
                "Found " + results.size() + " result(s)", results));
    }

    // ── GET /api/v1/students/status/{status} ─────────────────────────────────
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<Response>>> getByStatus(
            @PathVariable EnrollmentStatus status) {
        List<Response> students = studentService.getStudentsByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(
                "Retrieved " + students.size() + " student(s) with status " + status, students));
    }

    // ── GET /api/v1/students/major/{major} ───────────────────────────────────
    @GetMapping("/major/{major}")
    public ResponseEntity<ApiResponse<List<Response>>> getByMajor(
            @PathVariable String major) {
        List<Response> students = studentService.getStudentsByMajor(major);
        return ResponseEntity.ok(ApiResponse.success(
                "Retrieved " + students.size() + " student(s) majoring in " + major, students));
    }

    // ── GET /api/v1/students/gpa?min=3.0&max=4.0 ─────────────────────────────
    @GetMapping("/gpa")
    public ResponseEntity<ApiResponse<List<Response>>> getByGpaRange(
            @RequestParam(defaultValue = "0.0") double min,
            @RequestParam(defaultValue = "4.0") double max) {
        List<Response> students = studentService.getStudentsByGpaRange(min, max);
        return ResponseEntity.ok(ApiResponse.success(
                "Found " + students.size() + " student(s) with GPA between " + min + " and " + max,
                students));
    }
}
