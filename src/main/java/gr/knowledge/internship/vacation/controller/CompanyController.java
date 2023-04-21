package gr.knowledge.internship.vacation.controller;

import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.service.CompanyService;
import gr.knowledge.internship.vacation.service.dto.CompanyDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api")
public class CompanyController {
    public static final String BAD_INPUT_FOR_UPDATE_COMPANY = "Bad input for update company";
    public static final String ID_NOT_FOUND = "Company with this id, not found";
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/company")
    public ResponseEntity<CompanyDTO> save(@RequestBody CompanyDTO companyDTO) {
        log.debug("Rest request to save Company : {}", companyDTO);
        CompanyDTO result = companyService.save(companyDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/company")
    public ResponseEntity<CompanyDTO> updateCompany(@RequestBody CompanyDTO companyDTO) {
        log.debug("Rest request to update Company : {}", companyDTO);
        if (companyDTO == null || companyDTO.getId() == null) {
            throw new NotFoundException(BAD_INPUT_FOR_UPDATE_COMPANY);
        }
        Boolean isExistingCompanyId = companyService.isExistingCompanyId(companyDTO.getId());
        if (Boolean.FALSE.equals(isExistingCompanyId)) {
            throw new NotFoundException(ID_NOT_FOUND);
        }
        CompanyDTO result = companyService.save(companyDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/companies")
    public ResponseEntity<List<CompanyDTO>> getAllCompanies() {
        log.debug("Rest request to get all Companies");
        List<CompanyDTO> result = companyService.getAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable Long id) {
        log.debug("Rest request to get Company by id :{}", id);
        CompanyDTO result = companyService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //endpoint for calculating a company's monthly expenses
    @GetMapping("/companyExpenses/{companyId}")
    public ResponseEntity<Double> calculateCompanyExpenses(@PathVariable Long companyId) {
        log.debug("Rest request to get a Company's expenses by id :{}", companyId);
        Double result = companyService.calculateMonthlyExpenses(companyId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
