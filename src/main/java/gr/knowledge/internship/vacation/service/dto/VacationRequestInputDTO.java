package gr.knowledge.internship.vacation.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class VacationRequestInputDTO implements Serializable {

    Long id;

    @NotNull
    Long employeeId;

    @NotNull
    @Size(max = 255)
    LocalDate startDate;

    @NotNull
    @Size(max = 255)
    LocalDate endDate;

    @NotNull
    Integer holidays;

}
