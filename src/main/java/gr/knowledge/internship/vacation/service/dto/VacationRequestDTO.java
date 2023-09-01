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
public class VacationRequestDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private LocalDate startDate;

    @NotNull
    @Size(max = 255)
    private LocalDate endDate;

    @NotNull
    @Size(max = 255)
    private String status;

    @NotNull
    private Integer days;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EmployeeDTO employee;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VacationRequestDTO that = (VacationRequestDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
