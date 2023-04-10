package gr.knowledge.internship.vacation.service.mapper;

import gr.knowledge.internship.vacation.domain.VacationRequest;
import gr.knowledge.internship.vacation.service.dto.VacationRequestDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class VacationRequestMapper extends ModelMapper {

    public VacationRequestDTO toDto(VacationRequest vacationRequest) {
        return this.map(vacationRequest, VacationRequestDTO.class);
    }

    public VacationRequest toEntity(VacationRequestDTO vacationRequestDTO) {
        return this.map(vacationRequestDTO, VacationRequest.class);
    }
}
