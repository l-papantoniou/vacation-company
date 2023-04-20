package gr.knowledge.internship.vacation.service;

import gr.knowledge.internship.vacation.domain.Employee;
import gr.knowledge.internship.vacation.domain.VacationRequest;
import gr.knowledge.internship.vacation.enums.VacationRequestStatus;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.EmployeeRepository;
import gr.knowledge.internship.vacation.repository.VacationRequestRepository;
import gr.knowledge.internship.vacation.service.dto.*;
import gr.knowledge.internship.vacation.service.mapper.EmployeeMapper;
import gr.knowledge.internship.vacation.service.mapper.VacationRequestMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@Transactional
@Log4j2
public class VacationRequestService {

    public static final String BAD_INPUT_FOR_VACATION_DAYS = "Bad Input for vacation Days";
    public static final String VACATION_DAYS_REQUESTED_AND_VACATION_DAYS_AVAILABLE_ARE_NO_COMPATIBLE = "Vacation days requested and vacation days available are no compatible";
    private final VacationRequestRepository vacationRequestRepository;
    private final VacationRequestMapper vacationRequestMapper;

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;


    private static final String NotFoundExceptionMessage = "Not Found";
    private static final String NotFoundStatusExceptionMessage = "Not Found status";
    private static final String NotFoundVacationExceptionMessage = "Not Found Vacation";

    public VacationRequestService(VacationRequestRepository vacationRequestRepository, EmployeeRepository employeeRepository, VacationRequestMapper vacationRequestMapper, EmployeeMapper employeeMapper) {
        this.vacationRequestRepository = vacationRequestRepository;
        this.employeeRepository = employeeRepository;
        this.vacationRequestMapper = vacationRequestMapper;
        this.employeeMapper = employeeMapper;
    }

    /**
     * Sava a vacationRequest
     *
     * @param vacationRequestDTO the entity to save
     * @return the entity
     */
    @Transactional
    public VacationRequestDTO save(VacationRequestDTO vacationRequestDTO) {
        log.debug("Request to save vacationRequestDTO : {}", vacationRequestDTO);
        VacationRequest vacationRequest = vacationRequestMapper.toEntity(vacationRequestDTO);
        vacationRequest = vacationRequestRepository.save(vacationRequest);
        return vacationRequestMapper.toDto(vacationRequest);
    }

    /**
     * Get a vacationRequest by id
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public VacationRequestDTO getById(Long id) {
        VacationRequestDTO result;
        log.debug("Request to get Vacation Request by id : {}", id);
        Optional<VacationRequest> vacationRequest = vacationRequestRepository.findById(id);
        if (vacationRequest.isPresent()) {
            result = vacationRequestMapper.toDto(vacationRequest.get());
        } else {
            throw new NotFoundException(NotFoundExceptionMessage);
        }
        return result;
    }


    /**
     * Get all products
     *
     * @return a list of all products
     */
    @Transactional
    public List<VacationRequestDTO> getAll() {
        List<VacationRequestDTO> result = new ArrayList<>();
        log.debug("Request to get all VacationRequests");
        List<VacationRequest> vacationRequestList = vacationRequestRepository.findAll();
        if (!vacationRequestList.isEmpty()) {
            for (VacationRequest vacationRequest : vacationRequestList) {
                result.add(vacationRequestMapper.toDto(vacationRequest));
            }
        }
        return result;
    }

