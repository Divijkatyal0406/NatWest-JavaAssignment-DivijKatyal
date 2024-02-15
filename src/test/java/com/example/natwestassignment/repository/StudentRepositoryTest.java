package com.example.natwestassignment.repository;

import com.example.natwestassignment.model.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@RunWith(SpringRunner.class)
public class StudentRepositoryTest {
    @Autowired
    private StudentRepository repo;

    @BeforeEach
    void setUp() {
        Student student = new Student();
        student.setRollNumber(1L);
        student.setEligible("YES");
        repo.save(student);
    }

    @Test
    void shouldFindStudentByRollNumber() {
        Long rollNumber = 1L;
        Student foundStudent = repo.findById(rollNumber).orElse(null);
        Assertions.assertNotNull(foundStudent);
        Assertions.assertEquals(rollNumber, foundStudent.getRollNumber());
        Assertions.assertEquals("YES", foundStudent.isEligible());
    }
}
