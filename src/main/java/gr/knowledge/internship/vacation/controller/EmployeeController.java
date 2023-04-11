package gr.knowledge.internship.vacation.controller;

import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.service.EmployeeService;
import gr.knowledge.internship.vacation.service.dto.EmployeeDTO;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.http.HeaderUtil;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api")
public class EmployeeController {
    public static final String ID_NOT_FOUND = "Employee with this id, not found";
    public static final String BAD_INPUT_FOR_EMPLOYEE_UPDATE = "Bad input for employee update";
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/employee")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        log.debug("REST request to save an Employee : {}", employeeDTO);
        EmployeeDTO result = employeeService.save(employeeDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/employee")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        log.debug("REST request to update an Employee");
        if (employeeDTO == null || employeeDTO.getId() == null) {
            throw new NotFoundException(BAD_INPUT_FOR_EMPLOYEE_UPDATE);
        }
        Boolean isExistingEmployeeId = employeeService.isExistingEmployeeId(employeeDTO.getId());
        if (Boolean.FALSE.equals(isExistingEmployeeId)) {
            throw new NotFoundException(ID_NOT_FOUND);
        }
        EmployeeDTO result = employeeService.save(employeeDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);

    }

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        log.debug("REST request to get Employees");
        List<EmployeeDTO> result = employeeService.getAll();
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable Long id) {
        log.debug("REST request to get Employee by id : {}", id);
        EmployeeDTO result = employeeService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        log.debug("REST request to delete an Employee : {}", id);
        employeeService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HttpHeaders.EMPTY)
                .build();

    }

}