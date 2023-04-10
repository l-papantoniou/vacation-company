package gr.knowledge.internship.vacation.service;


import gr.knowledge.internship.vacation.domain.Bonus;
import gr.knowledge.internship.vacation.domain.EmployeeProduct;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.EmployeeProductRepository;
import gr.knowledge.internship.vacation.service.dto.BonusDTO;
import gr.knowledge.internship.vacation.service.dto.EmployeeProductDTO;
import gr.knowledge.internship.vacation.service.mapper.EmployeeProductMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Log4j2
public class EmployeeProductService {

    private final EmployeeProductRepository employeeProductRepository;

    private final EmployeeProductMapper employeeProductMapper;

    private static final String NotFoundExceptionMessage = "Not Found";

    public EmployeeProductService(EmployeeProductRepository employeeProductRepository, EmployeeProductMapper employeeProductMapper) {
        this.employeeProductRepository = employeeProductRepository;
        this.employeeProductMapper = employeeProductMapper;
    }

    @Transactional
    public EmployeeProductDTO save(EmployeeProductDTO employeeProductDTO) {
        log.debug("Request to save EmployeeProductDTO : {}", employeeProductDTO);
        EmployeeProduct employeeProduct = employeeProductMapper.toEntity(employeeProductDTO);
        employeeProduct = employeeProductRepository.save(employeeProduct);
        return employeeProductMapper.toDto(employeeProduct);
    }

    @Transactional(readOnly = true)
    public EmployeeProductDTO getById(Long id) {
        EmployeeProductDTO result;
        log.debug("Request to get Employee Product by id : {}", id);
        Optional<EmployeeProduct> employeeProduct = employeeProductRepository.findById(id);
        if (employeeProduct.isPresent()) {
            result = employeeProductMapper.toDto(employeeProduct.get());
        } else {
            throw new NotFoundException(NotFoundExceptionMessage);
        }
        return result;
    }
}
