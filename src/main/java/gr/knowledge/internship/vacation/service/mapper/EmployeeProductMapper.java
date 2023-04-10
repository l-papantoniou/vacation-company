package gr.knowledge.internship.vacation.service.mapper;

import gr.knowledge.internship.vacation.domain.EmployeeProduct;
import gr.knowledge.internship.vacation.service.dto.EmployeeProductDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EmployeeProductMapper extends ModelMapper {

    public EmployeeProductDTO toDto(EmployeeProduct employeeProduct) {
        return this.map(employeeProduct, EmployeeProductDTO.class);
    }

    public EmployeeProduct toEntity(EmployeeProductDTO employeeProductDTO) {
        return this.map(employeeProductDTO, EmployeeProduct.class);
    }

}
