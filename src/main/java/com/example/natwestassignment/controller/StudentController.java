package com.example.natwestassignment.controller;

import com.example.natwestassignment.model.Student;
import com.example.natwestassignment.repository.StudentRepository;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
    static String SHEET = "Sheet1";

    @Autowired
    StudentRepository studentRepository;

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public String studentStatus(@RequestParam("Rollno") long rollNo) {
        try {
            Optional<Student> optionalStudent = studentRepository.findById(rollNo);
            if (optionalStudent.isPresent()) {
                String eligibilityStatus = optionalStudent.get().isEligible();
                logger.info("Student with RollNo {} found. Eligibility Status: {}", rollNo, eligibilityStatus);
                return eligibilityStatus;
            } else {
                logger.info("Student with RollNo {} not found. Returning 'NA'", rollNo);
                return "NA";
            }
        } catch (Exception e) {
            logger.error("Error while fetching student status for RollNo {}: {}", rollNo, e.getMessage());
            throw new RuntimeException("Error while fetching student status: " + e.getMessage());
        }
    }

    @PostMapping(consumes = "multipart/form-data", value = "/upload")
    public HttpEntity<ByteArrayResource> uploadFile(@RequestPart(value = "Students CSV File") MultipartFile file, @RequestParam("Enter Cutoff Marks for Science") long science, @RequestParam("Enter Cutoff Marks for Maths") long maths, @RequestParam("Enter Cutoff Marks for Computer") long computer, @RequestParam("Enter Cutoff Marks for English") long english) throws IOException {
        logger.info("Received file: {} with size: {}", file.getOriginalFilename(), file.getSize());
        try {
            List<Student> students = new ArrayList<Student>();
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();

            Row firstRow = rows.next();
            boolean hasHeaders = firstRow.getCell(0).getCellType() == CellType.STRING;
            int offset = hasHeaders ? 1 : 0;

            int threadCnt = Runtime.getRuntime().availableProcessors();
            ExecutorService executorService = Executors.newFixedThreadPool(threadCnt);
            int totalRows = sheet.getLastRowNum() - sheet.getFirstRowNum() + 1;
            int rowsPerThread = totalRows / threadCnt;
            for (int i = 0; i < threadCnt; i++) {
                int startRow = offset + i * rowsPerThread;
                int endRow = (i == threadCnt - 1) ? sheet.getLastRowNum() : startRow + rowsPerThread - 1;
                executorService.submit(() -> processRows(sheet, science, maths, computer, english, startRow, endRow));
            }

            executorService.shutdown();

            try {
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            OutputStream outputStream = new OutputStream() {
                @Override
                public void write(int b) throws IOException {

                }
            };
            ByteArrayOutputStream updatedExcelStream = new ByteArrayOutputStream();
            workbook.write(updatedExcelStream);
            workbook.close();

            byte[] updatedExcelContent = updatedExcelStream.toByteArray();
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "force-download"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=updated_data.xlsx");
            return new HttpEntity<>(new ByteArrayResource(updatedExcelContent), header);
        } catch (IOException e) {
            logger.error("Failed to parse Excel file: {}", e.getMessage());
            throw new RuntimeException("Fail to parse Excel file: " + e.getMessage());
        }
    }

    void processRows(Sheet sheet, long science, long maths, long computer, long english, int startRow, int endRow) {
        for (int rowNum = startRow; rowNum <= endRow; rowNum++) {
            Row currentRow = sheet.getRow(rowNum);
            Iterator<Cell> cellsInRow = currentRow.iterator();
            Student student = new Student();
            int cellIdx = 0;
            while (cellsInRow.hasNext()) {
                Cell currentCell = cellsInRow.next();

                switch (cellIdx) {
                    case 0:
                        student.setRollNumber((long) currentCell.getNumericCellValue());
                        break;

                    case 1:
                        student.setName(currentCell.getStringCellValue());
                        break;

                    case 2:
                        student.setScienceMarks((long) currentCell.getNumericCellValue());
                        break;

                    case 3:
                        student.setMathsMarks((long) currentCell.getNumericCellValue());
                        break;

                    case 4:
                        student.setEnglishMarks((long) currentCell.getNumericCellValue());
                        break;

                    case 5:
                        student.setComputerMarks((long) currentCell.getNumericCellValue());
                        break;

                    case 6:
                        if(student.getScienceMarks()>science && student.getComputerMarks()>computer && student.getEnglishMarks()>english && student.getMathsMarks()>maths){
                            currentCell.setCellValue("YES");
                        }
                        else currentCell.setCellValue("NO");
                        student.setEligible(currentCell.getStringCellValue());
                        break;

                    default:
                        break;
                }

                cellIdx++;
            }
            logger.error("New student added with RollNo {}", student.getRollNumber());
            studentRepository.save(student);
        }
    }
}
