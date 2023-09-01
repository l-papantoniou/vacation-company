package gr.knowledge.internship.vacation.service.dto;

import lombok.*;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BonusDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private Double amount;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CompanyDTO company;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EmployeeDTO employee;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BonusDTO bonusDTO = (BonusDTO) o;
        return Objects.equals(id, bonusDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
