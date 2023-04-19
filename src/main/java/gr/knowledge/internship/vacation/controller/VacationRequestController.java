package gr.knowledge.internship.vacation.controller;

import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.service.VacationRequestService;
import gr.knowledge.internship.vacation.service.dto.VacationRequestDTO;
import gr.knowledge.internship.vacation.service.dto.VacationRequestInfoDTO;
import gr.knowledge.internship.vacation.service.dto.VacationRequestInputDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api")
public class VacationRequestController {

    public static final String ID_NOT_FOUND = "VacationRequest id not found";
    public static final String BAD_INPUT_FOR_UPDATING_THE_VACATION_REQUEST = "Bad input for updating the vacationRequest";

    private final VacationRequestService vacationRequestService;


    public VacationRequestController(VacationRequestService vacationRequestService) {
        this.vacationRequestService = vacationRequestService;
    }

    @PostMapping("/createVacationRequest")
    public ResponseEntity<VacationRequestDTO> createVacationRequest(@RequestBody VacationRequestInputDTO vacationRequestInputDTO) {
        log.debug("REST request to create a vacationRequest : {}", vacationRequestInputDTO);
        VacationRequestDTO result = vacationRequestService.createVacationRequest(vacationRequestInputDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/vacationRequest")
    public ResponseEntity<VacationRequestDTO> saveVacationRequest(@RequestBody VacationRequestDTO vacationRequestDTO) {
        log.debug("REST request to save a vacationRequest : {}", vacationRequestDTO);
        VacationRequestDTO result = vacationRequestService.save(vacationRequestDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/vacationRequest")
    public ResponseEntity<VacationRequestDTO> updateVacationRequest(@RequestBody VacationRequestDTO vacationRequestDTO) {
        log.debug("REST request to update a vacationRequest : {}", vacationRequestDTO);
        if (vacationRequestDTO == null || vacationRequestDTO.getId() == null) {
            throw new NotFoundException(BAD_INPUT_FOR_UPDATING_THE_VACATION_REQUEST);
        }
        Boolean isExistingVacationRequest = vacationRequestService.isExistingVacationRequest(vacationRequestDTO.getId());
        if (Boolean.FALSE.equals(isExistingVacationRequest)) {
            throw new NotFoundException(ID_NOT_FOUND);
        }
        VacationRequestDTO result = vacationRequestService.save(vacationRequestDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/vacationRequests")
    public ResponseEntity<List<VacationRequestDTO>> getAllVacationRequest() {
        log.debug("Rest request to get all VacationRequests ");
        List<VacationRequestDTO> result = vacationRequestService.getAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/vacationRequest/{id}")
    public ResponseEntity<VacationRequestDTO> getVacationRequest(@PathVariable Long id) {
        log.debug("Rest request to get  vacationRequest :{} ", id);
        VacationRequestDTO result = vacationRequestService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/vacationRequestsCompany")
    public ResponseEntity<List<VacationRequestDTO>> getVacationRequestsByTimelineAndStatus(@RequestBody VacationRequestInfoDTO vacationRequestInfoDTO) {
        log.debug("Rest request to get all vacationRequests of a company based on status and timeline");
        List<VacationRequestDTO> result = vacationRequestService.getVacationRequestsByTimelineAndStatus(vacationRequestInfoDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/vacationRequest/{id}")
    public ResponseEntity<Void> deleteVacationRequest(@PathVariable Long id) {
        log.debug("REST request to delete VacationRequest  : {}", id);
        vacationRequestService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HttpHeaders.EMPTY)
                .build();
    }
}
