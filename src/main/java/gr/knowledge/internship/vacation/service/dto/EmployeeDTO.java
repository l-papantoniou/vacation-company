package gr.knowledge.internship.vacation.service.dto;

import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class EmployeeDTO implements Serializable {

    Long id;

    @NotNull
    @Size(max = 255)
    String name;

    @NotNull
    @Size(max = 255)
    String surName;

    @NotNull
    @Size(max = 255)
    String email;

    @NotNull
    LocalDate startDate;

    @NotNull
    Integer vacationDays;

    @NotNull
    Double salary;

    @NotNull
    @Size(max = 20)
    String employmentType;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CompanyDTO employeeCompany;
}
