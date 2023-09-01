package gr.knowledge.internship.vacation.service.dto;

import lombok.*;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String surName;

    @NotNull
    @Size(max = 255)
    private String email;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private Integer vacationDays;

    @NotNull
    private Double salary;

    @NotNull
    @Size(max = 20)
    private String employmentType;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CompanyDTO employeeCompany;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDTO that = (EmployeeDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
