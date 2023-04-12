package gr.knowledge.internship.vacation.service;

import gr.knowledge.internship.vacation.domain.Bonus;
import gr.knowledge.internship.vacation.domain.Product;
import gr.knowledge.internship.vacation.domain.VacationRequest;
import gr.knowledge.internship.vacation.exception.NotFoundException;
import gr.knowledge.internship.vacation.repository.VacationRequestRepository;
import gr.knowledge.internship.vacation.service.dto.BonusDTO;
import gr.knowledge.internship.vacation.service.dto.ProductDTO;
import gr.knowledge.internship.vacation.service.dto.VacationRequestDTO;
import gr.knowledge.internship.vacation.service.mapper.VacationRequestMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Log4j2
public class VacationRequestService {

    private final VacationRequestRepository vacationRequestRepository;

    private final VacationRequestMapper vacationRequestMapper;

    private static final String NotFoundExceptionMessage = "Not Found";

    public VacationRequestService(VacationRequestRepository vacationRequestRepository, VacationRequestMapper vacationRequestMapper) {
        this.vacationRequestRepository = vacationRequestRepository;
        this.vacationRequestMapper = vacationRequestMapper;
    }

    /**
     * Sava a vacationRequest
     *
     * @param vacationRequestDTO the entity to save
     * @return the entity
     */
    @Transactional
    public VacationRequestDTO save(VacationRequestDTO vacationRequestDTO) {
        log.debug("Request to save vacationRequestDTO : {}", vacationRequestDTO);
        VacationRequest vacationRequest = vacationRequestMapper.toEntity(vacationRequestDTO);
        vacationRequest = vacationRequestRepository.save(vacationRequest);
        return vacationRequestMapper.toDto(vacationRequest);
    }

    /**
     * \
     * Get a vacationRequest by id
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public VacationRequestDTO getById(Long id) {
        VacationRequestDTO result;
        log.debug("Request to get Vacation Request by id : {}", id);
        Optional<VacationRequest> vacationRequest = vacationRequestRepository.findById(id);
        if (vacationRequest.isPresent()) {
            result = vacationRequestMapper.toDto(vacationRequest.get());
        } else {
            throw new NotFoundException(NotFoundExceptionMessage);
        }
        return result;
    }


    /**
     * Get all products
     *
     * @return a list of all products
     */
    @Transactional
    public List<VacationRequestDTO> getAll() {
        List<VacationRequestDTO> result = new ArrayList<>();
        log.debug("Request to get all VacationRequests");
        List<VacationRequest> vacationRequestList = vacationRequestRepository.findAll();
        if (!vacationRequestList.isEmpty()) {
            for (VacationRequest vacationRequest : vacationRequestList) {
                result.add(vacationRequestMapper.toDto(vacationRequest));
            }
        }
        return result;
    }

    /**
     * Delete a vacationRequestService by id
     *
     * @param id the id of the vacationRequest
     */
    public void delete(Long id) {
        log.debug("Request to delete a vacationRequest by id :{}", id);
        vacationRequestRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Boolean isExistingVacationRequest(Long id) {
        return vacationRequestRepository.findById(id).isPresent();
    }

}