    /**
     * Delete a vacationRequestService by id
     *
     * @param id the id of the vacationRequest
     */
    public void delete(Long id) {
        log.debug("Request to delete a vacationRequest by id :{}", id);
        vacationRequestRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Boolean isExistingVacationRequest(Long id) {
        return vacationRequestRepository.findById(id).isPresent();
    }


    /**
     * create VacationRequest method
     *
     * @param vacationRequestInputDTO the vacationRequest input
     * @return the vacationRequestDTO
     */
    public VacationRequestDTO createVacationRequest(VacationRequestInputDTO vacationRequestInputDTO) {
        log.debug("Request to get Vacation ");
        EmployeeDTO employee;

        // Perform validation on the vacationRequestInputDTO here, such as checking that the start date is before the end date
        if (vacationRequestInputDTO.getStartDate().isAfter(vacationRequestInputDTO.getEndDate())) {
            throw new RuntimeException(BAD_INPUT_FOR_VACATION_DAYS);
        }
        // Get the days for the vacation
        long daysBetween = DAYS.between(vacationRequestInputDTO.getStartDate(), vacationRequestInputDTO.getEndDate());
        int vacationDays = (int) daysBetween - vacationRequestInputDTO.getHolidays() + 1;

        // Get the Employee by id
        Optional<Employee> optionalEmployee = employeeRepository.findById(vacationRequestInputDTO.getEmployeeId());
        if (optionalEmployee.isPresent()) {
            employee = employeeMapper.toDto(optionalEmployee.get());
        } else {
            throw new NotFoundException(NotFoundExceptionMessage);
        }

        VacationRequestDTO vacationRequestDTO = new VacationRequestDTO();
        // Validation check if employees vacations days are compatible with the requested vacation Days
        if (vacationDays != 0 && employee.getVacationDays() >= vacationDays) {

            // Set the properties of the vacationRequestDTO object based on the vacationRequestInputDTO object
            vacationRequestDTO.setEmployee(employee);
            vacationRequestDTO.setStatus(VacationRequestStatus.PENDING.getDescription());
            vacationRequestDTO.setStartDate(vacationRequestInputDTO.getStartDate());
            vacationRequestDTO.setEndDate(vacationRequestInputDTO.getEndDate());
            vacationRequestDTO.setDays(vacationDays);

        } else {
            throw new RuntimeException(VACATION_DAYS_REQUESTED_AND_VACATION_DAYS_AVAILABLE_ARE_NO_COMPATIBLE);
        }

        // Save the vacationRequest to database. Finally, return the completed VacationRequestDTO object
        return save(vacationRequestDTO);
    }

    /**
     * Method  that returns The VacationRequests for a specific timeline for a company based on their status.
     *
     * @param vacationRequestInfoDTO the info for the vacationRequests
     * @return the vacationREQUESTS
     */
    public List<VacationRequestDTO> getVacationRequestsByTimeline(VacationRequestInfoDTO vacationRequestInfoDTO) {
        log.debug("Request to get all vacationRequests of a company, on a specific timeline based on status");

        //get the list of vacationRequests for a company of a specific timeline, based on status
        List<VacationRequest> vacationRequestList = vacationRequestRepository.getVacationRequestsByTimelineAndStatus(
                vacationRequestInfoDTO.getCompanyId(),
                vacationRequestInfoDTO.getStartDate(),
                vacationRequestInfoDTO.getEndDate(),
                vacationRequestInfoDTO.getStatus());

        return vacationRequestList.stream().map(vacationRequestMapper::toDto).collect(Collectors.toList());

    }

    /**
     * Method that returns an updated VacationRequest
     *
     * @param processVacationRequestDTO the processVacationRequest
     * @return the updated vacationRequest
     */
    public VacationRequestDTO processVacationRequest(ProcessVacationRequestDTO processVacationRequestDTO) {
        log.debug("Request to process a vacationRequest, accept/reject it");

        VacationRequest vacationRequest;

        // get the VacationRequest based on vacationId (check if vacationId is valid)
        Optional<VacationRequest> optionalVacationRequest = vacationRequestRepository.findById(processVacationRequestDTO.getVacationId());
        if (optionalVacationRequest.isPresent()) {
            vacationRequest = optionalVacationRequest.get();
        } else {
            throw new NotFoundException(NotFoundVacationExceptionMessage);
        }

        // get the employee that applied for the vacationRequest
        Employee employee = vacationRequest.getEmployee();

        // logic based on the status of the vacationRequest
        if (processVacationRequestDTO.getStatus().equals(VacationRequestStatus.ACCEPTED.description)) {

            //calculate the new vacationDays
            Integer newVacationDays = employee.getVacationDays() - vacationRequest.getDays();

            //update the employees vacationDays
            employee.setVacationDays(newVacationDays);

            //update the vacationRequest status
            vacationRequest.setStatus(VacationRequestStatus.APPROVED.getDescription());

        } else if (processVacationRequestDTO.getStatus().equals(VacationRequestStatus.REJECTED.description)) {
            //update the vacationRequest status
            vacationRequest.setStatus(VacationRequestStatus.REJECTED.description);
        } else {
            throw new NotFoundException(NotFoundStatusExceptionMessage);
        }

        return vacationRequestMapper.toDto(vacationRequest);
    }
}