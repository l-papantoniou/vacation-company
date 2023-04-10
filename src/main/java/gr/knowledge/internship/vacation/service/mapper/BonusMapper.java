package gr.knowledge.internship.vacation.service.mapper;

import gr.knowledge.internship.vacation.domain.Bonus;
import gr.knowledge.internship.vacation.service.dto.BonusDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BonusMapper extends ModelMapper {

    public BonusDTO toDto(Bonus bonus) {
        return this.map(bonus, BonusDTO.class);
    }

    public Bonus toEntity(BonusDTO bonusDTO) {
        return this.map(bonusDTO, Bonus.class);
    }
}
