package com.example.school.Controller;

import com.example.school.Entity.School;
import com.example.school.Response.FullSchoolResponse;
import com.example.school.Services.SchoolService;

import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/schools")

public class SchoolController {
  @Autowired SchoolService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(
            @RequestBody School s
            ){
        service.saveSchools(s);
    }

    @GetMapping()
    public ResponseEntity<?> findAllSchools(){
        return ResponseEntity.ok(service.findAllSchools());
    }
    @GetMapping(value = "/with-students/{school-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FullSchoolResponse> findAllSchools(@PathVariable("school-id") Long schoolId){
        return ResponseEntity.ok(service.findSchoolsWithStudents(schoolId));
    }

}
