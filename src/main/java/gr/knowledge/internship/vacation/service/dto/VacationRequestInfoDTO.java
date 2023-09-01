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
public class VacationRequestInfoDTO implements Serializable {

    private Long id;

    @NotNull
    private Long companyId;

    @NotNull
    @Size(max = 255)
    private LocalDate startDate;

    @NotNull
    @Size(max = 255)
    private LocalDate endDate;

    @NotNull
    private String status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VacationRequestInfoDTO that = (VacationRequestInfoDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
