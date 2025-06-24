package com.sm.statemaster.controller;

import com.sm.statemaster.dto.pincode.PinCodeCreateDto;
import com.sm.statemaster.dto.pincode.PinCodeDto;
import com.sm.statemaster.dto.pincode.PinCodeSearchDto;
import com.sm.statemaster.enums.PinCodeStatus;
import com.sm.statemaster.service.PinCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@Tag(name = "PinCode_Controller", description = "Restfull API's for Pincode Master")
public class PinCodeController {

    private PinCodeService pinCodeService;

    public PinCodeController(PinCodeService pinCodeService) {
        this.pinCodeService = pinCodeService;
    }

    @Operation(
            description = "Accepts optional pageNumber and pageSize query parameters to control pagination.\n" +
                    "Returns a list of PinCodeDto objects."
    )
    @GetMapping("/pincodes")
    public ResponseEntity<List<PinCodeDto>> getAllPinCodes(@RequestParam(value = "PageNumber", defaultValue = "0", required = false) Integer page,
                                                           @RequestParam(value = "PageSize", defaultValue = "5", required = false) Integer pageSize) {
        List<PinCodeDto> pincodes = pinCodeService.getAllPincodes(page, pageSize);
        return new ResponseEntity<List<PinCodeDto>>(pincodes, HttpStatus.FOUND);

    }

    @Operation(
            description = "Accepts pin code details in the request body (PinCodeCreateDto) and returns a success message upon creation."
    )
    @PostMapping("/pincodes")
    public ResponseEntity<String> insertPinCode(@Valid @RequestBody PinCodeCreateDto pinCodeCreateDto) {
        String response = pinCodeService.insertPincodes(pinCodeCreateDto);
        return new ResponseEntity<String>(response, HttpStatus.CREATED);
    }

    @Operation(
            description = "Updates the details of an existing pin code identified by pinId.\n" +
                    "Accepts updated pin code data in the request body and returns the updated pin code information."
    )
    @PutMapping("/pincodes/{pinId}")
    public ResponseEntity<PinCodeDto> updatePinCode(@PathVariable Long pinId, @RequestBody PinCodeCreateDto pinCodeCreateDto) {
        PinCodeDto pinCodeDto = pinCodeService.updatePinCodes(pinId, pinCodeCreateDto);
        return new ResponseEntity<PinCodeDto>(pinCodeDto, HttpStatus.OK);
    }

    @Operation(
            description = "Deletes the pin code with the specified pinId.\n" +
                    "Returns a confirmation message upon successful deletion."
    )

    @DeleteMapping("/pincodes/{pinId}")
    public ResponseEntity<String> deletePinCode(@PathVariable Long pinId) {
        String response = pinCodeService.deletePinCode(pinId);
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }

    @Operation(
            description = "Searches pin codes based on given criteria.\n" +
                    "Accepts search filters via PinCodeSearchDto and supports pagination through pageNumber and pageSize.\n" +
                    "Returns a list of matching PinCodeDto results."
    )
    @PostMapping("/pincodes/search")
    public ResponseEntity<List<PinCodeDto>> searchPincode(@RequestParam(value = "PageNumber", defaultValue = "0", required = false) Integer page,
                                                          @RequestParam(value = "PageSize", defaultValue = "5", required = false) Integer pageSize,
                                                          PinCodeSearchDto pinCodeSearchDto) {
        List<PinCodeDto> search = pinCodeService.postSearchData(page, pageSize, pinCodeSearchDto);
        return new ResponseEntity<List<PinCodeDto>>(search, HttpStatus.OK);
    }


}
