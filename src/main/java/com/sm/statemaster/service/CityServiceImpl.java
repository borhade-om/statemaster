package com.sm.statemaster.service;

import com.sm.statemaster.dto.city.CityCreateDto;
import com.sm.statemaster.dto.city.CityDto;
import com.sm.statemaster.dto.city.CitySearchDto;
import com.sm.statemaster.dto.city.CityUpdateDto;
import com.sm.statemaster.entity.City;
import com.sm.statemaster.entity.State;
import com.sm.statemaster.enums.CityStatus;
import com.sm.statemaster.mapper.CityMapper;
import com.sm.statemaster.repository.CityRepository;
import com.sm.statemaster.repository.StateRepository;
import com.sm.statemaster.specification.SpecificationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService{

    private CityRepository cityRepository;

    private CityMapper cityMapper;

    private StateRepository stateRepository;

    public CityServiceImpl(CityRepository cityRepository, CityMapper cityMapper, StateRepository stateRepository) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
        this.stateRepository = stateRepository;
    }


    @Override
    public List<CityDto> getAllCities(Integer page,Integer pageSize) {
        Pageable pages= PageRequest.of(page,pageSize);
        Page<City> pagesData=cityRepository.findByDeletedAtNull(pages);
        List<City> cities=pagesData.getContent();
        return cities.stream().map(data->cityMapper.toDto(data)).collect(Collectors.toList());
    }

    @Override
    public String insertCities(CityCreateDto cityCreateDto) {
        State state=stateRepository.findByStateIdAndDeletedAtNull(cityCreateDto.getStateId());
        if (state==null){
            throw  new IllegalArgumentException("StateId Not Found");
        }
        City city= cityMapper.toCity(cityCreateDto);
            city.setState(state);
        cityRepository.save(city);
        return "Data inserted Successfully";
    }

    @Override
    public CityDto updateCity(Long cityId, CityUpdateDto cityUpdateDto) {
        City cityid=cityRepository.findById(cityId)
                .orElseThrow(()->new IllegalArgumentException("cityId Not Found"));

        if (cityid!=null){
            if(cityUpdateDto.getName()!=null){
                  cityid.setName(cityUpdateDto.getName());
            }
            if (cityUpdateDto.getStateId()!=null){
                State state=stateRepository.findByStateIdAndDeletedAtNull(cityUpdateDto.getStateId());
                if (state==null){
                    throw new IllegalArgumentException("stateId not found");
                }
                cityid.setState(state);
            }
            cityRepository.save(cityid);
        }
        return cityMapper.toDto(cityid);
    }

    @Override
    public CityDto updateCityStatus(Long cityId, CityStatus cityStatus) {
        City citydata =cityRepository.findById(cityId)
                .orElseThrow(()->new IllegalArgumentException("cityId Not Found"));
        if(citydata !=null){
            citydata.setCityStatus(cityStatus);
        }
        cityRepository.save(citydata);

        return cityMapper.toDto(citydata);
    }

    @Override
    public String deleteCity(Long cityId) {
        City citydelete =cityRepository.findById(cityId)
                .orElseThrow(()->new IllegalArgumentException("cityId Not Found"));


            cityRepository.delete(citydelete);

        return "data deleted successfully";
    }

    @Override
    public List<CityDto> postSearchdata(Integer page, Integer pageSize, CitySearchDto citySearchDto) {
        Pageable pages=PageRequest.of(page,pageSize, Sort.by("createdAt").ascending());
        Specification<City> citySpecification = SpecificationHelper.citySearchHelper(citySearchDto);

        Page<City> pagedata=cityRepository.findAll(citySpecification,pages);
        List<City> data=pagedata.getContent();
        return cityMapper.toListCity(data);
    }

    @Override
    public String saveExcelImport(MultipartFile file) throws IOException {

        Workbook workbook=new XSSFWorkbook(file.getInputStream());
        Sheet sheet =workbook.getSheetAt(0);
        sheet.forEach(row->{
            if(row.getRowNum()!=0){
                City city = new City();
                city.setName(row.getCell(3).getStringCellValue());
                String stateName=row.getCell(1).getStringCellValue();
                State state = stateRepository.findByNameIgnoreCase(stateName).orElseThrow(()->new IllegalArgumentException("state name not found"));
                if (state!=null){
                    city.setState(state);
                }
                cityRepository.save(city);
            }
        });

        return "data inserted successfully";
    }
}
