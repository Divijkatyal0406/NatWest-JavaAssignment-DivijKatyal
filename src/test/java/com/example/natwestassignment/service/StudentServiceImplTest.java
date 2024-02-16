package com.example.natwestassignment.service;
import com.example.natwestassignment.model.Student;
import com.example.natwestassignment.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class StudentServiceTests {

    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private StudentRepository studentRepository;


    @Test
    void testGetStudentStatusForValidStudent() {
        long rollNo = 1L;
        Student mockStudent = new Student(1L,"Divij",76,76,786,86,"YES");
        studentRepository.save(mockStudent);
        String status = studentService.getStudentStatus(rollNo);
        assertEquals("YES", status);
    }

    @Test
    void testGetStudentStatusForInvalidStudent() {
        long rollNo = 2L;
        String status = studentService.getStudentStatus(rollNo);
        assertEquals("NA", status);
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
        System.out.println(mockFile);
        HttpEntity<ByteArrayResource> result = studentService.processUpload(mockFile, science, maths, computer, english);
        assertEquals("application/force-download", result!=null?result.getHeaders().getContentType().toString():"");
    }
}
