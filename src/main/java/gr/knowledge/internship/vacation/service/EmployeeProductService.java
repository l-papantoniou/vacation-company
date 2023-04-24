package gr.knowledge.internship.vacation.service;


import gr.knowledge.internship.vacation.domain.EmployeeProduct;
import gr.knowledge.internship.vacation.domain.Product;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.EmployeeProductRepository;
import gr.knowledge.internship.vacation.service.dto.EmployeeProductDTO;
import gr.knowledge.internship.vacation.service.mapper.EmployeeProductMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
     * Method that returns the products of a company
     *
     * @param companyId the id of the company
     * @return a map of product owners and products
     */
    public Map<String, List<Product>> getProductsByCompany(Long companyId) {
        log.debug("Request to get all Products of a Company :{}", companyId);
        // get all products alongside  their owners
        List<Object[]> employeeProductList = employeeProductRepository.getProductByCompany(companyId);

        Map<String, List<Product>> employeeProductMap = new HashMap<>();
        for (Object[] arr : employeeProductList) {
            String employeeName = (String) arr[0];
            Product product = (Product) arr[1];

            //if key does not exist in map create a new key pair value for the map
            if (!employeeProductMap.containsKey(employeeName)) {
                employeeProductMap.put(employeeName, new ArrayList<>());
            }
            //if key exists get List<Products> related to that key (the employeeName)
            employeeProductMap.get(employeeName).add(product);
        }

        return employeeProductMap;
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
