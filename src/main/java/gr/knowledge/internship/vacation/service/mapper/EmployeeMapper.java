package gr.knowledge.internship.vacation.service.mapper;

import gr.knowledge.internship.vacation.domain.Employee;
import gr.knowledge.internship.vacation.service.dto.EmployeeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper extends ModelMapper {

    public EmployeeDTO toDto(Employee employee){
        return this.map(employee, EmployeeDTO.class);
    }

    public Employee toEntity(EmployeeDTO employeeDTO){
        return this.map(employeeDTO, Employee.class);
    }
}
