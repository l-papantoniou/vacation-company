package gr.knowledge.internship.vacation.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class ProcessVacationRequestDTO implements Serializable {

    Long id;

    @NotNull
    Long vacationId;

    @NotNull
    @Size(max = 255)
    String status;
}
