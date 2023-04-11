package gr.knowledge.internship.vacation.service;

import gr.knowledge.internship.vacation.domain.Bonus;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.BonusRepository;
import gr.knowledge.internship.vacation.service.dto.BonusDTO;
import gr.knowledge.internship.vacation.service.mapper.BonusMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Log4j2
public class BonusService {

    private final BonusRepository bonusRepository;

    private final BonusMapper bonusMapper;

    private static final String NOT_FOUND_EXCEPTION_MESSAGE = "Not Found";

    public BonusService(BonusRepository bonusRepository, BonusMapper bonusMapper) {
        this.bonusRepository = bonusRepository;
        this.bonusMapper = bonusMapper;
    }

    /**
     * Save a bonus
     *
     * @param bonusDTO the entity to save
     * @return the entity
     */
    @Transactional
    public BonusDTO save(BonusDTO bonusDTO) {
        log.debug("Request to save BonusDTO : {}", bonusDTO);
        Bonus bonus = bonusMapper.toEntity(bonusDTO);
        bonus = bonusRepository.save(bonus);
        return bonusMapper.toDto(bonus);
    }

    /**
     * Get a bonus by id
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public BonusDTO getById(Long id) {
        BonusDTO result;
        log.debug("Request to get Bonus by id : {}", id);
        Optional<Bonus> bonus = bonusRepository.findById(id);
        if (bonus.isPresent()) {
            result = bonusMapper.toDto(bonus.get());
        } else {
            throw new NotFoundException(NOT_FOUND_EXCEPTION_MESSAGE);
        }
        return result;
    }

    /**
     * Get all bonuses
     *
     * @return a list of all bonuses
     */
    @Transactional(readOnly = true)
    public List<BonusDTO> getAll() {
        List<BonusDTO> result = new ArrayList<>();
        log.debug("Request to get all employees");
        List<Bonus> bonusList = bonusRepository.findAll();
        if (!bonusList.isEmpty()) {
            for (Bonus bonus : bonusList) {
                result.add(bonusMapper.toDto(bonus));
            }
        }
        return result;
    }


    @Transactional(readOnly = true)
    public Boolean isExistingBonusId(Long id) {
        return bonusRepository.findById(id).isPresent();
    }

}
