package com.example.student.Controller;

import com.example.student.Entity.Student;
import com.example.student.Services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student")

public class StudentController {
   @Autowired StudentService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(
            @RequestBody Student student
            ){
        service.saveStudent(student);
    }

    @GetMapping()
    public ResponseEntity<?> findAllStudent(){
        return ResponseEntity.ok(service.findAllStudent());
    }

    @GetMapping("/school/{school-id}")
    public ResponseEntity<List<Student>> findAllStudents(
            @PathVariable("school-id") Integer schoolId){

return ResponseEntity.ok(service.findAllStudentsBySchool(schoolId));}
}
