package gr.knowledge.internship.vacation.service.dto;

import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class VacationRequestDTO implements Serializable {

    Long id;

    @NotNull
    @Size(max = 255)
    LocalDate startDate;

    @NotNull
    @Size(max = 255)
    LocalDate endDate;

    @NotNull
    @Size(max = 255)
    String status;

    @NotNull
    @Size(max = 255)
    Integer days;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EmployeeDTO employee;

}
