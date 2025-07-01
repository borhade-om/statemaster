package com.sm.statemaster.config;

import com.sm.statemaster.entity.PinCode;
import com.sm.statemaster.repository.PinCodeRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExcelDataWriter implements ItemWriter<PinCode> {
    @Autowired
    private PinCodeRepository pinCodeRepository;
    @Override
    public void write(Chunk<? extends PinCode> chunk) throws Exception {
            pinCodeRepository.saveAll(chunk);
    }
}
