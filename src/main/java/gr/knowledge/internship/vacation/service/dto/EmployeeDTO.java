package gr.knowledge.internship.vacation.service.dto;

import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class EmployeeDTO implements Serializable {

    Long id;

    @Size(max = 255)
    String name;

    @Size(max = 255)
    String surName;

    @Size(max = 255)
    String email;

    LocalDate startDate;

    Integer vacationDays;

    Double salary;

    @Size(max = 20)
    String employmentType;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CompanyDTO employeeCompany;
}
