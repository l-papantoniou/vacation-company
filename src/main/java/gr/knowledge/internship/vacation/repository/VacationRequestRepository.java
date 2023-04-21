package gr.knowledge.internship.vacation.repository;

import gr.knowledge.internship.vacation.domain.VacationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VacationRequestRepository extends JpaRepository<VacationRequest, Long>, JpaSpecificationExecutor<VacationRequest> {

    @Query(value = "SELECT vacationRequest  FROM VacationRequest vacationRequest " +
            " INNER JOIN  Employee employee ON employee.id = vacationRequest.employee.id" +
            " INNER JOIN Company  company ON employee.employeeCompany.id = company.id" +
            " WHERE (vacationRequest.startDate >= :startDate OR vacationRequest.endDate <= :endDate) " +
            "AND (vacationRequest.endDate > :startDate AND vacationRequest.startDate < :endDate) " +
            "AND vacationRequest.status =:status  " +
            "AND company.id =:companyId" )
    List<VacationRequest> getVacationRequestsByTimelineAndStatus(@Param("companyId") Long companyId,
                                                                 @Param("startDate") LocalDate startDate,
                                                                 @Param("endDate") LocalDate endDate,
                                                                 @Param("status") String status);

}
