package gr.knowledge.internship.vacation.service;


import gr.knowledge.internship.vacation.domain.EmployeeProduct;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.EmployeeProductRepository;
import gr.knowledge.internship.vacation.service.dto.EmployeeProductDTO;
import gr.knowledge.internship.vacation.service.mapper.EmployeeProductMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Log4j2
public class EmployeeProductService {

    private final EmployeeProductRepository employeeProductRepository;

    private final EmployeeProductMapper employeeProductMapper;

    private static final String NOT_FOUND_EXCEPTION_MESSAGE = "Not Found";

    public EmployeeProductService(EmployeeProductRepository employeeProductRepository, EmployeeProductMapper employeeProductMapper) {
        this.employeeProductRepository = employeeProductRepository;
        this.employeeProductMapper = employeeProductMapper;
    }

    /**
     * Save an employeeProduct
     *
     * @param employeeProductDTO the entity to save
     * @return the entity
     */
    @Transactional
    public EmployeeProductDTO save(EmployeeProductDTO employeeProductDTO) {
        log.debug("Request to save EmployeeProductDTO : {}", employeeProductDTO);
        EmployeeProduct employeeProduct = employeeProductMapper.toEntity(employeeProductDTO);
        employeeProduct = employeeProductRepository.save(employeeProduct);
        return employeeProductMapper.toDto(employeeProduct);
    }

    /**
     * Get an employeeProduct by id
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public EmployeeProductDTO getById(Long id) {
        EmployeeProductDTO result;
        log.debug("Request to get employeeProduct by id :{}", id);
        Optional<EmployeeProduct> employeeProduct = employeeProductRepository.findById(id);
        if (employeeProduct.isPresent()) {
            result = employeeProductMapper.toDto(employeeProduct.get());
        } else {
            throw new NotFoundException(NOT_FOUND_EXCEPTION_MESSAGE);
        }
        return result;
    }

    /**
     * Get all products
     *
     * @return a list of all products
     */
    @Transactional
    public List<EmployeeProductDTO> getAll() {
        List<EmployeeProductDTO> result = new ArrayList<>();
        log.debug("Request to get all employeeProducts");
        List<EmployeeProduct> employeeProductList = employeeProductRepository.findAll();
        if (!employeeProductList.isEmpty()) {
            for (EmployeeProduct employeeProduct : employeeProductList) {
                result.add(employeeProductMapper.toDto(employeeProduct));
            }
        }
        return result;
    }

    /**
     * Delete a employeeProduct by id
     *
     * @param id the id of the employeeProduct
     */
    public void delete(Long id) {
        log.debug("Request to delete an employeeProduct by id :{}", id);
        employeeProductRepository.deleteById(id);
    }
}
