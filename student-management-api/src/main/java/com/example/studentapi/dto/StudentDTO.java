package com.example.studentapi.dto;

import com.example.studentapi.model.Student.EnrollmentStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class StudentDTO {

    // ── Request DTO (used for Create & Update) ──────────────────────────────
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {

        @NotBlank(message = "First name is required")
        @Size(min = 2, max = 50)
        private String firstName;

        @NotBlank(message = "Last name is required")
        @Size(min = 2, max = 50)
        private String lastName;

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        private String email;

        @NotNull(message = "Date of birth is required")
        @Past(message = "Date of birth must be in the past")
        private LocalDate dateOfBirth;

        @NotBlank(message = "Major is required")
        private String major;

        @NotNull(message = "GPA is required")
        @DecimalMin(value = "0.0", message = "GPA must be at least 0.0")
        @DecimalMax(value = "4.0", message = "GPA must be at most 4.0")
        private Double gpa;

        private EnrollmentStatus status;
    }

    // ── Response DTO (used for all responses) ───────────────────────────────
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private String firstName;
        private String lastName;
        private String fullName;
        private String email;
        private LocalDate dateOfBirth;
        private String major;
        private Double gpa;
        private EnrollmentStatus status;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    // ── API Envelope ─────────────────────────────────────────────────────────
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ApiResponse<T> {
        private boolean success;
        private String message;
        private T data;

        public static <T> ApiResponse<T> success(String message, T data) {
            return ApiResponse.<T>builder()
                    .success(true)
                    .message(message)
                    .data(data)
                    .build();
        }

        public static <T> ApiResponse<T> error(String message) {
            return ApiResponse.<T>builder()
                    .success(false)
                    .message(message)
                    .build();
        }
    }
}
