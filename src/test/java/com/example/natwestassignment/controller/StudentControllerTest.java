package com.example.natwestassignment.controller;

import com.example.natwestassignment.model.Student;
import com.example.natwestassignment.repository.StudentRepository;
import com.example.natwestassignment.service.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentControllerTest {

    @Autowired
    private StudentController studentController;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void testStudentStatus() {
        long rollNo = 1234;
        Student expectedStudent = new Student(rollNo, "Divij Katyal", 75, 80, 90, 85, "YES");
        studentRepository.save(expectedStudent);
        String actualStatus = studentController.studentStatus(rollNo);
        assertEquals("YES", actualStatus);
    }

    @Test
    void testProcessUpload() throws IOException {
        File file = ResourceUtils.getFile("classpath:test.xlsx");
        MultipartFile mockFile = new MockMultipartFile(
                "file",
                file.getName(),
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                new FileInputStream(file)
        );
        long science = 70L;
        long maths = 75L;
        long computer = 80L;
        long english = 85L;
        HttpEntity<ByteArrayResource> result = studentController.uploadFile(mockFile, science, maths, computer, english);
        assertEquals("application/force-download", result!=null?result.getHeaders().getContentType().toString():"");
    }
}
