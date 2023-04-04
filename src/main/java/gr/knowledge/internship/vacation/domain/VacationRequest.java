package gr.knowledge.internship.vacation.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "vacation_request")
@AllArgsConstructor
@NoArgsConstructor
public class VacationRequest implements Serializable {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vacation_request_generator")
    @SequenceGenerator(name = "vacation_request_generator", sequenceName = "vacation_request_seq")
    @Column(name = "id", nullable = false)
    Long id;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    @NotNull
    @Column(name = "start_date")
    LocalDate startDate;

    @NotNull
    @Column(name = "end_date")
    LocalDate endDate;

    @NotNull
    @Column(name = "status")
    String status;

    @NotNull
    @Column(name = "days")
    Integer days;


}
