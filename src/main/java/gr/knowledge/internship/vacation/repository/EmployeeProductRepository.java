package gr.knowledge.internship.vacation.repository;

import gr.knowledge.internship.vacation.domain.EmployeeProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeProductRepository extends JpaRepository<EmployeeProduct, Long>, JpaSpecificationExecutor<EmployeeProduct> {

    @Query("SELECT concat(employee.name, ' ',employee.surName) AS employeeName, product " +
            "FROM EmployeeProduct employeeProduct " +
            "JOIN employeeProduct.employee employee " +
            "JOIN employee.employeeCompany company " +
            "JOIN employeeProduct.product product " +
            "WHERE company.id = :companyId")
    List<Object[]> getProductByCompany(@Param("companyId") Long companyId);


}