package gr.knowledge.internship.vacation.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "bonus")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Bonus implements Serializable {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bonus_generator")
    @SequenceGenerator(name = "bonus_generator", sequenceName = "seq_bonus")
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "amount")
    private Double amount;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @ToString.Exclude
    private Company company;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ToString.Exclude
    private Employee employee;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bonus bonus = (Bonus) o;
        return Objects.equals(id, bonus.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
