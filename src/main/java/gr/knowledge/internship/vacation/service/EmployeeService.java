package gr.knowledge.internship.vacation.service;

import gr.knowledge.internship.vacation.domain.Employee;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.EmployeeRepository;
import gr.knowledge.internship.vacation.service.dto.EmployeeDTO;
import gr.knowledge.internship.vacation.service.mapper.EmployeeMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Log4j2
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    private EmployeeMapper employeeMapper;

    private static final String NOT_FOUND_EXCEPTION_MESSAGE = "Not Found";


    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    /**
     * Save an Employee
     *
     * @param employeeDTO the entity to save
     * @return the persisted entity
     */
    @Transactional
    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        log.debug("Request to save Company : {}", employeeDTO);
        Employee employee = employeeMapper.toEntity(employeeDTO);
        employee = employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }


    /**
     * Get an Employee by id
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public EmployeeDTO getById(Long id) {
        EmployeeDTO result;
        log.debug("Request to get Employee by id : {}", id);
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            result = employeeMapper.toDto(employee.get());
        } else {
            throw new NotFoundException(NOT_FOUND_EXCEPTION_MESSAGE);
        }
        return result;
    }

    /**
     * Get all Employees
     *
     * @return a list of all Employees
     */
    @Transactional(readOnly = true)
    public List<EmployeeDTO> getAll() {
        List<EmployeeDTO> result = new ArrayList<>();
        log.debug("Request to get all employees");
        List<Employee> employeeList = employeeRepository.findAll();
        if (!employeeList.isEmpty()) {
            for (Employee employee : employeeList) {
                result.add(employeeMapper.toDto(employee));
            }
        } else {
            throw new NotFoundException(NOT_FOUND_EXCEPTION_MESSAGE);
        }
        return result;
    }

    /**
     * Delete an Employee by id
     *
     * @param id
     */
    public void delete(Long id) {
        log.debug("Request to delete Employee : {}", id);
        employeeRepository.deleteById(id);
    }


    @Transactional(readOnly = true)
    public Boolean isExistingEmployeeId(Long id) {
        return employeeRepository.findById(id).isPresent();
    }
}