package gr.knowledge.internship.vacation.service.dto;

import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class EmployeeProductDTO implements Serializable {

    Long id;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EmployeeDTO employee;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ProductDTO product;
}
