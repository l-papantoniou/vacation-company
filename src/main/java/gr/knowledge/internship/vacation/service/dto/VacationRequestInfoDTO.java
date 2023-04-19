package gr.knowledge.internship.vacation.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class VacationRequestInfoDTO implements Serializable {

    Long id;

    @NotNull
    Long companyId;

    @NotNull
    @Size(max = 255)
    LocalDate startDate;

    @NotNull
    @Size(max = 255)
    LocalDate endDate;

    @NotNull
    String status;

}
