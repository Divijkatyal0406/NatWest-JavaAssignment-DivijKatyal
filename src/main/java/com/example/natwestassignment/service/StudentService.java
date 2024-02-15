package com.example.natwestassignment.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StudentService {
    String getStudentStatus(long rollNo);
    HttpEntity<ByteArrayResource> processUpload(MultipartFile file, long science, long maths, long computer, long english) throws IOException;
}
