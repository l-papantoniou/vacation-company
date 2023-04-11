package gr.knowledge.internship.vacation.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "employee")
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_generator")
    @SequenceGenerator(name = "employee_generator", sequenceName = "seq_employee")
    @Column(name = "id" , nullable = false)
    Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name")
    String name;

    @NotNull
    @Size(max = 255)
    @Column(name = "surname")
    String surName;

    @NotNull
    @Size(max = 255)
    @Column(name = "email")
    String email;

    @NotNull
    @Column(name = "start_date")
    LocalDate startDate;

    @NotNull
    @Column(name = "vacation_days")
    Integer vacationDays;

    @NotNull
    @Column(name = "salary")
    Double salary;

    @NotNull
    @Size(max = 20)
    @Column(name = "employment_type")
    String employmentType;


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company employeeCompany;
}
