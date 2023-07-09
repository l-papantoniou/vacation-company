package gr.knowledge.internship.vacation.service;

import gr.knowledge.internship.vacation.domain.Company;
import gr.knowledge.internship.vacation.domain.Employee;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.EmployeeRepository;
import gr.knowledge.internship.vacation.service.dto.CompanyDTO;
import gr.knowledge.internship.vacation.service.dto.EmployeeDTO;
import gr.knowledge.internship.vacation.service.mapper.EmployeeMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Log4j2
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

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
        log.debug("Request to save an Employee : {}", employeeDTO);
        Employee employee = employeeMapper.toEntity(employeeDTO);
        employee = employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    /**
     * Save a list of Employees
     *
     * @param filePath the filePath of the file to save
     * @return the persisted entity list
     */
    @Transactional
    public List<Employee> saveEmployeesFromFile(String filePath) throws IOException {
        List<EmployeeDTO> employeeDTOList = parseCSVFile(filePath);
        List<Employee> employeeList = new ArrayList<>();
        for (EmployeeDTO employeeDTO : employeeDTOList) {
            Employee employee = employeeMapper.toEntity(employeeDTO);
            employeeList.add(employee);
        }
        return employeeRepository.saveAll(employeeList);

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
     * @param id the employee id
     */
    public void delete(Long id) {
        log.debug("Request to delete Employee : {}", id);
        employeeRepository.deleteById(id);
    }

    /**
     * custom method to parse the f
     *
     * @param filePath the path of the csvFile
     * @return the employeeDTOlist to save
     * @throws IOException
     */
    @Transactional
    public List<EmployeeDTO> parseCSVFile(String filePath) throws IOException {
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Skip the header line
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                EmployeeDTO employeeDTO = setNewEmployee(fields);
                employeeDTOList.add(employeeDTO);
            }
        }

        return employeeDTOList;
    }

    private EmployeeDTO setNewEmployee(String[] fields) {

        EmployeeDTO employeeDTO = new EmployeeDTO();

        //set the new employeeDTO
        employeeDTO.setName(fields[0].trim());
        employeeDTO.setSurName(fields[1].trim());
        employeeDTO.setEmail(fields[2].trim());
        employeeDTO.setStartDate(LocalDate.parse(fields[3].trim()));
        employeeDTO.setVacationDays(Integer.parseInt(fields[4].trim()));
        employeeDTO.setSalary(Double.parseDouble(fields[5].trim()));
        employeeDTO.setEmploymentType(fields[6].trim());

        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(Long.parseLong(fields[7].trim()));
        employeeDTO.setEmployeeCompany(companyDTO);

        return employeeDTO;
    }

    @Transactional(readOnly = true)
    public Boolean isExistingEmployeeId(Long id) {
        return employeeRepository.findById(id).isPresent();
    }
}