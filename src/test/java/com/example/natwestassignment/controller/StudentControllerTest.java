package com.example.natwestassignment.controller;

import org.apache.poi.ss.usermodel.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StudentControllerTest {
    @Mock
    private Sheet sheet;

    @Mock
    private Row row;

    @Mock
    private Cell cell;

    @Test
    public void testProcessRows() throws IOException {
        File file = ResourceUtils.getFile("classpath:test_data.xlsx");
        InputStream inputStream = new FileInputStream(file);
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "Students CSV File", "test_data.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", inputStream);

        Workbook workbook = WorkbookFactory.create(mockMultipartFile.getInputStream());
        when(workbook.getSheet(any())).thenReturn(sheet);

        when(sheet.iterator()).thenReturn(List.of(row).iterator());
        when(row.iterator()).thenReturn(List.of(cell).iterator());

        when(cell.getNumericCellValue()).thenReturn(90.0);

        StudentController controller = new StudentController();
        controller.processRows(sheet, 85, 90, 95, 75, 0, 0);
    }
}
