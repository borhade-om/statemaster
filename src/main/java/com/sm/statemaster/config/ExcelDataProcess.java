package com.sm.statemaster.config;

import com.sm.statemaster.dto.pincode.PinCodeSearchDto;
import com.sm.statemaster.entity.City;
import com.sm.statemaster.entity.PinCode;
import com.sm.statemaster.entity.State;
import com.sm.statemaster.repository.CityRepository;
import com.sm.statemaster.repository.PinCodeRepository;
import com.sm.statemaster.repository.StateRepository;
import com.sm.statemaster.service.PinCodeService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ExcelDataProcess  implements ItemProcessor<PinCodeSearchDto, PinCode> {
    private StateRepository stateRepository;
    private CityRepository cityRepository;
    private PinCodeRepository pinCodeRepository;

    public ExcelDataProcess(StateRepository stateRepository, CityRepository cityRepository, PinCodeRepository pinCodeRepository) {
        this.stateRepository = stateRepository;
        this.cityRepository = cityRepository;
        this.pinCodeRepository = pinCodeRepository;
    }
    @Override
    public PinCode process(PinCodeSearchDto dto) throws Exception {

        State states = stateRepository.findByNameIgnoreCase(dto.getStateName()).orElseGet(() -> {
                    State state = new State();
                    state.setName(dto.getStateName());
                    return stateRepository.save(state);
                }
        );

        City cities=cityRepository.findByNameIgnoreCase(dto.getCityName()).orElseGet(()->{
             City city=new City();
             city.setState(states);
             city.setName(dto.getCityName());
             return cityRepository.save(city);
        });
        Optional<PinCode> pincodes=pinCodeRepository.findByPinCode(dto.getPincode());
              PinCode pin=new PinCode();
                if(pincodes.isEmpty()){
                    pin.setPinCode(dto.getPincode());
                    pin.setStates(states);
                    pin.setCities(cities);
                }

        return pin;
  }



}
