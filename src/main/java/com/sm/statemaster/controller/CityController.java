package com.sm.statemaster.controller;

import com.sm.statemaster.dto.city.CityCreateDto;
import com.sm.statemaster.dto.city.CityDto;
import com.sm.statemaster.dto.city.CitySearchDto;
import com.sm.statemaster.dto.city.CityUpdateDto;
import com.sm.statemaster.enums.CityStatus;
import com.sm.statemaster.service.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1")
@Tag(name = "City_Controller",description = "Restfull API's for City_master")
public class CityController {


    private CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

@Operation(
        summary = "Clients can specify the page number and page size to control the amount of data returned per request.\n" +
                "It is useful for displaying city data in user interfaces with pagination support."
)
    @GetMapping("/cities")
    public ResponseEntity<List<CityDto>> getAllCity(@RequestParam(value = "PageNumber",defaultValue = "0",required = false) Integer page,
                                                    @RequestParam(value = "PageSize",defaultValue = "5",required = false)Integer pageSize){
        List<CityDto> cities=cityService.getAllCities(page,pageSize);
        return new ResponseEntity<>(cities, HttpStatus.FOUND);
    }

    @Operation(
            summary ="Accepts a JSON request body containing city details (as CityCreateDto) and saves the city to the database.\n" +
                    "Returns a success message upon creation or a 400 Bad Request if the input is invalid."
    )
    @PostMapping("/cities")
    public ResponseEntity<String> insertCity(@Valid @RequestBody CityCreateDto cityCreateDto){
       try {
           String response=cityService.insertCities(cityCreateDto);
           return  new ResponseEntity<String>(response,HttpStatus.CREATED);
       }catch (IllegalArgumentException exception){
           return ResponseEntity.badRequest().body(exception.getMessage());
       }
    }

    @Operation(
            description = "Accepts optional pagination parameters and a request body (CitySearchDto) containing search filters (e.g., name, state, etc.).\n" +
                    "Returns a paginated list of cities that match the search criteria."
    )
    @PostMapping("/cities/search")
    public ResponseEntity<List<CityDto>> searchCity(@RequestParam(value = "PageNumber",defaultValue = "0",required = false) Integer page,
                                              @RequestParam(value = "PageSize",defaultValue = "5",required = false)Integer pageSize,
                                              CitySearchDto citySearchDto){
       List<CityDto> dto= cityService.postSearchdata(page,pageSize,citySearchDto);
       return new ResponseEntity<List<CityDto>>(dto,HttpStatus.OK);
    }

    @Operation(
            description = "Accepts a cityId path variable and a JSON payload (CityUpdateDto) containing updated city details.\n" +
                    "Returns the updated CityDto on success with HTTP status 200 OK."
    )

    @PutMapping("/cities/{cityId}")
    public ResponseEntity<CityDto> updateCity(@PathVariable Long cityId, @RequestBody CityUpdateDto cityUpdateDto){
        CityDto dto=cityService.updateCity(cityId,cityUpdateDto);
        return new ResponseEntity<CityDto>(dto,HttpStatus.OK);
    }

    @Operation(
            description = "Accepts the city ID as a path variable and a cityStatus value as a request parameter.\n" +
                    "Returns the updated city details as CityDto with HTTP status 200 OK."
    )
    @PatchMapping("/cities/{cityId}")
    public ResponseEntity<CityDto> updateCityStatus(@PathVariable Long cityId, @RequestParam CityStatus cityStatus){
        CityDto dto=cityService.updateCityStatus(cityId,cityStatus);
        return  new ResponseEntity<CityDto>(dto,HttpStatus.OK);
    }

    @Operation(
            description ="Accepts the cityId as a path variable and removes the corresponding city record from the system.\n" +
                    "Returns a confirmation message with HTTP status 200 OK upon successful deletion."
    )
    @DeleteMapping("/cities/{cityId}")
    public ResponseEntity<String> deleteCityById(@PathVariable Long cityId){
        String response=cityService.deleteCity(cityId);
        return  new ResponseEntity<String>(response,HttpStatus.OK);
    }


}
