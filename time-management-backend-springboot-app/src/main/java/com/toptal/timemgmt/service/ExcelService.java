package com.toptal.timemgmt.service;

import com.toptal.timemgmt.payloads.WorkLogResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
public class ExcelService {

  static String[] HEADERs = {"Date", "TotalTime", "Notes"};

  public ByteArrayInputStream formData(List<WorkLogResponse> filteredWorkLog) {
    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      Sheet sheet = workbook.createSheet("WorkLogs");

      Row headerRow = sheet.createRow(0);

      for (int col = 0; col < HEADERs.length; col++) {
        Cell cell = headerRow.createCell(col);
        cell.setCellValue(HEADERs[col]);
      }

      Map<Date, List<WorkLogResponse>> groupedbyData = filteredWorkLog.stream().collect(Collectors
          .groupingBy(WorkLogResponse::getWorkingDate));

      int rowIndex=1;
      for(Entry<Date, List<WorkLogResponse>> entry : groupedbyData.entrySet()) {
        Row row = sheet.createRow(rowIndex++);
        Cell cell1 = row.createCell(0);
        cell1.setCellValue(entry.getKey().toString());

        Cell cell2 = row.createCell(1);
        cell2.setCellValue(entry.getValue().stream().collect(Collectors.summingInt(WorkLogResponse::getWorkingTime)));

        Cell cell3 = row.createCell(2);
        cell3.setCellValue(entry.getValue().stream().map(WorkLogResponse::getNotes).collect(Collectors.joining(", ")));


      }

      workbook.write(out);
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
    }
  }

}
