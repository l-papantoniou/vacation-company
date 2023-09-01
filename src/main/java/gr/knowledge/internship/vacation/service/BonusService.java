package gr.knowledge.internship.vacation.service;

import gr.knowledge.internship.vacation.domain.Bonus;
import gr.knowledge.internship.vacation.domain.Employee;
import gr.knowledge.internship.vacation.enums.BonusRate;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.BonusRepository;
import gr.knowledge.internship.vacation.repository.CompanyRepository;
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

    private final CompanyService companyService;
    private static final String NOT_FOUND_EXCEPTION_MESSAGE = "Not Found";

    public BonusService(BonusRepository bonusRepository, BonusMapper bonusMapper, CompanyService companyService) {
        this.bonusRepository = bonusRepository;
        this.bonusMapper = bonusMapper;
        this.companyService = companyService;
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

    /**
     * Custom method that returns the calculated bonus of an employee based on the season
     *
     * @param salary the employee's salary
     * @param season the season of the bonus
     * @return the calculated bonus
     */
    public Double calculateBonus(Double salary, String season) {

        // Get the rate from the BonusRate enum based on the season
        BonusRate bonusRate = BonusRate.getRateForSeason(season);

        // Calculate the bonus amount by multiplying the salary by the rate

        return salary * bonusRate.getRate();
    }

    /**
     * Method to create Bonuses for each employee of a specific company
     *
     * @param companyId the id of the company
     * @param season    the season for the bonus
     * @return a list of bonuses
     */
    public List<Bonus> createCompanyBonus(Long companyId, String season) {
        log.debug("Request to create Bonus for every employee of a company ");
        List<Bonus> bonusList = new ArrayList<>();
        // get all employees of a specific company
        List<Employee> employeeList = companyService.getCompanyEmployees(companyId);

        // calculate their bonuses and add them to a list
        for (Employee employee : employeeList) {
            Bonus bonus = new Bonus();
            bonus.setAmount(calculateBonus(employee.getSalary(), season));
            bonus.setEmployee(employee);
            bonus.setCompany(employee.getEmployeeCompany());
            bonusList.add(bonus);
        }
        // save all bonuses to db
        return bonusRepository.saveAll(bonusList);
    }

    @Transactional(readOnly = true)
    public Boolean isExistingBonusId(Long id) {
        return bonusRepository.findById(id).isPresent();
    }

}
