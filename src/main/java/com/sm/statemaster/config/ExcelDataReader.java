package com.sm.statemaster.config;

import com.sm.statemaster.dto.pincode.PinCodeSearchDto;

import com.sm.statemaster.entity.PinCode;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
@Component
@StepScope
public class ExcelDataReader implements ItemReader<PinCodeSearchDto> {
     private final Iterator<Row> rowIterator;


    public ExcelDataReader(@Value("#{jobParameters['filePath']}") String filepath) throws IOException {
        InputStream inputStream=new FileInputStream(filepath);
        Workbook workbook=new XSSFWorkbook(inputStream);
        Sheet sheet=workbook.getSheetAt(0);
        this.rowIterator=sheet.iterator();
        this.rowIterator.next();

    }



    @Override
    public PinCodeSearchDto read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (!rowIterator.hasNext()) return null;

        Row row=rowIterator.next();
        PinCodeSearchDto pincodes=new PinCodeSearchDto();
        pincodes.setPincode((long) row.getCell(0).getNumericCellValue());
        pincodes.setCityName(row.getCell(1).getStringCellValue());
        pincodes.setStateName(row.getCell(2).getStringCellValue());
        return pincodes;
    }
}
