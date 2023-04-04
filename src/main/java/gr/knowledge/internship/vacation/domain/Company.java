package gr.knowledge.internship.vacation.domain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Data
@Table(name = "company")
@AllArgsConstructor
@NoArgsConstructor
public class Company implements Serializable {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_generator")
    @SequenceGenerator(name = "company_generator", sequenceName = "seq_company")
    @Column(name = "id" ,nullable = false)
    Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name" ,nullable = false)
    String name;

    @NotNull
    @Size(max = 255)
    @Column(name = "address" ,nullable = false)
    String address;

    @NotNull
    @Size(max = 10, min = 10)
    @Column(name = "phone" ,nullable = false)
    String phone;

}
