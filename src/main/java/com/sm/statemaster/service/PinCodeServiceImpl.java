package com.sm.statemaster.service;

import com.sm.statemaster.dto.pincode.PinCodeCreateDto;
import com.sm.statemaster.dto.pincode.PinCodeDto;
import com.sm.statemaster.dto.pincode.PinCodeSearchDto;
import com.sm.statemaster.entity.City;
import com.sm.statemaster.entity.PinCode;
import com.sm.statemaster.entity.State;
import com.sm.statemaster.enums.PinCodeStatus;
import com.sm.statemaster.mapper.PinCodeMapper;
import com.sm.statemaster.repository.CityRepository;
import com.sm.statemaster.repository.PinCodeRepository;
import com.sm.statemaster.repository.StateRepository;
import com.sm.statemaster.specification.SpecificationHelper;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PinCodeServiceImpl implements PinCodeService {

    private PinCodeRepository pinCodeRepository;

    private CityRepository cityRepository;

    private StateRepository stateRepository;

    private PinCodeMapper pinCodeMapper;

    private final Integer BATCHCOUNT=1000;

    public PinCodeServiceImpl(PinCodeRepository pinCodeRepository, CityRepository cityRepository, StateRepository stateRepository, PinCodeMapper pinCodeMapper) {
        this.pinCodeRepository = pinCodeRepository;
        this.cityRepository = cityRepository;
        this.stateRepository = stateRepository;
        this.pinCodeMapper = pinCodeMapper;
    }

    @Override
    public List<PinCodeDto> getAllPincodes(Integer page, Integer pageSize) {
        Pageable pages = PageRequest.of(page, pageSize);
        Page<PinCode> pageList = pinCodeRepository.findByDeletedAtNull(pages);
        List<PinCode> data = pageList.getContent();
        return data.stream().map(listdata ->
                pinCodeMapper.toDto(listdata)).collect(Collectors.toList());
    }


    @Override
    public String insertPincodes(PinCodeCreateDto pinCodeCreateDto) {
        State state = stateRepository.findByStateIdAndDeletedAtNull(pinCodeCreateDto.getStateId());
        if (state == null) {
            throw new IllegalArgumentException("StateId not Found");
        }
        City city = cityRepository.findByCityIdAndDeletedAtNull(pinCodeCreateDto.getCityId());
        if (city == null) {
            throw new IllegalArgumentException("CityId Not Found");
        }
        PinCode pinCode = pinCodeMapper.toEntity(pinCodeCreateDto);
        pinCode.setCities(city);
        pinCode.setStates(state);
        pinCodeRepository.save(pinCode);
        return "Data Inserted successfully";
    }

    @Override
    public PinCodeDto updatePinCodes(Long pinId, PinCodeCreateDto pinCodeCreateDto) {
        PinCode pinCode = pinCodeRepository.findById(pinId)
                .orElseThrow(() -> new IllegalArgumentException("PinCodeId Not Found"));
        if (pinCode != null) {
            if (pinCodeCreateDto.getPinCode() != null) {
                pinCode.setPinCode(pinCodeCreateDto.getPinCode());
            }
            if (pinCodeCreateDto.getCityId() != null) {
                City city = cityRepository.findByCityIdAndDeletedAtNull(pinCodeCreateDto.getCityId());
                if (city == null) {
                    throw new IllegalArgumentException("CityId Not Found");
                }
                pinCode.setCities(city);
            }
            if (pinCodeCreateDto.getStateId() != null) {
                State state = stateRepository.findByStateIdAndDeletedAtNull(pinCodeCreateDto.getStateId());
                if (state == null) {
                    throw new IllegalArgumentException("StateId not Found");
                }
                pinCode.setStates(state);
            }
            pinCodeRepository.save(pinCode);

        }
        return pinCodeMapper.toDto(pinCode);
    }

    @Override
    public String deletePinCode(Long pinId) {
        PinCode pinCode = pinCodeRepository.findByPinIdAndDeletedAtNull(pinId);
        if (pinCode == null) {
            throw new IllegalArgumentException("PinCodeId Not Found");
        }
        pinCodeRepository.delete(pinCode);

        return "PinCode Deleted successfully";

    }

    @Override
    public PinCodeDto updatePincodeStetus(Long pinId, PinCodeStatus pinCodeStatus) {
        PinCode pinCode = pinCodeRepository.findByPinIdAndDeletedAtNull(pinId);
        if (pinCode == null) {
            throw new IllegalArgumentException("PinCodeId Not Found");
        }
        pinCode.setPinStatus(pinCodeStatus);
        pinCodeRepository.save(pinCode);

        return pinCodeMapper.toDto(pinCode);
    }

    @Override
    public List<PinCodeDto> postSearchData(Integer page, Integer pageSize, PinCodeSearchDto pinCodeSearchDto) {
        Pageable pages=PageRequest.of(page,pageSize, Sort.by("createdAt").ascending());
        Specification<PinCode> pinCodeSpecification = SpecificationHelper.pinCodeSeaarchHelper(pinCodeSearchDto);
        Page<PinCode> pageData=pinCodeRepository.findAll(pinCodeSpecification,pages);
        List<PinCode> search=pageData.getContent();
        return pinCodeMapper.toListDto(search);
    }

    @Override
    public String saveExcelData(MultipartFile file) throws IOException {
        Workbook workbook= new XSSFWorkbook(file.getInputStream());

        Sheet sheet=workbook.getSheetAt(0);

        sheet.forEach(row->{
            if(row.getRowNum()!=0){
                String stateName=row.getCell(1).getStringCellValue();
                String cityName=row.getCell(2).getStringCellValue();
                Long pincode= (long) row.getCell(0).getNumericCellValue();

                State state=  stateRepository.findByNameIgnoreCase(stateName)
                        .orElseGet(()->{
                            State newstate=new State();
                            newstate.setName(stateName);
                           return stateRepository.save(newstate);
                        });

                City city= cityRepository.findByNameIgnoreCase(cityName)
                        .orElseGet(()->{
                            City cities=new City();
                            cities.setName(cityName);
                            cities.setState(state);
                            return cityRepository.save(cities);

                        });

                PinCode pincodes=pinCodeRepository.findByPinCode(pincode)
                        .orElseGet(()->{
                            PinCode pin=new PinCode();
                            pin.setPinCode(pincode);
                            pin.setStates(state);
                            pin.setCities(city);
                            return pinCodeRepository.save(pin);
                        });



            }
        });
        return "data Inserted successfully";
    }

    @Override
    public String pinCodeExcelImport(MultipartFile file) throws IOException {
        Workbook workbook=new XSSFWorkbook(file.getInputStream());
        Sheet sheet =workbook.getSheetAt(0);
        sheet.forEach(row->{
            if (row.getRowNum()!=0){
                PinCode pincode=new PinCode();
                pincode.setPinCode((long) row.getCell(0).getNumericCellValue());
                String cityname=row.getCell(2).getStringCellValue();
                String statename=row.getCell(1).getStringCellValue();
                State state = stateRepository.findByNameIgnoreCase(statename).orElseThrow(()->new IllegalArgumentException("state name not found"));
                if (state!=null){
                    pincode.setStates(state);
                }
                City city=cityRepository.findByNameIgnoreCase(cityname).orElseThrow(()->new IllegalArgumentException("city id not found"));
                if (city!=null){
                    pincode.setCities(city);
                }

                pinCodeRepository.save(pincode);
            }
        });
        return "data inserted successfully";
    }

    @Override
    public void exportPinCodeData(HttpServletResponse response) throws IOException {
        List<PinCode> PinData = pinCodeRepository.findAll();
        Workbook workbook=new XSSFWorkbook();
        Sheet sheet=workbook.createSheet();
        String[] columns={"PinCode Id","PinCodes","City Name","State Name"};
        Font headerFont=workbook.createFont();
        headerFont.setBold(true);
        CellStyle headerStyle =workbook.createCellStyle();
        headerStyle.setFont(headerFont);

        Row headerRow=sheet.createRow(0);
        for(int i=0;i<columns.length;i++){
            Cell cell=headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerStyle);
        }

        int index=1;
        for(PinCode pin:PinData){
            Row row=sheet.createRow(index++);
            row.createCell(0).setCellValue(pin.getPinId());
            row.createCell(1).setCellValue(pin.getPinCode());
            row.createCell(2).setCellValue(pin.getCities().getName());
            row.createCell(3).setCellValue(pin.getStates().getName());
        }
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition","attachment; filename=Pincodes.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
