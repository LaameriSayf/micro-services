package com.example.school.Response;

import com.example.school.Entity.Student;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FullSchoolResponse {
    private String name;
    private String email;
    List<Student> students;

    private FullSchoolResponse(Builder builder) {
        this.name = builder.name;
        this.email = builder.email;
        this.students = builder.students;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private String email;
        private List<Student> students;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder students(List<Student> students) {
            this.students = students;
            return this;
        }

        public FullSchoolResponse build() {
            return new FullSchoolResponse(this);
        }
    }


}
