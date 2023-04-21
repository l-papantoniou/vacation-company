package gr.knowledge.internship.vacation.repository;

import gr.knowledge.internship.vacation.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>, JpaSpecificationExecutor<Company> {

    @Query("SELECT SUM(employee.salary) FROM Company company INNER JOIN Employee employee " +
            "ON employee.employeeCompany.id =:companyId" +
            " WHERE company.id =:companyId")
    Double calculateExpensesByCompanyId(@Param("companyId") Long companyId);

}
