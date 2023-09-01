package gr.knowledge.internship.vacation.service;

import gr.knowledge.internship.vacation.domain.Company;
import gr.knowledge.internship.vacation.domain.Employee;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.CompanyRepository;
import gr.knowledge.internship.vacation.service.dto.CompanyDTO;
import gr.knowledge.internship.vacation.service.mapper.CompanyMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Log4j2
public class CompanyService {

    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    private static final String NOT_FOUND_EXCEPTION_MESSAGE = "Not Found";
    private static final String NOT_FOUND_COMPANY_EXCEPTION_MESSAGE = "No Company with the given Id";


    public CompanyService(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }


    /**
     * Save a Company
     *
     * @param companyDTO the entity to save
     * @return the persisted entity
     */
    @Transactional
    public CompanyDTO save(CompanyDTO companyDTO) {
        log.debug("Request to save Company : {}", companyDTO);
        Company company = companyMapper.toEntity(companyDTO);
        company = companyRepository.save(company);
        return companyMapper.toDto(company);
    }

    /**
     * Get All Companies
     *
     * @return a list of all Companies
     */
    @Transactional(readOnly = true)
    public List<CompanyDTO> getAll() {
        log.debug("Request to get all Companies");
        List<CompanyDTO> result = new ArrayList<>();
        List<Company> companyList = companyRepository.findAll();
        if (!companyList.isEmpty()) {
            for (Company company : companyList) {
                result.add(companyMapper.toDto(company));
            }
        }
        return result;
    }


    /**
     * Get a Company by id
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public CompanyDTO getById(Long id) {
        CompanyDTO result;
        log.debug("Request to get Company by id : {}", id);
        Optional<Company> company = companyRepository.findById(id);
        if (company.isPresent()) {
            result = companyMapper.toDto(company.get());
        } else {
            throw new NotFoundException(NOT_FOUND_EXCEPTION_MESSAGE);
        }
        return result;
    }


    /**
     * A method that returns a Double amount
     *
     * @param companyId the id of the company
     * @return the amount of the monthly expenses
     */
    public Double calculateMonthlyExpenses(Long companyId) {
        log.debug("Request to calculate the monthly expenses of a company : {}", companyId);
        Double expenses;
        if (Boolean.TRUE.equals(isExistingCompanyId(companyId))) {
            expenses = companyRepository.calculateExpensesByCompanyId(companyId);
        } else {
            throw new NotFoundException(NOT_FOUND_COMPANY_EXCEPTION_MESSAGE);
        }
        return expenses;
    }

    public List<Employee> getCompanyEmployees(Long companyId) {
        log.debug("Request to get all employees of a company based on companyId: {}", companyId);
        return companyRepository.getEmployeesByCompanyId(companyId);
    }

    @Transactional(readOnly = true)
    public Boolean isExistingCompanyId(Long id) {
        return companyRepository.findById(id).isPresent();
    }
}
