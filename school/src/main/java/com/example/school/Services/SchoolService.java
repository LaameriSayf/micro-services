package com.example.school.Services;

import com.example.school.Entity.School;
import com.example.school.Repository.SchoolRepository;

import com.example.school.Response.FullSchoolResponse;
import com.example.school.client.StudentClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class SchoolService {
    @Autowired
    SchoolRepository studentRepository;
    @Autowired

    private StudentClient studentClient;

    public void  saveSchools(School student){
        studentRepository.save(student);

    }

    public List<School> findAllSchools(){
        return studentRepository.findAll();
    }

    public FullSchoolResponse findSchoolsWithStudents(Long schoolId) {
        var school=studentRepository.findById(schoolId).orElse(null);
        var students=studentClient.findAllStudentsBySchool(schoolId);
        return FullSchoolResponse.builder()
                .name(school.getName())
                .email(school.getEmail())
                .students(students).build();
    }
}
