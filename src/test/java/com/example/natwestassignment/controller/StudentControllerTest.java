package com.example.natwestassignment.controller;

import org.apache.poi.ss.usermodel.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static javax.swing.UIManager.get;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StudentControllerTest {
    @Mock
    private Sheet sheet;

    @Mock
    private Row row;

    @Mock
    private Cell cell;

    @Autowired
    private MockMvc mockMvc;

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

    @Test
    public void testStatusEndpoint() throws Exception {
        String rollNumber = "5";

        mockMvc.perform((RequestBuilder) get("/status?RollNo=" + rollNumber))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) content().string("NA"));

    }

}
