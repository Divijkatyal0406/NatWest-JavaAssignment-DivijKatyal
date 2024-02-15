package com.example.natwestassignment.controller;
import com.example.natwestassignment.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @GetMapping(value = "/status")
    public String studentStatus(@RequestParam("Rollno") long rollNo) {
        try {
            return studentService.getStudentStatus(rollNo);
        } catch (RuntimeException e) {
            logger.error("Error while fetching student status for RollNo {}: {}", rollNo, e.getMessage());
            throw new RuntimeException("Error while fetching student status: " + e.getMessage());
        }
    }

    @PostMapping(consumes = "multipart/form-data", value = "/upload")
    public HttpEntity<ByteArrayResource> uploadFile(@RequestPart(value = "Students CSV File") MultipartFile file, @RequestParam("Enter Cutoff Marks for Science") long science, @RequestParam("Enter Cutoff Marks for Maths") long maths, @RequestParam("Enter Cutoff Marks for Computer") long computer, @RequestParam("Enter Cutoff Marks for English") long english) throws IOException {
        logger.info("Received uploaded file");
        return studentService.processUpload(file, science, maths, computer, english);

    }
}