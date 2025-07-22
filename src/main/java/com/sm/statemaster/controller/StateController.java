package com.sm.statemaster.controller;

import com.sm.statemaster.dto.state.StateCreateDto;
import com.sm.statemaster.dto.state.StateDto;
import com.sm.statemaster.dto.state.StateSearchDto;
import com.sm.statemaster.enums.StateStatus;
import com.sm.statemaster.service.StateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "State_Controller")
public class StateController {

    private StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @Operation(
            description = "Retrieves a paginated list of all states.\n" +
                    "Accepts optional pageNumber and pageSize query parameters to control the size of the result set.\n" +
                    "Returns a list of StateDto objects.\n"

    )
    @GetMapping("/states")
    public ResponseEntity<List<StateDto>> GetStateData(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer page,
                                                       @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize) {
        List<StateDto> dto = stateService.getAllSate(page, pageSize);
        return new ResponseEntity<>(dto, HttpStatus.FOUND);
    }

    @Operation(
            description = "Accepts state details in the request body (StateCreateDto) and returns a success message upon creation."
    )
    @PostMapping("/states")
    public ResponseEntity<String> PostDataInState(@Valid @RequestBody StateCreateDto stateCreateDto) {
        String data = stateService.inserIntoState(stateCreateDto);
        return new ResponseEntity<String>(data, HttpStatus.CREATED);
    }

    @Operation(
            description = "Performs a soft delete of the state identified by stateId.\n" +
                    "Returns a confirmation message if the deletion is successful, or a 400 Bad Request if the state ID is invalid.\n" +
                    "\n"
    )

    @DeleteMapping("/states/{stateId}")
    public ResponseEntity<String> deleteState(@PathVariable Long stateId) {
        try {
            String delete = stateService.softDeleteState(stateId);
            return new ResponseEntity<String>(delete, HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }

    }

    @Operation(
            description = "Updates the details of an existing state identified by stateId.\n" +
                    "Accepts updated state data in the request body (StateCreateDto) and returns the updated StateDto on success."
    )
    @PutMapping("/states/{stateId}")
    public ResponseEntity<StateDto> updatedata(@PathVariable Long stateId, @RequestBody StateCreateDto stateCreateDto) {
        StateDto sdto = stateService.updateStatedata(stateId, stateCreateDto);
        return new ResponseEntity<StateDto>(sdto, HttpStatus.OK);
    }

    @Operation(
            description = "Updates the status of an existing state identified by stateId.\n" +
                    "Accepts the new status as a request parameter (StateStatus) and returns the updated StateDto on success."
    )
    @PatchMapping("/states/{stateId}")
    public ResponseEntity<StateDto> PatchStateStatus(@PathVariable Long stateId, @RequestParam StateStatus stateStatus) {
        StateDto sdto = stateService.updateStatus(stateId, stateStatus);
        return new ResponseEntity<StateDto>(sdto, HttpStatus.OK);
    }

    @Operation(
            description = "Searches for states based on given criteria.\n" +
                    "Accepts filter data via StateSearchDto and supports pagination using pageNumber and pageSize.\n" +
                    "Returns a list of matching StateDto objects."
    )
    @PostMapping("/states/search")
    public ResponseEntity<List<StateDto>> searchState(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer page,
                                                      @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                                      StateSearchDto stateSearchDto) {
        List<StateDto> dto = stateService.postSearchData(page, pageSize, stateSearchDto);
        return new ResponseEntity<List<StateDto>>(dto, HttpStatus.OK);
    }

    @PostMapping(value = "/states/bulkupload" ,consumes = "multipart/form-data")
    public ResponseEntity<String> bulkUpload(@RequestParam("file") MultipartFile file ) throws IOException {
        String data=stateService.stateExcelImport(file);
        return new ResponseEntity<String>(data,HttpStatus.OK);

    }

    @GetMapping("/states/export")
    public void stateExport(HttpServletResponse response) throws IOException {
        stateService.exportStateData(response);
    }


}
