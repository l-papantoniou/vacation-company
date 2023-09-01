package gr.knowledge.internship.vacation.service.dto;

import lombok.*;

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
public class VacationRequestInputDTO implements Serializable {

    private Long id;

    @NotNull
    private Long employeeId;

    @NotNull
    @Size(max = 255)
    private LocalDate startDate;

    @NotNull
    @Size(max = 255)
    private LocalDate endDate;

    @NotNull
    private Integer holidays;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VacationRequestInputDTO that = (VacationRequestInputDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
