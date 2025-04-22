package com.example.student.Services;

import com.example.student.Entity.Student;
import com.example.student.Repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class StudentService {
   @Autowired
   StudentRepository studentRepository;

    public void  saveStudent(Student student){
        studentRepository.save(student);

    }

    public List<Student> findAllStudent(){
        return studentRepository.findAll();
    }

    public List<Student> findAllStudentsBySchool(Integer schoolId) {
        return studentRepository.findAllBySchoolId(schoolId);}
}
