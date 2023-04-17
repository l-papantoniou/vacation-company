package gr.knowledge.internship.vacation.controller;

import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.service.BonusService;
import gr.knowledge.internship.vacation.service.dto.BonusDTO;
import gr.knowledge.internship.vacation.service.dto.CompanyDTO;
import gr.knowledge.internship.vacation.service.dto.EmployeeDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api")
public class BonusController {
    public static final String ID_NOT_FOUND = "Bonus id not found";
    public static final String BAD_INPUT_FOR_UPDATE_BONUS = "Bad input for update bonus";
    private final BonusService bonusService;

    public BonusController(BonusService bonusService) {
        this.bonusService = bonusService;
    }


    @PostMapping("/bonus")
    public ResponseEntity<BonusDTO> createEmployee(@RequestBody BonusDTO bonusDTO) {
        log.debug("REST request to save a Bonus : {}", bonusDTO);
        BonusDTO result = bonusService.save(bonusDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/bonus")
    public ResponseEntity<BonusDTO> updateEmployee(@RequestBody BonusDTO bonusDTO) {
        log.debug("REST request to update a Bonus : {}", bonusDTO);
        if (bonusDTO == null || bonusDTO.getId() == null) {
            throw new NotFoundException(BAD_INPUT_FOR_UPDATE_BONUS);
        }
        Boolean isEXistingBonusId = bonusService.isExistingBonusId(bonusDTO.getId());
        if (Boolean.FALSE.equals(isEXistingBonusId)) {
            throw new NotFoundException(ID_NOT_FOUND);
        }
        BonusDTO result = bonusService.save(bonusDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }


    @GetMapping("/bonus/{id}")
    public ResponseEntity<BonusDTO> getBonus(@PathVariable Long id) {
        log.debug("Rest request to get bonus by id :{}", id);
        BonusDTO result = bonusService.getById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/bonuses")
    public ResponseEntity<List<BonusDTO>> getAllBonuses() {
        log.debug("Rest request to get all Bonuses");
        List<BonusDTO> result = bonusService.getAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/calculateBonus")
    public ResponseEntity<Double> calculateBonus(Double salary, String season) {
        log.debug("Rest request to calculate the bonus based on salary and season rate");
        Double result = bonusService.calculateBonus(salary, season);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }


}
