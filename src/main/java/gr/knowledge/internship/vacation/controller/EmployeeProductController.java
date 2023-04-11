package gr.knowledge.internship.vacation.controller;

import gr.knowledge.internship.vacation.service.EmployeeProductService;
import gr.knowledge.internship.vacation.service.dto.EmployeeProductDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api")
public class EmployeeProductController {

    public static final String ID_NOT_FOUND = "employeeProduct id not found";

    private EmployeeProductService employeeProductService;

    public EmployeeProductController(EmployeeProductService employeeProductService) {
        this.employeeProductService = employeeProductService;
    }

    @PostMapping("/employeeProduct")
    public ResponseEntity<EmployeeProductDTO> save(@RequestBody EmployeeProductDTO employeeProductDTO) {
        log.debug("REST request to save an employeeProduct : {}", employeeProductDTO);
        EmployeeProductDTO result = employeeProductService.save(employeeProductDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }


    @GetMapping("/employeeProduct/{id}")
    public ResponseEntity<EmployeeProductDTO> getProduct(@PathVariable Long id) {
        log.debug("Rest request to get employeeProduct by id :{}", id);
        EmployeeProductDTO result = employeeProductService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/employeeProducts")
    public ResponseEntity<List<EmployeeProductDTO>> getAllEmployeeProducts() {
        log.debug("Rest request to get all EmployeeProducts");
        List<EmployeeProductDTO> result = employeeProductService.getAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/employeeProduct/{id}")
    public ResponseEntity<Void> deleteEmployeeProduct(@PathVariable Long id) {
        log.debug("REST request to delete an EmployeeProduct : {}", id);
        employeeProductService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HttpHeaders.EMPTY)
                .build();
    }
}
