package gr.knowledge.internship.vacation.service;

import gr.knowledge.internship.vacation.domain.Company;
import gr.knowledge.internship.vacation.domain.Employee;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.CompanyRepository;
import gr.knowledge.internship.vacation.service.dto.CompanyDTO;
import gr.knowledge.internship.vacation.service.dto.EmployeeDTO;
import gr.knowledge.internship.vacation.service.mapper.CompanyMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Log4j2
public class CompanyService {

    private final CompanyRepository companyRepository;


    private final CompanyMapper companyMapper;

    private static final String NotFoundExceptionMessage = "Not Found";


    public CompanyService(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }


    @Transactional
    public CompanyDTO save(CompanyDTO companyDTO) {
        log.debug("Request to save Company : {}", companyDTO);
        Company company = companyMapper.toEntity(companyDTO);
        company = companyRepository.save(company);
        return companyMapper.toDto(company);
    }

    @Transactional(readOnly = true)
    public CompanyDTO getById(Long id) {
        CompanyDTO result;
        log.debug("Request to get Company by id : {}", id);
        Optional<Company> company = companyRepository.findById(id);
        if (company.isPresent()) {
            result = companyMapper.toDto(company.get());
        } else {
            throw new NotFoundException(NotFoundExceptionMessage);
        }
        return result;
    }

}
